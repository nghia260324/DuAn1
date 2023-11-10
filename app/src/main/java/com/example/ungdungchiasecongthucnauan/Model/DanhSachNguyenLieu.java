package com.example.ungdungchiasecongthucnauan.Model;

public class DanhSachNguyenLieu {
    private int id;
    private String idCongThuc;
    private int idNguyenLieu;
    private int khoiLuuong;

    public DanhSachNguyenLieu() {
    }

    public DanhSachNguyenLieu(int id, String idCongThuc, int idNguyenLieu, int khoiLuuong) {
        this.id = id;
        this.idCongThuc = idCongThuc;
        this.idNguyenLieu = idNguyenLieu;
        this.khoiLuuong = khoiLuuong;
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

    public int getKhoiLuuong() {
        return khoiLuuong;
    }

    public void setKhoiLuuong(int khoiLuuong) {
        this.khoiLuuong = khoiLuuong;
    }
}
