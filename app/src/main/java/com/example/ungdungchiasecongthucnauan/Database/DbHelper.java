package com.example.ungdungchiasecongthucnauan.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    static final String dbName = "CONG_THUC_NAU_AN";
    static final int dbVersion = 1;
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
                        "matKhau TEXT NOT NULL)";




        db.execSQL(createTableKieuNguyenLieu);

        db.execSQL(createTableNguoiDung);



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
        String add_nguoiDung = "INSERT INTO NguoiDung(hoTen,email,matKhau) VALUES" +
                "('Admin','admin@gmail.com','admin')";

        db.execSQL(add_kieuNguyenLieu);
        db.execSQL(add_nguoiDung);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableKieuNguyenLieu = "Drop table if exists KieuNguyenLieu";
        String dropTableNguoiDung = "Drop table if exists NguoiDung";
        if(oldVersion != newVersion){
            db.execSQL(dropTableKieuNguyenLieu);
            db.execSQL(dropTableNguoiDung);
            onCreate(db);
        }
    }
}
