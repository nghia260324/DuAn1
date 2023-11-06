package com.example.ungdungchiasecongthucnauan.Model;

import java.util.ArrayList;
import java.util.Date;

public class CongThuc {
    private int id;
    private String ten;
    private String anhDaiDien;
    private String nguoiDung;
    private int khauPhan;
    private int thoiGianNau;
    private Date ngayTao;
    private int loai;
    private int chiaSe;
    private ArrayList<BuocLam> lstBuocLam;
    private ArrayList<NguyenLieu> lstNguyenLieu;
    private ArrayList<BinhLuan> lstBinhLuan;

    public CongThuc() {
    }

    public CongThuc(int id, String ten, String anhDaiDien, String nguoiDung, int khauPhan, int thoiGianNau, Date ngayTao, int loai, int chiaSe, ArrayList<BuocLam> lstBuocLam, ArrayList<NguyenLieu> lstNguyenLieu, ArrayList<BinhLuan> lstBinhLuan) {
        this.id = id;
        this.ten = ten;
        this.anhDaiDien = anhDaiDien;
        this.nguoiDung = nguoiDung;
        this.khauPhan = khauPhan;
        this.thoiGianNau = thoiGianNau;
        this.ngayTao = ngayTao;
        this.loai = loai;
        this.chiaSe = chiaSe;
        this.lstBuocLam = lstBuocLam;
        this.lstNguyenLieu = lstNguyenLieu;
        this.lstBinhLuan = lstBinhLuan;
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

    public String getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }

    public String getNguoiDung() {
        return nguoiDung;
    }

    public void setNguoiDung(String nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

    public int getKhauPhan() {
        return khauPhan;
    }

    public void setKhauPhan(int khauPhan) {
        this.khauPhan = khauPhan;
    }

    public int getThoiGianNau() {
        return thoiGianNau;
    }

    public void setThoiGianNau(int thoiGianNau) {
        this.thoiGianNau = thoiGianNau;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public int getLoai() {
        return loai;
    }

    public void setLoai(int loai) {
        this.loai = loai;
    }

    public int getChiaSe() {
        return chiaSe;
    }

    public void setChiaSe(int chiaSe) {
        this.chiaSe = chiaSe;
    }

    public ArrayList<BuocLam> getLstBuocLam() {
        return lstBuocLam;
    }

    public void setLstBuocLam(ArrayList<BuocLam> lstBuocLam) {
        this.lstBuocLam = lstBuocLam;
    }

    public ArrayList<NguyenLieu> getLstNguyenLieu() {
        return lstNguyenLieu;
    }

    public void setLstNguyenLieu(ArrayList<NguyenLieu> lstNguyenLieu) {
        this.lstNguyenLieu = lstNguyenLieu;
    }

    public ArrayList<BinhLuan> getLstBinhLuan() {
        return lstBinhLuan;
    }

    public void setLstBinhLuan(ArrayList<BinhLuan> lstBinhLuan) {
        this.lstBinhLuan = lstBinhLuan;
    }
}
