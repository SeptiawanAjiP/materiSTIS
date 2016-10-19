package com.example.septiawanajip.printnet.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioTrack;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.septiawanajip.printnet.Object.AtributeName;
import com.example.septiawanajip.printnet.Object.User;
import com.example.septiawanajip.printnet.R;
import com.example.septiawanajip.printnet.ServerConfiguration.ConvertParameter;
import com.example.septiawanajip.printnet.ServerConfiguration.EndpointAPI;
import com.example.septiawanajip.printnet.ServerConfiguration.JSONParser;
import com.example.septiawanajip.printnet.ServerConfiguration.MethodAPI;
import com.example.septiawanajip.printnet.ServerConfiguration.ResponServer;
import com.example.septiawanajip.printnet.SessionManeger.SessionManager;
import com.example.septiawanajip.printnet.Tab.TabMainACtivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Septiawan Aji P on 10/12/2016.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText nimEditText,passwordEditText;
    private Button loginButton;
    private TextView daftarTv;
    User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        user = new User();
        nimEditText = (EditText)findViewById(R.id.input_nim);
        passwordEditText = (EditText)findViewById(R.id.input_password);
        daftarTv = (TextView)findViewById(R.id.daftarTv);
        loginButton = (Button)findViewById(R.id.login);

        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Gaitera_Ball-demo-FFP.ttf");
        daftarTv.setTypeface(type);
        nimEditText.setTypeface(type);
        passwordEditText.setTypeface(type);
        loginButton.setTypeface(type);

        daftarTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Daftar di kemon.komputasi.net ", Toast.LENGTH_SHORT).show();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setNim(nimEditText.getText().toString());
                user.setPassword(passwordEditText.getText().toString());
                if(user.getNim().isEmpty()|| user.getPassword().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Masukan nim dan password dengan benar", Toast.LENGTH_SHORT).show();
                }else{
                    if(adaKoneksi()){
                        new getStatusLogin().execute();
                    }else{
                        Toast.makeText(getApplicationContext(), R.string.no_connection, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private class getStatusLogin extends AsyncTask<String,Void,String> {
        ProgressDialog pDialog;
        JSONObject json;
        JSONArray data;

        String tangkapError = "";
        String respon;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Loading");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d("Do", "inback");
            HashMap<String,String> parameter = new HashMap<>();
//            parameter.put(AtributName.getNIM(), user.getNim());
//            parameter.put(AtributName.getKODE(),AtributName.getGetStatusLogin());
//            parameter.put(AtributName.getPASSWORD(), user.getPassword());
            HashMap<String,String> hm = new HashMap<>();
            hm.put(MethodAPI.getKODE(),MethodAPI.getGetStatusLogin());
            hm.put(AtributeName.getNIM(),user.getNim());
            hm.put(AtributeName.getPASSWORD(),user.getPassword());
            Log.d("Parameter", parameter.toString());

            JSONParser jsonParser = new JSONParser();

            try{
                json = jsonParser.getJSONFromUrl(EndpointAPI.serverCRUD+ ConvertParameter.getQuery(hm));
                respon = json.getString(ResponServer.getRESPON());
                Log.d("Respon",respon);
                if(!respon.equals(ResponServer.getNOL())){
                    parameter.remove(MethodAPI.getKODE());
                    parameter.put(MethodAPI.getKODE(),MethodAPI.getGetNama());

                    HashMap<String,String> hm2 = new HashMap<>();
                    hm2.put(MethodAPI.getKODE(),MethodAPI.getGetNama());
                    hm2.put(AtributeName.getNIM(),user.getNim());
                    hm2.put(AtributeName.getPASSWORD(),user.getPassword());

                    json = jsonParser.getJSONFromUrl(EndpointAPI.serverCRUD+ConvertParameter.getQuery(hm2));
                    data = json.getJSONArray(ResponServer.getRESPON());
                    for(int i = 0;i<data.length();i++){
                        JSONObject c = data.getJSONObject(i);
                        user.setNama(c.getString(AtributeName.getNAMA()));
                        user.setPath_foto(c.getString(AtributeName.getPathFoto()));
                        user.setTingkat(c.getString(AtributeName.getTINGKAT()));
                        user.setJurusan(c.getString(AtributeName.getJURUSAN()));
                        user.setKelas(c.getString(AtributeName.getKELAS()));
                        Log.d("user kelas",user.getKelas());
                    }
                }
//
            }catch (Exception e){
                tangkapError = e.getMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            SessionManager sessionManager = new SessionManager(getApplicationContext());
            if (tangkapError == ("")) {
                if(respon.equals("1")){
                    sessionManager.createSessionLogin(user.getNim(), user.getNama());
                    sessionManager.setTingkat(user.getTingkat());
                    sessionManager.setJurusan(user.getJurusan());
                    sessionManager.setKelas(user.getKelas());
                    sessionManager.setPathFoto(user.getPath_foto());


                    Intent intent = new Intent(getApplicationContext(),TabMainACtivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Username dan Password Salah", Toast.LENGTH_SHORT).show();
                }

            }else{
//                Log.e("SERVER ERROR", tangkapError);
                Toast.makeText(LoginActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
            }
            pDialog.dismiss();

        }
    }
    public boolean adaKoneksi() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
