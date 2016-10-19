package com.example.septiawanajip.printnet.ListCatatan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.septiawanajip.printnet.Database.DatabaseHandler;
import com.example.septiawanajip.printnet.ImageLoader.ImageLoader;
import com.example.septiawanajip.printnet.Object.AtributeName;
import com.example.septiawanajip.printnet.R;
import com.example.septiawanajip.printnet.ServerConfiguration.ConvertParameter;
import com.example.septiawanajip.printnet.ServerConfiguration.EndpointAPI;
import com.example.septiawanajip.printnet.ServerConfiguration.JSONParser;
import com.example.septiawanajip.printnet.ServerConfiguration.MethodAPI;
import com.example.septiawanajip.printnet.ServerConfiguration.ResponServer;
import com.example.septiawanajip.printnet.Tab.WebViewActivity;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Septiawan Aji P on 10/17/2016.
 */
public class AdapterCatatan extends ArrayAdapter {
    Context context;
    ArrayList<ArrayList> arrayList;
    ArrayList<String> catatan ;
    LayoutInflater inflater;
    Typeface type;
    String idCatatan,baca,download;
    String namaMatkul,nmPenulis,pathFileServer;


//    private static String file_url = "http://iyasayang.esy.es/ab.pdf";

    public AdapterCatatan(Context context,int layoutResourceId,String namaMatkul,ArrayList<ArrayList> arrayList,Typeface type){
        super(context,layoutResourceId,arrayList);
        this.context = context;
        this.namaMatkul = namaMatkul;
        this.arrayList = arrayList;
        this.type = type;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        final TextView namaPenulis,totalDownload,size,totalBaca;
        final ImageView simpanFile,lihatFile;
        final ImageView fotoPenulis;
        ImageLoader imageLoader = new ImageLoader(context);

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.list_catatan,parent,false);

        fotoPenulis = (ImageView)itemView.findViewById(R.id.foto_penulis);
        simpanFile = (ImageView)itemView.findViewById(R.id.simpan_file);

        lihatFile = (ImageView)itemView.findViewById(R.id.lihat_file);
        namaPenulis = (TextView)itemView.findViewById(R.id.nama_penulis);
        totalDownload = (TextView)itemView.findViewById(R.id.total_download);
        size = (TextView)itemView.findViewById(R.id.size);
        totalBaca = (TextView)itemView.findViewById(R.id.total_baca);

        namaPenulis.setTypeface(type);
        totalDownload.setTypeface(type);
        size.setTypeface(type);
        totalBaca.setTypeface(type);

        catatan = new ArrayList<>();
        catatan = arrayList.get(position);
        idCatatan = catatan.get(0);
        baca = catatan.get(6);
        download = catatan.get(3);
        nmPenulis = catatan.get(4);
        pathFileServer = catatan.get(2);


        Log.d("catatan array",catatan.toString());
        imageLoader.DisplayImage(catatan.get(5),fotoPenulis);
        namaPenulis.setText("penulis :"+ catatan.get(4));
        totalDownload.setText(download);
        size.setText(catatan.get(1));
        totalBaca.setText(baca);


        final String penulis = catatan.get(4);


        final DatabaseHandler databaseHandler = new DatabaseHandler(context);
        final String path = databaseHandler.getPathFile(Integer.parseInt(idCatatan));

            simpanFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("asem",pathFileServer);

                    if(path.equals("0")){
                        String as = "/sdcard/Komnet/"+pathFileServer.replace("http://komputasi.net/kemon_learning/file_pdf/","");
                        databaseHandler.insertKodeMatkul(Integer.parseInt(idCatatan),nmPenulis,namaMatkul,as);
                        totalDownload.setText(Integer.toString(Integer.parseInt(download)+1));
                            new DownloadFileFromURL().execute(pathFileServer);

                    }else{
                    Toast.makeText(context, "File telah di download", Toast.LENGTH_SHORT).show();
                    }

                }
            });



        lihatFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalBaca.setText(Integer.toString(Integer.parseInt(baca)+1));
                new updateBaca().execute();
                Intent intent = new Intent(context,WebViewActivity.class);
                intent.putExtra("filePath",pathFileServer);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, penulis, Toast.LENGTH_SHORT).show();
//            }
//        });

        return itemView;
    }

//    @Override
//    protected Dialog onCreateDialog(int id) {
//        switch (id) {
//            case progress_bar_type: // we set this to 0
//                pDialog = new ProgressDialog(this);
//                pDialog.setMessage("Download Materi. Tunggu sebentar ...");
//                pDialog.setIndeterminate(false);
//                pDialog.setMax(100);
//                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                pDialog.setCancelable(true);
//                pDialog.show();
//                return pDialog;
//            default:
//                return null;
//        }
//    }
    class updateBaca extends AsyncTask<String,String,String> {
        JSONParser jsonParser = new JSONParser();
        JSONObject json;

        @Override
        protected String doInBackground(String... params) {
            try {
                HashMap<String, String> parameter = new HashMap<>();
                parameter.put(MethodAPI.getKODE(), MethodAPI.getUpdateBaca());
                parameter.put(AtributeName.getIdCatatan(), idCatatan);
                json = jsonParser.getJSONFromUrl(EndpointAPI.serverCRUD + ConvertParameter.getQuery(parameter));
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());

            }return null;
        }
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {
        ProgressDialog pDialog;
        JSONParser jsonParser = new JSONParser();
        JSONObject json;
        DatabaseHandler db = new DatabaseHandler(context);
        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Download Materi. Tunggu sebentar ...");
            pDialog.setIndeterminate(false);
            pDialog.setMax(100);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setCancelable(false);
            pDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            pDialog.show();
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                int lenghtOfFile = conection.getContentLength();


                File kemonFolder = new File("/sdcard/Komnet/");
                if(!kemonFolder.exists()){
                    kemonFolder.mkdirs();
                    Log.d("onPre","Buat FOlder");
                }
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                OutputStream output = new FileOutputStream("/sdcard/Komnet/"+pathFileServer.replace("http://komputasi.net/kemon_learning/file_pdf/",""));
                Log.d("pathFileSD","/sdcard/Komnet/"+pathFileServer.replace("http://komputasi.net/kemon_learning/file_pdf/",""));
                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress(""+(int)((total*100)/lenghtOfFile));
                    output.write(data, 0, count);
                }


                output.flush();

                output.close();
                input.close();
                HashMap<String,String> parameter = new HashMap<>();
                parameter.put(MethodAPI.getKODE(),MethodAPI.getUpdateDownload());
                parameter.put(AtributeName.getIdCatatan(),idCatatan);
                json = jsonParser.getJSONFromUrl(EndpointAPI.serverCRUD+ ConvertParameter.getQuery(parameter));


            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        protected void onProgressUpdate(String... progress) {
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            //id_catatan,judul,nama user, nama matkul, path sd


            pDialog.dismiss();
            Toast.makeText(context, "Selesai,lihat di menu Tersimpan", Toast.LENGTH_SHORT).show();

        }

    }



}
