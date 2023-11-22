package com.example.ungdungchiasecongthucnauan;

import android.os.Bundle;
import android.view.MenuItem;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_user);

        initUI();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        list = nguoiDungDao.getAll();

        adminUserAdapter = new AdminUserAdapter(this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(adminUserAdapter);

    }

    private void initUI() {
        recyclerView = findViewById(R.id.rcv_user);
        list = new ArrayList<>();
        toolbar = findViewById(R.id.toolbar);
        nguoiDungDao = new NguoiDungDao(this);

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