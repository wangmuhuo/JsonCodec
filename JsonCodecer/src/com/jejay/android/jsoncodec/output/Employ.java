package com.jejay.android.jsoncodec.output;

import com.jejay.android.jsoncodec.codecer.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/** 
 * Automatically generated file and should not be edited. 
 * you can see {@link com.jejay.android.jsoncodec.xmlparse.JsonMain}
 */
public class Employ implements IDecoder{
    public String fn;
    public String ln;
    public int age;
    public int[] other;

    public Employ () {
    }

    public static Employ decode(String jsonStr) {
        android.util.Log.d(Employ.class.getName(), "decode " + jsonStr);
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            Employ result = new Employ();
            result.fn = StringDecoder.decode(jsonObject.getString("firstName"));
            result.ln = StringDecoder.decode(jsonObject.getString("lastName"));
            try {
                result.age = IntDecoder.decode(jsonObject.getString("age"));
            }catch (JSONException e) {
            }
            try {
                result.other = IntDecoder.decodeArray(jsonObject.getString("other"));
            }catch (JSONException e) {
            }
            return result;
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Employ[] decodeArray(String jsonStr) {
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            Employ[] result = new Employ[jsonArray.length()];
            for (int i = 0, len = result.length; i < len; i++) {
                result[i] = decode(jsonArray.getString(i));
            }
            return result;
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "{" +
                "fn = " + fn + 
                ", ln = " + ln + 
                ", age = " + age + 
                ", other = " + java.util.Arrays.toString(other) + 
                '}';
    }

}
