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
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ungdungchiasecongthucnauan.Dao.AnhDao;
import com.example.ungdungchiasecongthucnauan.Dao.BinhLuanDao;
import com.example.ungdungchiasecongthucnauan.Dao.DanhSachNguyenLieuDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguoiDungDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguyenLieuDao;
import com.example.ungdungchiasecongthucnauan.Model.Anh;
import com.example.ungdungchiasecongthucnauan.Model.BinhLuan;
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
    RecyclerView rcvMaking;
    ImageView imgBanner,imgAvatar;
    ImageButton btnBack,btnSend;
    TextView tvNameUser,tvFormulaName,tvTime,tvRation,tvCookingTime,tvComment,tvNoneComment;
    EditText edtContent;
    RelativeLayout layoutLine,lineComment;
    LinearLayout layoutMaterial,layoutMaking,layoutComment,layoutAddComment;
    MainActivity mainActivity;
    BinhLuanDao binhLuanDao;
    public ChiTietCongThuc(Context context, CongThuc congThuc,MainActivity mainActivity) {
        this.context = context;
        this.congThuc = congThuc;
        this.anhDao = new AnhDao(context);
        this.nguoiDungDao = new NguoiDungDao(context);
        this.danhSachNguyenLieuDao = new DanhSachNguyenLieuDao(context);
        this.nguyenLieuDao = new NguyenLieuDao(context);
        this.mainActivity = mainActivity;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void OpenDialogCreateRecipes(int type) {
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

        initUI(dialog);

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

        switch (type) {
            case 0:
                ChiTiet(dialog);break;
            case 1:
                Edit();break;
            case 2:
                Delete();break;
            default:break;
        }

        dialog.show();
    }
    private void Delete() {

    }
    private void Edit() {
        HideComment();

    }

    private void HideComment() {
        tvNoneComment.setVisibility(View.GONE);
        tvComment.setVisibility(View.GONE);
        layoutComment.setVisibility(View.GONE);
        layoutAddComment.setVisibility(View.GONE);
        lineComment.setVisibility(View.GONE);
    }

    private void initUI(Dialog dialog) {
//        rcvMaking = dialog.findViewById(R.id.rcv_making);
        imgBanner = dialog.findViewById(R.id.img_banner);
        imgAvatar = dialog.findViewById(R.id.img_avatar);
        tvNameUser = dialog.findViewById(R.id.tv_nameUser);
        tvFormulaName = dialog.findViewById(R.id.tv_formulaName);
        tvTime = dialog.findViewById(R.id.tv_time);
        tvRation = dialog.findViewById(R.id.tv_ration);
        tvCookingTime = dialog.findViewById(R.id.tv_cookingTime);
        layoutLine = dialog.findViewById(R.id.layout_line);
        lineComment = dialog.findViewById(R.id.line_comment);
        layoutMaterial = dialog.findViewById(R.id.layout_material);
        layoutMaking = dialog.findViewById(R.id.layout_making);
        btnBack = dialog.findViewById(R.id.btn_back);
        tvComment = dialog.findViewById(R.id.tv_comment);
        tvNoneComment = dialog.findViewById(R.id.tv_noneComment);
        layoutComment = dialog.findViewById(R.id.layout_comment);
        layoutAddComment = dialog.findViewById(R.id.layout_addComment);
        btnSend = dialog.findViewById(R.id.btn_send);

        edtContent = dialog.findViewById(R.id.edt_content);

        binhLuanDao = new BinhLuanDao(context);
    }

    private void ChiTiet(Dialog dialog) {
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

        if (!congThuc.getLstBinhLuan().isEmpty()) {
            ArrayList<BinhLuan> lstBinhLuan = congThuc.getLstBinhLuan();
            tvNoneComment.setVisibility(View.GONE);
            layoutComment.setVisibility(View.VISIBLE);
            tvComment.setText("Bình luận(" + congThuc.getLstBinhLuan().size() + ")");

            for (int i = 0; i < lstBinhLuan.size(); i++){
                BinhLuan binhLuan = lstBinhLuan.get(i);
                NguoiDung userComment = nguoiDungDao.getID(String.valueOf(binhLuan.getNguoiDung()));
                View view = LayoutInflater.from(context).inflate(R.layout.item_comment,null);
                TextView tvContentComment = view.findViewById(R.id.tv_content);
                ImageView imgAvatarUserComment = view.findViewById(R.id.img_avatarUserComment);
                setAvatar(imgAvatarUserComment,userComment.getAvatar());
                tvContentComment.setText(binhLuan.getNoiDung());
                layoutComment.addView(view);
            }

        } else {
            tvNoneComment.setVisibility(View.VISIBLE);
            tvComment.setText("Bình luận(0)");
            layoutComment.setVisibility(View.GONE);
        }
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edtContent.getText().toString().trim();
                if (!content.isEmpty()) {
                    NguoiDung nguoiDung = mainActivity.getUser();
                    BinhLuan binhLuan = new BinhLuan();
                    binhLuan.setId(0);
                    binhLuan.setIdCongThuc(congThuc.getId());
                    binhLuan.setNguoiDung(nguoiDung.getId());
                    binhLuan.setNoiDung(content);
                    binhLuanDao.insert(binhLuan);
                    Toast.makeText(context, "Đã thêm bình luận !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Vui lòng nhập nội dung !", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
