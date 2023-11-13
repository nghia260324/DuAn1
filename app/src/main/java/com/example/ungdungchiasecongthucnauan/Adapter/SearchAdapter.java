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
import com.example.ungdungchiasecongthucnauan.Model.Anh;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.DanhSachNguyenLieu;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;
import com.example.ungdungchiasecongthucnauan.Model.NguyenLieu;
import com.example.ungdungchiasecongthucnauan.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    Context context;
    ArrayList<CongThuc> lstCongThuc;
    NguoiDungDao nguoiDungDao;
    NguyenLieuDao nguyenLieuDao;
    AnhDao anhDao;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public SearchAdapter(Context context, ArrayList<CongThuc> lstCongThuc) {
        this.context = context;
        this.lstCongThuc = lstCongThuc;
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
        if (congThuc.getIdAnh() != null) {
            anh = anhDao.getID(congThuc.getIdAnh());
        }
        if (congThuc != null) {
            String listMaterial = "";
            holder.tvName.setText(congThuc.getTen());
            holder.tvUser.setText(nguoiDung.getHoTen());
            holder.tvTime.setText(sdf.format(congThuc.getNgayTao()));
            setAvatar(holder.imgAvatar,nguoiDung.getAvatar());
            Glide.with(context).load(anh.getUrl()).error(R.drawable.logoapp).into(holder.imgRecipe);
            for (DanhSachNguyenLieu dsnl: congThuc.getLstNguyenLieu()) {
                NguyenLieu nguyenLieu = nguyenLieuDao.getID(String.valueOf(dsnl.getIdNguyenLieu()));
                listMaterial += nguyenLieu.getTen() + ",";
            }
            holder.tvListMaterial.setText(listMaterial);
        }
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
