package com.example.ungdungchiasecongthucnauan.Model;

import java.io.Serializable;

public class BinhLuan implements Serializable {
    private int id;
    private String idCongThuc;
    private String nguoiDung;
    private String noiDung;

    public BinhLuan() {
    }

    public BinhLuan(int id, String idCongThuc, String nguoiDung, String noiDung) {
        this.id = id;
        this.idCongThuc = idCongThuc;
        this.nguoiDung = nguoiDung;
        this.noiDung = noiDung;
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

    public String getNguoiDung() {
        return nguoiDung;
    }

    public void setNguoiDung(String nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
