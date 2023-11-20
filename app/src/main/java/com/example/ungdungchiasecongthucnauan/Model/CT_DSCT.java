package com.example.ungdungchiasecongthucnauan.Model;

public class CT_DSCT {
    private int id;
    private String idCongThuc;
    private int iđSCT;
    public CT_DSCT(int id, String idCongThuc, int iđSCT) {
        this.id = id;
        this.idCongThuc = idCongThuc;
        this.iđSCT = iđSCT;
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

    public int getIđSCT() {
        return iđSCT;
    }

    public void setIđSCT(int iđSCT) {
        this.iđSCT = iđSCT;
    }
}
