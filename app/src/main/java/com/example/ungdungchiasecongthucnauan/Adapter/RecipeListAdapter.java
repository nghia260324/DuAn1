package com.example.ungdungchiasecongthucnauan.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ungdungchiasecongthucnauan.ChiTietCongThuc;
import com.example.ungdungchiasecongthucnauan.Dao.AnhDao;
import com.example.ungdungchiasecongthucnauan.Dao.CongThucDSCTDao;
import com.example.ungdungchiasecongthucnauan.Dao.CongThucDao;
import com.example.ungdungchiasecongthucnauan.Dao.DanhSachCongThucDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguoiDungDao;
import com.example.ungdungchiasecongthucnauan.MainActivity;
import com.example.ungdungchiasecongthucnauan.Model.Anh;
import com.example.ungdungchiasecongthucnauan.Model.CT_DSCT;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.DanhSachCongThuc;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;
import com.example.ungdungchiasecongthucnauan.R;
import com.example.ungdungchiasecongthucnauan.Service;

import java.util.ArrayList;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DanhSachCongThuc> lstDSCT;
    private CongThucDao congThucDao;
    private AnhDao anhDao;
    private NguoiDungDao nguoiDungDao;
    private CongThucDSCTDao congThucDSCTDao;
    private DanhSachCongThucDao dsctDao;
    MainActivity mainActivity;

    public RecipeListAdapter(Context context, ArrayList<DanhSachCongThuc> lstDSCT, MainActivity mainActivity) {
        this.context = context;
        this.lstDSCT = lstDSCT;
        this.congThucDao = new CongThucDao(context);
        this.anhDao = new AnhDao(context);
        this.nguoiDungDao = new NguoiDungDao(context);
        this.congThucDSCTDao = new CongThucDSCTDao(context);
        this.dsctDao = new DanhSachCongThucDao(context);
        this.mainActivity = mainActivity;
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
                Glide.with(context).load(anh.getUrl()).error(R.drawable.ct).into(holder.img_banner);
                if (lstCT.size() >= 2) {
                    holder.img_subBanner.setVisibility(View.VISIBLE);
                    CongThuc congThuc1 = congThucDao.getID(lstCT.get(1));
                    Anh anh1 = new Anh();
                    if (congThuc1 != null && congThuc1.getIdAnh() != null) {
                        anh1 = anhDao.getID(congThuc1.getIdAnh());
                    }
                    Glide.with(context).load(anh1.getUrl()).error(R.drawable.ct).into(holder.img_subBanner);
                }
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDialog(dsct);
            }
        });
    }
    TextView tv_listName;
    LinearLayout layout_item,layout_user;
    private void OpenDialog(DanhSachCongThuc dsct) {
        final View dialogView = View.inflate(context,R.layout.dialog_item_recipe_list,null);
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

        ImageButton btn_back = dialogView.findViewById(R.id.btn_back);
        tv_listName = dialog.findViewById(R.id.tv_listName);
        layout_item = dialog.findViewById(R.id.layout_item);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ShowItem(dsct,dialog);

        dialog.show();
    }

    private void ShowItem(DanhSachCongThuc dsct,Dialog dialog) {
        if (dsct != null) {
            ArrayList<CT_DSCT> lstSCT = (ArrayList<CT_DSCT>) congThucDSCTDao.getAll(String.valueOf(dsct.getId()));

            tv_listName.setText(dsct.getTen() + " (" + lstSCT.size() + "công thức)");
            for (CT_DSCT s:lstSCT) {
                CongThuc ct = congThucDao.getID(s.getIdCongThuc());
                View view = LayoutInflater.from(context).inflate(R.layout.item_recipe_list_save,null);
                ImageView imgRecipe = view.findViewById(R.id.img_recipe);
                TextView tvName = view.findViewById(R.id.tv_name);
                ImageView imgAvatar = view.findViewById(R.id.img_avatar);
                TextView tvUser = view.findViewById(R.id.tv_user);
                LinearLayout btnMore = view.findViewById(R.id.btn_more);
                layout_user = view.findViewById(R.id.layout_user);

                if (ct != null) {
                    NguoiDung nguoiDung = nguoiDungDao.getID(String.valueOf(ct.getIdNguoiDung()));
                    Anh anh = new Anh();
                    if (ct != null) {
                        if (ct.getIdAnh() != null) {
                            anh = anhDao.getID(ct.getIdAnh());
                        }
                        tvName.setText(ct.getTen());
                        tvUser.setText(nguoiDung.getHoTen());
                        new Service().setAvatar(imgAvatar,nguoiDung.getAvatar());
                        Glide.with(context).load(anh.getUrl()).error(R.drawable.ct).into(imgRecipe);
                    }

                } else {
                    imgRecipe.setVisibility(View.GONE);
                    imgAvatar.setVisibility(View.GONE);
                    tvUser.setVisibility(View.GONE);
                    tvName.setText("Công thức này đã bị người dùng ẩnh hoặc xóa !");
                }
                btnMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu(v,s.getId(),dialog);
                    }
                });
                imgRecipe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ChiTietCongThuc ctct = new ChiTietCongThuc(context,ct,mainActivity);
                        ctct.OpenDialogCreateRecipes();
                    }
                });
                layout_user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ChiTietCongThuc ctct = new ChiTietCongThuc(context,ct,mainActivity);
                        ctct.OpenDialogCreateRecipes();
                    }
                });

                layout_item.addView(view);
            }
        }
    }

    @Override
    public int getItemCount() {
        return lstDSCT != null?lstDSCT.size():0;
    }

    private void PopupMenu(View view,int id,Dialog dialog){
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_delete, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.delete_option) {
                    congThucDSCTDao.deleteID(String.valueOf(id));
                    lstDSCT.clear();
                    lstDSCT = (ArrayList<DanhSachCongThuc>) dsctDao.getAllIdUser(String.valueOf(mainActivity.getUser().getId()));
                    notifyDataSetChanged();

                    dialog.dismiss();

                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
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
