package com.example.ungdungchiasecongthucnauan;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ungdungchiasecongthucnauan.Adapter.AdminCTAdapter;
import com.example.ungdungchiasecongthucnauan.Dao.CongThucDao;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;

import java.util.ArrayList;
import java.util.List;

public class ManagerCTActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdminCTAdapter adminCTAdapter;
    List<CongThuc> list;
    MainActivity mainActivity;
    CongThucDao congThucDao;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_ctactivity);

        initUI();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        recyclerView = findViewById(R.id.rcv_congthuc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = congThucDao.getAll();

        adminCTAdapter = new AdminCTAdapter(this,list,mainActivity);
        recyclerView.setAdapter(adminCTAdapter);
    }

    private void initUI() {
        toolbar = findViewById(R.id.toolbar);

        congThucDao = new CongThucDao(this);
        list = new ArrayList<>();
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