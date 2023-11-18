package com.example.ungdungchiasecongthucnauan.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ungdungchiasecongthucnauan.Database.DbHelper;
import com.example.ungdungchiasecongthucnauan.Model.Anh;

import java.util.ArrayList;
import java.util.List;

public class AnhDao {
    private SQLiteDatabase db;

    public AnhDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    private List<Anh> getData(String sql, String ... selectionArgs) {
        List<Anh> lstAnh = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()) {
            lstAnh.add(new Anh(
                    cursor.getString(0),
                    cursor.getString(1)
            ));
        }
        return lstAnh;
    }
    public long insert(Anh obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",obj.getId());
        contentValues.put("url",obj.getUrl());
        return db.insert("Anh",null,contentValues);
    }
    public long update(Anh obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("url",obj.getUrl());

        return db.update("Anh",contentValues,"id = ?",new String[]{String.valueOf(obj.getId())});
    }
    public int delete(String id) {
        return db.delete("ANH","id = ?",new String[]{String.valueOf(id)});
    }
    public List<Anh> getAll() {
        String sql = "SELECT * FROM ANH";
        return getData(sql);
    }
    public Anh getID(String id) {
        String sql = "SELECT * FROM ANH WHERE id = ?";
        List<Anh> lstAnh = getData(sql, id);
        return lstAnh.get(0);
    }
}
