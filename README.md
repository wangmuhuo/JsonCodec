#说明：<br>
1、基于用于android json编解码的一套java bean生成工具；<br>
2、使用xml配置java bean属性，自动生成java代码；<br>
3、自动的java代码是基于 org.json 完成json解析的，所以需要有 org.json；<br>
<br>
#使用步骤：<br>
1、通过 json_bean.xml 配置java bean 相关属性；<br>
 json_bean.xml的使用注意点：<br>
 a、包含三种标签：root、bean、attr：<br>
<table>
<tr>
    <th>标签名称</th>
    <th>含义</th>
</tr>
<tr>
    <th>root</th>
    <th>跟标签标识</th>
</tr>
<tr>
    <th>bean</th>
    <th>java bean 标识</th>
</tr>
<tr>
    <th>attr</th>
    <th>java bean 属性标识</th>
</tr>
</table>
 b、属性及其含义：<br>
<table>
<tr>
    <th>属性</th>
    <th>所在标签</th>
    <th>含义</th>
    <th>是否必须值</th>
</tr>
<tr>
    <th>name</th>
    <th>bean</th>
    <th>java bean 的名称，也是对应的java类名</th>
    <th>是</th>
</tr>
<tr>
    <th>name</th>
    <th>attr</th>
    <th>该属性在json中的名称</th>
    <th>是</th>
</tr>
<tr>
    <th>alias</th>
    <th>attr</th>
    <th>该属性在java bean中的名称，没有配置默认使用name</th>
    <th>否</th>
</tr>
<tr>
    <th>type</th>
    <th>attr</th>
    <th>该属性的类型，同java的类型，如int,boolean,String,int[],String[]等</th>
    <th>是</th>
</tr>
<tr>
    <th>nullable</th>
    <th>attr</th>
    <th>改属性是否可空</th>
    <th>否</th>
</tr>
</table>
 <br>
2、进入JsonMain.java文件，修改输出的路径和输出类的包名，修改位置如下两个变量值：<br>
   String outDir = "JsonCodecer\\src\\com\\jejay\\android\\jsoncodec\\output";<br>
   String packageName = "com.jejay.android.jsoncodec.output";<br>
   <br>
3、运行JsonMain.java的main方法，就可以在目标输出路径自动生成java代码；<br>
<br>
4、使用者通过生成的java bean的 decode(String jsonStr)方法参数传入 json字符串就能解析出目标对象；<br>
