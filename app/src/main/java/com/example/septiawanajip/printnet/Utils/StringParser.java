package com.example.septiawanajip.printnet.Utils;

import com.example.septiawanajip.printnet.ServerConfiguration.Template;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Septiawan Aji P on 10/9/2016.
 */
public class StringParser {

    public static String getCode(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            return String.valueOf(jsonObject.getInt(Template.Query.KEY_CODE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getMessage(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getString(Template.Query.KEY_MESSAGE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
}
