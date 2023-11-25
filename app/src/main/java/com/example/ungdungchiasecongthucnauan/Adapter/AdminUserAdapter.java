package com.example.ungdungchiasecongthucnauan.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ungdungchiasecongthucnauan.Dao.NguoiDungDao;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;
import com.example.ungdungchiasecongthucnauan.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public void setList(List<NguoiDung> list) {
        this.list = list;
        notifyDataSetChanged();
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
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_delete, popupMenu.getMenu());
        MenuItem shareItem= popupMenu.getMenu().findItem(R.id.delete_option);
        if (nd.getTrangThai()==0){
            shareItem.setTitle("Khóa");
        }else {
            shareItem.setTitle("Mở khóa");
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.delete_option) {
                    if (nd.getTrangThai()==0){
                        nd.setTrangThai(1);
                        nguoiDungDao.update(nd);
                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("NGUOI_DUNG");
                        databaseReference.child(nd.getId()).child("trangThai").setValue(1);
                    }else {
                        nd.setTrangThai(0);
                        nguoiDungDao.update(nd);
                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("NGUOI_DUNG");
                        databaseReference.child(nd.getId()).child("trangThai").setValue(0);
                    }
                    list.clear();
                    list=nguoiDungDao.getAllND();
                    notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }


}
