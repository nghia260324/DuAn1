package com.example.ungdungchiasecongthucnauan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ungdungchiasecongthucnauan.Adapter.DecentralizatioAdapter;
import com.example.ungdungchiasecongthucnauan.Dao.NguoiDungDao;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;
import com.example.ungdungchiasecongthucnauan.Model.PhanQuyen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    EditText edtName,edtEmail,edtPassword,edtRePassword;
    Button btnGoLogin,btnRegister;
    NguoiDungDao nguoiDungDao;
    NguoiDung nguoiDung;
    Spinner spinner;

    String email;
    String name;
    String password;
    String rePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initUI();

        btnGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.slide_in_top,R.anim.slide_out_top);
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 email = edtEmail.getText().toString().trim();
                 name = edtName.getText().toString().trim();
                 password = edtPassword.getText().toString().trim();
                 rePassword = edtRePassword.getText().toString().trim().trim();

                if (Validate(email,name,password,rePassword)){
                    if(nguoiDungDao.insert(nguoiDung) > 0) {
                        SaveUserToFireBase(email,password);
                        Toast.makeText(RegisterActivity.this, "Đăng ký tài khoản thành công !", Toast.LENGTH_SHORT).show();
//                        btnGoLogin.callOnClick();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Tạo tài khoản thất bại !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        DecentralizatioAdapter decentralizatioAdapter = new DecentralizatioAdapter(this,R.layout.item_spinner_decentralization_selected,getArrDecentralizatio());
        spinner.setAdapter(decentralizatioAdapter);
    }

    private ArrayList<PhanQuyen> getArrDecentralizatio() {
        ArrayList<PhanQuyen> arr = new ArrayList<>();
        arr.add(new PhanQuyen("Người dùng"));
        arr.add(new PhanQuyen("Đầu bếp"));
        return arr;
    }

    private boolean Validate(String email,String name,String password,String rePassword){
        boolean check;
        if(!email.isEmpty() && !name.isEmpty() && !password.isEmpty() && !rePassword.isEmpty()) {
            if(!email.matches("^[a-zA-Z0-9_]+@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+){1,2}$")
                    || password.length() < 5
                    || !password.equals(rePassword)){
                if (password.length() < 5) {
                    edtPassword.setError("Mật khẩu phải có 5 ký tự trở lên !");
                    edtPassword.requestFocus();
                } else if (!password.equals(rePassword)) {
                    edtRePassword.setError("Nhập lại mật khẩu chưa chính xác !");
                    edtRePassword.requestFocus();
                } else {
                    edtEmail.setError("Email không hợp lệ !");
                    edtEmail.requestFocus();
                }
                check = false;
            } else {
                nguoiDung.setId(0);
                nguoiDung.setHoTen(name);
                nguoiDung.setEmail(email);
                nguoiDung.setMatKhau(password);
                nguoiDung.setTrangThai(0);
                nguoiDung.setPhanQuyen(1);
                Random random = new Random();
                int rdAvatar = random.nextInt(10) + 1;
                nguoiDung.setAvatar(rdAvatar);
                check = true;
            }
        } else {
            if (email.isEmpty()){
                edtEmail.requestFocus();
                edtEmail.setError(getResources().getString(R.string.is_empty));
            }
            if(name.isEmpty()){
                edtName.requestFocus();
                edtName.setError(getResources().getString(R.string.is_empty));
            }
            if (password.isEmpty()){
                edtPassword.requestFocus();
                edtPassword.setError(getResources().getString(R.string.is_empty));
            }
            if(rePassword.isEmpty()){
                edtRePassword.requestFocus();
                edtRePassword.setError(getResources().getString(R.string.is_empty));
            }
            check = false;
        }
        return check;
    }
    private void initUI() {
        nguoiDungDao = new NguoiDungDao(this);
        nguoiDung = new NguoiDung();

        btnGoLogin = findViewById(R.id.btn_goLogin);
        btnRegister = findViewById(R.id.btn_register);

        edtName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        edtRePassword = findViewById(R.id.edt_rePassword);

        spinner = findViewById(R.id.spn_decentralization);
    }
    private void SaveUserToFireBase(String email, String password){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            Log.w("w", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}