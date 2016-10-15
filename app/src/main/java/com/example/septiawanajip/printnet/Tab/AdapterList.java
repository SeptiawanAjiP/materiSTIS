package com.example.septiawanajip.printnet.Tab;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.septiawanajip.printnet.DownloadFile.DownloadFile;
import com.example.septiawanajip.printnet.R;

import java.util.ArrayList;

/**
 * Created by Septiawan Aji P on 10/14/2016.
 */
public class AdapterList extends RecyclerView.Adapter<AdapterList.MyViewHolder> {
    private ArrayList<String> arrayList;
    Context context;

    public static class  MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mTextView;
        public MyViewHolder(View v){
            super(v);

            mCardView = (CardView)v.findViewById(R.id.card_view);
            mTextView = (TextView)v.findViewById(R.id.tv_text);

        }
    }

    public AdapterList(Context context,ArrayList<String> arrayList){
        Log.d("Dataset", Integer.toString(arrayList.size()));
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_matkul, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DownloadFile.class);
                context.startActivity(intent);
            }
        });
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextView.setText(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return  arrayList.size();
    }
}
