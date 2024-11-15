package com.duongnd.quanlythuvien.data.model;

import java.util.Date;

public class PhieuMuon {
    private int maPM, maTT, maTV, maSach, tienThue, traSach;
    private String ngayMuon, ngayTra;

    public PhieuMuon() {
    }

    public PhieuMuon(int maPM, int maTT, int maTV, int maSach, int tienThue, int traSach, String ngayMuon, String ngayTra) {
        this.maPM = maPM;
        this.maTT = maTT;
        this.maTV = maTV;
        this.maSach = maSach;
        this.tienThue = tienThue;
        this.traSach = traSach;
        this.ngayMuon = ngayMuon;
        this.ngayTra = ngayTra;
    }

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public int getMaTT() {
        return maTT;
    }

    public void setMaTT(int maTT) {
        this.maTT = maTT;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getTienThue() {
        return tienThue;
    }

    public void setTienThue(int tienThue) {
        this.tienThue = tienThue;
    }

    public int getTraSach() {
        return traSach;
    }

    public void setTraSach(int traSach) {
        this.traSach = traSach;
    }

    public String getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(String ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public String getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(String ngayTra) {
        this.ngayTra = ngayTra;
    }

    @Override
    public String toString() {
        return "PhieuMuon{" +
                "maPM=" + maPM +
                ", maTT=" + maTT +
                ", maTV=" + maTV +
                ", maSach=" + maSach +
                ", tienThue=" + tienThue +
                ", traSach=" + traSach +
                ", ngayMuon='" + ngayMuon + '\'' +
                ", ngayTra='" + ngayTra + '\'' +
                '}';
    }
}