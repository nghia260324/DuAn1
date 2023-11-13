package com.example.ungdungchiasecongthucnauan.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    static final String dbName = "CONG_THUC_NAU_AN";
    static final int dbVersion = 9;
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
                        "khoiLuong INTEGER)";
        String createTableBinhLuan =
                "Create table BinhLuan (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "idCongThuc TEXT NOT NULL," +
                        "idNguoiDung INTEGER NOT NULL," +
                        "noiDung TEXT NOT NULL)";
        String createTableDanhSachCongThuc =
                "Create table DanhSachCongThuc (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "ten TEXT NOT NULL," +
                        "idNguoiDung INTEGER NOT NULL)";
        String createTableCongThuc_DSCT =
                "Create table CongThuc_DSCT (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "idCongThuc TEXT NOT NULL," +
                        "idDanhSachCongThuc INTEGER NOT NULL)";


        db.execSQL(createTableKieuNguyenLieu);
        db.execSQL(createTableNguoiDung);
        db.execSQL(createTableAnh);
        db.execSQL(createTableBuocLam);
        db.execSQL(createTableLoaiCongThuc);
        db.execSQL(createTableCongThuc);
        db.execSQL(createTableNguyenLieu);
        db.execSQL(createTableDanhSachNguyenLieu);
        db.execSQL(createTableBinhLuan);
        db.execSQL(createTableDanhSachCongThuc);
        db.execSQL(createTableCongThuc_DSCT);
        InsertValues(db);
    }

    private void InsertValues(SQLiteDatabase db) {
        String add_kieuNguyenLieu = "INSERT INTO KieuNguyenLieu(tenKieu) VALUES" +
                "('Ngũ cốc')," +//1
                "('Rau')," +//2
                "('Trái cây')," +//3
                "('Nấm')," +//4
                "('Thịt')," +//5
                "('Cá')," +//6
                "('Trứng')," +//7
                "('Sữa')," +//8
                "('Gia vị')," +//9
                "('Khác')";//10

        String add_NguyenLieu = "INSERT INTO NguyenLieu(ten,idKieuNguyenLieu,calo,gia) VALUES" +
                "('Thịt lợn',5,216,0)," +
                "('Thịt bò',5,250,0)," +
                "('Đùi gà',5,214,0)," +
                "('Ức gà',5,129,0)," +
                "('Cá hồi',6,208,0)," +
                "('Cá thu',7,215,0)," +
                "('Thịt cừu',5,294,0)," +
                "('Cá ngừ',6,129,0)," +
                "('Tôm',6,99,0)," +
                "('Mực',6,215,0)," +
                "('Hàu',6,199,0)," +
                "('Cua biển',6,97,0)," +
                "('Chim bồ câu',5,213,0)," +
                "('Trứng gà',7,155,0)," +
                "('Trứng vịt',7,170,0)," +
                "('Trứng chim cút',7,142,0)," +
                "('Sữa tươi',8,42,0)," +
                "('Phô mai',10,402,0)," +
                "('Bơ động vật',10,716,0)," +
                "('Váng sữa',8,375,0)," +
                "('Đậu phụ',10,68,0)," +
                "('Sò điệp',6,111,0)," +
                "('Ngao',6,93,0)," +
                "('Gạo trắng',1,71,0)," +
                "('Yến mạch',1,67,0)," +
                "('Bắp cải',2,24,0)," +
                "('Cà rốt',2,41,0)," +
                "('Khoai tây',2,76,0)," +
                "('Nấm rơm',4,22,0)," +
                "('Bông cải xanh',5,33,0)," +
                "('Cà chua',2,17,0)," +
                "('Khoai lang',2,83,0)," +
                "('Rau muống',2,30,0)," +
                "('Cải thảo',2,25,0)," +
                "('Ớt chuông',2,40,0)," +
                "('Cải xanh',2,20,0)," +
                "('Su hào',2,27,0)," +
                "('Xà lách',2,15,0)," +
                "('Cần tây',2,19,0)," +
                "('Củ cải',2,14,0)," +
                "('Rau mồng tơi',2,26,0)," +
                "('Cải xoong',2,27,0)," +
                "('Rau chân vịt',2,32,0)," +
                "('Bí đỏ',2,76,0)," +
                "('Nấm kim châm',4,29,0)," +
                "('Khoai sọ',2,64,0)," +
                "('Dọc mùng',2,15,0)," +
                "('Bí xanh',2,49,0)," +
                "('Bầu',2,38,0)," +
                "('Rau rền',2,24,0)," +
                "('Đậu đen',10,86,0)," +
                "('Đậu nành',10,92,9)," +
                "('Cải thìa',2,27,0)";
        String add_nguoiDung = "INSERT INTO NguoiDung(hoTen,email,matKhau,trangThai,phanQuyen,avatar) VALUES" +
                "('Admin','admin@gmail.com','admin',0,0,5)";
        String add_LoaiCongThuc = "INSERT INTO LoaiCongThuc(ten) VALUES" +
                "('Đồ uống')," +
                "('Nướng')," +
                "('Đặc sản')," +
                "('Canh')," +
                "('Hấp')," +
                "('Chiên')," +
                "('Xào')," +
                "('Rán')," +
                "('Nấu')," +
                "('Súp')," +
                "('Bánh')";


        db.execSQL(add_kieuNguyenLieu);
        db.execSQL(add_NguyenLieu);
        db.execSQL(add_nguoiDung);
        db.execSQL(add_LoaiCongThuc);
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
        String dropTableBinhLuan = "Drop table if exists BinhLuan";
        String dropTableDanhSachCongThuc = "Drop table if exists DanhSachCongThuc";
        String dropTbalecreateTableCongThuc_DSCT = "Drop table if exists CongThuc_DSCT";

        if(oldVersion != newVersion){
            db.execSQL(dropTableKieuNguyenLieu);
            db.execSQL(dropTableNguoiDung);
            db.execSQL(dropTableAnh);
            db.execSQL(dropTableBuocLam);
            db.execSQL(dropTableLoaiCongThuc);
            db.execSQL(dropTableCongThuc);
            db.execSQL(dropTableNguyenLieu);
            db.execSQL(dropTableDanhSachNguyenLieu);
            db.execSQL(dropTableBinhLuan);
            db.execSQL(dropTableDanhSachCongThuc);
            db.execSQL(dropTbalecreateTableCongThuc_DSCT);
            onCreate(db);
        }
    }
}
