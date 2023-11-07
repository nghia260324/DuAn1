package com.example.ungdungchiasecongthucnauan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ungdungchiasecongthucnauan.Database.DbHelper;

public class WelcomeActivity extends AppCompatActivity {
    TextView tvTitle2;
    Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initUI();
        String textRecipe = getColoredSpanned("Công thức ", "#FDA17A");
        String textAdvantage = getColoredSpanned("nấu ăn","#333333");
        tvTitle2.setText(Html.fromHtml(textRecipe + textAdvantage));

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                overridePendingTransition(R.anim.slide_in_down,R.anim.slide_out_down);
                finish();
            }
        });
    }

    private void initUI() {
        tvTitle2 = findViewById(R.id.tv_title2);
        btnStart = findViewById(R.id.btn_start);
    }
    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }
}