package com.jejay.android.jsoncodec.sample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.jejay.android.jsoncodec.R;
import com.jejay.android.jsoncodec.output.Employ;
import com.jejay.android.jsoncodec.output.Employees;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String jsonStr1 = "{\n" +
                "            \"firstName\": \"Bill\",\n" +
                "            \"lastName\": \"20\",\n" +
                "            \"age\": \"10\"\n" +
                "        }";

        Employ employ = Employ.decode(jsonStr1);
        Log.d(TAG, employ.toString());

        String jsonStr2 = "{\n" +
                "    \"employees\": [\n" +
                "        {\n" +
                "            \"firstName\": \"Bill\",\n" +
                "            \"lastName\": \"Gates\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"firstName\": \"George\",\n" +
                "            \"lastName\": \"Bush\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"firstName\": \"Thomas\",\n" +
                "            \"lastName\": \"Carter\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        Employees employees = Employees.decode(jsonStr2);
        Log.d(TAG, employees.toString());
    }
}
