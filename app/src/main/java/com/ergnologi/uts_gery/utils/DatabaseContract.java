package com.ergnologi.uts_gery.utils;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_NAME = "mahasiswa";

    static final class MahasiswaColumns implements BaseColumns {
        static String NIM = "NIM";
        static String NAMA = "NAMA";
        static String KELAS = "KELAS";
        static String PRODI = "PRODI";
    }
}
