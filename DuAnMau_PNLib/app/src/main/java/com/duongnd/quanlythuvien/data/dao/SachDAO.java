package com.duongnd.quanlythuvien.data.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duongnd.quanlythuvien.data.database.Database;
import com.duongnd.quanlythuvien.data.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    private SQLiteDatabase sqLiteDatabase;

    public SachDAO(Context context) {
        Database database = new Database(context);
        sqLiteDatabase = database.getWritableDatabase();
    }


    public long themSach(Sach sach) {
        ContentValues values = new ContentValues();
        values.put("tenSach", sach.getTenSach());
        values.put("maLoai", sach.getMaLoai());
        values.put("giaThue", sach.getGiaThue());
        values.put("namXuatBan", sach.getNamXuatBan());
        return sqLiteDatabase.insert("Sach", null, values);
    }


    public long suaSach(Sach sach) {
        ContentValues values = new ContentValues();
        values.put("tenSach", sach.getTenSach());
        values.put("maLoai", sach.getMaLoai());
        values.put("giaThue", sach.getGiaThue());
        values.put("namXuatBan", sach.getNamXuatBan());
        return sqLiteDatabase.update("Sach", values, "maSach=?", new String[]{String.valueOf(sach.getMaLoai())});
    }

    public int xoaSach(int maSach) {
        return sqLiteDatabase.delete("Sach", "maSach=?", new String[]{String.valueOf(maSach)});
    }

    public Sach getSach(int maSach) {
        String query = "SELECT * FROM Sach WHERE maSach = ?";
        List<Sach> sachList = getData(query, String.valueOf(maSach));
        return sachList.get(0);
    }


    public List<Sach> getAllSach() {
        String query = "SELECT * FROM Sach";
        return getData(query);
    }


    @SuppressLint("Range")
    private List<Sach> getData(String sql, String... selectionArgs) {
        List<Sach> list = new ArrayList<>();
        @SuppressLint("Recycle")
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            Sach sach = new Sach();
            sach.setMaSach(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maSach"))));
            sach.setMaLoai(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maLoai"))));
            sach.setTenSach(cursor.getString(cursor.getColumnIndex("tenSach")));
            sach.setGiaThue(Integer.parseInt(cursor.getString(cursor.getColumnIndex("giaThue"))));
            sach.setNamXuatBan(Integer.parseInt(cursor.getString(cursor.getColumnIndex("namXuatBan"))));
            list.add(sach);
        }
        return list;
    }


}