package com.duongnd.quanlythuvien.data.model;

public class NhanVien {
    private int maNV;
    private String hoTen;
    private String tenDangNhap;
    private int role;
    private String matKhau;

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