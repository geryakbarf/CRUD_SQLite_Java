package com.ergnologi.uts_gery.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABSAE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_MAHASISWA = String.format("CREATE TABLE %s" +
                    " (%s INTEGER PRIMARY KEY, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL)",
            DatabaseContract.TABLE_NAME,
            DatabaseContract.MahasiswaColumns.NIM,
            DatabaseContract.MahasiswaColumns.NAMA,
            DatabaseContract.MahasiswaColumns.KELAS,
            DatabaseContract.MahasiswaColumns.PRODI);
    public static String DATABASE_NAME = "dbmahasiswa";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABSAE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MAHASISWA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
