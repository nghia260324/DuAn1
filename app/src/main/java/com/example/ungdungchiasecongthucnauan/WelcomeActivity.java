package com.example.ungdungchiasecongthucnauan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ungdungchiasecongthucnauan.Dao.AnhDao;
import com.example.ungdungchiasecongthucnauan.Dao.BinhLuanDao;
import com.example.ungdungchiasecongthucnauan.Dao.BuocLamDao;
import com.example.ungdungchiasecongthucnauan.Dao.CongThucDao;
import com.example.ungdungchiasecongthucnauan.Dao.DanhSachNguyenLieuDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguoiDungDao;
import com.example.ungdungchiasecongthucnauan.Model.Anh;
import com.example.ungdungchiasecongthucnauan.Model.BinhLuan;
import com.example.ungdungchiasecongthucnauan.Model.BuocLam;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.DanhSachNguyenLieu;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {
    TextView tvTitle2;
    Button btnStart;
    DatabaseReference databaseReference;
    NguoiDungDao nguoiDungDao;
    CongThucDao congThucDao;
    BuocLamDao buocLamDao;
    BinhLuanDao binhLuanDao;
    DanhSachNguyenLieuDao dsnlDao;
    ProgressDialog progressDialog;
    AnhDao anhDao;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initUI();
        String textRecipe = getColoredSpanned("Công thức ", "#FDA17A");
        String textAdvantage = getColoredSpanned("nấu ăn","#333333");
        tvTitle2.setText(Html.fromHtml(textRecipe + textAdvantage));

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isFirstTime = prefs.getBoolean(IS_FIRST_TIME_LAUNCH, true);

        if (isFirstTime) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(IS_FIRST_TIME_LAUNCH, false);
            editor.apply();
        } else {
            checkUserLogin();
        }
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(WelcomeActivity.this);
                progressDialog.setMessage("Đang tải dữ liệu . . .");
                progressDialog.show();
                CheckDataImage();
            }
        });
    }
    private void CheckDataImage() {
        databaseReference = FirebaseDatabase.getInstance().getReference("ALL_IMAGE");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Anh> lstNDFirebase = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Anh anh = snapshot.getValue(Anh.class);
                    lstNDFirebase.add(anh);
                }
                if (lstNDFirebase != null && !lstNDFirebase.isEmpty()) {
                    for (Anh anh:lstNDFirebase) {
                        anhDao.insert(anh);
                    }
                }
                CheckDataUser();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void CheckDataRecipe() {
        databaseReference = FirebaseDatabase.getInstance().getReference("CONG_THUC");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<CongThuc> lstCTFirebase = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CongThuc congThuc = snapshot.getValue(CongThuc.class);
                    lstCTFirebase.add(congThuc);
                }
                if (lstCTFirebase != null && !lstCTFirebase.isEmpty()) {
                    for (CongThuc congThuc:lstCTFirebase) {
                        congThucDao.insert(congThuc);
                        ArrayList<BuocLam> lstBuocLam = congThuc.getLstBuocLam();
                        ArrayList<BinhLuan> lstBinhLuan = congThuc.getLstBinhLuan();
                        ArrayList<DanhSachNguyenLieu> lstDSNL = congThuc.getLstNguyenLieu();
                        for (BuocLam buocLam:lstBuocLam) {
                            buocLamDao.insertID(buocLam);
                        }
                        for (DanhSachNguyenLieu dsnl:lstDSNL){
                            dsnlDao.insert(dsnl);
                        }
                        if (lstBinhLuan != null && !lstBinhLuan.isEmpty()) {
                            for (BinhLuan binhLuan:lstBinhLuan) {
                                binhLuanDao.insertID(binhLuan);
                            }
                        }

                    }
                }
                checkUserLogin();
                overridePendingTransition(R.anim.slide_in_down,R.anim.slide_out_down);
                progressDialog.dismiss();
                finish();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void CheckDataUser() {
        databaseReference = FirebaseDatabase.getInstance().getReference("NGUOI_DUNG");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<NguoiDung> lstNDFirebase = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    NguoiDung nguoiDung = snapshot.getValue(NguoiDung.class);
                    lstNDFirebase.add(nguoiDung);
                }
                if (lstNDFirebase != null && !lstNDFirebase.isEmpty()) {
                    for (NguoiDung nd:lstNDFirebase) {
                        nguoiDungDao.insert(nd);
                    }
                }
                CheckDataRecipe();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void initUI() {
        tvTitle2 = findViewById(R.id.tv_title2);
        btnStart = findViewById(R.id.btn_start);

        nguoiDungDao = new NguoiDungDao(this);
        congThucDao = new CongThucDao(this);
        buocLamDao = new BuocLamDao(this);
        binhLuanDao = new BinhLuanDao(this);
        dsnlDao = new DanhSachNguyenLieuDao(this);
        anhDao = new AnhDao(this);
    }
    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

    private void checkUserLogin(){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if (user ==null){
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        }else {
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        finish();
    }

}