package com.example.septiawanajip.printnet.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.septiawanajip.printnet.Main;
import com.example.septiawanajip.printnet.Object.AtributeName;
import com.example.septiawanajip.printnet.R;
import com.example.septiawanajip.printnet.SessionManeger.SessionManager;
import com.example.septiawanajip.printnet.Tab.TabMainACtivity;

import java.util.HashMap;

/**
 * Created by Septiawan Aji P on 10/12/2016.
 */
public class SplashScreen extends AppCompatActivity {
    SessionManager sessionManager;
    HashMap<String,String> hm ;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        sessionManager = new SessionManager(getApplicationContext());
        tv = (TextView)findViewById(R.id.komnet);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Gaitera_Ball-demo-FFP.ttf");
        tv.setTypeface(type);
        hm = sessionManager.getUserSession();
        Log.d("HM ",hm.toString());
        Thread splash = new Thread(){
            public void run(){
                try{
                    sleep(2500);
                    if(hm.get(AtributeName.getNAMA())==null){
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent = new Intent(getApplicationContext(),TabMainACtivity.class);
                        startActivity(intent);
                        finish();
                    }

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        splash.start();
    }
}
