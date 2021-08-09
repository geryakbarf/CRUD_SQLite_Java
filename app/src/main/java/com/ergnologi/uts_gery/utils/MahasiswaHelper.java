package com.ergnologi.uts_gery.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ergnologi.uts_gery.models.MahasiswaModels;

import java.util.ArrayList;

public class MahasiswaHelper {
    //Ini Class Khusus Untuk DML
    private static final String DATABASE_TABLE = "mahasiswa";
    private static DatabaseHelper dataBaseHelper;
    private static MahasiswaHelper INSTANCE;
    private static SQLiteDatabase database;

    private MahasiswaHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static MahasiswaHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MahasiswaHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<MahasiswaModels> getAllData() {
        ArrayList<MahasiswaModels> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                "NIM" + " ASC",
                null);
        cursor.moveToFirst();
        MahasiswaModels mahasiswaModels;
        if (cursor.getCount() > 0) {
            do {
                mahasiswaModels = new MahasiswaModels();
                mahasiswaModels.setNim(cursor.getString(cursor.getColumnIndexOrThrow("NIM")));
                mahasiswaModels.setNama(cursor.getString(cursor.getColumnIndexOrThrow("NAMA")));
                mahasiswaModels.setKelas(cursor.getString(cursor.getColumnIndexOrThrow("KELAS")));
                mahasiswaModels.setProdi(cursor.getString(cursor.getColumnIndexOrThrow("PRODI")));
                arrayList.add(mahasiswaModels);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long inserData(MahasiswaModels mahasiswaModels) {
        ContentValues args = new ContentValues();
        args.put("NIM", mahasiswaModels.getNim());
        args.put("NAMA", mahasiswaModels.getNama());
        args.put("KELAS", mahasiswaModels.getKelas());
        args.put("PRODI", mahasiswaModels.getProdi());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public long updateData(MahasiswaModels mahasiswaModels) {
        ContentValues args = new ContentValues();
        args.put("NAMA", mahasiswaModels.getNama());
        args.put("KELAS", mahasiswaModels.getKelas());
        args.put("PRODI", mahasiswaModels.getProdi());
        return database.update(DATABASE_TABLE, args, "NIM" + "= '" + mahasiswaModels.getNim() + "'", null);
    }

    public int deleteData(String nim) {
        return database.delete("mahasiswa", "NIM" + " = '" + nim + "'", null);
    }

    public boolean checkNim(String nim) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE NIM ='" + nim + "'", null);
        if (cursor.getCount() < 1) {
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }
    }

}
