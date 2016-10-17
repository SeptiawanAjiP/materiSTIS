package com.example.septiawanajip.printnet.Tab;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.septiawanajip.printnet.DownloadFile.DownloadFile;
import com.example.septiawanajip.printnet.ImageLoader.ImageLoader;
import com.example.septiawanajip.printnet.ListCatatan.ListCatatanActivity;
import com.example.septiawanajip.printnet.Object.AtributeName;
import com.example.septiawanajip.printnet.R;
import com.example.septiawanajip.printnet.SessionManeger.SessionManager;

import java.util.ArrayList;

/**
 * Created by Septiawan Aji P on 10/17/2016.
 */
public class AdapterListView extends ArrayAdapter {
    Context context;
    ArrayList<ArrayList> arrayList;
    ArrayList<String> matkul = new ArrayList<>();
    LayoutInflater inflater;
    Typeface type;

    public AdapterListView(Context context,int layoutResourceId,ArrayList<ArrayList> arrayList,Typeface type){
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
        TextView namaMatkul,totalCatatan;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.list_matkul,parent,false);
        namaMatkul = (TextView)itemView.findViewById(R.id.tv_text);
        totalCatatan = (TextView)itemView.findViewById(R.id.total_catatan);
        namaMatkul.setTypeface(type);
        totalCatatan.setTypeface(type);

        matkul = arrayList.get(position);
        final String nm = matkul.get(0);
        namaMatkul.setText(nm);
        totalCatatan.setText("Total catatan : "+matkul.get(1));
        final String id = matkul.get(2);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, nm, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), ListCatatanActivity.class);
                intent.putExtra(AtributeName.getIdMatkul(),id);
                intent.putExtra(AtributeName.getNamaMatkul(),nm);
                getContext().startActivity(intent);
            }
        });

        return itemView;
    }
}
