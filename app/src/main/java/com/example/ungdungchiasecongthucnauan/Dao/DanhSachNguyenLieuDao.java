package com.example.ungdungchiasecongthucnauan.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ungdungchiasecongthucnauan.Database.DbHelper;
import com.example.ungdungchiasecongthucnauan.Model.DanhSachNguyenLieu;

import java.util.ArrayList;
import java.util.List;

public class DanhSachNguyenLieuDao {
    private SQLiteDatabase db;

    public DanhSachNguyenLieuDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    private List<DanhSachNguyenLieu> getData(String sql, String ... selectionArgs) {
        List<DanhSachNguyenLieu> lstDSNL = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()) {
            lstDSNL.add(new DanhSachNguyenLieu(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    Integer.parseInt(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3))
            ));
        }
        return lstDSNL;
    }
    public long insert(DanhSachNguyenLieu obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("idCongThuc",obj.getIdCongThuc());
        contentValues.put("idNguyenLieu",obj.getIdNguyenLieu());
        contentValues.put("khoiLuong",obj.getKhoiLuuong());

        return db.insert("DanhSachNguyenLieu",null,contentValues);
    }
    public int delete(String id) {
        return db.delete("DanhSachNguyenLieu","id = ?",new String[]{String.valueOf(id)});
    }
    public long update(DanhSachNguyenLieu obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("idCongThuc",obj.getIdCongThuc());
        contentValues.put("idNguyenLieu",obj.getIdNguyenLieu());
        contentValues.put("khoiLuong",obj.getKhoiLuuong());
        return db.update("DanhSachNguyenLieu",contentValues,"id = ?",new String[]{String.valueOf(obj.getId())});
    }
    public List<DanhSachNguyenLieu> getAll() {
        String sql = "SELECT * FROM DANHSACHNGUYENLIEU";
        return getData(sql);
    }
    public DanhSachNguyenLieu getID(String id) {
        String sql = "SELECT * FORM DANHSACHNGUYENLIEU WHERE ID = ?";
        List<DanhSachNguyenLieu> lstDSNL = getData(sql, id);
        return lstDSNL.get(0);
    }
}
