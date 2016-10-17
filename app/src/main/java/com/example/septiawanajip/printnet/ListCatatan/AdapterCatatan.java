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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.septiawanajip.printnet.ImageLoader.ImageLoader;
import com.example.septiawanajip.printnet.R;
import com.example.septiawanajip.printnet.Tab.WebViewActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Septiawan Aji P on 10/17/2016.
 */
public class AdapterCatatan extends ArrayAdapter {
    Context context;
    ArrayList<ArrayList> arrayList;
    ArrayList<String> catatan ;
    LayoutInflater inflater;
    Typeface type;



    public AdapterCatatan(Context context,int layoutResourceId,ArrayList<ArrayList> arrayList,Typeface type){
        super(context,layoutResourceId,arrayList);
        this.context = context;
        this.arrayList = arrayList;
        this.type = type;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        TextView namaPenulis,totalDownload,size;
        ImageView simpanFile,lihatFile;
        final ImageView fotoPenulis,iconNew;
        ImageLoader imageLoader = new ImageLoader(context);




        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.list_catatan,parent,false);

        fotoPenulis = (ImageView)itemView.findViewById(R.id.foto_penulis);
        simpanFile = (ImageView)itemView.findViewById(R.id.simpan_file);

        lihatFile = (ImageView)itemView.findViewById(R.id.lihat_file);
        namaPenulis = (TextView)itemView.findViewById(R.id.nama_penulis);
        totalDownload = (TextView)itemView.findViewById(R.id.total_download);
        size = (TextView)itemView.findViewById(R.id.size);

        namaPenulis.setTypeface(type);
        totalDownload.setTypeface(type);
        size.setTypeface(type);

        catatan = new ArrayList<>();
        catatan = arrayList.get(position);

        Log.d("catatan array",catatan.toString());
        imageLoader.DisplayImage(catatan.get(5),fotoPenulis);
        namaPenulis.setText(catatan.get(4));
        totalDownload.setText(catatan.get(3));
        size.setText(catatan.get(1));


        final String penulis = catatan.get(4);
        lihatFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,WebViewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        simpanFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadFileFromURL().execute();
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

    class DownloadFileFromURL extends AsyncTask<String, String, String> {
        ProgressDialog pDialog;
        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ListCatatanActivity.this);
            pDialog.setMessage("Download Materi. Tunggu sebentar ...");
            pDialog.setIndeterminate(false);
            pDialog.setMax(100);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setCancelable(true);
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

                OutputStream output = new FileOutputStream("/sdcard/Komnet/ab.pdf");

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
            pDialog.dismiss();
            Toast.makeText(context, "Materi Sudah Terdownload", Toast.LENGTH_SHORT).show();

        }

    }



}
