package com.duongnd.quanlythuvien.data.model;

public class Sach {
    private int maSach, maLoai, giaThue;
    private String tenSach, tacGia;

    public Sach() {
    }

    public Sach(int maSach, int maLoai, int giaThue, String tenSach, String tacGia) {
        this.maSach = maSach;
        this.maLoai = maLoai;
        this.giaThue = giaThue;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
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

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }
}