package com.example.ungdungchiasecongthucnauan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ungdungchiasecongthucnauan.Dao.CongThucDao;
import com.example.ungdungchiasecongthucnauan.Dao.KieuNguyenLieuDao;
import com.example.ungdungchiasecongthucnauan.Dao.LoaiCongThucDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguoiDungDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguyenLieuDao;
import com.example.ungdungchiasecongthucnauan.Fragment.CreateRecipesFragment;
import com.example.ungdungchiasecongthucnauan.Fragment.HomeFragment;
import com.example.ungdungchiasecongthucnauan.Fragment.IndividualFragment;
import com.example.ungdungchiasecongthucnauan.Fragment.SearchFragment;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.KieuNguyenLieu;
import com.example.ungdungchiasecongthucnauan.Model.LoaiCongThuc;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;
import com.example.ungdungchiasecongthucnauan.Model.NguyenLieu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_SEARCH = 1;
    private static final int FRAGMENT_CREATE_RECIPES = 2;
    private static final int FRAGMENT_INDIVIDIAL = 3;
    private int mCurrentFragment = FRAGMENT_HOME;
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    KieuNguyenLieuDao kieuNguyenLieuDao;
    NguoiDungDao nguoiDungDao;
    NguyenLieuDao nguyenLieuDao;
    LoaiCongThucDao loaiCongThucDao;
    CongThucDao congThucDao;

    public ArrayList<CongThuc> lstCongThuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home) {
                    if (mCurrentFragment != FRAGMENT_HOME) {
                        replaceFragment(new HomeFragment());
                        mCurrentFragment = FRAGMENT_HOME;
                    }
                } else if(item.getItemId() == R.id.search) {
                    if (mCurrentFragment != FRAGMENT_SEARCH) {
                        replaceFragment(new SearchFragment());
                        mCurrentFragment = FRAGMENT_SEARCH;
                    }
                } else if (item.getItemId() == R.id.create_recipes) {
                    if (mCurrentFragment != FRAGMENT_CREATE_RECIPES) {
                        replaceFragment(new CreateRecipesFragment());
                        mCurrentFragment = FRAGMENT_CREATE_RECIPES;
                    }
                } else if (item.getItemId() == R.id.individual) {
                    if (mCurrentFragment != FRAGMENT_INDIVIDIAL) {
                        replaceFragment(new IndividualFragment());
                        mCurrentFragment = FRAGMENT_INDIVIDIAL;
                    }
                }
                return true;
            }
        });
        lstCongThuc = (ArrayList<CongThuc>) congThucDao.getAll();
        for (CongThuc congThuc: lstCongThuc){
            Log.e("Công thức","" + congThuc.toString());
        }
    }

    public NguoiDung getUser() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("userID");
        NguoiDung nguoiDung = nguoiDungDao.getID(id);
        return nguoiDung;
    }

    private void initUI() {
        nguoiDungDao = new NguoiDungDao(this);
        nguyenLieuDao = new NguyenLieuDao(this);
        loaiCongThucDao = new LoaiCongThucDao(this);
        congThucDao = new CongThucDao(this);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        frameLayout = findViewById(R.id.frameLayout);

        kieuNguyenLieuDao = new KieuNguyenLieuDao(this);
    }

    public ArrayList<KieuNguyenLieu> getAllKieuNguyenLieu(){
        ArrayList<KieuNguyenLieu> lstKNT = (ArrayList<KieuNguyenLieu>) kieuNguyenLieuDao.getAll();
        return lstKNT;
    }
    public ArrayList<NguyenLieu> getAllNguyenLieu(){
        ArrayList<NguyenLieu> lstNL = (ArrayList<NguyenLieu>) nguyenLieuDao.getAll();
        return lstNL;
    }
    public ArrayList<LoaiCongThuc> getAllLoaiCongThuc(){
        ArrayList<LoaiCongThuc> lstLCT = (ArrayList<LoaiCongThuc>) loaiCongThucDao.getAll();
        return lstLCT;
    }


    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
}