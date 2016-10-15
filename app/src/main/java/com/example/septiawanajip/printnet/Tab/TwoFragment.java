package com.example.septiawanajip.printnet.Tab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.septiawanajip.printnet.Activity.MainActivity;
import com.example.septiawanajip.printnet.R;

import java.util.ArrayList;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_two,container,false);
        RecyclerView rv = (RecyclerView)view.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Aljabar Linear");
        arrayList.add("Pengantar Demografi");
        AdapterList adapter = new AdapterList(getContext(),arrayList);
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        return view;
    }
}
