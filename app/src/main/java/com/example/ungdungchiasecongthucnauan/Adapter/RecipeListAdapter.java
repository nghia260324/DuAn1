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
import com.example.ungdungchiasecongthucnauan.Dao.CongThucDao;
import com.example.ungdungchiasecongthucnauan.Model.Anh;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.DanhSachCongThuc;
import com.example.ungdungchiasecongthucnauan.R;

import java.util.ArrayList;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DanhSachCongThuc> lstDSCT;
    private CongThucDao congThucDao;
    private AnhDao anhDao;

    public RecipeListAdapter(Context context, ArrayList<DanhSachCongThuc> lstDSCT) {
        this.context = context;
        this.lstDSCT = lstDSCT;
        this.congThucDao = new CongThucDao(context);
        anhDao = new AnhDao(context);
    }

    @NonNull
    @Override
    public RecipeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_list,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListAdapter.ViewHolder holder, int position) {
        DanhSachCongThuc dsct = lstDSCT.get(position);
        if (dsct != null) {
            ArrayList<String> lstCT = (ArrayList<String>) congThucDao.getAllListRecipes(String.valueOf(dsct.getId()));
            holder.tv_name.setText(dsct.getTen());
            holder.tv_quantity.setText(lstCT.size() + " công thức");
            if (lstCT != null && !lstCT.isEmpty()) {
                CongThuc congThuc = congThucDao.getID(lstCT.get(0));
                Anh anh = new Anh();
                if (congThuc != null && congThuc.getIdAnh() != null) {
                    anh = anhDao.getID(congThuc.getIdAnh());
                }
                Glide.with(context).load(anh.getUrl()).error(R.drawable.logoapp).into(holder.img_banner);
                if (lstCT.size() >= 2) {
                    holder.img_subBanner.setVisibility(View.VISIBLE);
                    CongThuc congThuc1 = congThucDao.getID(lstCT.get(1));
                    Anh anh1 = new Anh();
                    if (congThuc1 != null && congThuc1.getIdAnh() != null) {
                        anh1 = anhDao.getID(congThuc1.getIdAnh());
                    }
                    Glide.with(context).load(anh1.getUrl()).error(R.drawable.logoapp).into(holder.img_subBanner);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return lstDSCT != null?lstDSCT.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_quantity;
        ImageView img_banner,img_subBanner;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            tv_name = itemView.findViewById(R.id.tv_name);
            img_banner = itemView.findViewById(R.id.img_banner);
            img_subBanner = itemView.findViewById(R.id.img_subBanner);
        }
    }
}
