package com.example.ungdungchiasecongthucnauan;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ungdungchiasecongthucnauan.Adapter.AdminUserAdapter;
import com.example.ungdungchiasecongthucnauan.Dao.NguoiDungDao;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class ManagerUserActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdminUserAdapter adminUserAdapter;
    List<NguoiDung> list;
    Toolbar toolbar;
    NguoiDungDao nguoiDungDao;
    EditText et_searchUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_user);

        initUI();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        list = nguoiDungDao.getAllND();

        adminUserAdapter = new AdminUserAdapter(this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(adminUserAdapter);


        et_searchUser.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (et_searchUser.getRight() - et_searchUser.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        String keyword = et_searchUser.getText().toString().trim();
                        if (TextUtils.isEmpty(keyword)) {
                            list = nguoiDungDao.getAllND();
                        } else {
                            list = nguoiDungDao.search(keyword);
                            if (list.size()==0){
                                Toast.makeText(getApplicationContext(),"Không tìm thấy kết quả phù hợp",Toast.LENGTH_SHORT).show();
                            }
                        }
                        adminUserAdapter.setList(list);
                        recyclerView.setAdapter(adminUserAdapter);
                        return true;
                    }
                }
                return false;
            }
        });


    }

    private void initUI() {
        recyclerView = findViewById(R.id.rcv_user);
        list = new ArrayList<>();
        toolbar = findViewById(R.id.toolbar);
        nguoiDungDao = new NguoiDungDao(this);
        et_searchUser=findViewById(R.id.edt_searchUser);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}