package com.example.ungdungchiasecongthucnauan.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ungdungchiasecongthucnauan.Dao.AnhDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguoiDungDao;
import com.example.ungdungchiasecongthucnauan.Model.Anh;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.DanhSachNguyenLieu;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;
import com.example.ungdungchiasecongthucnauan.Model.NguyenLieu;
import com.example.ungdungchiasecongthucnauan.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CongThuc> datten;
    private NguoiDungDao nguoiDungDao;
    private AnhDao anhDao;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public BannerAdapter(Context context, ArrayList<CongThuc> datten) {
        this.context = context;
        this.datten = datten;
        this.nguoiDungDao = new NguoiDungDao(context);
        this.anhDao = new AnhDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_banner,null,false);
        return new BannerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    CongThuc congThuc = datten.get(position);
    NguoiDung nguoiDung = nguoiDungDao.getID(String.valueOf(congThuc.getIdNguoiDung()));
    Anh anh = new Anh();
        if (congThuc.getIdAnh() != null) {
            anh = anhDao.getID(congThuc.getIdAnh());
        }
        if (congThuc != null) {
            String listMaterial = "";
            holder.tv_nameuser.setText(congThuc.getTen());
            holder.tv_namedish.setText(nguoiDung.getHoTen());
            holder.tv_date.setText(sdf.format(congThuc.getNgayTao()));
            setAvatar(holder.img_avata,nguoiDung.getAvatar());
            Glide.with(context).load(anh.getUrl()).error(R.drawable.logoapp).into(holder.img_bgr);


        }
    }

    @Override
    public int getItemCount() {
        return datten != null? datten.size():0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_bgr, img_avata;
        TextView tv_nameuser, tv_namedish, tv_date;
        ImageButton btn_save;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_bgr = itemView.findViewById(R.id.img_bgr);
            img_avata = itemView.findViewById(R.id.img_avata);
            tv_nameuser = itemView.findViewById(R.id.tv_nameuser);
            tv_namedish = itemView.findViewById(R.id.tv_namedish);
            tv_date = itemView.findViewById(R.id.tv_date);
            btn_save = itemView.findViewById(R.id.btn_save);


        }
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
