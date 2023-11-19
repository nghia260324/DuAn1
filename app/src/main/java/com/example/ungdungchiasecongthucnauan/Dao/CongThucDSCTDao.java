package com.example.ungdungchiasecongthucnauan.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ungdungchiasecongthucnauan.Database.DbHelper;
import com.example.ungdungchiasecongthucnauan.Model.CT_DSCT;

import java.util.ArrayList;
import java.util.List;

public class CongThucDSCTDao {
    private SQLiteDatabase db;
    CongThucDao congThucDao;
    public CongThucDSCTDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        congThucDao = new CongThucDao(context);
    }
    public long insert(String idCT,int idDSCT) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("idCongThuc",idCT);
        contentValues.put("idDanhSachCongThuc",idDSCT);
        return db.insert("CongThuc_DSCT",null,contentValues);
    }
    public int delete(String id) {
        return db.delete("CongThuc_DSCT","id = ?",new String[]{String.valueOf(id)});
    }
    private List<CT_DSCT> getData(String sql, String ... selectionArgs) {
        List<CT_DSCT> lstDSCT = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()) {
            lstDSCT.add(new CT_DSCT(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    Integer.parseInt(cursor.getString(2))
            ));
        }
        return lstDSCT;
    }
    public List<CT_DSCT> getAll() {
        String sql = "SELECT * FROM CongThuc_DSCT";
        return getData(sql);
    }
    public int deleteID(String id) {
        return db.delete("CongThuc_DSCT","id = ?",new String[]{String.valueOf(id)});
    }
    public CT_DSCT getID(String id) {
        String sql = "SELECT * FROM CongThuc_DSCT WHERE idDanhSachCongThuc = ?";
        List<CT_DSCT> lstCongThuc = getData(sql, id);
        if (lstCongThuc != null && !lstCongThuc.isEmpty()) {
            return lstCongThuc.get(0);
        }
        return null;
    }

}
