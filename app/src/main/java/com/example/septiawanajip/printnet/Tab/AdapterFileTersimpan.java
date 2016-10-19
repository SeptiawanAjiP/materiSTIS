package com.example.septiawanajip.printnet.Tab;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.septiawanajip.printnet.Database.DatabaseHandler;
import com.example.septiawanajip.printnet.DownloadFile.DownloadFile;
import com.example.septiawanajip.printnet.ImageLoader.ImageLoader;
import com.example.septiawanajip.printnet.ListCatatan.ListCatatanActivity;
import com.example.septiawanajip.printnet.Object.AtributeName;
import com.example.septiawanajip.printnet.Object.Catatan;
import com.example.septiawanajip.printnet.R;
import com.example.septiawanajip.printnet.SessionManeger.SessionManager;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Septiawan Aji P on 10/17/2016.
 */
public class AdapterFileTersimpan extends ArrayAdapter {
    Context context;
    ArrayList<Catatan> arrayList;
    Catatan catatan;
    LayoutInflater inflater;
    Typeface type;

    public AdapterFileTersimpan(Context context, int layoutResourceId, ArrayList<Catatan> arrayList, Typeface type){
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
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView namaMatkul,namaPenulis;
        DatabaseHandler db = new DatabaseHandler(context);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.list_file_download,parent,false);
        namaMatkul = (TextView)itemView.findViewById(R.id.matkul_file_simpan);
        namaPenulis = (TextView)itemView.findViewById(R.id.nama_file_simpan);
        namaMatkul.setTypeface(type);
        namaPenulis.setTypeface(type);
        final String matkul,nama,path;

        catatan = arrayList.get(position);
        matkul = catatan.getNamaMatkul();
        nama = catatan.getNama();
        path = catatan.getPathFile();

        namaMatkul.setText(matkul);
        namaPenulis.setText("By " + nama);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tetot",path);
                File file = new File(path);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file),"application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                context.startActivity(intent);
            }
        });

        return itemView;
    }
}
