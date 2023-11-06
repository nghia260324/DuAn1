package com.example.ungdungchiasecongthucnauan.Model;

public class BinhLuan {
    private int id;
    private int nguoiDung;
    private String noiDung;

    public BinhLuan() {
    }

    public BinhLuan(int id, int nguoiDung, String noiDung) {
        this.id = id;
        this.nguoiDung = nguoiDung;
        this.noiDung = noiDung;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
