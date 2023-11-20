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

import com.bumptech.glide.Glide;
import com.example.ungdungchiasecongthucnauan.ChiTietCongThuc;
import com.example.ungdungchiasecongthucnauan.Dao.AnhDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguoiDungDao;
import com.example.ungdungchiasecongthucnauan.MainActivity;
import com.example.ungdungchiasecongthucnauan.Model.Anh;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;
import com.example.ungdungchiasecongthucnauan.R;
import com.example.ungdungchiasecongthucnauan.SaveRecipe;
import com.example.ungdungchiasecongthucnauan.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CongThuc> lstCongthuc;
    private NguoiDungDao nguoiDungDao;
    private AnhDao anhDao;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    MainActivity mainActivity;

    public BannerAdapter(Context context, ArrayList<CongThuc> lstCongthuc, MainActivity mainActivity) {
        this.context = context;
        this.lstCongthuc = lstCongthuc;
        this.nguoiDungDao = new NguoiDungDao(context);
        this.anhDao = new AnhDao(context);
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_banner,null,false);
        return new BannerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CongThuc congThuc = lstCongthuc.get(position);
        NguoiDung nguoiDung = nguoiDungDao.getID(String.valueOf(congThuc.getIdNguoiDung()));
        if (congThuc != null) {
            Anh anh = new Anh();
            if (congThuc.getIdAnh() != null) {
                anh = anhDao.getID(congThuc.getIdAnh());
            }
            holder.tv_nameuser.setText(congThuc.getTen());
            holder.tv_namedish.setText(nguoiDung.getHoTen());
            holder.tv_date.setText(sdf.format(congThuc.getNgayTao()));
            Glide.with(context).load(anh.getUrl()).error(R.drawable.ct).into(holder.img_bgr);
            new Service().setAvatar(holder.img_avata,nguoiDung.getAvatar());
        }
        holder.btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SaveRecipe().OpenDialogSaveRecipe(context,congThuc,mainActivity);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChiTietCongThuc ctct = new ChiTietCongThuc(context,congThuc,mainActivity);
                ctct.OpenDialogCreateRecipes();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstCongthuc != null? lstCongthuc.size():0;
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
}
