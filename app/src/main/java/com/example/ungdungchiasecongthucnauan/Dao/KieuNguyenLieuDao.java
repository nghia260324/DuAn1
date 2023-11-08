package com.example.ungdungchiasecongthucnauan.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ungdungchiasecongthucnauan.Database.DbHelper;
import com.example.ungdungchiasecongthucnauan.Model.KieuNguyenLieu;

import java.util.ArrayList;
import java.util.List;

public class KieuNguyenLieuDao {
    private SQLiteDatabase db;

    public KieuNguyenLieuDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    private List<KieuNguyenLieu> getData(String sql, String ... selectionArgs) {
        List<KieuNguyenLieu> lstKieuNguyenLieu = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()) {
            lstKieuNguyenLieu.add(new KieuNguyenLieu(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1)
            ));
        }
        return lstKieuNguyenLieu;
    }
    public List<KieuNguyenLieu> getAll() {
        String sql = "SELECT * FROM KIEUNGUYENLIEU";
        return getData(sql);
    }
    public KieuNguyenLieu getID(String id) {
        String sql = "SELECT * FORM KIEUNGUYENLIEU WHERE id = ?";
        List<KieuNguyenLieu> lstKNL = getData(sql, id);
        return lstKNL.get(0);
    }
}
