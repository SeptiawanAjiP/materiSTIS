package com.example.septiawanajip.printnet.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.septiawanajip.printnet.Object.Catatan;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Septiawan Aji P on 10/14/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAMA = "kemon_learning";

    private static final String TABLE_CATATAN = "table_catatan";
    private static final String ID_CATATAN = "id_catatan";
//    private static final String JUDUL_CATATAN = "judul_catatan";
    private static final String NAMA_USER = "nama_user";
    private static final String NAMA_MATKUL = "nama_matkul";
    private static final String PATH_SD = "path_sd";

    public DatabaseHandler(Context context){
        super(context,DATABASE_NAMA,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CATATAN_TABLE = "CREATE TABLE "+ TABLE_CATATAN +" ("
                +ID_CATATAN+" INTEGER PRIMARY KEY, "
//                +JUDUL_CATATAN+" TEXT, "
                +NAMA_USER+" TEXT, "
                +NAMA_MATKUL+" TEXT, "
                +PATH_SD+" TEXT)";
        db.execSQL(CREATE_CATATAN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CATATAN);
        onCreate(db);
    }

    public void insertKodeMatkul(int idCatatan,String namaUser,String namaMatkul,String pathSd){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_CATATAN,idCatatan);
//        values.put(JUDUL_CATATAN,"Catatanku");
        values.put(NAMA_USER,namaUser);
        values.put(NAMA_MATKUL,namaMatkul);
        values.put(PATH_SD,pathSd);
        db.insert(TABLE_CATATAN,null,values);
        db.close();
    }

    public String getPathFile(int idCatatan){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+PATH_SD+" FROM "+TABLE_CATATAN+" WHERE "+ID_CATATAN+"='"+idCatatan+"'";
        Cursor c = db.rawQuery(query,null);
        String pathFile="";
        if(c!=null & c.moveToFirst()){
            pathFile=c.getString(0);
        }else{
            pathFile="0";
        }
        return pathFile;
    }

    public ArrayList<Catatan> getKeteranganTersimpan (){
        ArrayList<Catatan> array =new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+NAMA_USER+", "+NAMA_MATKUL+", "+PATH_SD+" FROM "+TABLE_CATATAN;
        Cursor c = db.rawQuery(query,null);
        Catatan catatanSimpan;
        if(c.moveToFirst()) {
            do {
                catatanSimpan = new Catatan();
                catatanSimpan.setNama(c.getString(0));
                catatanSimpan.setNamaMatkul(c.getString(1));
                catatanSimpan.setPathFile(c.getString(2));


                array.add(catatanSimpan);
            } while (c.moveToNext());
        }
        return array;
    }

//    public String cekCatatan(int id){
//        String kosong="";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c  = db.rawQuery("SELECT " + CLICK + " FROM " + TABLE_CATATAN+" WHERE "+ID_CATATAN+"='"+id+"'" , null);
//
//        if(c!=null && c.moveToFirst()){
//            kosong="ada";
//        }
//        return kosong;
//    }
//
//
//    public void afterClickCatatan(int idCatatan,int click){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(ID_CATATAN,idCatatan);
//        values.put(CLICK,click);
//        db.insert(TABLE_CATATAN,null,values);
//        db.close();
//    }
//
//    public String getStatusClick(int idCatatan){
//        String kosong="";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c  = db.rawQuery("SELECT " + CLICK + " FROM " + TABLE_CATATAN +" WHERE "+ID_CATATAN+"='"+ idCatatan+"'", null);
//
//        String click;
//        if(c!=null && c.moveToFirst()){
//            kosong="1";
//        }
//        return kosong;
//    }
//
public void deleteCatatan(){
    SQLiteDatabase db = this.getReadableDatabase();
    db.delete(TABLE_CATATAN,null,null);
}
}
