package com.example.septiawanajip.printnet.Tab;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.septiawanajip.printnet.Database.DatabaseHandler;
import com.example.septiawanajip.printnet.Object.Catatan;
import com.example.septiawanajip.printnet.R;

import java.util.ArrayList;

/**
 * Created by Septiawan Aji P on 10/18/2016.
 */
public class TersimpanFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.file_download_list,container,false);
        DatabaseHandler db = new DatabaseHandler(getContext());
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Gaitera_Ball-demo-FFP.ttf");
        ArrayList<Catatan> arrayList = db.getKeteranganTersimpan();
        TextView tv = (TextView)view.findViewById(R.id.belum);
        tv.setTypeface(type);
        if(arrayList.isEmpty()){
            tv.setVisibility(View.VISIBLE);
        }else{
            tv.setVisibility(View.INVISIBLE);
        }
        Log.d("ukuran",Integer.toString(arrayList.size()));


        ListView lv = (ListView)view.findViewById(R.id.list_view_file_download);
        AdapterFileTersimpan adapterFile = new AdapterFileTersimpan(getContext(),R.layout.list_file_download,arrayList,type);
        lv.setAdapter(adapterFile);
        return view;
    }


}
