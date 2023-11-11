package com.example.ungdungchiasecongthucnauan.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ungdungchiasecongthucnauan.Database.DbHelper;
import com.example.ungdungchiasecongthucnauan.Model.NguyenLieu;

import java.util.ArrayList;
import java.util.List;

public class NguyenLieuDao {
    private SQLiteDatabase db;

    public NguyenLieuDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    private List<NguyenLieu> getData(String sql, String ... selectionArgs) {
        List<NguyenLieu> lstNguyenLieu = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()) {
            lstNguyenLieu.add(new NguyenLieu(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    Integer.parseInt(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3)),
                    Integer.parseInt(cursor.getString(4))
            ));
        }
        return lstNguyenLieu;
    }
    public List<NguyenLieu> getAll() {
        String sql = "SELECT * FROM NGUYENLIEU";
        return getData(sql);
    }
    public NguyenLieu getID(String id) {
        String sql = "SELECT * FROM NGUYENLIEU WHERE id = ?";
        List<NguyenLieu> lstNL = getData(sql, id);
        return lstNL.get(0);
    }
    public NguyenLieu getTen(String ten) {
        String sql = "SELECT * FROM NGUYENLIEU WHERE ten = ?";
        List<NguyenLieu> lstNL = getData(sql, ten);
        return lstNL.get(0);
    }
}
