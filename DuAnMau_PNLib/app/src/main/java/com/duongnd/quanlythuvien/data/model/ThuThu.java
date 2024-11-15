package com.duongnd.quanlythuvien.data.model;

public class ThuThu {
    private int maTT;
    private String hoten, matKhau;

    public ThuThu() {
    }

    public ThuThu(int maTT, String hoten, String matKhau) {
        this.maTT = maTT;
        this.hoten = hoten;
        this.matKhau = matKhau;
    }

    public int getMaTT() {
        return maTT;
    }

    public void setMaTT(int maTT) {
        this.maTT = maTT;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    @Override
    public String toString() {
        return "ThuThu{" +
                "maTT=" + maTT +
                ", hoten='" + hoten + '\'' +
                ", matKhau='" + matKhau + '\'' +
                '}';
    }
}