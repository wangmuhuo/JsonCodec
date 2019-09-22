package com.jejay.android.jsoncodec.codecer;

import org.json.JSONArray;
import org.json.JSONException;

public class StringDecoder implements IDecoder {

    public static String decode(String jsonStr) {
        android.util.Log.d(StringDecoder.class.getName(), "decode " + jsonStr);
        return jsonStr;
    }

    public static String[] decodeArray(String jsonStr) {
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            String[] result = new String[jsonArray.length()];
            for (int i = 0, len = result.length; i < len; i++) {
                result[i] = decode(jsonArray.getString(i));
            }
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
