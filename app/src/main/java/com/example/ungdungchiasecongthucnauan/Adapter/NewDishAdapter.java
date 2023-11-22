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
import com.example.ungdungchiasecongthucnauan.Dao.CongThucDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguoiDungDao;
import com.example.ungdungchiasecongthucnauan.MainActivity;
import com.example.ungdungchiasecongthucnauan.Model.Anh;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;
import com.example.ungdungchiasecongthucnauan.R;
import com.example.ungdungchiasecongthucnauan.SaveRecipe;
import com.example.ungdungchiasecongthucnauan.Service;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class NewDishAdapter extends RecyclerView.Adapter<NewDishAdapter.ViewHolder>{
    private Context context;
    private ArrayList<CongThuc> lstCongthuc;
    DatabaseReference databaseReference;
    private AnhDao anhDao;
    CongThucDao congThucDao;
    MainActivity mainActivity;
    private NguoiDungDao nguoiDungDao;

    public NewDishAdapter(Context context, ArrayList<CongThuc> lstCongthuc,MainActivity mainActivity) {
        this.context = context;
        this.lstCongthuc = lstCongthuc;
        this.anhDao = new AnhDao(context);
        this.mainActivity = mainActivity;
        this.congThucDao = new CongThucDao(context);
        this.nguoiDungDao = new NguoiDungDao(context);
    }

    @NonNull
    @Override
    public NewDishAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newdish,null);
        return new NewDishAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewDishAdapter.ViewHolder holder, int position) {
        CongThuc congThuc = lstCongthuc.get(position);

        if (congThuc != null){
            NguoiDung nguoiDung = nguoiDungDao.getID(congThuc.getIdNguoiDung());
            Anh anh = new Anh();
            if (congThuc.getIdAnh() != null) {
                anh = anhDao.getID(congThuc.getIdAnh());
            }
            Glide.with(context).load(anh.getUrl()).error(R.drawable.canh).into(holder.img_newDish);
            holder.tv_namenewdish.setText(congThuc.getTen());
            holder.tv_namend.setText(nguoiDung.getHoTen());
            new Service().setDay(congThuc.getNgayTao(),holder.tv_time);
            Glide.with(context).load(anh.getUrl()).error(R.drawable.canh).into(holder.img_newDish);
            new Service().setAvatar(holder.img_avtnd,nguoiDung.getAvatar());
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
        return lstCongthuc != null?lstCongthuc.size():0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_newDish, img_avtnd;
        TextView tv_namend, tv_namenewdish, tv_time;
        ImageButton btn_save;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_newDish = itemView.findViewById(R.id.img_newDish);
            img_avtnd = itemView.findViewById(R.id.img_avtnd);
            tv_namend = itemView.findViewById(R.id.tv_namend);
            tv_namenewdish = itemView.findViewById(R.id.tv_namenewdish);
            tv_time = itemView.findViewById(R.id.tv_time);
            btn_save = itemView.findViewById(R.id.btn_save);
        }
    }
}
