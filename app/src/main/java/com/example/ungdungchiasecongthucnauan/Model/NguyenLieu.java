package com.example.ungdungchiasecongthucnauan.Model;

public class NguyenLieu {
    private int id;
    private String ten;
    private int kieu;
    private int calo;
    private int gia;

    public NguyenLieu() {
    }

    public NguyenLieu(int id, String ten, int kieu, int calo, int gia) {
        this.id = id;
        this.ten = ten;
        this.kieu = kieu;
        this.calo = calo;
        this.gia = gia;
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

    public int getKieu() {
        return kieu;
    }

    public void setKieu(int kieu) {
        this.kieu = kieu;
    }

    public int getCalo() {
        return calo;
    }

    public void setCalo(int calo) {
        this.calo = calo;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    @Override
    public String toString() {
        return "NguyenLieu{" +
                "id=" + id +
                ", ten='" + ten + '\'' +
                ", kieu=" + kieu +
                ", calo=" + calo +
                ", gia=" + gia +
                '}';
    }
}
