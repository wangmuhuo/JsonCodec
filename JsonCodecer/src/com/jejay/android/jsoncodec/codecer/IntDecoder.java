package com.jejay.android.jsoncodec.codecer;

import org.json.JSONArray;
import org.json.JSONException;

public class IntDecoder implements IDecoder {

    public static int decode(String jsonStr) {
        android.util.Log.d(IntDecoder.class.getName(), "decode " + jsonStr);
        return Integer.parseInt(jsonStr);
    }

    public static int[] decodeArray(String jsonStr) {
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            int[] result = new int[jsonArray.length()];
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
