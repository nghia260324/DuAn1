package com.example.ungdungchiasecongthucnauan.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ungdungchiasecongthucnauan.Database.DbHelper;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CongThucDao {
    private SQLiteDatabase db;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public CongThucDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    private List<CongThuc> getData(String sql, String ... selectionArgs) {
        List<CongThuc> lstCongThuc = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()) {
            CongThuc congThuc = new CongThuc();
            congThuc.setId(cursor.getString(0));
            congThuc.setTen(cursor.getString(1));
            congThuc.setIdAnh(cursor.getString(2));
            congThuc.setIdNguoiDung(Integer.parseInt(cursor.getString(3)));
            congThuc.setKhauPhan(Integer.parseInt(cursor.getString(4)));
            congThuc.setThoiGianNau(Integer.parseInt(cursor.getString(5)));
            try {
                congThuc.setNgayTao(sdf.parse(cursor.getString(6)));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            congThuc.setIdLoai(Integer.parseInt(cursor.getString(7)));
            congThuc.setTrangThai(Integer.parseInt(cursor.getString(8)));
            lstCongThuc.add(congThuc);
        }
        return lstCongThuc;
    }
    public long insert(CongThuc obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",obj.getId());
        contentValues.put("ten",obj.getTen());
        contentValues.put("idAnh",obj.getIdAnh());
        contentValues.put("idnguoiDung",obj.getIdNguoiDung());
        contentValues.put("khauPhan",obj.getKhauPhan());
        contentValues.put("thoiGianNau",obj.getThoiGianNau());
        contentValues.put("ngay",sdf.format(obj.getNgayTao()));
        contentValues.put("idLoaiCongThuc",obj.getIdLoai());
        contentValues.put("trangThai",obj.getTrangThai());

        return db.insert("CongThuc",null,contentValues);
    }
    public int delete(String id) {
        return db.delete("CongThuc","id = ?",new String[]{String.valueOf(id)});
    }
    public long update(CongThuc obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",obj.getId());
        contentValues.put("ten",obj.getTen());
        contentValues.put("idAnh",obj.getIdAnh());
        contentValues.put("idnguoiDung",obj.getIdNguoiDung());
        contentValues.put("khauPhan",obj.getKhauPhan());
        contentValues.put("thoiGianNau",obj.getThoiGianNau());
        contentValues.put("ngay",sdf.format(obj.getNgayTao()));
        contentValues.put("idLoaiCongThuc",obj.getIdLoai());
        contentValues.put("trangThai",obj.getTrangThai());
        return db.update("CongThuc",contentValues,"id = ?",new String[]{String.valueOf(obj.getId())});
    }
    public List<CongThuc> getAll() {
        String sql = "SELECT * FROM CongThuc";
        return getData(sql);
    }
    public CongThuc getID(String id) {
        String sql = "SELECT * FORM CongThuc WHERE id = ?";
        List<CongThuc> lstCongThuc = getData(sql, id);
        return lstCongThuc.get(0);
    }

}
