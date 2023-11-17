package com.example.ungdungchiasecongthucnauan.Adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ungdungchiasecongthucnauan.LoginActivity;
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
    EditText edt_mkcu, edt_mkmoi,edt_nhaplaimk;

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
                //Phan quyen and them cac nut
                if (item.equals("Đổi mật khẩu")) {
                       changePassword();
                } else if (item.equals("Đăng xuất")) {
                       logOut();
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
        dialog=new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_chanepassword);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        edt_mkcu=dialog.findViewById(R.id.edt_mkcu);
        edt_mkmoi=dialog.findViewById(R.id.edt_mkmoi);
        edt_nhaplaimk= dialog.findViewById(R.id.edt_nhaplaimk);
        Button btnRegister= dialog.findViewById(R.id.btn_register);
//        ImageView img_Close=dialog.findViewById(R.id.img_Close);

//        img_Close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  String oldpassword=edt_mkcu.getText().toString().trim();
                  String newpassword=edt_mkmoi.getText().toString().trim();
                  String renewpass=edt_nhaplaimk.getText().toString().trim();

                Log.e("Tag",""+validate(oldpassword,newpassword,renewpass));
                  if (validate(oldpassword,newpassword,renewpass)){
                      FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                      String email=user.getEmail();

                      FirebaseAuth.getInstance().signInWithEmailAndPassword(email, oldpassword)
                              .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                  @Override
                                  public void onComplete(@NonNull Task<AuthResult> task) {
                                      if (task.isSuccessful()) {
                                          // Mật khẩu hiện tại hợp lệ
                                          updatePass(newpassword);
                                          dialog.dismiss();

                                      } else {
                                          // Mật khẩu hiện tại không hợp lệ
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
                check=false;
            }
            if (!newpassword.equals(repassword)) {
                edt_nhaplaimk.setError("Nhập lại mật khẩu chưa chính xác !");
                edt_nhaplaimk.requestFocus();
                check=false;
            }

            check=false;
        }else{
          //true
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

//    private void reAuthenticate(String newPassword){
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//
//        AuthCredential credential = EmailAuthProvider
//                .getCredential("user@example.com", "password1234");
//
//
//        user.reauthenticate(credential)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()){
//                            updatePass(newPassword);
//                        }else {
//
//                        }
//                    }
//                });
//    }

}
