package com.example.ungdungchiasecongthucnauan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ungdungchiasecongthucnauan.Dao.AnhDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguoiDungDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguyenLieuDao;
import com.example.ungdungchiasecongthucnauan.IReturnDone;
import com.example.ungdungchiasecongthucnauan.Model.Anh;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.DanhSachNguyenLieu;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;
import com.example.ungdungchiasecongthucnauan.Model.NguyenLieu;
import com.example.ungdungchiasecongthucnauan.R;
import com.example.ungdungchiasecongthucnauan.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    Context context;
    ArrayList<CongThuc> lstCongThuc;
    NguoiDungDao nguoiDungDao;
    NguyenLieuDao nguyenLieuDao;
    AnhDao anhDao;
    IReturnDone returnDone;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public SearchAdapter(Context context, ArrayList<CongThuc> lstCongThuc,IReturnDone iReturnDone) {
        this.context = context;
        this.lstCongThuc = lstCongThuc;
        this.returnDone = iReturnDone;
        nguoiDungDao = new NguoiDungDao(context);
        nguyenLieuDao = new NguyenLieuDao(context);
        anhDao = new AnhDao(context);
    }
    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_search,null,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        CongThuc congThuc = lstCongThuc.get(position);
        NguoiDung nguoiDung = nguoiDungDao.getID(String.valueOf(congThuc.getIdNguoiDung()));
        Anh anh = new Anh();
        if (congThuc != null) {
            if (congThuc.getIdAnh() != null) {
                anh = anhDao.getID(congThuc.getIdAnh());
            }
            String listMaterial = "";
            holder.tvName.setText(congThuc.getTen());
            holder.tvUser.setText(nguoiDung.getHoTen());
            new Service().setDay(congThuc.getNgayTao(),holder.tvTime);
            new Service().setAvatar(holder.imgAvatar,nguoiDung.getAvatar());
            Glide.with(context).load(anh.getUrl()).error(R.drawable.logoapp).into(holder.imgRecipe);
            for (DanhSachNguyenLieu dsnl: congThuc.getLstNguyenLieu()) {
                NguyenLieu nguyenLieu = nguyenLieuDao.getID(String.valueOf(dsnl.getIdNguyenLieu()));
                listMaterial += nguyenLieu.getTen() + ",";
            }
            holder.tvListMaterial.setText(listMaterial);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnDone.IReturnDone(context,congThuc);
            }
        });
    }

    @Override
    public int getItemCount() {
        return !lstCongThuc.isEmpty()?lstCongThuc.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar,imgRecipe;
        TextView tvName,tvUser,tvTime,tvListMaterial;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvUser = itemView.findViewById(R.id.tv_user);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvListMaterial = itemView.findViewById(R.id.tv_listMaterial);

            imgAvatar = itemView.findViewById(R.id.img_avatar);
            imgRecipe = itemView.findViewById(R.id.img_recipe);
        }
    }
}
