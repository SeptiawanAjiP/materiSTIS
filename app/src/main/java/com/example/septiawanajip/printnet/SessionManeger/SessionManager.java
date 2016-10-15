package com.example.septiawanajip.printnet.SessionManeger;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.septiawanajip.printnet.Object.AtributeName;
import com.example.septiawanajip.printnet.Object.User;
import com.example.septiawanajip.printnet.Utils.StringParser;

import java.util.HashMap;

/**
 * Created by Septiawan Aji P on 10/12/2016.
 */
public class SessionManager {
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(AtributeName.getSESSION(),Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void createSessionLogin (String nim,String nama){
        editor.putString(AtributeName.getNIM(),nim);
        editor.putString(AtributeName.getNAMA(),nama);
    }

    public HashMap<String,String> getUserSession(){
        HashMap<String,String> hm = new HashMap<>();
        hm.put(AtributeName.getNIM(),sharedPreferences.getString(AtributeName.getNIM(),null));
        hm.put(AtributeName.getNAMA(),sharedPreferences.getString(AtributeName.getNAMA(),null));
        return hm;
    }
}
