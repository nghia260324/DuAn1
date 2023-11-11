package com.example.ungdungchiasecongthucnauan.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ungdungchiasecongthucnauan.Database.DbHelper;
import com.example.ungdungchiasecongthucnauan.Model.BuocLam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BuocLamDao {
    private SQLiteDatabase db;

    public BuocLamDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    private List<BuocLam> getData(String sql, String ... selectionArgs) {
        List<BuocLam> lstBuocLam = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()) {
            lstBuocLam.add(new BuocLam(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                Integer.parseInt(cursor.getString(4))
            ));
        }
        return lstBuocLam;
    }
    public long insert(BuocLam obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("idCongThuc",obj.getIdCongThuc());
        contentValues.put("noiDung",obj.getNoiDung());
        contentValues.put("idAnh",obj.getIdAnh());
        contentValues.put("thuTu",obj.getThuTu());
        return db.insert("BuocLam",null,contentValues);
    }
    public int delete(String id) {
        return db.delete("BuocLam","id = ?",new String[]{String.valueOf(id)});
    }
    public List<BuocLam> getAll() {
        String sql = "SELECT * FROM BuocLam";
        return getData(sql);
    }
    public List<BuocLam> getAllID(String id) {
        String sql = "SELECT * FROM BuocLam WHERE idCongThuc = ?";
        List<BuocLam> lstBuocLam = getData(sql, id);
        Collections.sort(lstBuocLam, new Comparator<BuocLam>() {
            @Override
            public int compare(BuocLam o1, BuocLam o2) {
                return o1.getThuTu() < o2.getThuTu()?1:-1;
            }
        });
        return lstBuocLam;
    }
    public BuocLam getID(String id) {
        String sql = "SELECT * FROM BuocLam WHERE id = ?";
        List<BuocLam> lstBuocLam = getData(sql, id);
        return lstBuocLam.get(0);
    }
    public long update(BuocLam obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",obj.getId());
        contentValues.put("idCongThuc",obj.getIdCongThuc());
        contentValues.put("noiDung",obj.getNoiDung());
        contentValues.put("idAnh",obj.getIdAnh());
        contentValues.put("thuTu",obj.getThuTu());
        return db.update("BuocLam",contentValues,"id = ?",new String[]{String.valueOf(obj.getId())});
    }
}
