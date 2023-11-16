package com.example.ungdungchiasecongthucnauan;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ungdungchiasecongthucnauan.Dao.AnhDao;
import com.example.ungdungchiasecongthucnauan.Dao.DanhSachNguyenLieuDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguoiDungDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguyenLieuDao;
import com.example.ungdungchiasecongthucnauan.Model.Anh;
import com.example.ungdungchiasecongthucnauan.Model.BuocLam;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.DanhSachNguyenLieu;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class ChiTietCongThuc {
    Context context;
    CongThuc congThuc;
    AnhDao anhDao;
    NguoiDungDao nguoiDungDao;
    DanhSachNguyenLieuDao danhSachNguyenLieuDao;
    NguyenLieuDao nguyenLieuDao;

    public ChiTietCongThuc(Context context, CongThuc congThuc) {
        this.context = context;
        this.congThuc = congThuc;
        this.anhDao = new AnhDao(context);
        this.nguoiDungDao = new NguoiDungDao(context);
        this.danhSachNguyenLieuDao = new DanhSachNguyenLieuDao(context);
        this.nguyenLieuDao = new NguyenLieuDao(context);
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void OpenDialogCreateRecipes() {
        final View dialogView = View.inflate(context,R.layout.dialog_recipe_details,null);
        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);

        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(layoutParams);
        RecyclerView rcvMaking = dialog.findViewById(R.id.rcv_making);
        ImageView imgBanner = dialog.findViewById(R.id.img_banner);
        ImageView imgAvatar = dialog.findViewById(R.id.img_avatar);
        TextView tvNameUser = dialog.findViewById(R.id.tv_nameUser);
        TextView tvFormulaName = dialog.findViewById(R.id.tv_formulaName);
        TextView tvTime = dialog.findViewById(R.id.tv_time);
        TextView tvRation = dialog.findViewById(R.id.tv_ration);
        TextView tvCookingTime = dialog.findViewById(R.id.tv_cookingTime);
        RelativeLayout layoutLine = dialog.findViewById(R.id.layout_line);
        LinearLayout layoutMaterial = dialog.findViewById(R.id.layout_material);
        LinearLayout layoutMaking = dialog.findViewById(R.id.layout_making);
        ImageButton btnBack = dialog.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Anh anh = new Anh();
        NguoiDung nguoiDung = nguoiDungDao.getID(String.valueOf(congThuc.getIdNguoiDung()));
        if (congThuc.getIdAnh() != null){
            anh = anhDao.getID(congThuc.getIdAnh());
        }
        Glide.with(context).load(anh.getUrl()).error(R.drawable.ic_picture).into(imgBanner);

        setAvatar(imgAvatar,nguoiDung.getAvatar());
        tvNameUser.setText(nguoiDung.getHoTen());
        tvFormulaName.setText(congThuc.getTen());
        tvTime.setText(sdf.format(congThuc.getNgayTao()));

        int ration = congThuc.getKhauPhan();
        int time = congThuc.getThoiGianNau();
        if (ration > 0) {
            tvRation.setText("Khẩu phần: " + congThuc.getKhauPhan() + " người.");
        } else {tvRation.setVisibility(View.GONE);}
        if (time > 0){
            tvCookingTime.setText("Thời gian nấu: " + congThuc.getThoiGianNau() + " phút.");
        } else {tvCookingTime.setVisibility(View.GONE);}
        if (ration < 0 && time < 0){
            tvRation.setVisibility(View.GONE);
            tvCookingTime.setVisibility(View.GONE);
            layoutLine.setVisibility(View.GONE);
        }
        ArrayList<DanhSachNguyenLieu> lstDSNL = congThuc.getLstNguyenLieu();
        for (DanhSachNguyenLieu dsnl:lstDSNL){
            View view = LayoutInflater.from(context).inflate(R.layout.item_search_history,null);
            TextView tvName = view.findViewById(R.id.tv_history);
            TextView tvMass = view.findViewById(R.id.tv_mass);
            new Service().SetMass(nguyenLieuDao,tvName,tvMass,dsnl.getIdNguyenLieu(),dsnl.getKhoiLuong());
            layoutMaterial.addView(view);
        }

        ArrayList<BuocLam> lstBuocLam = congThuc.getLstBuocLam();
        Collections.sort(lstBuocLam, (o1, o2) -> Integer.compare(o1.getThuTu(), o2.getThuTu()));
//        MakingAdapter makingAdapter = new MakingAdapter(context,lstBuocLam);
//        rcvMaking.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
//        rcvMaking.setLayoutManager(new GridLayoutManager(context,1));
//        rcvMaking.setNestedScrollingEnabled(false);
//        rcvMaking.setAdapter(makingAdapter);
        for (int i = 0; i < lstBuocLam.size(); i++) {
            BuocLam buocLam = lstBuocLam.get(i);
            if (buocLam != null){
                View view = LayoutInflater.from(context).inflate(R.layout.item_making,null);
                TextView tvLocation = view.findViewById(R.id.tv_location);
                EditText edtContent = view.findViewById(R.id.edt_content);
                ImageView imgPictureMaking = view.findViewById(R.id.img_selected_pictureMaking);
                Button btnRemoveMaking = view.findViewById(R.id.btn_removeMaking);

                tvLocation.setText(String.valueOf(buocLam.getThuTu()));
                edtContent.setText(buocLam.getNoiDung());

                Anh anhBuocLam = null;
                if (buocLam.getIdAnh() != null){
                    anhBuocLam = anhDao.getID(buocLam.getIdAnh());
                }
                btnRemoveMaking.setVisibility(View.GONE);
                edtContent.setFocusable(false);
                edtContent.setClickable(false);

                if (anhBuocLam != null) {
                    Glide.with(context).load(anhBuocLam.getUrl()).error(R.drawable.ic_picture).into(imgPictureMaking);
                } else {
                    imgPictureMaking.setVisibility(View.GONE);
                }
                layoutMaking.addView(view);
            }
        }
        dialog.show();
    }

    private void setAvatar(ImageView imageView,int index){
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
}
