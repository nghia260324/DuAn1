package com.example.ungdungchiasecongthucnauan.Adapter;

import android.content.Context;
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
import com.example.ungdungchiasecongthucnauan.Dao.CongThucDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguoiDungDao;
import com.example.ungdungchiasecongthucnauan.Model.Anh;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;
import com.example.ungdungchiasecongthucnauan.R;
import com.example.ungdungchiasecongthucnauan.Service;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminCTAdapter extends RecyclerView.Adapter<AdminCTAdapter.ViewHolder>{
    List<CongThuc> congThucList;
    Context context;
    AnhDao anhDao;
    NguoiDungDao nguoiDungDao;
    CongThucDao congThucDao;
    public AdminCTAdapter(Context context,List<CongThuc> congThucList) {
        this.context = context;
        this.congThucList = congThucList;
        this.anhDao = new AnhDao(context);
        this.nguoiDungDao = new NguoiDungDao(context);
        this.congThucDao = new CongThucDao(context);
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
          CongThuc congThuc = congThucList.get(position);
          Anh anh = new Anh();
          if (congThuc.getIdAnh() != null) {
              anh = anhDao.getID(congThucList.get(position).getIdAnh());
          }
          NguoiDung nguoiDung = nguoiDungDao.getID(congThucList.get(position).getIdNguoiDung());
          Picasso.get().load(anh.getUrl()).into(holder.img_bgr);
          new Service().setAvatar(holder.img_avata,nguoiDung.getAvatar());
          holder.tv_nameuser.setText(nguoiDung.getHoTen());
          holder.tv_namedish.setText(congThuc.getTen());

          new Service().setDay(congThuc.getNgayTao(),holder.tv_date);
          holder.btn_option.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  PopupMenu(v,congThuc);
              }
          });

    }
    private void PopupMenu(View view,CongThuc congThuc){
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_delete, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.delete_option) {
                    congThucDao.delete(congThuc.getId());

                    congThucList.clear();
                    congThucList = congThucDao.getAll();
                    notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
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
