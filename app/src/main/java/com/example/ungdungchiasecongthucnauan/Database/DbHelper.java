package com.example.ungdungchiasecongthucnauan.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    static final String dbName = "CONG_THUC_NAU_AN";
    static final int dbVersion = 10;
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
                        "id TEXT PRIMARY KEY NOT NULL, " +
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
//        "FOREIGN KEY(idCongThuc) REFERENCES CongThuc(id)" +
        String createTableLoaiCongThuc =
                "Create table LoaiCongThuc (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "ten TEXT NOT NULL)";
        String createTableCongThuc =
                "Create table CongThuc (" +
                        "id TEXT PRIMARY KEY NOT NULL," +
                        "ten TEXT NOT NULL," +
                        "idAnh TEXT," +
                        "idnguoiDung TEXT NOT NULL," +
                        "khauPhan INTEGER," +
                        "thoiGianNau INTEGER," +
                        "ngay DATE NOT NULL," +
                        "idLoaiCongThuc INTEGER NOT NULL," +
                        "trangThai INTEGER NOT NULL)";
//        "FOREIGN KEY(idnguoiDung) REFERENCES NguoiDung(id)," +
//        "FOREIGN KEY(idLoaiCongThuc) REFERENCES LoaiCongThuc(id)" +
        String createTableNguyenLieu =
                "Create table NguyenLieu (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "ten TEXT NOT NULL," +
                        "idKieuNguyenLieu INTEGER NOT NULL," +
                        "calo INTEGER NOT NULL," +
                        "gia INTEGER NOT NULL)";
//        "FOREIGN KEY(idKieuNguyenLieu) REFERENCES KieuNguyenLieu(id)" +
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
                        "idNguoiDung TEXT NOT NULL," +
                        "noiDung TEXT NOT NULL)";
        //        "FOREIGN KEY(idCongThuc) REFERENCES CongThuc(id)" +
        //        "FOREIGN KEY(idNguoiDung) REFERENCES NguoiDung(id)" +
        String createTableDanhSachCongThuc =
                "Create table DanhSachCongThuc (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "ten TEXT NOT NULL," +
                        "idNguoiDung TEXT NOT NULL)";
        //        "FOREIGN KEY(idNguoiDung) REFERENCES NguoiDung(id)" +
        String createTableCongThuc_DSCT =
                "Create table CongThuc_DSCT (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "idCongThuc TEXT NOT NULL," +
                        "idDanhSachCongThuc INTEGER NOT NULL)";
        //        "FOREIGN KEY(idCongThuc) REFERENCES CongThuc(id)" +
        //        "FOREIGN KEY(idDanhSachCongThuc) REFERENCES DanhSachCongThuc(id)" +


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
                "('Thịt')," +//1//100gram
                "('Hải sản')," +//2//100gram
                "('Thủy sản')," +//3//100gram
                "('Trứng')," +//4//Quả
                "('Sữa')," +//5//ml
                "('Rau')," +//6/500gram
                "('Củ')," +//7//500gram
                "('Đậu')," +//8//500gram
                "('Nấm')," +//9//100gram
                "('Ngũ cốc')," +//10//500gram
                "('Gia vị')," +//11//
                "('Khác')";//12

        String add_NguyenLieu = "INSERT INTO NguyenLieu(ten,idKieuNguyenLieu,calo,gia) VALUES" +
                "('Thịt bò',1,250,20000)," +
                "('Thịt bò xay',1,250,32000)," +
                "('Sườn bò',1,230,50000)," +
                "('Sườn heo - lợn',1,277,6000)," +
                "('Thăm ngoại bò',1,244,100000)," +
                "('Thịt lợn - heo',1,216,16000)," +
                "('Giò lợn - heo',1,170,14000)," +
                "('Thịt lợn - heo xay',1,216,16000)," +
                "('Bồ câu',1,213,50000)," +
                "('Ức gà',1,165,10000)," +
                "('Cánh gà',1,203,10000)," +
                "('Đùi gà',1,177,12000)," +
                "('Mề gà tươi',1,94,10000)," +
                "('Thịt cừu vụn',1,294,40000)," +
                "('Sườn cừu',1,294,70000)," +
                "('Thịt chân cừu',1,294,70000)," +
                "('Xương ống chân cừu',1,294,70000)," +
                "('Thịt tôm sú',3,99,33000)," +

                "('Thịt cá hồi',2,208,50000)," +
                "('Đầu cá hồi',2,208,10000)," +
                "('Thịt cá thu',2,215,0)," +
                "('Thịt cá ngừ tươi phi lê',2,129,30000)," +
                "('Cua hoàng đế',2,130,220000)," +
                "('Hàu sữa',2,199,25000)," +
                "('Cua thịt',2,97,35000)," +
                "('Râu mực',2,215,15000)," +
                "('Ngao',2,93,15000)," +
                "('Sò điệp tươi',2,111,16000)," +

                "('Trứng chim cút',4,142,900)," +
                "('Trứng gà',4,70,3000)," +
                "('Trứng vịt',4,100,3000)," +

                "('Sữa tươi không đường',5,55,7000)," +

                "('Bắp cải trắng',6,24,12000)," +
                "('Bông cải trắng',6,33,35000)," +
                "('Bông cải xanh',6,33,35000)," +
                "('Rau muống',6,30,10000)," +
                "('Rau mồng tơi',6,26,15000)," +
                "('Cải bó xôi',6,32,25000)," +
                "('Cải xoong',6,27,15000)," +
                "('Dọc mùng',6,15,15000)," +
                "('Rau rền',6,24,0)," +
                "('Rau ngót',6,25,25000)," +
                "('Xà lách',6,15,20000)," +
                "('Cần tây',6,19,30000)," +
                "('Cải thìa',6,19,12000)," +
                "('Cà chua',6,17,15000)," +
                "('Ớt chuông xanh',6,40,50000)," +
                "('Ớt chuông đỏ',6,40,50000)," +

                "('Khoai sọ',6,64,40000)," +
                "('Khoai lang tím',6,83,20000)," +
                "('Khoai lang mật',6,83,20000)," +
                "('Khoai tây',6,76,50000)," +

                "('Củ cải trắng',6,14,11000)," +
                "('Su hào',6,27,16000)," +
                "('Cà rốt',6,41,12000)," +
                "('Bí xanh',6,49,8000)," +
                "('Bí đỏ',6,76,14000)," +
                "('Bầu',6,38,8000)," +

                "('Đậu đen',8,86,28000)," +
                "('Đậu nành',8,92,23000)," +

                "('Nấm rơm',9,22,25000)," +
                "('Nấm mèo',9,25,25000)," +
                "('Nấm hương',9,25,30000)," +
                "('Nấm kim châm',9,29,10000)," +

                "('Gạo trắng',10,71,30000)," +
                "('Gạo nếp',10,71,20000)," +
                "('Yến mạch',10,67,100000)," +//500gram

                "('Váng sữa',8,375,0)," +
                "('Phô mai lát con bò cười',12,402,120000)," +//500gram
                "('Bơ thực vật',12,716,40000)," +//500gram
                "('Đậu phụ',12,68,15000)";//500gram
//        String add_nguoiDung = "INSERT INTO NguoiDung(hoTen,email,matKhau,trangThai,phanQuyen,avatar) VALUES" +
//                "('Admin','admin@gmail.com','admin',0,0,5)";
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
//        db.execSQL(add_nguoiDung);
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
