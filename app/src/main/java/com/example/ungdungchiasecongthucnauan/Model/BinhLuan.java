package com.example.ungdungchiasecongthucnauan.Model;

public class BinhLuan {
    private int id;
    private String idCongThuc;
    private int nguoiDung;
    private String noiDung;

    public BinhLuan() {
    }

    public BinhLuan(int id, String idCongThuc, int nguoiDung, String noiDung) {
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

    public int getNguoiDung() {
        return nguoiDung;
    }

    public void setNguoiDung(int nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
