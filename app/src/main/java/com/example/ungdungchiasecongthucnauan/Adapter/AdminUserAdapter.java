package com.example.ungdungchiasecongthucnauan.Adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ungdungchiasecongthucnauan.Dao.AnhDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguoiDungDao;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;
import com.example.ungdungchiasecongthucnauan.R;

import java.util.List;

public class AdminUserAdapter extends RecyclerView.Adapter<AdminUserAdapter.ViewHolder> {
    NguoiDungDao nguoiDungDao;
    Context context;
    List<NguoiDung> list;


    public AdminUserAdapter(Context context, List<NguoiDung> list) {
        this.context = context;
        this.list = list;
        this.nguoiDungDao=new NguoiDungDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_admin,null,false);
        return new AdminUserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NguoiDung nd=list.get(position);

        holder.tv_hoten.setText("Họ tên: "+nd.getHoTen());
        if(nd.getTrangThai()==0){
            holder.tv_trangthai.setText("Trạng thái: Đang hoạt động");
        }else {
            holder.tv_trangthai.setText("Trạng thái: Đang khóa");
        }

        holder.img_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu(view,nd);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_option;
        TextView tv_hoten, tv_trangthai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_hoten=itemView.findViewById(R.id.tv_hoten);
            tv_trangthai=itemView.findViewById(R.id.tv_trangthai);
            img_option=itemView.findViewById(R.id.ic_option);

        }
    }

    private void PopupMenu(View view,NguoiDung nd){
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_admin, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
//                if (item.getItemId() == R.id.delete_option) {
//                    congThucDao.delete(congThuc.getId());
//
//                    congThucList.clear();
//                    congThucList = congThucDao.getAll();
//                    notifyDataSetChanged();
//                    return true;
//                }
                return false;
            }
        });
        popupMenu.show();
    }


}
