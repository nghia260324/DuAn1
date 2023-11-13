package com.example.ungdungchiasecongthucnauan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ungdungchiasecongthucnauan.Model.LoaiCongThuc;
import com.example.ungdungchiasecongthucnauan.R;

import java.util.ArrayList;

public class RCVLoaiCongThucAdapter extends RecyclerView.Adapter<RCVLoaiCongThucAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LoaiCongThuc> lstLCT;
    public RCVLoaiCongThucAdapter(Context context, ArrayList<LoaiCongThuc> lstLCT) {
        this.context = context;
        this.lstLCT = lstLCT;
    }

    @NonNull
    @Override
    public RCVLoaiCongThucAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_formula_type,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RCVLoaiCongThucAdapter.ViewHolder holder, int position) {
        LoaiCongThuc loaiCongThuc = lstLCT.get(position);
        if (loaiCongThuc != null) {
            setBackground(holder.imgFormula,loaiCongThuc.getId());
            holder.tvName.setText(loaiCongThuc.getTenLoai());
        }
    }

    @Override
    public int getItemCount() {
        return lstLCT != null?lstLCT.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFormula;
        TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFormula = itemView.findViewById(R.id.img_formula);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }

    private void setBackground(ImageView img,int index){
        switch (index){
            case 1:
                img.setImageResource(R.drawable.douong);
                break;
            case 2:
                img.setImageResource(R.drawable.nuong);
                break;
            case 3:
                img.setImageResource(R.drawable.dacsan);
                break;
            case 4:
                img.setImageResource(R.drawable.canh);
                break;
            case 5:
                img.setImageResource(R.drawable.hap);
                break;
            case 6:
                img.setImageResource(R.drawable.chien);
                break;
            case 7:
                img.setImageResource(R.drawable.xao);
                break;
            case 8:
                img.setImageResource(R.drawable.ran);
                break;
            case 9:
                img.setImageResource(R.drawable.nau);
                break;
            case 10:
                img.setImageResource(R.drawable.sup);
                break;
            case 11:
                img.setImageResource(R.drawable.banh);
                break;
            default:break;
        }
    }
}
