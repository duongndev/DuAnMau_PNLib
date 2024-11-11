package com.duongnd.quanlythuvien.data.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duongnd.quanlythuvien.data.database.Database;
import com.duongnd.quanlythuvien.data.model.NhanVien;

import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {

    private SQLiteDatabase sqLiteDatabase;

    public NhanVienDAO(Context context) {
        Database database = new Database(context);
        sqLiteDatabase = database.getWritableDatabase();
    }

    public void themNhanVien(NhanVien nhanVien) {
        ContentValues values = new ContentValues();
        values.put("hoTen", nhanVien.getHoTen());
        values.put("tenDangNhap", nhanVien.getTenDangNhap());
        values.put("role", nhanVien.getRole());
        values.put("matKhau", nhanVien.getMatKhau());
        sqLiteDatabase.insert("NhanVien", null, values);
    }

    public int suaNhanVien(NhanVien nhanVien) {
        ContentValues values = new ContentValues();
        values.put("hoTen", nhanVien.getHoTen());
        values.put("tenDangNhap", nhanVien.getTenDangNhap());
        values.put("role", nhanVien.getRole());
        values.put("matKhau", nhanVien.getMatKhau());
        return sqLiteDatabase.update("NhanVien", values, "maNV = ?", new String[]{String.valueOf(nhanVien.getMaNV())});
    }

    public int xoaNhanVien(int maNV) {
        return sqLiteDatabase.delete("NhanVien", "maNV = ?", new String[]{String.valueOf(maNV)});
    }

    public List<NhanVien> layTatCaNhanVien() {
        String sql = "SELECT * FROM NhanVien";
        return getData(sql);
    }


    @SuppressLint("Range")
    private List<NhanVien> getData(String sql, String...selectionArgs) {
        List<NhanVien> nhanViens = new ArrayList<>();
        @SuppressLint("Recycle")
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            NhanVien nhanVien = new NhanVien();
            nhanVien.setMaNV(cursor.getInt(cursor.getColumnIndex("maNV")));
            nhanVien.setHoTen(cursor.getString(cursor.getColumnIndex("hoTen")));
            nhanVien.setTenDangNhap(cursor.getString(cursor.getColumnIndex("tenDangNhap")));
            nhanVien.setRole(cursor.getInt(cursor.getColumnIndex("role")));
            nhanVien.setMatKhau(cursor.getString(cursor.getColumnIndex("matKhau")));
            nhanViens.add(nhanVien);
        }
        return nhanViens;
    }

    public NhanVien layNhanVienTheoMa(int maNV) {
        String sql = "SELECT * FROM NhanVien WHERE maNV = ?";
        List<NhanVien> nhanViens = getData(sql, String.valueOf(maNV));
        return nhanViens.get(0);
    }

    public NhanVien layNhanVienTheoTenDangNhap(String tenDangNhap) {
        String sql = "SELECT * FROM NhanVien WHERE tenDangNhap = ?";
        List<NhanVien> nhanViens = getData(sql, tenDangNhap);
        return nhanViens.get(0);
    }

    public int doiMatKhauNhanVien(int maNV, String matKhauMoi) {
        ContentValues values = new ContentValues();
        values.put("matKhau", matKhauMoi);
        return sqLiteDatabase.update("NhanVien", values, "maNV = ?", new String[]{String.valueOf(maNV)});
    }

    public boolean checkDangNhap(String tenDangNhap, String matKhau) {
        String sql = "SELECT * FROM NhanVien WHERE tenDangNhap = ? AND matKhau = ?";
        List<NhanVien> nhanViens = getData(sql, tenDangNhap, matKhau);
        return !nhanViens.isEmpty();
    }



}