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

import com.bumptech.glide.Glide;
import com.example.ungdungchiasecongthucnauan.Dao.AnhDao;
import com.example.ungdungchiasecongthucnauan.Model.Anh;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.R;

import java.util.ArrayList;

public class MyRecipeAdapter extends RecyclerView.Adapter<MyRecipeAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CongThuc> lstCongThuc;
    AnhDao anhDao;

    public MyRecipeAdapter(Context context, ArrayList<CongThuc> lstCongThuc) {
        this.context = context;
        this.lstCongThuc = lstCongThuc;
        this.anhDao = new AnhDao(context);
    }

    @NonNull
    @Override
    public MyRecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_recipe,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecipeAdapter.ViewHolder holder, int position) {
        CongThuc congThuc = lstCongThuc.get(position);
        Anh anh = new Anh();
        if (congThuc != null){
            if (congThuc.getIdAnh() != null) {
                anh = anhDao.getID(congThuc.getIdAnh());
            }
            Glide.with(context).load(anh.getUrl()).error(R.drawable.logoapp).into(holder.imgMyRecipe);
            holder.tvNameRecipe.setText(congThuc.getTen());
        }
        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu(v);
            }
        });
    }

    private void PopupMenu(View view){
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.edit_option) {
                    EditRecipes();
                    return true;
                } else if (item.getItemId() == R.id.delete_option) {
                    DeleteRecipes();

                    return true;
                } else {
                    return false;
                }
            }
        });
        popupMenu.show();
    }
    private void EditRecipes() {

    }

    private void DeleteRecipes() {

    }

    @Override
    public int getItemCount() {
        return lstCongThuc != null?lstCongThuc.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMyRecipe;
        TextView tvNameRecipe;
        ImageButton btnMore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMyRecipe = itemView.findViewById(R.id.img_myRecipe);
            tvNameRecipe = itemView.findViewById(R.id.tv_nameRecipe);
            btnMore = itemView.findViewById(R.id.btn_more);
        }
    }
}
