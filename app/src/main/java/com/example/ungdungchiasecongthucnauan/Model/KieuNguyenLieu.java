package com.example.ungdungchiasecongthucnauan.Model;

public class KieuNguyenLieu {
    private int id;
    private String tenKieu;

    public KieuNguyenLieu() {
    }

    public KieuNguyenLieu(int id, String tenKieu) {
        this.id = id;
        this.tenKieu = tenKieu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenKieu() {
        return tenKieu;
    }

    public void setTenKieu(String tenKieu) {
        this.tenKieu = tenKieu;
    }
}
