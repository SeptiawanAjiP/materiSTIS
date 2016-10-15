package com.example.septiawanajip.printnet.DownloadFile;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.septiawanajip.printnet.R;
import com.example.septiawanajip.printnet.Tab.WebViewActivity;


/**
 * Created by Septiawan Aji P on 10/15/2016.
 */

public class DownloadFile extends AppCompatActivity {

    Button btnShowProgress,baca;
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;

    private static String file_url = "http://iyasayang.esy.es/ab.pdf";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download);

        btnShowProgress = (Button) findViewById(R.id.btnProgressBar);
        baca = (Button)findViewById(R.id.bacaButton);
        baca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WebViewActivity.class);
                startActivity(intent);
            }
        });
        btnShowProgress.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DownloadFileFromURL().execute(file_url);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Download Materi. Tunggu sebentar ...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
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

                File kemonFolder = new File("/sdcard/Kemon/");
                if(!kemonFolder.exists()){
                    kemonFolder.mkdirs();
                    Log.d("onPre","Buat FOlder");
                }
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                OutputStream output = new FileOutputStream("/sdcard/Kemon/ab.pdf");

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
            dismissDialog(progress_bar_type);
            Toast.makeText(getApplicationContext(), "Materi Sudah Terdownload", Toast.LENGTH_SHORT).show();

        }

    }
}