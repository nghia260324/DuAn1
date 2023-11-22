package com.example.ungdungchiasecongthucnauan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.ungdungchiasecongthucnauan.Adapter.AdminCTAdapter;
import com.example.ungdungchiasecongthucnauan.Adapter.AdminUserAdapter;
import com.example.ungdungchiasecongthucnauan.Dao.NguoiDungDao;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class ManagerUserActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdminUserAdapter adminUserAdapter;

    List<NguoiDung> list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_user);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        recyclerView=findViewById(R.id.rcv_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        NguoiDungDao nguoiDungDao=new NguoiDungDao(this);
        list=new ArrayList<>();
        list=nguoiDungDao.getAll();

        adminUserAdapter=new AdminUserAdapter(this,list);
        recyclerView.setAdapter(adminUserAdapter);

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