package com.example.testkasirpintar.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.io.IOException;
import java.util.ArrayList;

public class DBBarang extends SQLiteOpenHelper {
    private static final String barangDaBase = "guntur.db";
    private static final int VersiDatabase=  2;
    private static final String NamaTabel = "Barang";

    static abstract class MyColumns implements BaseColumns{
        static final String NamaTabel = "Barang";
        static final String IdBarang = "Id_Barang";
        static final String KodeBarang = "Kode_Barang";
        static final String NamaBarang = "Nama_Barang";
        static final String Stok = "Stok";
    }

//    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE"+MyColumns.NamaTabel
//            +"("+MyColumns.IdBarang+" INTEGER PRIMARY KEY, "+MyColumns.KodeBarang+

    public DBBarang(Context context){
        super(context, barangDaBase, null, VersiDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(" create table " +NamaTabel+
                    "(id_barang INTEGER PRIMARY KEY, kode_barang text NOT NULL, " +
                    "nama_barang text NOT NULL, stok INTEGER NOT NULL )");
            sqLiteDatabase.execSQL("CREATE UNIQUE INDEX id_kode_barang ON barangDaBase (kode_barang);");
        } catch (SQLiteException e) {
            try {
                throw new IOException(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NamaTabel);
        onCreate(sqLiteDatabase);
    }

    public boolean insert(String s, String s1, String s2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama_barang", s);
        contentValues.put("kode_barang", s1);
        contentValues.put("stok", s2);
//        contentValues.put("stok", s3);
        db.insert(NamaTabel, null, contentValues);
        return true;
    }

    @SuppressLint("Range")
    public ArrayList getAllBarang() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res = db.rawQuery("select (id_barang ||' : '||trim(kode_barang) || ' : ' || nama_barang || ' : '|| stok) as fullname from " + NamaTabel, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            if ((res != null) && (res.getCount() > 0))
                array_list.add(res.getString(res.getColumnIndex("nama_barang")));
            res.moveToNext();
        }
        return array_list;
    }

    public boolean update(String s, String s1, String s2){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" UPDATE " + NamaTabel + " SET nama_barang = "  + "" + s + ","+ " kode_barang = "  + "" + s1 + "," + "stok = " +""+ s2+"");
        return true;
    }
}
