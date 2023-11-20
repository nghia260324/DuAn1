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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ungdungchiasecongthucnauan.ChiTietCongThuc;
import com.example.ungdungchiasecongthucnauan.Dao.AnhDao;
import com.example.ungdungchiasecongthucnauan.Dao.CongThucDao;
import com.example.ungdungchiasecongthucnauan.IOpenEdit;
import com.example.ungdungchiasecongthucnauan.MainActivity;
import com.example.ungdungchiasecongthucnauan.Model.Anh;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyRecipeAdapter extends RecyclerView.Adapter<MyRecipeAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CongThuc> lstCongThuc;
    private IOpenEdit openEdit;
    DatabaseReference databaseReference;
    AnhDao anhDao;
    CongThucDao congThucDao;
    MainActivity mainActivity;

    public MyRecipeAdapter(Context context, ArrayList<CongThuc> lstCongThuc, MainActivity mainActivity,IOpenEdit iOpenEdit) {
        this.context = context;
        this.lstCongThuc = lstCongThuc;
        this.anhDao = new AnhDao(context);
        this.mainActivity = mainActivity;
        this.openEdit = iOpenEdit;
        this.congThucDao = new CongThucDao(context);
        databaseReference = FirebaseDatabase.getInstance().getReference("CONG_THUC");
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
            Glide.with(context).load(anh.getUrl()).error(R.drawable.ct).into(holder.imgMyRecipe);
            holder.tvNameRecipe.setText(congThuc.getTen());
        }
        holder.imgMyRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChiTietCongThuc ctct = new ChiTietCongThuc(context,congThuc,mainActivity);
                ctct.OpenDialogCreateRecipes();
            }
        });
        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu(v,congThuc,holder.getAdapterPosition());
            }
        });
    }
    private void PopupMenu(View view,CongThuc congThuc,int indexChange){
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        MenuItem shareMenuItem = popupMenu.getMenu().findItem(R.id.share_option);
        if (congThuc.getTrangThai() == 0) {
            shareMenuItem.setTitle("Chia sẻ");
        } else {
            shareMenuItem.setTitle("Hủy chia sẻ");
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.edit_option) {
                    openEdit.IOpenDialogEdit(congThuc);
                    return true;
                } else if (item.getItemId() == R.id.share_option) {
                    switch (congThuc.getTrangThai()) {
                        case 0:
                            congThuc.setTrangThai(1);
                            Share(congThuc,indexChange);
                            break;
                        case 1:
                            congThuc.setTrangThai(0);
                            CancelShare(congThuc,indexChange);
                            break;
                        default: break;
                    }
                    congThucDao.update(congThuc);
                    return true;
                } else if (item.getItemId() == R.id.delete_option) {
                    openEdit.IOpenDialogDelete(congThuc);
                    return true;
                } else {
                    return false;
                }
            }
        });
        popupMenu.show();
    }
    private void Share(CongThuc congThuc,int indexChange){
        databaseReference.child(congThuc.getId()).setValue(congThuc).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Chia sẻ thành công !", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    private void CancelShare(CongThuc congThuc,int indexChange) {
        databaseReference.child(congThuc.getId()).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Chia sẻ thành công !", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
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
