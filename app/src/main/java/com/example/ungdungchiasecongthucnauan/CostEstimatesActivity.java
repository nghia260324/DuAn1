package com.example.ungdungchiasecongthucnauan;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ungdungchiasecongthucnauan.Adapter.NLAdapter;
import com.example.ungdungchiasecongthucnauan.Dao.NguyenLieuDao;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.DanhSachNguyenLieu;
import com.example.ungdungchiasecongthucnauan.Model.NguyenLieu;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class CostEstimatesActivity extends AppCompatActivity {

    LinearLayout layoutPrice;
    Button btnAddMaterial,btnCalculatorCalories,btnCompare,btnComparePrice;
    TextView tvSum;
    ImageButton btnBack;
    CongThuc congThuc;
    NguyenLieuDao nguyenLieuDao;
    ArrayList<NguyenLieu> lstNL;
    ArrayList<Integer> lstPrice;
    HashMap<Integer,String> lstPriceCheck = new HashMap<>();
    AutoCompleteTextView actvNL;
    EditText edt_quantity,edt_price;
    TextView tv_leftQuantity,tv_leftPrice;
    LinearLayout layoutCompare,wLayoutCalories,layoutCompareMain;
    RelativeLayout btnShowMore,layoutColumnCompare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_estimates);

        initUI();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnAddMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(CostEstimatesActivity.this).inflate(R.layout.item_cost_estimates,null);
                layoutPrice.addView(view);
                lstNL.add(new NguyenLieu());
                AddEventView();
                btnCompare.setVisibility(View.GONE);
                layoutCompare.removeAllViews();
                layoutCompare.setVisibility(View.GONE);
                layoutCompareMain.setVisibility(View.GONE);
            }
        });
        btnComparePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> lstPriceU = new ArrayList<>();
                for (int i = 0; i < layoutCompare.getChildCount(); i++) {
                    View view = layoutCompare.getChildAt(i);
                    EditText edtPrice = view.findViewById(R.id.edt_price);
                    String price = edtPrice.getText().toString().trim();
                    if (price.isEmpty() || !price.matches("[0-9]+")) {
                        Toast.makeText(CostEstimatesActivity.this, "Vui lòng kiểm tra lại thông tin!", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        lstPriceU.add(Integer.parseInt(price));
                    }
                }
                for (int i = 0; i < lstNL.size(); i++) {
                    NguyenLieu nguyenLieu = lstNL.get(i);
                    if (nguyenLieu.getTen().equals(lstPriceCheck.get(nguyenLieu.getId()))) {
                        lstNL.remove(nguyenLieu);
                        lstPrice.remove(i);
                    }
                }
                layoutColumnCompare.setVisibility(View.VISIBLE);
                LinearLayout layput_estimatedPrice = findViewById(R.id.layput_estimatedPrice);
                LinearLayout layput_userPrice = findViewById(R.id.layput_userPrice);
                LinearLayout layput_nameMaterial = findViewById(R.id.layput_nameMaterial);
                int sumPrice = 0;
                int sumPriceU = 0;
                LayoutInflater inflater = LayoutInflater.from(CostEstimatesActivity.this);
                for (int i = 0; i < lstNL.size(); i++) {
                    sumPrice += lstPrice.get(i);
                    sumPriceU += lstPriceU.get(i);

                    View view = inflater.inflate(R.layout.item_price_compare,null);
                    LinearLayout layout_bgrC = view.findViewById(R.id.layout_bgr);
                    TextView tvNameC = view.findViewById(R.id.tv_name);

                    if (i%2==1) {
                        layout_bgrC.setBackgroundColor(getColor(R.color.orange_FFD));
                    } else {
                        layout_bgrC.setBackgroundColor(getColor(R.color.orange_FDA));
                    }

                    tvNameC.setText(lstNL.get(i).getTen());
                    layput_nameMaterial.addView(view);
                    View view1 = inflater.inflate(R.layout.item_material_compare,null);
                    TextView tvPrice = view1.findViewById(R.id.tv_priceC);
                    tvPrice.setText(FormatPrice(lstPrice.get(i)) + "VND");
                    layput_estimatedPrice.addView(view1);
                    View view2 = inflater.inflate(R.layout.item_material_compare,null);
                    TextView tvPrice2 = view2.findViewById(R.id.tv_priceC);

                    if (lstPriceU.get(i) > lstPrice.get(i)) {
                        tvPrice2.setTextColor(getColor(R.color.orange_FDA));
                    } else {
                        tvPrice2.setTextColor(getColor(R.color.green_00F));
                    }
                    tvPrice2.setText(FormatPrice(lstPriceU.get(i)) + "VND");
                    layput_userPrice.addView(view2);

                    layoutCompare.setVisibility(View.GONE);
                    btnComparePrice.setVisibility(View.GONE);
                    btnShowMore.setVisibility(View.GONE);
                    if (i == lstNL.size() - 1) {
                        View viewD = inflater.inflate(R.layout.item_price_compare,null);

                        LinearLayout layout_bgrCD = viewD.findViewById(R.id.layout_bgr);
                        TextView tvNameCD = viewD.findViewById(R.id.tv_name);

                        tvNameCD.setTextColor(getColor(R.color.white));
                        tvNameCD.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        layout_bgrCD.setBackgroundColor(getColor(R.color.black_333));
                        tvNameCD.setText("Tổng:");
                        layput_nameMaterial.addView(viewD);

                        View view1D = inflater.inflate(R.layout.item_material_compare,null);
                        TextView tvPriceD = view1D.findViewById(R.id.tv_priceC);
                        tvPriceD.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        tvPriceD.setText(FormatPrice(sumPrice) + "VND");
                        layput_estimatedPrice.addView(view1D);

                        View view2D = inflater.inflate(R.layout.item_material_compare,null);
                        TextView tvPrice2D = view2D.findViewById(R.id.tv_priceC);
                        tvPrice2D.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        if (sumPriceU > sumPrice) {
                            tvPrice2D.setTextColor(getColor(R.color.orange_FDA));
                        } else {
                            tvPrice2D.setTextColor(getColor(R.color.green_00F));
                        }
                        tvPrice2D.setText(FormatPrice(sumPriceU) + "VND");
                        layput_userPrice.addView(view2D);
                    }
                }
            }
        });
        btnCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition((ViewGroup) wLayoutCalories, new Slide(Gravity.TOP).setDuration(300));
                wLayoutCalories.setVisibility(View.GONE);

                btnShowMore.setVisibility(View.VISIBLE);
                TransitionManager.beginDelayedTransition(wLayoutCalories);
                layoutCompare.setVisibility(View.VISIBLE);
                layoutCompareMain.setVisibility(View.VISIBLE);
                for (int i = 0; i < lstNL.size(); i++) {
                    if (lstPrice.get(i) != -1) {
                        View view = LayoutInflater.from(CostEstimatesActivity.this).inflate(R.layout.item_compare,null);
                        TextView tvMaterial = view.findViewById(R.id.tv_material);
                        TextView tvPrice = view.findViewById(R.id.tv_price);
                        tvMaterial.setText(lstNL.get(i).getTen());
                        tvPrice.setText(String.valueOf(lstPrice.get(i)));
                        layoutCompare.addView(view);
                    }
                }
//                AddEventViewLayoutCompare();
                btnCompare.setVisibility(View.GONE);
            }
        });


        ArrayList<DanhSachNguyenLieu> lstDSNL = congThuc.getLstNguyenLieu();
        for (int i = 0; i < lstDSNL.size(); i++) {
            View viewMain = LayoutInflater.from(CostEstimatesActivity.this).inflate(R.layout.item_cost_estimates,null);
            initView(viewMain);
            lstNL.add(nguyenLieuDao.getID(String.valueOf(lstDSNL.get(i).getIdNguyenLieu())));
            DanhSachNguyenLieu dsnl = congThuc.getLstNguyenLieu().get(i);
            NguyenLieu nguyenLieu = nguyenLieuDao.getID(String.valueOf(dsnl.getIdNguyenLieu()));
            actvNL.setText(nguyenLieu.getTen());
            edt_price.setText(nguyenLieu.getGia() + "");
            setUnit(tv_leftPrice,nguyenLieu);
            edt_quantity.setText(dsnl.getKhoiLuong() + "");
            new Service().SetMass(nguyenLieuDao,null,tv_leftQuantity,nguyenLieu.getId(),-1);
            layoutPrice.addView(viewMain);
            AddEventView();
        }
        btnShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition((ViewGroup) wLayoutCalories, new Slide(Gravity.TOP).setDuration(300));
                wLayoutCalories.setVisibility(View.VISIBLE);
                btnCompare.setVisibility(View.GONE);
                layoutCompare.removeAllViews();
                layoutCompare.setVisibility(View.GONE);
                btnShowMore.setVisibility(View.GONE);
                layoutCompareMain.setVisibility(View.GONE);
            }
        });
        btnCalculatorCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkLength = layoutPrice.getChildCount();
                if (checkLength > 0) {
                    ArrayList<String> lstHide = new ArrayList<>();
                    lstPrice = new ArrayList<>();
                    int sum = 0;
                    for (int i = 0; i < checkLength; i++) {
                        View view = layoutPrice.getChildAt(i);
                        initView(view);
                        String quantity = edt_quantity.getText().toString().trim();
                        String price = edt_price.getText().toString().trim();
                        if (quantity.isEmpty() || !quantity.matches("[0-9]+")) {
                            edt_quantity.setError("Vui lòng kiểm tra lại thông tin!");
                            return;
                        }
                        if (price.isEmpty() || !price.matches("[0-9]+")) {
                            edt_price.setError("Vui lòng kiểm tra lại thông tin!");
                            return;
                        }

                        int knl = lstNL.get(i).getKieu();
                        double p;
                        switch (knl){
                            case 1:
                            case 2:
                            case 3:
                            case 5:
                            case 9:
                                p = (double) (Integer.parseInt(quantity) * Integer.parseInt(price)) / 100;
                                lstPrice.add((int) Math.round(p));
                                break;
                            case 4:
                                lstPrice.add(Integer.parseInt(quantity) * Integer.parseInt(price));
                                break;
                            case 6:
                            case 7:
                            case 8:
                            case 10:
                                p = (double) (Integer.parseInt(quantity) * Integer.parseInt(price)) / 500;
                                lstPrice.add((int) Math.round(p));
                                break;
                            case 11:
                                lstHide.add(lstNL.get(i).getTen());
                                break;
                            case 12:
                                String name = lstNL.get(i).getTen();
                                switch (name) {
                                    case "Đường":
                                    case "Bột rau câu":
                                    case "Đậu phụ":
                                    case "Tắc - Quất":
                                    case "Nước cốt dừa":
                                    case "Khoai tím":
                                    case "Trà":
                                    case "Mật ong":
                                    case "Đường phèn":
                                    case "Trà hoa nhài":
                                    case "Bún tươi":
                                        p = (double) (Integer.parseInt(quantity) * Integer.parseInt(price)) / 100;
                                        lstPrice.add((int) Math.round(p));
                                        break;
                                    case "Mía tươi":
                                        p = (double) (Integer.parseInt(quantity) * Integer.parseInt(price)) / 500;
                                        lstPrice.add((int) Math.round(p));
                                        break;
                                    case "Nước nóng":
                                        lstPriceCheck.put(lstNL.get(i).getId(),lstNL.get(i).getTen());
                                        lstPrice.add(-1);
                                        lstHide.add(lstNL.get(i).getTen());
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            default:break;
                        }
                    }
                    for (int i = 0; i < lstPrice.size();i++) {
                        sum += lstPrice.get(i);
                    }
                    String textRecipe = getColoredSpanned("Tổng chi phí cho món ăn này là: ", "#333333");
                    String textAdvantage = getColoredSpanned(String.valueOf(sum),"#FDA17A");
                    tvSum.setText(Html.fromHtml(textRecipe + textAdvantage + " VND."));
                    TextView tv_note = findViewById(R.id.tv_note);
                    tv_note.setVisibility(View.VISIBLE);
                    tv_note.setText(" - Lưu ý: " + getResources().getText(R.string.text_note_price));
                    if (!lstHide.isEmpty()) {
                        String arr = "";
                        for (String s:lstHide) {
                            arr += s + ", ";
                        }
                        String s1 = getColoredSpanned("- Không bao gồm: ", "#333333");
                        String textHide = getColoredSpanned(arr, "#FDA17A");
                        tv_note.setText(Html.fromHtml(s1 + textHide + "<br/> - " + getResources().getText(R.string.text_note_price)));
                    }
                    btnCompare.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private String FormatPrice(int price) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        return numberFormat.format(price);
    }

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }
    private void AddEventView() {
        int check = layoutPrice.getChildCount();
        if (check > 0) {
            for (int i = 0; i < check; i++) {
                View viewMain = layoutPrice.getChildAt(i);
                initView(viewMain);
                int finalI = i;
                viewMain.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        lstNL.remove(finalI);
                        layoutPrice.removeView(viewMain);
                        AddEventView();
                        return true;
                    }
                });
                NLAdapter knlAdapter = new NLAdapter(CostEstimatesActivity.this, R.layout.item_selected_spinner_knl, getAllNguyenLieu());
                actvNL.setAdapter(knlAdapter);
                actvNL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        View view1 = layoutPrice.getChildAt(finalI);
                        initView(view1);
                        NguyenLieu nguyenLieu1 = nguyenLieuDao.getTen(actvNL.getText().toString().trim());
                        lstNL.remove(finalI);
                        lstNL.add(finalI,nguyenLieu1);
                        edt_price.setText(nguyenLieu1.getGia() + "");
                        setUnit(tv_leftPrice,nguyenLieu1);
                        new Service().SetMass(nguyenLieuDao,null,tv_leftQuantity,nguyenLieu1.getId(),-1);
                    }
                });
            }
        }
    }

    private void setUnit(TextView tv, NguyenLieu nguyenLieu){
        int typeMaterial = nguyenLieu.getKieu();
        String name = nguyenLieu.getTen();
        switch (typeMaterial){
            case 1:
            case 2:
            case 3:
            case 9:
                tv.setText("VND/100gram");
                break;
            case 4:
                tv.setText("VND/quả");
                break;
            case 5:
                tv.setText("VND/100ml");
                break;
            case 6:
            case 7:
            case 8:
            case 10:
                tv.setText("VND/500gram");
                break;
            case 11:
            case 12:
                switch (name) {
                    case "Đường":
                    case "Bột rau câu":
                    case "Đậu phụ":
                    case "Tắc - Quất":
                    case "Khoai tím":
                    case "Trà":
                    case "Đường phèn":
                    case "Trà hoa nhài":
                    case "Bún tươi":
                        tv.setText("VND/100gram");
                        break;
                    case "Nước cốt dừa":
                    case "Mật ong":
                        tv.setText("VND/100ml");
                        break;
                    case "Nước nóng":
                        tv.setText("");
                        break;
                    case "Mi tươi":
                        tv.setText("VND/500gram");
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
    private void initView(View view) {
        actvNL = view.findViewById(R.id.edt_materialName);
        edt_quantity = view.findViewById(R.id.edt_quantity);
        edt_price = view.findViewById(R.id.edt_price);
        tv_leftQuantity = view.findViewById(R.id.tv_leftQuantity);
        tv_leftPrice = view.findViewById(R.id.tv_leftPrice);
    }

    public ArrayList<NguyenLieu> getAllNguyenLieu(){
        ArrayList<NguyenLieu> lstNL = (ArrayList<NguyenLieu>) nguyenLieuDao.getAll();
        return lstNL;
    }
    private void initUI() {
        congThuc = new CongThuc();
        Intent intent = getIntent();
        if (intent.hasExtra("CONG_THUC")) {
            congThuc = (CongThuc) intent.getSerializableExtra("CONG_THUC");
        }

        lstNL = new ArrayList<>();
        nguyenLieuDao = new NguyenLieuDao(this);

        btnShowMore = findViewById(R.id.btn_showMore);
        wLayoutCalories = findViewById(R.id.w_layout_Calories);
        layoutCompare = findViewById(R.id.layout_compare);
        layoutCompareMain = findViewById(R.id.layout_compareMain);
        layoutPrice = findViewById(R.id.layout_price);
        layoutColumnCompare = findViewById(R.id.layout_columnCompare);
        btnAddMaterial = findViewById(R.id.btn_addMaterial);
        btnCalculatorCalories = findViewById(R.id.btn_calculatorPrice);
        btnCompare = findViewById(R.id.btn_compare);
        btnComparePrice = findViewById(R.id.btn_comparePrice);
        tvSum = findViewById(R.id.tv_sum);
        btnBack = findViewById(R.id.btn_back);
    }
}