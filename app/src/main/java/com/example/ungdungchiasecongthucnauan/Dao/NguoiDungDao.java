package com.example.ungdungchiasecongthucnauan.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ungdungchiasecongthucnauan.Database.DbHelper;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class NguoiDungDao {
    private SQLiteDatabase db;
    public NguoiDungDao (Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(NguoiDung obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",obj.getId());
        contentValues.put("hoTen", obj.getHoTen());
        contentValues.put("email",obj.getEmail());
        contentValues.put("matKhau",obj.getMatKhau());
        contentValues.put("trangThai",obj.getTrangThai());
        contentValues.put("phanQuyen",obj.getPhanQuyen());
        contentValues.put("avatar",obj.getAvatar());



        return db.insert("NguoiDung",null,contentValues);
    }
    public long update(NguoiDung obj) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("NGUOI_DUNG");
        databaseReference.child(obj.getId()).child("avatar").setValue(obj.getAvatar());
        ContentValues contentValues = new ContentValues();
        contentValues.put("avatar",obj.getAvatar());
        contentValues.put("matKhau", obj.getMatKhau());
        contentValues.put("trangThai",obj.getTrangThai());

        return db.update("NguoiDung",contentValues,"id = ?",new String[]{String.valueOf(obj.getId())});
    }
    private List<NguoiDung> getData(String sql, String ... selectionArgs) {
        List<NguoiDung> lstNguoiDung = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()) {
            lstNguoiDung.add(new NguoiDung(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    Integer.parseInt(cursor.getString(4)),
                    Integer.parseInt(cursor.getString(5)),
                    Integer.parseInt(cursor.getString(6))
            ));
        }
        return lstNguoiDung;
    }
    public List<NguoiDung> getAll() {
        String sql = "SELECT * FROM NguoiDung";
        return getData(sql);
    }
    public NguoiDung getID (String id) {
        String sql = "SELECT * FROM NguoiDung WHERE id = ?";
        List<NguoiDung> lstNguoiDung = getData(sql,id);
        return lstNguoiDung.get(0);
    }
    public long checkLogin(String email,String password) {
        Cursor cursor = db.rawQuery("SELECT * FROM NguoiDung WHERE email = ? AND matKhau = ?",new String[]{email,password});
        if (cursor.getCount() > 0) {
            return 1;
        } else {
            return -1;
        }
    }
    public NguoiDung getNguoiDung (String email,String password) {
        List<NguoiDung> lstNguoiDung = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM NguoiDung WHERE email = ? AND matKhau = ?",new String[]{email,password});
        while (cursor.moveToNext()) {
            lstNguoiDung.add(new NguoiDung(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    Integer.parseInt(cursor.getString(4)),
                    Integer.parseInt(cursor.getString(5)),
                    Integer.parseInt(cursor.getString(6))
            ));
        }
        if(!lstNguoiDung.isEmpty()) {
            return lstNguoiDung.get(0);
        }
        return null;
    }
    public NguoiDung getNguoiDungFromEmail (String email) {
        List<NguoiDung> lstNguoiDung = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM NguoiDung WHERE email = ?",new String[]{email});
        while (cursor.moveToNext()) {
            lstNguoiDung.add(new NguoiDung(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    Integer.parseInt(cursor.getString(4)),
                    Integer.parseInt(cursor.getString(5)),
                    Integer.parseInt(cursor.getString(6))
            ));
        }
        if(!lstNguoiDung.isEmpty()) {
            return lstNguoiDung.get(0);
        }
        return null;
    }

    public List<NguoiDung> search(String keyword) {
        List<NguoiDung> resultList = new ArrayList<>();
        Cursor cursor=db.rawQuery("SELECT * FROM NguoiDung WHERE hoTen= ?",new String[]{keyword});
        while (cursor.moveToNext()) {
            resultList.add(new NguoiDung(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    Integer.parseInt(cursor.getString(4)),
                    Integer.parseInt(cursor.getString(5)),
                    Integer.parseInt(cursor.getString(6))
            ));
        }
        return resultList;
    }

    public List<NguoiDung> getAllND() {
        String sql = "SELECT * FROM NguoiDung WHERE phanQuyen = 1 or phanQuyen =2";
        return getData(sql);
    }
}
