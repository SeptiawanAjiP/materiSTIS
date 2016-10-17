package com.example.septiawanajip.printnet.Tab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.septiawanajip.printnet.ImageLoader.ImageLoader;
import com.example.septiawanajip.printnet.Object.AtributeName;
import com.example.septiawanajip.printnet.R;
import com.example.septiawanajip.printnet.SessionManeger.SessionManager;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;

/**
 * Created by Septiawan Aji P on 10/16/2016.
 */
public class ProfilFragment extends Fragment {
    RoundedImageView fotoProfil;
    TextView nama,kelas,judul;
    ImageLoader imgLoad;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profil,container,false);
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Gaitera_Ball-demo-FFP.ttf");
        fotoProfil = (RoundedImageView)view.findViewById(R.id.foto_profil);
        fotoProfil.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imgLoad = new ImageLoader(getContext());



        nama = (TextView) view.findViewById(R.id.nama_profil);
        kelas = (TextView) view.findViewById(R.id.kelas_profil);

        HashMap<String,String> hm = new HashMap<>();
        SessionManager sm = new SessionManager(getContext());
        hm = sm.getUserSession();
        imgLoad.DisplayImage(sm.getPathFoto(),fotoProfil);
        nama.setText(hm.get(AtributeName.getNAMA()));
        kelas.setText(sm.getKelas());

        nama.setTypeface(type);
        kelas.setTypeface(type);

        return view;
    }
}
