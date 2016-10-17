package com.example.septiawanajip.printnet.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Septiawan Aji P on 10/14/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAMA = "kemon_learning";

    private static final String TABLE_CATATAN = "table_catatan";
    private static final String ID_CATATAN = "id_catatan";
    private static final String CLICK = "click";

    public DatabaseHandler(Context context){
        super(context,DATABASE_NAMA,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CATATAN_TABLE = "CREATE TABLE "+ TABLE_CATATAN +" ("
                +ID_CATATAN+" INTEGER PRIMARY KEY,"
                +CLICK+" INTEGER)";
        db.execSQL(CREATE_CATATAN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CATATAN);
        onCreate(db);
    }

    public void insertKodeMatkul(int idCatatan){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_CATATAN,idCatatan);
        values.put(CLICK,"0");
        db.insert(TABLE_CATATAN,null,values);
        db.close();
    }

    public String cekCatatan(int id){
        String kosong="";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c  = db.rawQuery("SELECT " + CLICK + " FROM " + TABLE_CATATAN+" WHERE "+ID_CATATAN+"='"+id+"'" , null);

        if(c!=null && c.moveToFirst()){
            kosong="ada";
        }
        return kosong;
    }


    public void afterClickCatatan(int idCatatan,int click){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_CATATAN,idCatatan);
        values.put(CLICK,click);
        db.insert(TABLE_CATATAN,null,values);
        db.close();
    }

    public String getStatusClick(int idCatatan){
        String kosong="";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c  = db.rawQuery("SELECT " + CLICK + " FROM " + TABLE_CATATAN +" WHERE "+ID_CATATAN+"='"+ idCatatan+"'", null);

        String click;
        if(c!=null && c.moveToFirst()){
            kosong="1";
        }
        return kosong;
    }


}
