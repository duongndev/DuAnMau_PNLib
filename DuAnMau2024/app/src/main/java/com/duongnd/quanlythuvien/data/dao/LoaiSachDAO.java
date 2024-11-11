package com.duongnd.quanlythuvien.data.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duongnd.quanlythuvien.data.database.Database;
import com.duongnd.quanlythuvien.data.model.LoaiSach;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDAO {

    private SQLiteDatabase sqLiteDatabase;

    public LoaiSachDAO(Context context) {
        Database database = new Database(context);
        sqLiteDatabase = database.getWritableDatabase();
    }

    public int themLoaiSach(LoaiSach loaiSach) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenLoai", loaiSach.getTenLoai());
        contentValues.put("moTa", loaiSach.getMoTa());
        long result = sqLiteDatabase.insert("LoaiSach", null, contentValues);
        if (result != 0) {
            return -1;
        }
        return 1;
    }

    public int suaLoaiSach(LoaiSach loaiSach) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("maLoai", loaiSach.getMaLoai());
        contentValues.put("tenLoai", loaiSach.getTenLoai());
        contentValues.put("moTa", loaiSach.getMoTa());
        return sqLiteDatabase.update("LoaiSach", contentValues, "maLoai = ?", new String[]{String.valueOf(loaiSach.getMaLoai())});
    }

    public int xoaLoaiSach(int maLoai) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Sach WHERE maLoai = ?", new String[]{String.valueOf(maLoai)});
        if (cursor.getCount() > 0){
            return 0;
        } else {
            int result = sqLiteDatabase.delete("LoaiSach", "maLoai = ?", new String[]{String.valueOf(maLoai)});
            if (result == 0){
                return -1;
            } else {
                return 1;
            }
        }
    }

    public LoaiSach layLoaiSachTheoMa(int maLoai) {
        String sql = "SELECT * FROM LoaiSach WHERE maLoai = ?";
        List<LoaiSach> loaiSaches = getData(sql, String.valueOf(maLoai));
        return loaiSaches.get(0);
    }

    public List<LoaiSach> layTatCaLoaiSach() {
        String sql = "SELECT * FROM LoaiSach";
        return getData(sql);
    }


    @SuppressLint("Range")
    private List<LoaiSach> getData(String sql, String... selectionArgs) {
        List<LoaiSach> loaiSaches = new ArrayList<>();
        @SuppressLint("Recycle")
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            LoaiSach loaiSach = new LoaiSach();
            loaiSach.setMaLoai(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maLoai"))));
            loaiSach.setTenLoai(cursor.getString(cursor.getColumnIndex("tenLoai")));
            loaiSach.setMoTa(cursor.getString(cursor.getColumnIndex("moTa")));
            loaiSaches.add(loaiSach);
        }

        return loaiSaches;
    }


}