package com.example.ungdungchiasecongthucnauan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ungdungchiasecongthucnauan.Dao.CongThucDSCTDao;
import com.example.ungdungchiasecongthucnauan.Dao.DanhSachCongThucDao;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.DanhSachCongThuc;

import java.util.ArrayList;

public class SaveRecipe {
    boolean check = false;
    public void OpenDialogSaveRecipe(Context context, CongThuc congThuc, MainActivity mainActivity) {
        DanhSachCongThucDao dsct = new DanhSachCongThucDao(context);
        DanhSachCongThucDao dsctDao = new DanhSachCongThucDao(context);
        CongThucDSCTDao congThucDSCTDao = new CongThucDSCTDao(context);
        if (dsct.getAllIdUser(mainActivity.getUser().getId()) != null && !dsct.getAll().isEmpty()) {
            ArrayList<CongThuc> lstCT = (ArrayList<CongThuc>) dsctDao.getAllCongThucUser(mainActivity.getUser().getId());
            for (CongThuc ct:lstCT) {
                if (ct != null && ct.getId().equals(congThuc.getId())) {
                    check = true;
                    Toast.makeText(context, "Bạn đã lưu công thức này !", Toast.LENGTH_SHORT).show();
                }
            }
            if (!check) {
                final View dialogView = View.inflate(context,R.layout.dialog_save_recipe,null);
                final Dialog dialog = new Dialog(context);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(dialogView);

                Window window = dialog.getWindow();
                window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);

                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                layoutParams.gravity = Gravity.CENTER;
                dialog.getWindow().setAttributes(layoutParams);

                LinearLayout layout_dsct = dialog.findViewById(R.id.layout_dsct);
                Button btn_save = dialog.findViewById(R.id.btn_save);

                ArrayList<DanhSachCongThuc> lstDSCT = (ArrayList<DanhSachCongThuc>) dsctDao.getAllIdUser(String.valueOf(mainActivity.getUser().getId()));
                for (DanhSachCongThuc DSCT:lstDSCT){
                    View view = LayoutInflater.from(context).inflate(R.layout.item_save_recipe,null);
                    TextView tvName = view.findViewById(R.id.tv_name);
                    tvName.setText(DSCT.getTen());
                    layout_dsct.addView(view);
                }
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < lstDSCT.size(); i++) {
                            View view = layout_dsct.getChildAt(i);
                            CheckBox checkBox = view.findViewById(R.id.cbo_select);
                            if (checkBox.isChecked()) {
                                congThucDSCTDao.insert(congThuc.getId(),lstDSCT.get(i).getId());
                            }
                        }
                        Toast.makeText(context, "Lưu thành công !", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Bạn chưa có danh sách công thức nào !\nHãy tạo 1 danh sách công thức trước.");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new RecipeList().OpenDialogCreateRecipeList(context,mainActivity);
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
    }
}
