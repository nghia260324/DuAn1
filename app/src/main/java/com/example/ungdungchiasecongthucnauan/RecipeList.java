package com.example.ungdungchiasecongthucnauan;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ungdungchiasecongthucnauan.Dao.DanhSachCongThucDao;
import com.example.ungdungchiasecongthucnauan.Model.DanhSachCongThuc;

public class RecipeList {
    public void OpenDialogCreateRecipeList(Context context,MainActivity mainActivity) {
        DanhSachCongThucDao dsclDao = new DanhSachCongThucDao(context);
        View dialogView = View.inflate(context,R.layout.dialog_recipe_list,null);
        Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);

        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.windowAnimations = R.style.showDialogMorePlay;
        dialog.getWindow().setAttributes(layoutParams);

        EditText edt_name = dialog.findViewById(R.id.edt_name);
        Button btn_save = dialog.findViewById(R.id.btn_save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt_name.getText().toString().trim();
                if (!name.isEmpty()) {
                    DanhSachCongThuc dsct = new DanhSachCongThuc();
                    dsct.setTen(name);
                    dsct.setIdNguoiDung(mainActivity.getUser().getId());
                    dsclDao.insert(dsct);
                    Toast.makeText(context, "Tạo thành công !", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    edt_name.setError("Vui lòng nhập tên danh sách!");
                }
            }
        });
        dialog.show();
    }
}
