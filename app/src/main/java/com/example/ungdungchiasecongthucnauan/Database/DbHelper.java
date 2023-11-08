package com.example.ungdungchiasecongthucnauan.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    static final String dbName = "CONG_THUC_NAU_AN";
    static final int dbVersion = 3;
    public DbHelper(Context context) {
        super(context, dbName, null, dbVersion);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableKieuNguyenLieu=
                "Create table KieuNguyenLieu (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "tenKieu TEXT NOT NULL)";
        String createTableNguoiDung=
                "Create table NguoiDung (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "hoTen TEXT NOT NULL," +
                        "email TEXT NOT NULL," +
                        "matKhau TEXT NOT NULL," +
                        "trangThai INTEGER NOT NULL," +
                        "phanQuyen INTEGER NOT NULL," +
                        "avatar INTEGER NOT NULL)";
        String createTableAnh =
                "Create table Anh (" +
                        "id TEXT PRIMARY KEY NOT NULL," +
                        "url TEXT NOT NULL)";
        String createTableBuocLam =
                "Create table BuocLam (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "idCongThuc TEXT NOT NULL," +
                        "noiDung TEXT NOT NULL," +
                        "idAnh TEXT," +
                        "thuTu INTEGER NOT NULL)";
        String createTableLoaiCongThuc =
                "Create table LoaiCongThuc (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "ten TEXT NOT NULL)";
        String createTableCongThuc =
                "Create table CongThuc (" +
                        "id TEXT PRIMARY KEY NOT NULL," +
                        "ten TEXT NOT NULL," +
                        "idAnh TEXT," +
                        "idnguoiDung INTEGER NOT NULL," +
                        "khauPhan INTEGER NOT NULL," +
                        "thoiGianNau INTEGER NOT NULL," +
                        "ngay DATE NOT NULL," +
                        "idLoaiCongThuc INTEGER NOT NULL," +
                        "trangThai INTEGER NOT NULL)";
        String createTableNguyenLieu =
                "Create table NguyenLieu (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "ten TEXT NOT NULL," +
                        "idKieuNguyenLieu INTEGER NOT NULL," +
                        "calo INTEGER NOT NULL," +
                        "gia INTEGER NOT NULL)";
        String createTableDanhSachNguyenLieu =
                "Create table DanhSachNguyenLieu (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "idCongThuc TEXT NOT NULL," +
                        "idNguyenLieu INTEGER NOT NULL," +
                        "khoiLuong INTEGER NOT NULL)";



        db.execSQL(createTableKieuNguyenLieu);
        db.execSQL(createTableNguoiDung);
        db.execSQL(createTableAnh);
        db.execSQL(createTableBuocLam);
        db.execSQL(createTableLoaiCongThuc);
        db.execSQL(createTableCongThuc);
        db.execSQL(createTableNguyenLieu);
        db.execSQL(createTableDanhSachNguyenLieu);

        InsertValues(db);
    }

    private void InsertValues(SQLiteDatabase db) {
        String add_kieuNguyenLieu = "INSERT INTO KieuNguyenLieu(tenKieu) VALUES" +
                "('Gia vị')," +
                "('Thịt')," +
                "('Cá')," +
                "('Trứng')," +
                "('Ngũ cốc')," +
                "('Nấm')," +
                "('Rau')";
        String add_nguoiDung = "INSERT INTO NguoiDung(hoTen,email,matKhau,trangThai,phanQuyen,avatar) VALUES" +
                "('Admin','admin@gmail.com','admin',0,0,5)";

        db.execSQL(add_kieuNguyenLieu);
        db.execSQL(add_nguoiDung);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableKieuNguyenLieu = "Drop table if exists KieuNguyenLieu";
        String dropTableNguoiDung = "Drop table if exists NguoiDung";
        String dropTableAnh = "Drop table if exists Anh";
        String dropTableBuocLam = "Drop table if exists BuocLam";
        String dropTableLoaiCongThuc = "Drop table if exists LoaiCongThuc";
        String dropTableCongThuc = "Drop table if exists CongThuc";
        String dropTableNguyenLieu = "Drop table if exists NguyenLieu";
        String dropTableDanhSachNguyenLieu = "Drop table if exists DanhSachNguyenLieu";

        if(oldVersion != newVersion){
            db.execSQL(dropTableKieuNguyenLieu);
            db.execSQL(dropTableNguoiDung);
            db.execSQL(dropTableAnh);
            db.execSQL(dropTableBuocLam);
            db.execSQL(dropTableLoaiCongThuc);
            db.execSQL(dropTableCongThuc);
            db.execSQL(dropTableNguyenLieu);
            db.execSQL(dropTableDanhSachNguyenLieu);
            onCreate(db);
        }
    }
}
