package com.duongnd.quanlythuvien.data.model;

public class Sach {
    private int maSach, maLoai, giaThue, namXuatBan;
    private String tenSach;

    public Sach() {
    }

    public Sach(int maSach, int maLoai, int giaThue, int namXuatBan, String tenSach) {
        this.maSach = maSach;
        this.maLoai = maLoai;
        this.giaThue = giaThue;
        this.namXuatBan = namXuatBan;
        this.tenSach = tenSach;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }

    public int getNamXuatBan() {
        return namXuatBan;
    }

    public void setNamXuatBan(int namXuatBan) {
        this.namXuatBan = namXuatBan;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }


    @Override
    public String toString() {
        return "Sach{" +
                "maSach=" + maSach +
                ", maLoai=" + maLoai +
                ", giaThue=" + giaThue +
                ", namXuatBan=" + namXuatBan +
                ", tenSach='" + tenSach + '\'' +
                '}';
    }
}