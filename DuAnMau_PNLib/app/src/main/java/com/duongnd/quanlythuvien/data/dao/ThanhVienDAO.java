package com.duongnd.quanlythuvien.data.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duongnd.quanlythuvien.data.database.Database;
import com.duongnd.quanlythuvien.data.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO {
    private SQLiteDatabase sqLiteDatabase;

    public ThanhVienDAO(Context context) {
        Database database = new Database(context);
        sqLiteDatabase = database.getWritableDatabase();
    }

    public long themThanhVien(ThanhVien thanhVien) {
        ContentValues values = new ContentValues();
        values.put("hoTen", thanhVien.getHoTen());
        values.put("namSinh", thanhVien.getNamSinh());
        return sqLiteDatabase.insert("ThanhVien", null, values);
    }


    public int suaThanhVien(ThanhVien thanhVien) {
        ContentValues values = new ContentValues();
        values.put("hoTen", thanhVien.getHoTen());
        values.put("namSinh", thanhVien.getNamSinh());
        return sqLiteDatabase.update("ThanhVien", values, "maTV", new String[]{String.valueOf(thanhVien.getMaTV())});
    }


    public int xoaThanhVien(int maTV) {
        return sqLiteDatabase.delete("ThanhVien", "maTV=?", new String[]{String.valueOf(maTV)});
    }


    public List<ThanhVien> getAllThanhVien() {
        String query = "SELECT * FROM ThanhVien";
        return getData(query);
    }

    public ThanhVien getThanhVien(int maTV) {
        String query = "SELECT * FROM ThanhVien WHERE maTV=?";
        List<ThanhVien> thanhVienList = getData(query, String.valueOf(maTV));
        return thanhVienList.get(0);
    }


    @SuppressLint("Range")
    private List<ThanhVien> getData(String query, String... selectionArgs) {
        List<ThanhVien> thanhVienList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(query, selectionArgs);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ThanhVien thanhVien = new ThanhVien();
            thanhVien.setMaTV(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maTV"))));
            thanhVien.setHoTen(cursor.getString(cursor.getColumnIndex("hoTen")));
            thanhVien.setNamSinh(Integer.parseInt(cursor.getString(cursor.getColumnIndex("namSinh"))));
            thanhVienList.add(thanhVien);
            cursor.moveToNext();
        }
        return thanhVienList;
    }
}