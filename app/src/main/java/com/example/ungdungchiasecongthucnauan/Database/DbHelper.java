package com.example.ungdungchiasecongthucnauan.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    static final String dbName = "CONG_THUC_NAU_AN";
    static final int dbVersion = 4;
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
                        "khauPhan INTEGER," +
                        "thoiGianNau INTEGER," +
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
                "('Ngũ cốc')," +//0
                "('Rau')," +//1
                "('Trái cây')," +//2
                "('Nấm')," +//3
                "('Thịt')," +//4
                "('Cá')," +//5
                "('Trứng')," +//6
                "('Sữa')," +//7
                "('Gia vị')," +//8
                "('Khác')";//9

        String add_NguyenLieu = "INSERT INTO NguyenLieu(ten,idKieuNguyenLieu,calo,gia) VALUES" +
                "('Thịt lợn',4,216,0)," +
                "('Thịt bò',4,250,0)," +
                "('Đùi gà',4,214,0)," +
                "('Ức gà',4,129,0)," +
                "('Cá hồi',5,208,0)," +
                "('Cá thu',5,215,0)," +
                "('Thịt cừu',4,294,0)," +
                "('Cá ngừ',5,129,0)," +
                "('Tôm',5,99,0)," +
                "('Mực',5,215,0)," +
                "('Hàu',5,199,0)," +
                "('Cua biển',5,97,0)," +
                "('Chim bồ câu',4,213,0)," +
                "('Trứng gà',6,155,0)," +
                "('Trứng vịt',6,170,0)," +
                "('Trứng chim cút',6,142,0)," +
                "('Sữa tươi',7,42,0)," +
                "('Phô mai',9,402,0)," +
                "('Bơ động vật',9,716,0)," +
                "('Váng sữa',7,375,0)," +
                "('Đậu phụ',9,68,0)," +
                "('Sò điệp',5,111,0)," +
                "('Ngao',5,93,0)," +
                "('Gạo trắng',0,71,0)," +
                "('Yến mạch',0,67,0)," +
                "('Bắp cải',1,24,0)," +
                "('Cà rốt',1,41,0)," +
                "('Khoai tây',1,76,0)," +
                "('Nấm rơm',3,22,0)," +
                "('Bông cải xanh',1,33,0)," +
                "('Cà chua',1,17,0)," +
                "('Khoai lang',1,83,0)," +
                "('Rau muống',1,30,0)," +
                "('Cải thảo',1,25,0)," +
                "('Ớt chuông',1,40,0)," +
                "('Cải xanh',1,20,0)," +
                "('Su hào',1,27,0)," +
                "('Xà lách',1,15,0)," +
                "('Cần tây',1,19,0)," +
                "('Củ cải',1,14,0)," +
                "('Rau mồng tơi',1,26,0)," +
                "('Cải xoong',1,27,0)," +
                "('Rau chân vịt',1,32,0)," +
                "('Bí đỏ',1,76,0)," +
                "('Nấm kim châm',3,29,0)," +
                "('Khoai sọ',1,64,0)," +
                "('Dọc mùng',1,15,0)," +
                "('Bí xanh',1,49,0)," +
                "('Bầu',1,38,0)," +
                "('Rau rền',1,24,0)," +
                "('Đậu đen',9,86,0)," +
                "('Đậu nành',9,92,9)," +
                "('Cải thìa',1,27,0)";
        String add_nguoiDung = "INSERT INTO NguoiDung(hoTen,email,matKhau,trangThai,phanQuyen,avatar) VALUES" +
                "('Admin','admin@gmail.com','admin',0,0,5)";

        db.execSQL(add_kieuNguyenLieu);
        db.execSQL(add_NguyenLieu);
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
