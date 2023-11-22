package com.example.ungdungchiasecongthucnauan.Adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ungdungchiasecongthucnauan.Dao.DanhSachCongThucDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguoiDungDao;
import com.example.ungdungchiasecongthucnauan.LoginActivity;
import com.example.ungdungchiasecongthucnauan.ManagerCTActivity;
import com.example.ungdungchiasecongthucnauan.ManagerUserActivity;
import com.example.ungdungchiasecongthucnauan.Model.DanhSachCongThuc;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;
import com.example.ungdungchiasecongthucnauan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class CaNhanAdapter extends ArrayAdapter<String> {
    Dialog dialog;
    ProgressDialog progressDialog;
    EditText edt_mkcu, edt_mkmoi,edt_nhaplaimk,edt_name;
    Button btn_save;
    DanhSachCongThucDao dsclDao = new DanhSachCongThucDao(getContext());
    NguoiDungDao nguoiDungDao =new NguoiDungDao(getContext());
    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
    String email=user.getEmail();


    public CaNhanAdapter(Context context, List<String> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        progressDialog=new ProgressDialog(getContext());

        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_canhan, parent, false);
        }

        String item = getItem(position);
        TextView textView = itemView.findViewById(R.id.tvcanhan);
        textView.setText(item);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.equals("Đổi mật khẩu")) {
                    changePassword();
                }else if (item.equals("Đăng xuất")) {
                    logOut();
                }else if(item.equals("Tạo danh sách công thức mới")){
                    addListCT();
                }else if (item.equals("Quản lý người dùng")){
                    Intent intent=new Intent(getContext(), ManagerUserActivity.class);
                    getContext().startActivity(intent);
                }else if (item.equals("Quản lý công thức")) {
                    Intent intent=new Intent(getContext(), ManagerCTActivity.class);
                    getContext().startActivity(intent);
                }
            }
        });

        return itemView;
    }

    private void logOut(){
        FirebaseAuth.getInstance().signOut();
        Intent intent=new Intent(getContext(),LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getContext().startActivity(intent);
    }
    private void changePassword(){
        View dialogView = View.inflate(getContext(),R.layout.dialog_chanepassword,null);
        dialog = new Dialog(getContext());

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

        edt_mkcu=dialog.findViewById(R.id.edt_mkcu);
        edt_mkmoi=dialog.findViewById(R.id.edt_mkmoi);
        edt_nhaplaimk= dialog.findViewById(R.id.edt_nhaplaimk);
        Button btnRegister= dialog.findViewById(R.id.btn_register);
        ImageButton img_Close=dialog.findViewById(R.id.img_Close);

        img_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  String oldpassword=edt_mkcu.getText().toString().trim();
                  String newpassword=edt_mkmoi.getText().toString().trim();
                  String renewpass=edt_nhaplaimk.getText().toString().trim();

                  if (validate(oldpassword,newpassword,renewpass)){

                      FirebaseAuth.getInstance().signInWithEmailAndPassword(email, oldpassword)
                              .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                  @Override
                                  public void onComplete(@NonNull Task<AuthResult> task) {
                                      if (task.isSuccessful()) {
                                          updatePass(newpassword);
                                          dialog.dismiss();
                                      } else {
                                          dialog.dismiss();
                                          Toast.makeText(getContext(),"Mật khẩu hiện tại chưa chính xác",Toast.LENGTH_LONG).show();
                                      }
                                  }
                              });
                  }
            }
        });
        dialog.show();
    }

    private boolean validate(String oldpassword,String newpassword, String repassword){
        boolean check;

        if (oldpassword.isEmpty()&&newpassword.isEmpty()&&repassword.isEmpty()){
            if (!newpassword.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")){
                edt_mkmoi.setError("Mật khẩu phải có 5 ký tự trở lên, Ít nhất 1 chữ in hoa và 1 chữ thường !");
                edt_mkmoi.requestFocus();
            }
            if (!newpassword.equals(repassword)) {
                edt_nhaplaimk.setError("Nhập lại mật khẩu chưa chính xác !");
                edt_nhaplaimk.requestFocus();
            }
            edt_mkcu.setError("Không để trống trường này !");
            edt_mkcu.requestFocus();
            check = false;
        }else{
           check=true;
        }
        return check;
    }

    private void updatePass(String newPassword){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(),"User password updated.",Toast.LENGTH_SHORT).show();
                        }else {

                        }
                    }
                });
    }

    private void addListCT(){
        View dialogView = View.inflate(getContext(),R.layout.dialog_recipe_list,null);
        dialog = new Dialog(getContext());

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
        ImageButton img=dialog.findViewById(R.id.img_Close);
        edt_name=dialog.findViewById(R.id.edt_name);
        btn_save=dialog.findViewById(R.id.btn_save);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edt_name.getText().toString().trim();
                if (!name.isEmpty()) {
                    NguoiDung nd=nguoiDungDao.getNguoiDungFromEmail(email);
                    DanhSachCongThuc dsct = new DanhSachCongThuc();
                    dsct.setTen(name);
                    dsct.setIdNguoiDung(nd.getId());
                    dsclDao.insert(dsct);
                    Toast.makeText(getContext(), "Tạo thành công !", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    edt_name.setError("Vui lòng nhập tên danh sách!");
                }
            }
        });
        dialog.show();
    }

}
