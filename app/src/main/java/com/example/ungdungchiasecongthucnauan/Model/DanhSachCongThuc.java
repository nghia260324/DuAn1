package com.example.ungdungchiasecongthucnauan.Model;

public class DanhSachCongThuc {
    private int id;
    private String ten;
    private int idNguoiDung;

    public DanhSachCongThuc() {
    }

    public DanhSachCongThuc(int id, String ten, int idNguoiDung) {
        this.id = id;
        this.ten = ten;
        this.idNguoiDung = idNguoiDung;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getIdNguoiDung() {
        return idNguoiDung;
    }

    public void setIdNguoiDung(int idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
    }
}
