package com.example.ungdungchiasecongthucnauan.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class CongThuc implements Serializable {
    private String id;
    private String ten;
    private String idAnh;
    private String idNguoiDung;
    private int khauPhan;
    private int thoiGianNau;
    private Date ngayTao;
    private int idLoai;
    private int trangThai;
    private ArrayList<BuocLam> lstBuocLam;
    private ArrayList<DanhSachNguyenLieu> lstNguyenLieu;
    private ArrayList<BinhLuan> lstBinhLuan;

    public CongThuc() {
    }

    public CongThuc(String id, String ten, String idAnh, String idNguoiDung, int khauPhan, int thoiGianNau, Date ngayTao, int idLoai, int trangThai, ArrayList<BuocLam> lstBuocLam, ArrayList<DanhSachNguyenLieu> lstNguyenLieu, ArrayList<BinhLuan> lstBinhLuan) {
        this.id = id;
        this.ten = ten;
        this.idAnh = idAnh;
        this.idNguoiDung = idNguoiDung;
        this.khauPhan = khauPhan;
        this.thoiGianNau = thoiGianNau;
        this.ngayTao = ngayTao;
        this.idLoai = idLoai;
        this.trangThai = trangThai;
        this.lstBuocLam = lstBuocLam;
        this.lstNguyenLieu = lstNguyenLieu;
        this.lstBinhLuan = lstBinhLuan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getIdAnh() {
        return idAnh;
    }

    public void setIdAnh(String idAnh) {
        this.idAnh = idAnh;
    }

    public String getIdNguoiDung() {
        return idNguoiDung;
    }

    public void setIdNguoiDung(String idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
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

    public int getIdLoai() {
        return idLoai;
    }

    public void setIdLoai(int idLoai) {
        this.idLoai = idLoai;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public ArrayList<BuocLam> getLstBuocLam() {
        return lstBuocLam;
    }

    public void setLstBuocLam(ArrayList<BuocLam> lstBuocLam) {
        this.lstBuocLam = lstBuocLam;
    }

    public ArrayList<DanhSachNguyenLieu> getLstNguyenLieu() {
        return lstNguyenLieu;
    }

    public void setLstNguyenLieu(ArrayList<DanhSachNguyenLieu> lstNguyenLieu) {
        this.lstNguyenLieu = lstNguyenLieu;
    }

    public ArrayList<BinhLuan> getLstBinhLuan() {
        return lstBinhLuan;
    }

    public void setLstBinhLuan(ArrayList<BinhLuan> lstBinhLuan) {
        this.lstBinhLuan = lstBinhLuan;
    }
}
