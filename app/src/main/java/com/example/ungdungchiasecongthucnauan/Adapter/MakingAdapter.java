package com.example.ungdungchiasecongthucnauan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ungdungchiasecongthucnauan.Dao.AnhDao;
import com.example.ungdungchiasecongthucnauan.Model.Anh;
import com.example.ungdungchiasecongthucnauan.Model.BuocLam;
import com.example.ungdungchiasecongthucnauan.R;

import java.util.ArrayList;

public class MakingAdapter extends RecyclerView.Adapter<MakingAdapter.ViewHolder> {
    private Context context;
    private ArrayList<BuocLam> lstBuocLam;
    AnhDao anhDao;

    public MakingAdapter(Context context, ArrayList<BuocLam> lstBuocLam) {
        this.context = context;
        this.lstBuocLam = lstBuocLam;
        this.anhDao = new AnhDao(context);
    }

    @NonNull
    @Override
    public MakingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_making,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MakingAdapter.ViewHolder holder, int position) {
        BuocLam buocLam = lstBuocLam.get(position);
        Anh anh = new Anh();
        if (buocLam.getIdAnh() != null){
             anh = anhDao.getID(buocLam.getIdAnh());
        }
        holder.btnRemoveMaking.setVisibility(View.GONE);
        holder.edtContent.setFocusable(false);
        holder.edtContent.setClickable(false);
//        holder.edtContent.setLayoutParams(new FrameLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        if (buocLam != null) {
            holder.tvLocation.setText(String.valueOf(buocLam.getThuTu()));
            holder.edtContent.setText(buocLam.getNoiDung());
            if (buocLam.getIdAnh() != null) {
                Glide.with(context).load(anh.getUrl()).error(R.drawable.ic_picture).into(holder.imgPictureMaking);
            } else {
                holder.imgPictureMaking.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return !lstBuocLam.isEmpty()?lstBuocLam.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLocation;
        EditText edtContent;
        ImageView imgPictureMaking;
        Button btnRemoveMaking;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnRemoveMaking = itemView.findViewById(R.id.btn_removeMaking);
            imgPictureMaking = itemView.findViewById(R.id.img_selected_pictureMaking);
            edtContent = itemView.findViewById(R.id.edt_content);
            tvLocation = itemView.findViewById(R.id.tv_location);
        }
    }
}
