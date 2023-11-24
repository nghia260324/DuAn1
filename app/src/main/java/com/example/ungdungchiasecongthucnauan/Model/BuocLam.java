package com.example.ungdungchiasecongthucnauan.Model;

import java.io.Serializable;

public class BuocLam implements Serializable {
    private int id;
    private String idCongThuc;
    private String noiDung;
    private String idAnh;
    private int thuTu;

    public BuocLam() {
    }

    public BuocLam(int id, String idCongThuc, String noiDung, String idAnh, int thuTu) {
        this.id = id;
        this.idCongThuc = idCongThuc;
        this.noiDung = noiDung;
        this.idAnh = idAnh;
        this.thuTu = thuTu;
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

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getIdAnh() {
        return idAnh;
    }

    public void setIdAnh(String idAnh) {
        this.idAnh = idAnh;
    }

    public int getThuTu() {
        return thuTu;
    }

    public void setThuTu(int thuTu) {
        this.thuTu = thuTu;
    }

    @Override
    public String toString() {
        return "BuocLam{" +
                "id=" + id +
                ", idCongThuc='" + idCongThuc + '\'' +
                ", noiDung='" + noiDung + '\'' +
                ", idAnh='" + idAnh + '\'' +
                ", thuTu=" + thuTu +
                '}';
    }
}
