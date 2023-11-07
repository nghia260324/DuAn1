package com.example.ungdungchiasecongthucnauan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.ungdungchiasecongthucnauan.Dao.KieuNguyenLieuDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguoiDungDao;
import com.example.ungdungchiasecongthucnauan.Fragment.CreateRecipesFragment;
import com.example.ungdungchiasecongthucnauan.Fragment.HomeFragment;
import com.example.ungdungchiasecongthucnauan.Fragment.IndividualFragment;
import com.example.ungdungchiasecongthucnauan.Fragment.SearchFragment;
import com.example.ungdungchiasecongthucnauan.Model.KieuNguyenLieu;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;
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
    NguoiDung nguoiDung;

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
        User();
    }

    private void User() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("userID");

        nguoiDung = nguoiDungDao.getID(id);
    }

    private void initUI() {
        nguoiDungDao = new NguoiDungDao(this);
        nguoiDung = new NguoiDung();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        frameLayout = findViewById(R.id.frameLayout);

        kieuNguyenLieuDao = new KieuNguyenLieuDao(this);
    }

    public ArrayList<KieuNguyenLieu> getAllKieuNguyenLieu(){
        ArrayList<KieuNguyenLieu> lstKNT = new ArrayList<>();
        lstKNT = (ArrayList<KieuNguyenLieu>) kieuNguyenLieuDao.getAll();
        return lstKNT;
    }
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
}