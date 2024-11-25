package com.duongnd.quanlythuvien.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    static final String DB_NAME = "PNLib.db";
    static final int DB_VERSION = 4;

    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // Tạo bảng nhan viên
        String tb_NhanVien = "create table NhanVien(" +
                "maNV integer primary key autoincrement," +
                "hoTen text not null," +
                "tenDangNhap text not null," +
                "role integer not null," +
                "matKhau text not null)";
        db.execSQL(tb_NhanVien);

        // Tạo bảng thủ thư
        String tb_ThuThu = "create table ThuThu(" +
                "maTT text primary key," +
                "hoTen text not null," +
                "matKhau text not null)";
        db.execSQL(tb_ThuThu);

        // Tạo bảng thành viên
        String tb_ThanhVien = "create table ThanhVien(" +
                "maTV integer primary key autoincrement," +
                "hoTen text not null," +
                "namSinh integer not null)";
        db.execSQL(tb_ThanhVien);

        // Tạo bảng loại sách
        String tb_LoaiSach = "create table LoaiSach(" +
                "maLoai integer primary key autoincrement," +
                "tenLoai text not null)";
        db.execSQL(tb_LoaiSach);

        // Tạo bảng sách
        String tb_Sach = "create table Sach(" +
                "maSach integer primary key autoincrement," +
                "tenSach text not null," +
                "giaThue integer not null," +
                "maLoai integer references LoaiSach(maLoai)," +
                "namXuatBan integer not null)";
        db.execSQL(tb_Sach);

        // Tạo bảng phiếu mượn
        String tb_PhieuMuon = "create table PhieuMuon(" +
                "maPM integer primary key autoincrement," +
                "maNV text references NhanVien(maNV)," +
                "maTV integer references ThanhVien(maTV)," +
                "maSach integer references Sach(maSach)," +
                "tienThue integer not null," +
                "traSach integer not null," +  //trả sách: 1: đã trả - 0: chưa trả
                "ngayMuon text not null, " +
                "ngayTra text not null) ";
        db.execSQL(tb_PhieuMuon);


        //data mẫu
        db.execSQL("INSERT INTO NhanVien VALUES (1,'Nhan vien 01','thuthu01',1,'123456'),(2,'Nhan vien 02','nhanvien',2,'123456')");
        db.execSQL("INSERT INTO LoaiSach VALUES (1, 'Lập trình'),(2,'Thiết kế web'),(3, 'Maketing')");
        db.execSQL("INSERT INTO Sach VALUES (1, 'Lập Trình Android', 2500, 1,2004), (2, 'Lập trình cơ bản', 1000, 1,2005), (3, 'Thiết kế web', 2000, 3,2006)");
        db.execSQL("INSERT INTO ThuThu VALUES ('thuthu1','Thủ văn thư 01','12346'),('nhanvien01','Nhân văn viên','1123456')");
        db.execSQL("INSERT INTO ThanhVien VALUES (1,'Thanh vien 01',2000),(2,'Thanh vien 02',2000)");
        db.execSQL("INSERT INTO PhieuMuon VALUES (1, 1, 1, 1, 2500, 1, '19/03/2022', '19/04/2022'),(2,2,1, 3, 2000, 0, '19/03/2022', '19/04/2022'), (3,1,2, 1, 2000, 1, '19/03/2022', '19/04/2022'), (4,2,2, 2, 1000, 0, '19/03/2022', '19/04/2022')");
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
            db.execSQL("DROP TABLE IF EXISTS ThuThu");
            onCreate(db);
        }
    }
}