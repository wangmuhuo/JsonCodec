package com.jejay.android.jsoncodec.codecer;

import org.json.JSONArray;
import org.json.JSONException;

public class BooleanDecoder implements IDecoder {

    public static boolean decode(String jsonStr) {
        android.util.Log.d(BooleanDecoder.class.getName(), "decode " + jsonStr);
        return "true".equals(jsonStr) || "1".equals(jsonStr);
    }

    public static boolean[] decodeArray(String jsonStr) {
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            boolean[] result = new boolean[jsonArray.length()];
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
