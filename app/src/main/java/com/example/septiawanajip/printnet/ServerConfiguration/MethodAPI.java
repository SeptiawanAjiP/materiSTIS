package com.example.septiawanajip.printnet.ServerConfiguration;

/**
 * Created by Septiawan Aji P on 10/16/2016.
 */
public class MethodAPI {
    private static final String KODE = "kode";
    private static final String GET_STATUS_LOGIN = "getStatusLogin";
    private static final String GET_NAMA = "getNama";
    private static final String DAFTAR_USER = "daftarUser";
    private static final String GET_MATKUL = "getMatkul";
    private static final String GET_CATATAN = "getCatatan";
    private static final String GET_DETAIL_CATATAN = "getDetailCatatan";
    private static final String INSERT_DOWNLOAD = "insertDownload";
    private static final String INSERT_MEMBACA = "insertMembaca";

    public static String getGetStatusLogin() {
        return GET_STATUS_LOGIN;
    }

    public static String getGetNama() {
        return GET_NAMA;
    }

    public static String getDaftarUser() {
        return DAFTAR_USER;
    }

    public static String getGetMatkul() {
        return GET_MATKUL;
    }

    public static String getGetCatatan() {
        return GET_CATATAN;
    }

    public static String getInsertDownload() {
        return INSERT_DOWNLOAD;
    }

    public static String getInsertMembaca() {
        return INSERT_MEMBACA;
    }

    public static String getGetDetailCatatan() {
        return GET_DETAIL_CATATAN;
    }

    public static String getKODE() {
        return KODE;
    }
}
