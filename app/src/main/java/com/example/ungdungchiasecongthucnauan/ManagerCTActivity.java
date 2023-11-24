package com.example.ungdungchiasecongthucnauan;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

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
    EditText ed_searchCT;

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

        ed_searchCT.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (ed_searchCT.getRight() - ed_searchCT.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        String keyword = ed_searchCT.getText().toString().trim();
                        if (TextUtils.isEmpty(keyword)) {
                            list = congThucDao.getAll();
                        } else {
                            list = congThucDao.getData("SELECT * FROM CongThuc WHERE ten= ?",new String[]{keyword});
                        }
                        adminCTAdapter.setList(list);
                        recyclerView.setAdapter(adminCTAdapter);
                        return true;
                    }
                }
                return false;
            }
        });




    }

    private void initUI() {
        toolbar = findViewById(R.id.toolbar);
        congThucDao = new CongThucDao(this);
        list = new ArrayList<>();
        ed_searchCT=findViewById(R.id.edt_searchCT);

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