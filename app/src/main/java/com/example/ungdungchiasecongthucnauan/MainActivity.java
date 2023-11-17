package com.example.ungdungchiasecongthucnauan;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ungdungchiasecongthucnauan.Adapter.ViewPagerBottomNavigationAdapter;
import com.example.ungdungchiasecongthucnauan.Dao.CongThucDao;
import com.example.ungdungchiasecongthucnauan.Dao.KieuNguyenLieuDao;
import com.example.ungdungchiasecongthucnauan.Dao.LoaiCongThucDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguoiDungDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguyenLieuDao;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.KieuNguyenLieu;
import com.example.ungdungchiasecongthucnauan.Model.LoaiCongThuc;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;
import com.example.ungdungchiasecongthucnauan.Model.NguyenLieu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_SEARCH = 1;
    private static final int FRAGMENT_CREATE_RECIPES = 2;
    private static final int FRAGMENT_INDIVIDIAL = 3;
    private int mCurrentFragment = FRAGMENT_HOME;
    BottomNavigationView bottomNavigationView;
    KieuNguyenLieuDao kieuNguyenLieuDao;
    NguoiDungDao nguoiDungDao;
    NguyenLieuDao nguyenLieuDao;
    LoaiCongThucDao loaiCongThucDao;
    CongThucDao congThucDao;
    public ArrayList<CongThuc> lstCongThuc;
    public ArrayList<LoaiCongThuc> lstLoaiCongThuc;
    ViewPager2 viewPager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        ViewPagerBottomNavigationAdapter viewPagerBottomNavigationAdapter = new ViewPagerBottomNavigationAdapter(this);
        viewPager2.setUserInputEnabled(false);
        viewPager2.setAdapter(viewPagerBottomNavigationAdapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0: bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
                        break;
                    case 1: bottomNavigationView.getMenu().findItem(R.id.search).setChecked(true);
                        break;
                    case 2: bottomNavigationView.getMenu().findItem(R.id.create_recipes).setChecked(true);
                        break;
                    case 3: bottomNavigationView.getMenu().findItem(R.id.individual).setChecked(true);
                        break;
                }
            }
        });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home) {
                    if (mCurrentFragment != FRAGMENT_HOME) {
                        viewPager2.setCurrentItem(0);
                        mCurrentFragment = FRAGMENT_HOME;
                    }
                } else if(item.getItemId() == R.id.search) {
                    if (mCurrentFragment != FRAGMENT_SEARCH) {
                        viewPager2.setCurrentItem(1);
                        mCurrentFragment = FRAGMENT_SEARCH;
                    }
                } else if (item.getItemId() == R.id.create_recipes) {
                    if (mCurrentFragment != FRAGMENT_CREATE_RECIPES) {
                        viewPager2.setCurrentItem(2);
                        mCurrentFragment = FRAGMENT_CREATE_RECIPES;
                    }
                } else if (item.getItemId() == R.id.individual) {
                    if (mCurrentFragment != FRAGMENT_INDIVIDIAL) {
                        viewPager2.setCurrentItem(3);
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
//        Intent intent = getIntent();
//        String id = intent.getStringExtra("userID");
//        NguoiDung nguoiDung = nguoiDungDao.getID(id);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        NguoiDung nguoiDung = nguoiDungDao.getNguoiDungFromEmail(email);
        return nguoiDung;
    }

    private void initUI() {
        nguoiDungDao = new NguoiDungDao(this);
        nguyenLieuDao = new NguyenLieuDao(this);
        loaiCongThucDao = new LoaiCongThucDao(this);
        congThucDao = new CongThucDao(this);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        viewPager2 = findViewById(R.id.viewPager2);

        kieuNguyenLieuDao = new KieuNguyenLieuDao(this);
        lstLoaiCongThuc = new ArrayList<>();
        lstLoaiCongThuc = (ArrayList<LoaiCongThuc>) loaiCongThucDao.getAll();
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
    public ArrayList<CongThuc> lstViewedRecipe(Context context){
        ArrayList<String> lstIDCongThuc = (ArrayList<String>) new Service().readFile(context,"recipe_viewed.txt");
        ArrayList<CongThuc> lstCT = new ArrayList<>();
        if (lstIDCongThuc != null){
            for (int i = 0; i < lstIDCongThuc.size(); i++){
                CongThuc congThuc = congThucDao.getID(lstIDCongThuc.get(i));
                lstCT.add(congThuc);
            }
            return lstCT;
        }
        return null;
    }
}