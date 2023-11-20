package com.example.ungdungchiasecongthucnauan.Model;

public class CT_DSCT {
    private int id;
    private String idCongThuc;
    private int idSCT;

    public CT_DSCT(int id, String idCongThuc, int idSCT) {
        this.id = id;
        this.idCongThuc = idCongThuc;
        this.idSCT = idSCT;
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

    public int getIdSCT() {
        return idSCT;
    }

    public void setIdSCT(int idSCT) {
        this.idSCT = idSCT;
    }
}
