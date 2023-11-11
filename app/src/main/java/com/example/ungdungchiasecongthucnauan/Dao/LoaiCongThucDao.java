package com.example.ungdungchiasecongthucnauan.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ungdungchiasecongthucnauan.Database.DbHelper;
import com.example.ungdungchiasecongthucnauan.Model.LoaiCongThuc;

import java.util.ArrayList;
import java.util.List;

public class LoaiCongThucDao {
    private SQLiteDatabase db;

    public LoaiCongThucDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    private List<LoaiCongThuc> getData(String sql, String ... selectionArgs) {
        List<LoaiCongThuc> lstLoaiCongThuc = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()) {
            lstLoaiCongThuc.add(new LoaiCongThuc(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1)
            ));
        }
        return lstLoaiCongThuc;
    }
    public List<LoaiCongThuc> getAll() {
        String sql = "SELECT * FROM LOAICONGTHUC";
        return getData(sql);
    }
    public LoaiCongThuc getID(String id) {
        String sql = "SELECT * FROM LOAICONGTHUC WHERE id = ?";
        List<LoaiCongThuc> lstLoaiCongThuc = getData(sql, id);
        return lstLoaiCongThuc.get(0);
    }
    public LoaiCongThuc getTen(String id) {
        String sql = "SELECT * FROM LOAICONGTHUC WHERE ten = ?";
        List<LoaiCongThuc> lstLoaiCongThuc = getData(sql, id);
        return lstLoaiCongThuc.get(0);
    }
}
