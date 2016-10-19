package com.example.septiawanajip.printnet.ListCatatan;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.septiawanajip.printnet.Object.AtributeName;
import com.example.septiawanajip.printnet.Object.Catatan;
import com.example.septiawanajip.printnet.R;
import com.example.septiawanajip.printnet.ServerConfiguration.ConvertParameter;
import com.example.septiawanajip.printnet.ServerConfiguration.EndpointAPI;
import com.example.septiawanajip.printnet.ServerConfiguration.JSONParser;
import com.example.septiawanajip.printnet.ServerConfiguration.MethodAPI;
import com.example.septiawanajip.printnet.ServerConfiguration.ResponServer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Septiawan Aji P on 10/17/2016.
 */
public class ListCatatanActivity extends AppCompatActivity  {
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;
    String idMatkul;
    String namaMatkul;
    ListView lv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catatan_list);
        idMatkul = getIntent().getStringExtra(AtributeName.getIdMatkul());
        namaMatkul = getIntent().getStringExtra(AtributeName.getNamaMatkul());
        Log.d("prett",namaMatkul);
        setTitle(namaMatkul);
        lv = (ListView)findViewById(R.id.list_view_catatan);
        new getCatatan().execute();
    }

    class getCatatan extends AsyncTask<String,Void,String> {
        JSONObject json;
        JSONArray data;
        Catatan catatan;
        ArrayList<String> catatanAtribut;
        ArrayList<ArrayList> arrayList;

        String tangkapError = "";
        @Override
        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(getContext());
//            pDialog.setMessage("Loading");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d("Do", "inbackghcgj");
            JSONParser jsonParser = new JSONParser();
            catatan = new Catatan();
            arrayList = new ArrayList<>();
            HashMap<String,String> parameter = new HashMap<>();

            parameter.put(MethodAPI.getKODE(),MethodAPI.getGetCatatan());
            parameter.put(AtributeName.getIdMatkul(),idMatkul);
            Log.d("Fra Parameter",parameter.toString());
            try{
                json = jsonParser.getJSONFromUrl(EndpointAPI.serverCRUD+ ConvertParameter.getQuery(parameter));
                data = json.getJSONArray(ResponServer.getRESPON());
                Log.d("Data",data.toString());
                for(int i = 0;i<data.length();i++) {
                    JSONObject c = data.getJSONObject(i);
                    catatanAtribut = new ArrayList<>();

                    catatan.setIdCatatan(c.getString(AtributeName.getIdCatatan()));
                    catatan.setFileSize(c.getString(AtributeName.getFileSize()));
                    catatan.setPathFile(c.getString(AtributeName.getPathFile()));
                    catatan.setDownload(c.getString(AtributeName.getDOWNLOAD()));
                    catatan.setNama(c.getString(AtributeName.getNAMA()));
                    catatan.setPath_foto(c.getString(AtributeName.getPathFoto()));
                    catatan.setBaca(c.getString(AtributeName.getBACA()));

                    catatanAtribut.add(catatan.getIdCatatan());
                    catatanAtribut.add(catatan.getFileSize());
                    catatanAtribut.add(catatan.getPathFile());
                    catatanAtribut.add(catatan.getDownload());
                    catatanAtribut.add(catatan.getNama());
                    catatanAtribut.add(catatan.getPath_foto());
                    catatanAtribut.add(catatan.getBaca());

                    arrayList.add(catatanAtribut);
                    Log.d("yes",Integer.toString(arrayList.size()));
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
            Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Gaitera_Ball-demo-FFP.ttf");

            AdapterCatatan adapterCatatan= new AdapterCatatan(getApplicationContext(),R.layout.catatan_list,namaMatkul, arrayList,type);
            lv.setAdapter(adapterCatatan);
        }
    }
    public boolean adaKoneksi() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }



}
