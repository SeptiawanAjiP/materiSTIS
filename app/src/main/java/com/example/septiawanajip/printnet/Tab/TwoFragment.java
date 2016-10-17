package com.example.septiawanajip.printnet.Tab;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.septiawanajip.printnet.Activity.MainActivity;
import com.example.septiawanajip.printnet.Object.AtributeName;
import com.example.septiawanajip.printnet.Object.Matkul;
import com.example.septiawanajip.printnet.R;
import com.example.septiawanajip.printnet.ServerConfiguration.ConvertParameter;
import com.example.septiawanajip.printnet.ServerConfiguration.EndpointAPI;
import com.example.septiawanajip.printnet.ServerConfiguration.JSONParser;
import com.example.septiawanajip.printnet.ServerConfiguration.MethodAPI;
import com.example.septiawanajip.printnet.ServerConfiguration.ResponServer;
import com.example.septiawanajip.printnet.SessionManeger.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Septiawan Aji P on 10/13/2016.
 */
public class TwoFragment extends Fragment {
    private ImageView mulaiPrint;
    public TwoFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_two,container,false);



        new getMatkul().execute();
        return view;
    }


    class getMatkul extends AsyncTask<String,Void,String> {
        JSONObject json;
        JSONArray data;
        Matkul matkul;
        ArrayList<String> matkulAtribut;
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
            matkul = new Matkul();
            arrayList = new ArrayList<>();
            HashMap<String,String> parameter = new HashMap<>();
            SessionManager sm = new SessionManager(getContext());

            parameter.put(MethodAPI.getKODE(),MethodAPI.getGetMatkul());
            parameter.put(AtributeName.getTINGKAT(),sm.getTingkat());
            parameter.put(AtributeName.getJURUSAN(),sm.getJurusan());
            Log.d("Fra Parameter",parameter.toString());



            try{
                json = jsonParser.getJSONFromUrl(EndpointAPI.serverCRUD+ ConvertParameter.getQuery(parameter));
                data = json.getJSONArray(ResponServer.getRESPON());
                Log.d("Data",data.toString());
                    for(int i = 0;i<data.length();i++) {
                        JSONObject c = data.getJSONObject(i);
                        matkulAtribut = new ArrayList<>();

                        matkul.setIdMatkul(c.getString(AtributeName.getIdMatkul()));
                        matkul.setNamaMatkul(c.getString(AtributeName.getNamaMatkul()));
                        matkul.setTotalCatatan(c.getString(AtributeName.getTotalCatatan()));


                        matkulAtribut.add(matkul.getNamaMatkul());
                        matkulAtribut.add(matkul.getTotalCatatan());
                        matkulAtribut.add(matkul.getIdMatkul());

                        arrayList.add(matkulAtribut);
                        Log.d("Matkul",Integer.toString(arrayList.size()));
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
            Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Gaitera_Ball-demo-FFP.ttf");

            ListView lv = (ListView)getView().findViewById(R.id.list_view_matkul);
            AdapterListView adapterListView = new AdapterListView(getContext(),R.layout.list_matkul,arrayList,type);
            lv.setAdapter(adapterListView);


//            pDialog.dismiss();

        }
    }
    public boolean adaKoneksi() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
