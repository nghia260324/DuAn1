package com.example.ungdungchiasecongthucnauan.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ungdungchiasecongthucnauan.ChiTietCongThuc;
import com.example.ungdungchiasecongthucnauan.Dao.CongThucDao;
import com.example.ungdungchiasecongthucnauan.IReturnDone;
import com.example.ungdungchiasecongthucnauan.MainActivity;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.LoaiCongThuc;
import com.example.ungdungchiasecongthucnauan.R;

import java.util.ArrayList;

public class RCVLoaiCongThucAdapter extends RecyclerView.Adapter<RCVLoaiCongThucAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LoaiCongThuc> lstLCT;
    private MainActivity mainActivity;
    private CongThucDao congThucDao;
    public RCVLoaiCongThucAdapter(Context context, ArrayList<LoaiCongThuc> lstLCT, MainActivity mainActivity) {
        this.context = context;
        this.lstLCT = lstLCT;
        this.mainActivity = mainActivity;
        this.congThucDao = new CongThucDao(context);
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogType(loaiCongThuc);
            }
        });
    }
    private void DialogType(LoaiCongThuc loaiCongThuc) {

        final View dialogView = View.inflate(context,R.layout.dialog_type,null);
        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);

        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(layoutParams);

        TextView tv_name = dialog.findViewById(R.id.tv_name);
        if (loaiCongThuc.getId() == 1) {
            tv_name.setText(loaiCongThuc.getTenLoai());
        } else {
            tv_name.setText("Món " + loaiCongThuc.getTenLoai());
        }
        ImageButton btn_backDialog = dialog.findViewById(R.id.btn_backDialog);
        RecyclerView rcv_type = dialog.findViewById(R.id.rcv_type);
        btn_backDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ArrayList<CongThuc> lstCongThuc = (ArrayList<CongThuc>) congThucDao.getAllType(String.valueOf(loaiCongThuc.getId()));
        SearchAdapter searchAdapter = new SearchAdapter(context,lstCongThuc , new IReturnDone() {
            @Override
            public void IReturnDone(Context context, CongThuc congThuc) {
                ChiTietCongThuc chiTietCongThuc = new ChiTietCongThuc(context,congThuc,mainActivity);
                chiTietCongThuc.OpenDialogCreateRecipes();
            }
        });
        rcv_type.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        rcv_type.setLayoutManager(new GridLayoutManager(context,1));
        rcv_type.setAdapter(searchAdapter);
        dialog.show();
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
