package com.duongnd.quanlythuvien.data.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duongnd.quanlythuvien.data.database.Database;
import com.duongnd.quanlythuvien.data.model.PhieuMuon;

import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDAO {
    private SQLiteDatabase sqLiteDatabase;

    public PhieuMuonDAO(Context context) {
        Database database = new Database(context);
        sqLiteDatabase = database.getWritableDatabase();
    }


    public int themPhieuMuon(PhieuMuon phieuMuon) {
        ContentValues values = new ContentValues();
        values.put("maTV", phieuMuon.getMaTV());
        values.put("maSach", phieuMuon.getMaSach());
        values.put("tienThue", phieuMuon.getTienThue());
        values.put("ngayMuon", phieuMuon.getNgayMuon());
        values.put("ngayTra", phieuMuon.getNgayTra());
        values.put("trangThai", phieuMuon.getTrangThai());
        long result = sqLiteDatabase.insert("PhieuMuon", null, values);
        if (result != 0) {
            return 1;
        } else {
            return 0;
        }
    }


    public int suaPhieuMuon(PhieuMuon phieuMuon) {
        ContentValues values = new ContentValues();
        values.put("maTV", phieuMuon.getMaTV());
        values.put("maSach", phieuMuon.getMaSach());
        values.put("tienThue", phieuMuon.getTienThue());
        values.put("ngayMuon", String.valueOf(phieuMuon.getNgayMuon()));
        values.put("ngayTra", String.valueOf(phieuMuon.getNgayTra()));
        long result = sqLiteDatabase.update("PhieuMuon", values, "maPM=?", new String[]{String.valueOf(phieuMuon.getMaPM())});
        if (result != 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public int xoaPhieuMuon(int maPM) {
        return sqLiteDatabase.delete("PhieuMuon", "maPM=?", new String[]{String.valueOf(maPM)});
    }

    public List<PhieuMuon> getAllPhieuMuon() {
        String query = "SELECT * FROM PhieuMuon";
        return getData(query);
    }

    public boolean thayDoiTrangThai(int maPM) {
        ContentValues values = new ContentValues();
        values.put("trangThai", 1);
        long result = sqLiteDatabase.update("PhieuMuon", values, "maPM=?", new String[]{String.valueOf(maPM)});
        if (result != 0) {
            return true;
        } else {
            return false;
        }
    }




    @SuppressLint("Range")
    public List<PhieuMuon> getData(String query, String... selectionArgs) {
        List<PhieuMuon> phieuMuons = new ArrayList<>();
        @SuppressLint("Recycle")
        Cursor cursor = sqLiteDatabase.rawQuery(query, selectionArgs);
        while (cursor.moveToNext()) {
            PhieuMuon phieuMuon = new PhieuMuon();
            phieuMuon.setMaPM(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maPM"))));
            phieuMuon.setMaTV(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maTV"))));
            phieuMuon.setMaSach(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maSach"))));
            phieuMuon.setTienThue(Integer.parseInt(cursor.getString(cursor.getColumnIndex("tienThue"))));
            phieuMuon.setNgayMuon(cursor.getString(cursor.getColumnIndex("ngayMuon")));
            phieuMuon.setNgayTra(cursor.getString(cursor.getColumnIndex("ngayTra")));
            phieuMuon.setTrangThai(Integer.parseInt(cursor.getString(cursor.getColumnIndex("trangThai"))));
            phieuMuons.add(phieuMuon);
        }
        return phieuMuons;
    }

}