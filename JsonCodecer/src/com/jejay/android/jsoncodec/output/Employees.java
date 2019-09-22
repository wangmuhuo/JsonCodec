package com.jejay.android.jsoncodec.output;

import com.jejay.android.jsoncodec.codecer.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/** 
 * Automatically generated file and should not be edited. 
 * you can see {@link com.jejay.android.jsoncodec.xmlparse.JsonMain}
 */
public class Employees implements IDecoder{
    public Employ[] fn;

    public Employees () {
    }

    public static Employees decode(String jsonStr) {
        android.util.Log.d(Employees.class.getName(), "decode " + jsonStr);
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            Employees result = new Employees();
            result.fn = Employ.decodeArray(jsonObject.getString("employees"));
            return result;
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Employees[] decodeArray(String jsonStr) {
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            Employees[] result = new Employees[jsonArray.length()];
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
                "fn = " + java.util.Arrays.toString(fn) + 
                '}';
    }

}
