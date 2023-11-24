package com.example.ungdungchiasecongthucnauan;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ungdungchiasecongthucnauan.Adapter.ViewPagerBottomNavigationAdapter;
import com.example.ungdungchiasecongthucnauan.Dao.AnhDao;
import com.example.ungdungchiasecongthucnauan.Dao.BinhLuanDao;
import com.example.ungdungchiasecongthucnauan.Dao.BuocLamDao;
import com.example.ungdungchiasecongthucnauan.Dao.CongThucDao;
import com.example.ungdungchiasecongthucnauan.Dao.DanhSachCongThucDao;
import com.example.ungdungchiasecongthucnauan.Dao.DanhSachNguyenLieuDao;
import com.example.ungdungchiasecongthucnauan.Dao.KieuNguyenLieuDao;
import com.example.ungdungchiasecongthucnauan.Dao.LoaiCongThucDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguoiDungDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguyenLieuDao;
import com.example.ungdungchiasecongthucnauan.Model.Anh;
import com.example.ungdungchiasecongthucnauan.Model.BinhLuan;
import com.example.ungdungchiasecongthucnauan.Model.BuocLam;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.DanhSachCongThuc;
import com.example.ungdungchiasecongthucnauan.Model.DanhSachNguyenLieu;
import com.example.ungdungchiasecongthucnauan.Model.KieuNguyenLieu;
import com.example.ungdungchiasecongthucnauan.Model.LoaiCongThuc;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;
import com.example.ungdungchiasecongthucnauan.Model.NguyenLieu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String DATABASE_PATH = "CONG_THUC";
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
    AnhDao anhDao;
    BinhLuanDao binhLuanDao;
    BuocLamDao buocLamDao;
    DanhSachNguyenLieuDao dsnlDao;
    public ArrayList<CongThuc> lstCongThuc;
    public ArrayList<CongThuc> myRecipes;
    public ArrayList<LoaiCongThuc> lstLoaiCongThuc;
    ViewPager2 viewPager2;
    DatabaseReference databaseReference;
    private BroadcastReceiver alarmReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            sendNotification();
        }
    };

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        ViewPagerBottomNavigationAdapter viewPagerBottomNavigationAdapter = new ViewPagerBottomNavigationAdapter(MainActivity.this,getUser());
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
                        viewPager2.setCurrentItem(0,false);
                        mCurrentFragment = FRAGMENT_HOME;
                    }
                } else if(item.getItemId() == R.id.search) {
                    if (mCurrentFragment != FRAGMENT_SEARCH) {
                        viewPager2.setCurrentItem(1,false);
                        mCurrentFragment = FRAGMENT_SEARCH;
                    }
                } else if (item.getItemId() == R.id.create_recipes) {
                    if (mCurrentFragment != FRAGMENT_CREATE_RECIPES) {
                        viewPager2.setCurrentItem(2,false);
                        mCurrentFragment = FRAGMENT_CREATE_RECIPES;
                    }
                } else if (item.getItemId() == R.id.individual) {
                    if (mCurrentFragment != FRAGMENT_INDIVIDIAL) {
                        viewPager2.setCurrentItem(3,false);
                        mCurrentFragment = FRAGMENT_INDIVIDIAL;
                    }
                }
                return true;
            }
        });

        Menu menu = bottomNavigationView.getMenu();
        MenuItem itemCreateRecipes = menu.findItem(R.id.create_recipes);
        if (getUser().getPhanQuyen() == 1) {
            itemCreateRecipes.setVisible(false);
        } else {
            itemCreateRecipes.setVisible(true);
        }
        GetRecipes();
//        GetAllData();
        CheckDataImage();
        IntentFilter filter = new IntentFilter("ALARM_TRIGGERED");
        registerReceiver(alarmReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(alarmReceiver);
    }

    public ArrayList<CongThuc> GetRecipes() {
        myRecipes = (ArrayList<CongThuc>) congThucDao.getAllMyRecipes(getUser().getId());
        for (CongThuc congThuc: lstCongThuc){
            Log.e("Công thức","" + congThuc.toString());
        }
        return myRecipes;
    }

//    private void GetAllData() {
//        Thread thread = new Thread(() -> {
//            databaseReference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    lstCongThuc.clear();
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        CongThuc congThuc = snapshot.getValue(CongThuc.class);
//                        if (congThuc.getTrangThai() == 1) {
//                            lstCongThuc.add(congThuc);
//                        }
//                    }
//                    if (dataChangeListener != null) {
//                        dataChangeListener.onDataChange((ArrayList<CongThuc>) congThucDao.getTopCtBL());
//                    }
//                    if (progressDialog != null) {
//                        progressDialog.dismiss();
//                    }
//                }
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                }
//            });
//        });
//        thread.start();
//    }

    public NguoiDung getUser() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        NguoiDung nguoiDung = nguoiDungDao.getNguoiDungFromEmail(email);
        return nguoiDung;
    }

    private void initUI() {
        databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);
        nguoiDungDao = new NguoiDungDao(this);
        nguyenLieuDao = new NguyenLieuDao(this);
        loaiCongThucDao = new LoaiCongThucDao(this);
        congThucDao = new CongThucDao(this);
        anhDao = new AnhDao(this);
        binhLuanDao = new BinhLuanDao(this);
        dsnlDao = new DanhSachNguyenLieuDao(this);
        buocLamDao = new BuocLamDao(this);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        viewPager2 = findViewById(R.id.viewPager2);

        lstCongThuc = new ArrayList<>();
        myRecipes = new ArrayList<>();
        kieuNguyenLieuDao = new KieuNguyenLieuDao(this);
        lstLoaiCongThuc = new ArrayList<>();
        lstLoaiCongThuc = (ArrayList<LoaiCongThuc>) loaiCongThucDao.getAll();

        DanhSachCongThucDao dsctDao = new DanhSachCongThucDao(this);
        ArrayList<DanhSachCongThuc> lstDSCT = (ArrayList<DanhSachCongThuc>) dsctDao.getAll();
        for (DanhSachCongThuc dsct:lstDSCT) {
            Log.e("DSCT",dsct.toString());
        }
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
    private void sendNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, ConfigNotification.CHANNEL_ID)
                .setSmallIcon(R.drawable.logoapp)
                .setContentTitle("Đã đến giờ hẹn. Hãy tiếp tục nấu bữa ăn của mình nào !!!")
                .setContentText("")
                .setColor(Color.RED)
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED) {
            notificationManagerCompat.notify((int) System.currentTimeMillis(),builder.build());
        } else {
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.POST_NOTIFICATIONS},7979);
        }
    }


    private void CheckDataImage() {
        databaseReference = FirebaseDatabase.getInstance().getReference("ALL_IMAGE");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Anh> lstAnhFirebase = new ArrayList<>();
                ArrayList<Anh> lstAnhCSDL = (ArrayList<Anh>) anhDao.getAll();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Anh anh = snapshot.getValue(Anh.class);
                    lstAnhFirebase.add(anh);
                }
                if (lstAnhFirebase != null && !lstAnhFirebase.isEmpty() && lstAnhCSDL.isEmpty()) {
                    for (Anh anh:lstAnhFirebase) {
                        anhDao.insert(anh);
                    }
                } else if (lstAnhFirebase != null && !lstAnhFirebase.isEmpty() && lstAnhCSDL != null && !lstAnhCSDL.isEmpty()) {
                    for (Anh anh:lstAnhFirebase) {
                        if (congThucDao.checkExists("Anh","id",anh.getId())) {
                            anhDao.update(anh);
                        } else {
                            anhDao.insert(anh);
                        }
                    }
                    for (Anh anh:lstAnhCSDL) {
                        boolean check = true;
                        for (Anh a:lstAnhFirebase) {
                            if (a.getId().equals(anh.getId())) {
                                check = false;
                                break;
                            }
                        }
                        if (check) {
                            anhDao.delete(anh.getId());
                        }
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
                ArrayList<CongThuc> lstCSDL = (ArrayList<CongThuc>) congThucDao.getAll();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CongThuc congThuc = snapshot.getValue(CongThuc.class);
                    lstCTFirebase.add(congThuc);
                }
                if (lstCTFirebase != null && !lstCTFirebase.isEmpty() && lstCSDL.isEmpty()) {
                    for (CongThuc congThuc:lstCTFirebase) {
                        congThucDao.insert(congThuc);
                        ArrayList<BuocLam> lstBuocLam = congThuc.getLstBuocLam();
                        ArrayList<BinhLuan> lstBinhLuan = congThuc.getLstBinhLuan();
                        ArrayList<DanhSachNguyenLieu> lstDSNL = congThuc.getLstNguyenLieu();
                        for (BuocLam buocLam:lstBuocLam) {
                            buocLamDao.insert(buocLam);
                        }
                        for (DanhSachNguyenLieu dsnl:lstDSNL){
                            dsnlDao.insert(dsnl);
                        }
                        if (lstBinhLuan != null && !lstBinhLuan.isEmpty()) {
                            for (BinhLuan binhLuan:lstBinhLuan) {
                                binhLuanDao.insert(binhLuan);
                            }
                        }
                    }
                } else if (lstCTFirebase != null && !lstCTFirebase.isEmpty() && lstCSDL != null && !lstCSDL.isEmpty()) {
                    for (CongThuc congThuc:lstCTFirebase) {
                        if (congThucDao.checkExists("CongThuc","id",congThuc.getId())) {
                            congThucDao.update(congThuc);
                            ArrayList<BuocLam> lstBuocLam = congThuc.getLstBuocLam();
                            ArrayList<DanhSachNguyenLieu> lstDSNL = congThuc.getLstNguyenLieu();
                            ArrayList<BinhLuan> lstBinhLuan = congThuc.getLstBinhLuan();

                            for (BuocLam buocLam:lstBuocLam) {
                                if (congThucDao.checkExists("BuocLam","id",String.valueOf(buocLam.getId()))) {
                                    buocLamDao.update(buocLam);
                                } else {
                                    buocLamDao.insert(buocLam);
                                }
                            }
                            for (DanhSachNguyenLieu dsnl:lstDSNL){
                                if (congThucDao.checkExists("DanhSachNguyenLieu","id",String.valueOf(dsnl.getId()))) {
                                    dsnlDao.update(dsnl);
                                } else {
                                    dsnlDao.insert(dsnl);
                                }
                            }
                            if (lstBinhLuan != null && !lstBinhLuan.isEmpty()) {
                                for (BinhLuan binhLuan:lstBinhLuan) {
                                    if (congThucDao.checkExists("BinhLuan","id",String.valueOf(binhLuan.getId()))) {
                                        binhLuanDao.update(binhLuan);
                                    } else {
                                        binhLuanDao.insert(binhLuan);
                                    }
                                }
                            }
                        } else {
                            congThucDao.insert(congThuc);
                            ArrayList<BuocLam> lstBuocLam = congThuc.getLstBuocLam();
                            ArrayList<BinhLuan> lstBinhLuan = congThuc.getLstBinhLuan();
                            ArrayList<DanhSachNguyenLieu> lstDSNL = congThuc.getLstNguyenLieu();
                            for (BuocLam buocLam:lstBuocLam) {
                                buocLamDao.insert(buocLam);
                            }
                            for (DanhSachNguyenLieu dsnl:lstDSNL){
                                dsnlDao.insert(dsnl);
                            }
                            if (lstBinhLuan != null && !lstBinhLuan.isEmpty()) {
                                for (BinhLuan binhLuan:lstBinhLuan) {
                                    binhLuanDao.insert(binhLuan);
                                }
                            }
                        }
                    }
                    for (CongThuc congThuc:lstCSDL) {
                        boolean check = true;
                        for (CongThuc ct:lstCTFirebase) {
                            if (ct.getId().equals(congThuc.getId())) {
                                check = false;
                                break;
                            }
                        }
                        if (check) {
                            congThucDao.delete(congThuc.getId());

                            ArrayList<BuocLam> lstBuocLam = congThuc.getLstBuocLam();
                            ArrayList<DanhSachNguyenLieu> lstDSNL = congThuc.getLstNguyenLieu();
                            ArrayList<BinhLuan> lstBinhLuan = congThuc.getLstBinhLuan();

                            for (BuocLam buocLam:lstBuocLam) {
                                buocLamDao.delete(String.valueOf(buocLam.getId()));
                            }
                            for (DanhSachNguyenLieu dsnl:lstDSNL){
                                dsnlDao.delete(String.valueOf(dsnl.getId()));
                            }
                            if (lstBinhLuan != null && !lstBinhLuan.isEmpty()) {
                                for (BinhLuan binhLuan:lstBinhLuan) {
                                    binhLuanDao.delete(String.valueOf(binhLuan.getId()));
                                }
                            }
                        }
                    }
                }
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
                ArrayList<NguoiDung> lstNDCSDL = (ArrayList<NguoiDung>) nguoiDungDao.getAll();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    NguoiDung nguoiDung = snapshot.getValue(NguoiDung.class);
                    lstNDFirebase.add(nguoiDung);
                }
                if (lstNDFirebase != null && !lstNDFirebase.isEmpty() && lstNDCSDL.isEmpty()) {
                    for (NguoiDung nd:lstNDFirebase) {
                        nguoiDungDao.insert(nd);
                    }
                } else if (lstNDFirebase != null && !lstNDFirebase.isEmpty() && lstNDCSDL != null && !lstNDCSDL.isEmpty()) {
                    for (NguoiDung nd:lstNDFirebase) {
                        if (congThucDao.checkExists("NguoiDung","id",nd.getId())) {
                            nguoiDungDao.update(nd);
                        } else {
                            nguoiDungDao.insert(nd);
                        }
                    }
                    for (NguoiDung nd:lstNDCSDL) {
                        boolean check = true;
                        for (NguoiDung n:lstNDFirebase) {
                            if (n.getId().equals(nd.getId())) {
                                check = false;
                                break;
                            }
                        }
                        if (check) {
                            nguoiDungDao.delete(nd.getId());
                        }
                    }
                }
                CheckDataRecipe();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}