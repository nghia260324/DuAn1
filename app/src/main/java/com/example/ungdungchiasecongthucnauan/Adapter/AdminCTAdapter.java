package com.example.ungdungchiasecongthucnauan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ungdungchiasecongthucnauan.Dao.AnhDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguoiDungDao;
import com.example.ungdungchiasecongthucnauan.Model.Anh;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;
import com.example.ungdungchiasecongthucnauan.R;
import com.example.ungdungchiasecongthucnauan.Service;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdminCTAdapter extends RecyclerView.Adapter<AdminCTAdapter.ViewHolder>{
    List<CongThuc> congThucList;
    Context context;
    AnhDao anhDao;
    NguoiDungDao nguoiDungDao;


    public AdminCTAdapter(Context context,List<CongThuc> congThucList) {
        this.context = context;
        this.congThucList = congThucList;
        this.anhDao=new AnhDao(context);
        this.nguoiDungDao=new NguoiDungDao(context);

    }

    public void AdminCTAdapter( List<CongThuc> congThucList) {
        this.congThucList = congThucList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_congthuc_admin,null,false);
        return new AdminCTAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          CongThuc congThuc=congThucList.get(position);
          Anh anh=anhDao.getID(congThucList.get(position).getIdAnh());
          NguoiDung nguoiDung=nguoiDungDao.getID(congThucList.get(position).getIdNguoiDung());

          Picasso.get().load(anh.getUrl()).into(holder.img_bgr);

          new Service().setAvatar(holder.img_avata,nguoiDung.getAvatar());

          holder.tv_nameuser.setText(nguoiDung.getHoTen());
          holder.tv_namedish.setText(congThuc.getTen());


          Date currentDate=new Date();
          Date formulaDate=congThuc.getNgayTao();
          long diffInMillies = Math.abs(currentDate.getTime() - formulaDate.getTime());
          long daysBetween = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

          holder.tv_date.setText(daysBetween+" ngày trước");

          //thieu chuc nang nut 3 cham

    }

    @Override
    public int getItemCount() {
        return congThucList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_bgr, img_avata;
        TextView tv_nameuser, tv_namedish, tv_date;
        ImageButton btn_option;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_bgr = itemView.findViewById(R.id.img_bgr);
            img_avata = itemView.findViewById(R.id.img_avata);
            tv_nameuser = itemView.findViewById(R.id.tv_nameuser);
            tv_namedish = itemView.findViewById(R.id.tv_namedish);
            tv_date = itemView.findViewById(R.id.tv_date);
            btn_option = itemView.findViewById(R.id.btn_option);
        }
    }
}
