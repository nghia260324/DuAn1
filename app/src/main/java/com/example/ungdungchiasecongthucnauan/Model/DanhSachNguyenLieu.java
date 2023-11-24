package com.example.ungdungchiasecongthucnauan.Model;

import java.io.Serializable;

public class DanhSachNguyenLieu implements Serializable {
    private int id;
    private String idCongThuc;
    private int idNguyenLieu;
    private int khoiLuong;

    public DanhSachNguyenLieu() {
    }

    public DanhSachNguyenLieu(int id, String idCongThuc, int idNguyenLieu, int khoiLuong) {
        this.id = id;
        this.idCongThuc = idCongThuc;
        this.idNguyenLieu = idNguyenLieu;
        this.khoiLuong = khoiLuong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdCongThuc() {
        return idCongThuc;
    }

    public void setIdCongThuc(String idCongThuc) {
        this.idCongThuc = idCongThuc;
    }

    public int getIdNguyenLieu() {
        return idNguyenLieu;
    }

    public void setIdNguyenLieu(int idNguyenLieu) {
        this.idNguyenLieu = idNguyenLieu;
    }

    public int getKhoiLuong() {
        return khoiLuong;
    }

    public void setKhoiLuong(int khoiLuong) {
        this.khoiLuong = khoiLuong;
    }

    @Override
    public String toString() {
        return "DanhSachNguyenLieu{" +
                "id=" + id +
                ", idCongThuc='" + idCongThuc + '\'' +
                ", idNguyenLieu=" + idNguyenLieu +
                ", khoiLuong=" + khoiLuong +
                '}';
    }
}
