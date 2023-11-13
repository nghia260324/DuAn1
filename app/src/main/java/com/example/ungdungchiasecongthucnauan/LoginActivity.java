package com.example.ungdungchiasecongthucnauan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ungdungchiasecongthucnauan.Dao.NguoiDungDao;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;

public class LoginActivity extends AppCompatActivity {
    EditText edtEmail,edtPassword;
    Button btnGoRegister,btnLogin;
    NguoiDungDao nguoiDungDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();

        btnGoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                overridePendingTransition(R.anim.slide_in_down,R.anim.slide_out_down);
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if(Validate(email,password)){
                    if(nguoiDungDao.checkLogin(email,password) > 0){
                        rememberAccount();
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        if (nguoiDungDao.getNguoiDung(email,password) != null) {
                            NguoiDung nguoiDung = nguoiDungDao.getNguoiDung(email,password);
                            intent.putExtra("userID",String.valueOf(nguoiDung.getId()));
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_down,R.anim.slide_out_down);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu chưa chính xác !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        checkSharedPreferences();
    }
    private boolean Validate(String email,String password){
        boolean check;
        if(!email.isEmpty() && !password.isEmpty()) {
            if(!email.matches("^[a-zA-Z0-9_]+@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+){1,2}$") || password.length() < 5){
                if (password.length() < 5) {
                    edtPassword.setError("Mật khẩu phải có 5 ký tự trở lên !");
                    edtPassword.requestFocus();
                } else {
                    edtEmail.requestFocus();
                    edtEmail.setError("Email không hợp lệ !");
                }
                check = false;
            } else {
                check = true;
            }
        } else {
            if (email.isEmpty()){
                edtEmail.requestFocus();
                edtEmail.setError(getResources().getString(R.string.is_empty));
            }
            if (password.isEmpty()){
                edtPassword.requestFocus();
                edtPassword.setError(getResources().getString(R.string.is_empty));
            }
            check = false;
        }
        return check;
    }
    private void initUI() {
        nguoiDungDao = new NguoiDungDao(this);

        btnGoRegister = findViewById(R.id.btn_goRegister);
        btnLogin = findViewById(R.id.btn_login);

        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
    }

    private void rememberAccount() {
        SharedPreferences sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", edtEmail.getText().toString().trim());
        editor.putString("password", edtPassword.getText().toString().trim());
        editor.apply();
    }
    private void checkSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("password","");
        if(nguoiDungDao.checkLogin(email,password) > 0){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            if (nguoiDungDao.getNguoiDung(email,password) != null) {
                NguoiDung nguoiDung = nguoiDungDao.getNguoiDung(email,password);
                intent.putExtra("userID",String.valueOf(nguoiDung.getId()));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_down,R.anim.slide_out_down);
                finish();
            }
        }
    }
}