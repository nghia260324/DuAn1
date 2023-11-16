package com.example.ungdungchiasecongthucnauan;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.example.ungdungchiasecongthucnauan.Dao.NguyenLieuDao;
import com.example.ungdungchiasecongthucnauan.Model.NguyenLieu;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Service {
    public void writeFile(Context context, String path, Object lstSearchHistory){
        try {
            Log.e("Check a","");
            FileOutputStream fos = context.openFileOutput(path,Context.MODE_PRIVATE);
            Log.e("Check b","");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(lstSearchHistory);
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Object readFile(Context context, String path){
        Object lstSearchHistory = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            lstSearchHistory =  ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstSearchHistory;
    }

    public void SetMass(NguyenLieuDao nguyenLieuDao, TextView tvName, TextView tvMass, int type, int mass) {
        NguyenLieu nguyenLieu = nguyenLieuDao.getID(String.valueOf(type));
        int typeMaterial = nguyenLieu.getKieu();
        switch (typeMaterial){
            case 1:
            case 2:
            case 3:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
                if (tvName != null) {
                    tvName.setText(nguyenLieu.getTen());
                    tvMass.setText(" ( " + mass + "gram )");
                } else {
                    tvMass.setText("(gram)");
                }
                break;
            case 4:
                if (tvName != null) {
                    tvName.setText(nguyenLieu.getTen());
                    tvMass.setText(" ( " + mass + "quả )");
                } else {
                    tvMass.setText("(quả)");
                }
                break;
            case 5:
                if (tvName != null) {
                    tvName.setText(nguyenLieu.getTen());
                    tvMass.setText(" ( " + mass + "ml )");
                } else {
                    tvMass.setText("(ml)");
                }
                break;
//            case 11: break;
            default:break;
        }
    }
}
