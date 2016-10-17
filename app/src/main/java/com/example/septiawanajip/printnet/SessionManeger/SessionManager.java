package com.example.septiawanajip.printnet.SessionManeger;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
        editor.commit();
    }

    public void setJurusan (String jurusan){
        editor.putString(AtributeName.getJURUSAN(),jurusan);
        editor.commit();
    }

    public String getJurusan(){
        String jurusan = sharedPreferences.getString(AtributeName.getJURUSAN(),null);
        return jurusan;
    }

    public void setTingkat (String tingkat){
        editor.putString(AtributeName.getTINGKAT(),tingkat);
        editor.commit();
    }

    public String getTingkat(){
        String tingkat = sharedPreferences.getString(AtributeName.getTINGKAT(),null);
        return tingkat;
    }

    public void setKelas(String kelas){
        editor.putString(AtributeName.getKELAS(),kelas);
        editor.commit();
    }

    public String getKelas(){
        String kelas = sharedPreferences.getString(AtributeName.getKELAS(),null);
        return kelas;
    }

    public String getPathFoto(){
        String pathFoto = sharedPreferences.getString(AtributeName.getPathFoto(),null);
        return pathFoto;
    }

    public void setPathFoto (String pathFoto){
        editor.putString(AtributeName.getPathFoto(),pathFoto);
        editor.commit();
    }

    public HashMap<String,String> getUserSession(){
        HashMap<String,String> hm = new HashMap<>();
        hm.put(AtributeName.getNIM(),sharedPreferences.getString(AtributeName.getNIM(),null));
        hm.put(AtributeName.getNAMA(),sharedPreferences.getString(AtributeName.getNAMA(),null));
        return hm;
    }
}
