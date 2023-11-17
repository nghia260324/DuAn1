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
import com.example.ungdungchiasecongthucnauan.ChiTietCongThuc;
import com.example.ungdungchiasecongthucnauan.Dao.AnhDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguoiDungDao;
import com.example.ungdungchiasecongthucnauan.MainActivity;
import com.example.ungdungchiasecongthucnauan.Model.Anh;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;
import com.example.ungdungchiasecongthucnauan.R;

import java.util.ArrayList;

public class RecipeViewedAdapter extends RecyclerView.Adapter<RecipeViewedAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CongThuc> lstCongThuc;
    private NguoiDungDao nguoiDungDao;
    private AnhDao anhDao;
    MainActivity mainActivity;

    public RecipeViewedAdapter(Context context, ArrayList<CongThuc> lstCongThuc,MainActivity mainActivity) {
        this.context = context;
        this.lstCongThuc = lstCongThuc;
        this.nguoiDungDao = new NguoiDungDao(context);
        this.anhDao = new AnhDao(context);
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public RecipeViewedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_viewed_recipe,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewedAdapter.ViewHolder holder, int position) {
        CongThuc congThuc = lstCongThuc.get(position);
        NguoiDung nguoiDung = nguoiDungDao.getID(String.valueOf(congThuc.getIdNguoiDung()));
        Anh anh = new Anh();
        if (congThuc != null){
            if (congThuc.getIdAnh() != null) {
                anh = anhDao.getID(congThuc.getIdAnh());
            }
            Glide.with(context).load(anh.getUrl()).error(R.drawable.logoapp).into(holder.imgRecipeViewed);
            holder.tvNameRecipe.setText(congThuc.getTen());
            holder.tvNameUser.setText(nguoiDung.getHoTen());
        }
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
        return lstCongThuc != null?lstCongThuc.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgRecipeViewed;
        TextView tvNameRecipe,tvNameUser;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRecipeViewed = itemView.findViewById(R.id.img_recipeViewed);
            tvNameRecipe = itemView.findViewById(R.id.tv_nameRecipe);
            tvNameUser = itemView.findViewById(R.id.tv_nameUser);
        }
    }
}
