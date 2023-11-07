package com.example.ungdungchiasecongthucnauan.Model;

public class BuocLam {
    private int congThuc;
    private String noiDung;
    private String anh;
    private int thuTu;

    public BuocLam() {
    }

    public BuocLam(int congThuc, String noiDung, String anh, int thuTu) {
        this.congThuc = congThuc;
        this.noiDung = noiDung;
        this.anh = anh;
        this.thuTu = thuTu;
    }

    public int getCongThuc() {
        return congThuc;
    }

    public void setCongThuc(int congThuc) {
        this.congThuc = congThuc;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public int getThuTu() {
        return thuTu;
    }

    public void setThuTu(int thuTu) {
        this.thuTu = thuTu;
    }
}
