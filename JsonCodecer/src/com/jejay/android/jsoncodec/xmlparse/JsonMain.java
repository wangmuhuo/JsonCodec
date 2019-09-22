package com.jejay.android.jsoncodec.xmlparse;

import com.jejay.android.jsoncodec.codecer.BooleanDecoder;
import com.jejay.android.jsoncodec.codecer.IDecoder;
import com.jejay.android.jsoncodec.codecer.IntDecoder;
import com.jejay.android.jsoncodec.codecer.StringDecoder;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.Closeable;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

public class JsonMain {

    public static void main(String[] args) {
        System.out.println("start  auto gen ......");

        String xmlPath = "JsonCodecer\\src\\com\\jejay\\android\\jsoncodec\\xmlparse\\json_bean.xml";
        String outDir = "JsonCodecer\\src\\com\\jejay\\android\\jsoncodec\\output";
        String packageName = "com.jejay.android.jsoncodec.output";

        System.out.println("will parse xml: " + xmlPath);
        List<Bean> beans = parseXml(xmlPath);
        System.out.println("xml parse end, bean size: " + beans.size());
        Map<String, String> decoder = new HashMap<String, String>() {
            {
                put("int", IntDecoder.class.getSimpleName());
                put("boolean", BooleanDecoder.class.getSimpleName());
                put("String", StringDecoder.class.getSimpleName());
            }

            @Override
            public String put(String key, String value) {
                super.put(key + "[]", value);
                return super.put(key, value);
            }

            @Override
            public String get(Object key) {
                if (containsKey(key)) {
                    return super.get(key);
                } else {
                    return getArrayType((String) key);
                }
            }
        };

        for (Bean bean : beans) {
            createJava(bean, packageName, decoder, outDir);
            System.out.println("create java: <" + bean.name + ">");
        }
        System.out.println("out dir: " + outDir);

        System.out.println("finish auto gen ......");
    }

    private static List<Bean> parseXml(String xmlPath) {
        List<Bean> result = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            Document document = factory.newDocumentBuilder().parse(xmlPath);
            NodeList rootNodes = document.getElementsByTagName("root");
            if (rootNodes.getLength() != 1) {
                throw new RuntimeException("error in xml, root count " + rootNodes.getLength());
            }
            Node rootNode = rootNodes.item(0);
            NodeList beanNodes = rootNode.getChildNodes();
            for (int iBean = 0, iBeanLen = beanNodes.getLength(); iBean < iBeanLen; iBean++) {
                Node beanNode = beanNodes.item(iBean);
                if (beanNode.getNodeName().startsWith("#text")) {
                    continue;
                }
                Bean bean = new Bean(beanNode);
                NodeList attrNodes = beanNode.getChildNodes();
                for (int iAttr = 0, iAttrLen = attrNodes.getLength(); iAttr < iAttrLen; iAttr++) {
                    Node attrNode = attrNodes.item(iAttr);
                    if (attrNode.getNodeName().startsWith("#text")) {
                        continue;
                    }
                    Attr attr = new Attr(attrNode);
                    bean.attrs.add(attr);
                }
                result.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void createJava(Bean bean, String packageName, Map<String, String> decoder, String outDir) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new File(outDir, bean.name + ".java"));
            writer.println("package " + packageName + ";");
            writer.println();
            appendImport(writer);
            appendNotes(writer);
            writer.println("public class " + bean.name + " implements IDecoder{");
            appendFields(writer, bean);
            appendConstructor(writer, bean);
            appendMethodDecode(writer, bean, decoder);
            appendMethodDecodeArray(writer, bean, decoder);
            appendMethodToString(writer, bean);
            writer.println("}");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(writer);
        }
    }

    private static void appendNotes(PrintWriter writer) {
        writer.println("/** ");
        writer.println(" * Automatically generated file and should not be edited. ");
        writer.println(" * you can see {@link " + JsonMain.class.getName() + "}");
        writer.println(" */");
    }

    private static void appendImport(PrintWriter writer) {
        writer.println("import " + IDecoder.class.getPackage().getName() + ".*;");
        writer.println("import org.json.JSONArray;");
        writer.println("import org.json.JSONException;");
        writer.println("import org.json.JSONObject;");
        writer.println();
    }

    private static void appendFields(PrintWriter writer, Bean bean) {
        for (int i = 0, len = bean.attrs.size(); i < len; i++) {
            Attr attr = bean.attrs.get(i);
            writer.println(tab(1) + "public " + attr.type + " " + attr.alias + ";");
        }
        writer.println();
    }

    private static void appendConstructor(PrintWriter writer, Bean bean) {
        writer.println(tab(1) + "public " + bean.name + " () {");
        writer.println(tab(1) + "}");
        writer.println();
    }


    private static void appendMethodDecode(PrintWriter writer, Bean bean, Map<String, String> decoder) {
        writer.println(tab(1) + "public static " + bean.name + " decode(String jsonStr) {");
        writer.println(tab(2) + "android.util.Log.d(" + bean.name + ".class.getName(), \"decode \" + jsonStr);");
        writer.println(tab(2) + "try {");
        writer.println(tab(3) + "JSONObject jsonObject = new JSONObject(jsonStr);");
        writer.println(tab(3) + bean.name + " result = new " + bean.name + "();");
        for (int i = 0, len = bean.attrs.size(); i < len; i++) {
            Attr attr = bean.attrs.get(i);
            String decoderName = decoder.get(attr.type);
            String decoderMethod = attr.type.trim().endsWith("[]") ? "decodeArray" : "decode";
            String outStr = "result." + attr.alias + " = " + decoderName + "." + decoderMethod + "(jsonObject.getString(\"" + attr.name + "\"));";
            if (attr.nullable) {
                writer.println(tab(3) + "try {");
                writer.println(tab(4) + outStr);
                writer.println(tab(3) + "}catch (JSONException e) {");
                writer.println(tab(3) + "}");
            } else {
                writer.println(tab(3) + outStr);
            }
        }
        writer.println(tab(3) + "return result;");
        writer.println(tab(2) + "}catch (JSONException e) {");
        writer.println(tab(3) + "e.printStackTrace();");
        writer.println(tab(2) + "}");
        writer.println(tab(2) + "return null;");
        writer.println(tab(1) + "}");
        writer.println();
    }

    private static void appendMethodDecodeArray(PrintWriter writer, Bean bean, Map<String, String> decoder) {
        writer.println(tab(1) + "public static " + bean.name + "[] decodeArray(String jsonStr) {");
        writer.println(tab(2) + "try {");
        writer.println(tab(3) + "JSONArray jsonArray = new JSONArray(jsonStr);");
        writer.println(tab(3) + bean.name + "[] result = new " + bean.name + "[jsonArray.length()];");
        writer.println(tab(3) + "for (int i = 0, len = result.length; i < len; i++) {");
        writer.println(tab(4) + "result[i] = decode(jsonArray.getString(i));");
        writer.println(tab(3) + "}");
        writer.println(tab(3) + "return result;");
        writer.println(tab(2) + "}catch (JSONException e) {");
        writer.println(tab(3) + "e.printStackTrace();");
        writer.println(tab(2) + "}");
        writer.println(tab(2) + "return null;");
        writer.println(tab(1) + "}");
        writer.println();
    }

    private static void appendMethodToString(PrintWriter writer, Bean bean) {
        writer.println(tab(1) + "@Override");
        writer.println(tab(1) + "public String toString() {");
        writer.println(tab(2) + "return \"{\" +");

        for (int i = 0, len = bean.attrs.size(); i < len; i++) {
            Attr attr = bean.attrs.get(i);
            writer.print(tab(4) + "\"");
            if (i > 0) {
                writer.print(", ");
            }
            if (attr.type.trim().endsWith("[]")) {
                writer.println(attr.alias + " = \" + java.util.Arrays.toString(" + attr.alias + ") + ");
            } else {
                writer.println(attr.alias + " = \" + " + attr.alias + " + ");
            }
        }
        writer.println(tab(4) + "'}';");
        writer.println(tab(1) + "}");
        writer.println();
    }


    private static String tab(int count) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < count; i++) {
            buffer.append("    ");
        }
        return buffer.toString();
    }

    private static void close(Closeable closeable) {
        try {
            closeable.close();
        } catch (Exception e) {
        }
    }

    private static String getArrayType(String type) {
        type = type.trim();
        if (type.endsWith("[]")) {
            return type.substring(0, type.indexOf("[]"));
        }
        return type;
    }

    static class Bean {
        final String name;
        final List<Attr> attrs = new ArrayList<>();

        Bean(Node node) {
            NamedNodeMap attrs = node.getAttributes();
            name = obtainString(attrs, "name", true, null);
        }
    }

    static class Attr {
        final String name;
        final String type;
        final String alias;
        final boolean nullable;

        Attr(Node node) {
            NamedNodeMap attrs = node.getAttributes();
            name = obtainString(attrs, "name", true, null);
            type = obtainString(attrs, "type", true, null);
            alias = obtainString(attrs, "alias", false, name);
            nullable = obtainBoolean(attrs, "nullable", false, false);
        }
    }

    private static String obtainString(NamedNodeMap attrs, String name, boolean required, String def) {
        Node node = attrs.getNamedItem(name);
        if (node != null) {
            return node.getNodeValue();
        } else if (required) {
            throw new RuntimeException("miss attr " + name);
        } else {
            return def;
        }
    }

    private static boolean obtainBoolean(NamedNodeMap attrs, String name, boolean required, boolean def) {
        String strValue = obtainString(attrs, name, required, null);
        return strValue == null ? def : "true".equals(strValue);
    }
}
