package com.example.septiawanajip.printnet.Object;

/**
 * Created by Septiawan Aji P on 10/12/2016.
 */
public class AtributeName {
    //user
    private static final String SESSION = "session";
    private static final String NIM = "nim";
    private static final String NAMA = "nama";
    private static final String PATH_FOTO = "path_foto";
    private static final String JURUSAN = "jurusan";
    private static final String KELAS = "kelas";
    private static final String TINGKAT = "tingkat";
    private static final String PASSWORD = "password";

    //matkul
    private static final String ID_MATKUL = "id_matkul";
    private static final String NAMA_MATKUL = "nama_matkul";
    private static final String TOTAL_CATATAN = "total_catatan";

    //catatan
    private static final String ID_CATATAN = "id_catatan";
    private static final String FILE_SIZE = "file_size";
    private static final String PATH_FILE = "path_file";
    private static final String DOWNLOAD = "download";


    public static String getSESSION() {
        return SESSION;
    }

    public static String getNIM() {
        return NIM;
    }

    public static String getNAMA() {
        return NAMA;
    }

    public static String getPathFoto() {
        return PATH_FOTO;
    }

    public static String getJURUSAN() {
        return JURUSAN;
    }

    public static String getKELAS() {
        return KELAS;
    }

    public static String getTINGKAT() {
        return TINGKAT;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static String getIdMatkul() {
        return ID_MATKUL;
    }

    public static String getNamaMatkul() {
        return NAMA_MATKUL;
    }

    public static String getTotalCatatan() {
        return TOTAL_CATATAN;
    }

    public static String getIdCatatan() {
        return ID_CATATAN;
    }

    public static String getFileSize() {
        return FILE_SIZE;
    }

    public static String getPathFile() {
        return PATH_FILE;
    }

    public static String getDOWNLOAD() {
        return DOWNLOAD;
    }
}
