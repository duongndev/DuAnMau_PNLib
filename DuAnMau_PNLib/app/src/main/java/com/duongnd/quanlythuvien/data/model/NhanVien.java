package com.duongnd.quanlythuvien.data.model;

import android.os.Parcelable;

import java.io.Serializable;

public class NhanVien implements Serializable {
    private int maNV, role;
    private String hoTen, tenDangNhap, matKhau;

    public NhanVien() {
    }

    public NhanVien(int maNV, String hoTen, String tenDangNhap, int role, String matKhau) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.tenDangNhap = tenDangNhap;
        this.role = role;
        this.matKhau = matKhau;
    }


    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}