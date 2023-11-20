package com.example.ungdungchiasecongthucnauan.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ungdungchiasecongthucnauan.Database.DbHelper;
import com.example.ungdungchiasecongthucnauan.Model.BinhLuan;
import com.example.ungdungchiasecongthucnauan.Model.BuocLam;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.DanhSachNguyenLieu;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CongThucDao {
    private SQLiteDatabase db;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    BuocLamDao buocLamDao;
    DanhSachNguyenLieuDao danhSachNguyenLieuDao;
    BinhLuanDao binhLuanDao;
    public CongThucDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        buocLamDao = new BuocLamDao(context);
        danhSachNguyenLieuDao = new DanhSachNguyenLieuDao(context);
        binhLuanDao = new BinhLuanDao(context);
    }
    public List<CongThuc> getData(String sql, String ... selectionArgs) {
        List<CongThuc> lstCongThuc = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()) {
            CongThuc congThuc = new CongThuc();
            congThuc.setId(cursor.getString(0));
            congThuc.setTen(cursor.getString(1));
            congThuc.setIdAnh(cursor.getString(2));
            congThuc.setIdNguoiDung(cursor.getString(3));
            congThuc.setKhauPhan(Integer.parseInt(cursor.getString(4)));
            congThuc.setThoiGianNau(Integer.parseInt(cursor.getString(5)));
            try {
                congThuc.setNgayTao(sdf.parse(cursor.getString(6)));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            congThuc.setIdLoai(Integer.parseInt(cursor.getString(7)));
            congThuc.setTrangThai(Integer.parseInt(cursor.getString(8)));
            congThuc.setLstBuocLam((ArrayList<BuocLam>) buocLamDao.getAllID(cursor.getString(0)));

            congThuc.setLstNguyenLieu((ArrayList<DanhSachNguyenLieu>) danhSachNguyenLieuDao.getAllID(cursor.getString(0)));
//            if (!binhLuanDao.getAllID(cursor.getString(0)).isEmpty()) {
//
//            } else congThuc.setLstBinhLuan(null);
            congThuc.setLstBinhLuan((ArrayList<BinhLuan>) binhLuanDao.getAllID(cursor.getString(0)));
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
    public List<CongThuc> getAllMyRecipes(NguoiDung nguoiDung) {
        String sql = "SELECT * FROM CongThuc Where idnguoiDung = " + nguoiDung.getId();
        return getData(sql);
    }
    public List<String> getAllListRecipes(String idDSCT) {
        ArrayList<String> lstIDCongThuc = new ArrayList<>();
        String sql = "SELECT idCongThuc FROM CongThuc_DSCT WHERE idDanhSachCongThuc = " + idDSCT;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            lstIDCongThuc.add(id);
        }
        cursor.close();
        return lstIDCongThuc;
    }

    public CongThuc getID(String id) {
        String sql = "SELECT * FROM CongThuc WHERE id = ?";
        List<CongThuc> lstCongThuc = getData(sql, id);
        if (lstCongThuc != null && !lstCongThuc.isEmpty()) {
            return lstCongThuc.get(0);
        }
        return null;
    }
    public List<CongThuc> getCongThucByIngredientIds(List<Integer> ingredientIds) {
        List<CongThuc> lstCongThuc = new ArrayList<>();
        ArrayList<String> lstIDCongThuc = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ingredientIds.size(); i++) {
            if (i > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(ingredientIds.get(i));
        }

        String sql = "SELECT CongThuc.id, COUNT(DanhSachNguyenLieu.idCongThuc) AS numIngredients " +
                "FROM CongThuc " +
                "JOIN DanhSachNguyenLieu ON CongThuc.id = DanhSachNguyenLieu.idCongThuc " +
                "WHERE DanhSachNguyenLieu.idNguyenLieu IN (" + stringBuilder.toString() + ") " +
                "GROUP BY CongThuc.id " +
                "ORDER BY numIngredients DESC " +
                "LIMIT 10";

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            lstIDCongThuc.add(id);
        }
        cursor.close();

        if (!lstIDCongThuc.isEmpty()) {
            for (String s:lstIDCongThuc) {
                lstCongThuc.add(getID(s));
            }
        }
        return lstCongThuc;
    }
}
