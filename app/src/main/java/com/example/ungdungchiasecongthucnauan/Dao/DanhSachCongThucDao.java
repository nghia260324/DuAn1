package com.example.ungdungchiasecongthucnauan.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ungdungchiasecongthucnauan.Database.DbHelper;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.DanhSachCongThuc;

import java.util.ArrayList;
import java.util.List;

public class DanhSachCongThucDao {
    private SQLiteDatabase db;
    CongThucDao congThucDao;
    public DanhSachCongThucDao (Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        congThucDao = new CongThucDao(context);
    }
    public long insert(DanhSachCongThuc obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ten",obj.getTen());
        contentValues.put("idNguoiDung",obj.getIdNguoiDung());

        return db.insert("DanhSachCongThuc",null,contentValues);
    }
    public int delete(String id) {
        return db.delete("DanhSachCongThuc","id = ?",new String[]{String.valueOf(id)});
    }
    public long update(DanhSachCongThuc obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ten",obj.getTen());
        contentValues.put("idNguoiDung",obj.getIdNguoiDung());

        return db.update("DanhSachCongThuc",contentValues,"id = ?",new String[]{String.valueOf(obj.getId())});
    }
    private List<DanhSachCongThuc> getData(String sql, String ... selectionArgs) {
        List<DanhSachCongThuc> lstDSCT = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()) {
            lstDSCT.add(new DanhSachCongThuc(
               Integer.parseInt(cursor.getString(0)),
               cursor.getString(1),
               Integer.parseInt(cursor.getString(2))
            ));
        }
        return lstDSCT;
    }
    public List<DanhSachCongThuc> getAll() {
        String sql = "SELECT * FROM DANHSACHCONGTHUC";
        return getData(sql);
    }
    public List<CongThuc> getAllID(String id) {
        String sql = "select * from congthuc where id in (select ct.id from CongThuc_DSCT ctdsct Join congthuc ct where ct.id = ctdsct.idCongThuc and idDanhSachCongThuc = "+ id +")";
        return congThucDao.getData(sql,id);
    }
    public int deleteAllByCongThucId(String id) {
        return db.delete("CongThuc", "idCongThuc = ?", new String[]{id});
    }
}
