package com.duongnd.quanlythuvien.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    static final String DB_NAME = "PNLib.db";
    static final int DB_VERSION = 1;
    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String TB_NhanVien = "CREATE TABLE NhanVien (" +
                "maNV INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "hoTen TEXT NOT NULL, " +
                "tenDangNhap TEXT NOT NULL, " +
                "role INTEGER NOT NULL, " +
                "matKhau TEXT NOT NULL)";
        db.execSQL(TB_NhanVien);
        // 1 - thu thu, 2 - nhan vien
        String insertNV = "insert into NhanVien values ('0', 'Thu Thu', 'thuthu', '1', 'thuthu'), ('1', 'Nhan Vien', 'nhanvien', '2', 'nhanvien')";
        db.execSQL(insertNV);

        String TB_thanhVien = "CREATE TABLE ThanhVien (" +
                "maTV INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "hoTen TEXT NOT NULL, " +
                "namSinh TEXT NOT NULL)";
        db.execSQL(TB_thanhVien);
        String insertTV = "insert into ThanhVien values (0,'thanh vien 1', '1999-01-01'), (1, 'thanh vien 2', '1999-01-01'), (2, 'thanh vien 3', '1999-01-01'), (3, 'thanh vien 4', '1999-01-01')";
        db.execSQL(insertTV);

        String TB_loaiSach = "CREATE TABLE LoaiSach (" +
                "maLoai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenLoai TEXT NOT NULL, " +
                "moTa TEXT NOT NULL)";
        db.execSQL(TB_loaiSach);
        String insertLS = "insert into LoaiSach values ('0', 'Loai Sach 1', 'loai sach 1'), ('1', 'Loai sach 2', 'Loai sach 2'), ('2', 'Loai sach 3', 'Loai sach 3'), ('3', 'Loai sach 4', 'Loai sach 4')";
        db.execSQL(insertLS);

        String TB_sach = "CREATE TABLE Sach (" +
                "maSach INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maLoai INTEGER NOT NULL, " +
                "giaThue INTEGER NOT NULL, " +
                "tenSach TEXT NOT NULL, " +
                "tacGia TEXT NOT NULL)";
        db.execSQL(TB_sach);
        String insertSach = "insert into Sach values (0, 0, 10000, 'Sach1', 'khong biet'), (1, 1, 20000, 'Sach2', 'khong biet'), (2, 2, 30000, 'Sach3', 'khong biet'), (3, 3, 40000, 'Sach4', 'khong biet')";
        db.execSQL(insertSach);


        String TB_phieuMuon = "CREATE TABLE PhieuMuon (" +
                "maPM INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maTV INTEGER REFERENCES ThanhVien(maTV), " +
                "maSach INTEGER REFERENCES Sach(maSach), " +
                "tienThue INTEGER NOT NULL, " +
                "ngayMuon DATE NOT NULL, " +
                "ngayTra DATE NOT NULL, " +
                "trangThai INTEGER NOT NULL)"; // 0 - chua tra, 1 - da tra
        db.execSQL(TB_phieuMuon);

        String insertPM = "INSERT INTO PhieuMuon values (0, 0, 0, 10000, '2020-01-01', '2020-01-01', 0), (1, 1, 1, 20000, '2020-01-01', '2020-01-01', 1), (2, 2, 2, 30000, '2020-01-01', '2020-01-01', 0), (3, 3, 3, 40000, '2020-01-01', '2020-01-01',1), (4, 3, 4, 50000, '2020-01-01', '2020-01-01', 0)";
        db.execSQL(insertPM);

        String TB_phieuMuonCT = "CREATE TABLE PhieuMuonCT (" +
                "maPM INTEGER REFERENCES PhieuMuon(maPM), " +
                "maSach INTEGER REFERENCES Sach(maSach), " +
                "soLuong INTEGER NOT NULL)";
        db.execSQL(TB_phieuMuonCT);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS NhanVien");
            db.execSQL("DROP TABLE IF EXISTS ThanhVien");
            db.execSQL("DROP TABLE IF EXISTS LoaiSach");
            db.execSQL("DROP TABLE IF EXISTS Sach");
            db.execSQL("DROP TABLE IF EXISTS LoaiSach");
            db.execSQL("DROP TABLE IF EXISTS PhieuMuon");
            db.execSQL("DROP TABLE IF EXISTS PhieuMuonCT");
            onCreate(db);
        }
    }
}