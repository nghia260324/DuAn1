package com.example.ungdungchiasecongthucnauan.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ungdungchiasecongthucnauan.Database.DbHelper;
import com.example.ungdungchiasecongthucnauan.Model.BinhLuan;

import java.util.ArrayList;
import java.util.List;

public class BinhLuanDao {
    private SQLiteDatabase db;
    public BinhLuanDao (Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(BinhLuan obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("idCongThuc",obj.getIdCongThuc());
        contentValues.put("idNguoiDung",obj.getNguoiDung());
        contentValues.put("noiDung",obj.getNoiDung());

        return db.insert("BinhLuan",null,contentValues);
    }
    public int delete(String id) {
        return db.delete("BinhLuan","id = ?",new String[]{String.valueOf(id)});
    }
    public long update(BinhLuan obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("idCongThuc",obj.getIdCongThuc());
        contentValues.put("idNguoiDung",obj.getNguoiDung());
        contentValues.put("noiDung",obj.getNoiDung());

        return db.update("BinhLuan",contentValues,"id = ?",new String[]{String.valueOf(obj.getId())});
    }
    private List<BinhLuan> getData(String sql, String ... selectionArgs) {
        List<BinhLuan> lstBinhLuan = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()) {
            lstBinhLuan.add(new BinhLuan(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            ));
        }
        return lstBinhLuan;
    }
    public BinhLuan getID (String id) {
        String sql = "SELECT * FROM BinhLuan WHERE id = ?";
        List<BinhLuan> lstBinhLuan = getData(sql,id);
        return lstBinhLuan.get(0);
    }
    public List<BinhLuan> getAllID(String id) {
        String sql = "SELECT * FROM BinhLuan WHERE idCongThuc = ?";
        return getData(sql,id);
    }
    public int deleteAllByCongThucId(String id) {
        return db.delete("BinhLuan", "idCongThuc = ?", new String[]{id});
    }
}
