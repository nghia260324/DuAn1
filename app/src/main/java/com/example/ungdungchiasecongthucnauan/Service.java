package com.example.ungdungchiasecongthucnauan;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ungdungchiasecongthucnauan.Dao.NguyenLieuDao;
import com.example.ungdungchiasecongthucnauan.Model.Anh;
import com.example.ungdungchiasecongthucnauan.Model.NguyenLieu;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Service {
    public void writeFile(Context context, String path, Object lstSearchHistory){
        try {
            FileOutputStream fos = context.openFileOutput(path,Context.MODE_PRIVATE);
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
        String name = nguyenLieu.getTen();
        switch (typeMaterial){
            case 1:
            case 2:
            case 3:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
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
            case 12:
                switch (name) {
                    case "Đường":
                    case "Bột rau câu":
                    case "Đậu phụ":
                    case "Tắc - Quất":
                    case "Khoai tím":
                    case "Trà":
                    case "Trà hoa nhài":
                    case "Đường phèn":
                    case "Bún tươi":
                    case "Mía tươi":
                    case "Phô mai lát con bò cười":
                    case "Bơ thực vật":
                        SetText(tvName,tvMass,nguyenLieu,mass,"gram");
                        break;
                    case "Nước cốt dừa":
                    case "Mật ong":
                    case "Nước nóng":
                        SetText(tvName,tvMass,nguyenLieu,mass,"ml");
                        break;
                    default:
                        break;
                }
                break;
            case 11:
                tvName.setText(nguyenLieu.getTen());
                tvMass.setText("");
            default:break;
        }
    }
    public void SetText(TextView tvN,TextView tvM,NguyenLieu nguyenLieu,int mass,String s){
        if (tvN != null) {
            tvN.setText(nguyenLieu.getTen());
            tvM.setText(" ( " + mass + "" + s +  " )");
        } else {
            tvM.setText("(" + s + ")");
        }
    }
    public void setAvatar(ImageView imageView, int index){
        switch (index){
            case 1: imageView.setImageResource(R.drawable.avatar1);break;
            case 2: imageView.setImageResource(R.drawable.avatar2);break;
            case 3: imageView.setImageResource(R.drawable.avatar3);break;
            case 4: imageView.setImageResource(R.drawable.avatar4);break;
            case 5: imageView.setImageResource(R.drawable.avatar5);break;
            case 6: imageView.setImageResource(R.drawable.avatar6);break;
            case 7: imageView.setImageResource(R.drawable.avatar7);break;
            case 8: imageView.setImageResource(R.drawable.avatar8);break;
            case 9: imageView.setImageResource(R.drawable.avatar9);break;
            case 10: imageView.setImageResource(R.drawable.avatar10);break;
            default:break;
        }
    }
    public void DialogEnlarge(Context context, Anh anh) {
        Dialog dialog = new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_enlarge);
        ImageView img_enlarge = dialog.findViewById(R.id.img_enlarge);

        Glide.with(context).load(anh.getUrl()).error(R.drawable.ct).into(img_enlarge);

        img_enlarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void setDay(Date day,TextView textView) {
        Date currentDate = new Date();
        Date formulaDate = day;
        long diffInMillies = Math.abs(currentDate.getTime() - formulaDate.getTime());
        long daysBetween = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        if (daysBetween == 0) {
            textView.setText("Hôm nay");
        } else textView.setText(daysBetween+" ngày trước");
    }
}
