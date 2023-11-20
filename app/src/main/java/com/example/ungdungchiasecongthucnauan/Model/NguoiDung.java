package com.example.ungdungchiasecongthucnauan.Model;

public class NguoiDung {
    private String id;
    private String hoTen;
    private String email;
    private String matKhau;
    private int trangThai;
    private int phanQuyen;
    private int avatar;

    public NguoiDung() {
    }

    public NguoiDung(String id, String hoTen, String email, String matKhau, int trangThai, int phanQuyen, int avatar) {
        this.id = id;
        this.hoTen = hoTen;
        this.email = email;
        this.matKhau = matKhau;
        this.trangThai = trangThai;
        this.phanQuyen = phanQuyen;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int getPhanQuyen() {
        return phanQuyen;
    }

    public void setPhanQuyen(int phanQuyen) {
        this.phanQuyen = phanQuyen;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
