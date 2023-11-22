package com.example.ungdungchiasecongthucnauan;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ungdungchiasecongthucnauan.Adapter.NLAdapter;
import com.example.ungdungchiasecongthucnauan.Dao.AnhDao;
import com.example.ungdungchiasecongthucnauan.Dao.BinhLuanDao;
import com.example.ungdungchiasecongthucnauan.Dao.CongThucDao;
import com.example.ungdungchiasecongthucnauan.Dao.DanhSachNguyenLieuDao;
import com.example.ungdungchiasecongthucnauan.Dao.LoaiCongThucDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguoiDungDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguyenLieuDao;
import com.example.ungdungchiasecongthucnauan.Model.Anh;
import com.example.ungdungchiasecongthucnauan.Model.BinhLuan;
import com.example.ungdungchiasecongthucnauan.Model.BuocLam;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.DanhSachNguyenLieu;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;
import com.example.ungdungchiasecongthucnauan.Model.NguyenLieu;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class ChiTietCongThuc {
    Context context;
    CongThuc congThuc;
    AnhDao anhDao;
    CongThucDao congThucDao;
    NguoiDungDao nguoiDungDao;
    DanhSachNguyenLieuDao danhSachNguyenLieuDao;
    BinhLuanDao binhLuanDao;
    NguyenLieuDao nguyenLieuDao;
    LoaiCongThucDao loaiCongThucDao;
    RecyclerView rcvMaking;
    ImageView imgBanner,imgAvatar,img_iconBtn;
    ImageButton btnBack,btnSend;
    LinearLayout btnSave;
    TextView tvNameUser,tvFormulaName,tvTime,tvRation,tvCookingTime,tvComment,tvNoneComment,tv_smBtn;
    EditText edtContent;
    RelativeLayout layoutLine,lineComment;
    LinearLayout layoutMaterial,layoutMaking,layoutComment,layoutAddComment;
    RelativeLayout btn_showMore;
    MainActivity mainActivity;
    public ChiTietCongThuc(Context context, CongThuc congThuc,MainActivity mainActivity) {
        this.context = context;
        this.congThuc = congThuc;
        this.anhDao = new AnhDao(context);
        this.nguoiDungDao = new NguoiDungDao(context);
        this.danhSachNguyenLieuDao = new DanhSachNguyenLieuDao(context);
        this.nguyenLieuDao = new NguyenLieuDao(context);
        this.mainActivity = mainActivity;
        this.congThucDao = new CongThucDao(context);
        this.loaiCongThucDao = new LoaiCongThucDao(context);
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void OpenDialogCreateRecipes() {
        final View dialogView = View.inflate(context,R.layout.dialog_recipe_details,null);
        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);

        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(layoutParams);

        initUI(dialog);



        LinearLayout btn_save = dialog.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SaveRecipe().OpenDialogSaveRecipe(context,congThuc,mainActivity);
            }
        });
        ImageButton img_btn_save = dialog.findViewById(R.id.img_btn_save);
        img_btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SaveRecipe().OpenDialogSaveRecipe(context,congThuc,mainActivity);
            }
        });
        ImageView img_CommmentUser = dialog.findViewById(R.id.img_CommmentUser);
        new Service().setAvatar(img_CommmentUser,mainActivity.getUser().getAvatar());

        ImageButton btnMore = dialog.findViewById(R.id.btn_more);
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu(v);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Anh anh = new Anh();
        NguoiDung nguoiDung = nguoiDungDao.getID(String.valueOf(congThuc.getIdNguoiDung()));
        if (congThuc.getIdAnh() != null){
            anh = anhDao.getID(congThuc.getIdAnh());
        }
        Glide.with(context).load(anh.getUrl()).error(R.drawable.ct).into(imgBanner);
        Anh finalAnh = anh;
        imgBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Service().DialogEnlarge(context, finalAnh);
            }
        });

        new Service().setAvatar(imgAvatar,nguoiDung.getAvatar());
        tvNameUser.setText(nguoiDung.getHoTen());
        tvFormulaName.setText(congThuc.getTen());
        tvTime.setText(sdf.format(congThuc.getNgayTao()));

        int ration = congThuc.getKhauPhan();
        int time = congThuc.getThoiGianNau();
        if (ration > 0) {
            tvRation.setText("Khẩu phần: " + congThuc.getKhauPhan() + " người.");
        } else {tvRation.setVisibility(View.GONE);}
        if (time > 0){
            tvCookingTime.setText("Thời gian nấu: " + congThuc.getThoiGianNau() + " phút.");
        } else {tvCookingTime.setVisibility(View.GONE);}
        if (ration < 0 && time < 0){
            tvRation.setVisibility(View.GONE);
            tvCookingTime.setVisibility(View.GONE);
            layoutLine.setVisibility(View.GONE);
        }
        ChiTiet();
        dialog.show();
    }


    private void initUI(Dialog dialog) {
//        rcvMaking = dialog.findViewById(R.id.rcv_making);
        imgBanner = dialog.findViewById(R.id.img_banner);
        imgAvatar = dialog.findViewById(R.id.img_avatar);
        tvNameUser = dialog.findViewById(R.id.tv_nameUser);
        tvFormulaName = dialog.findViewById(R.id.tv_formulaName);
        tvTime = dialog.findViewById(R.id.tv_time);
        tvRation = dialog.findViewById(R.id.tv_ration);
        tvCookingTime = dialog.findViewById(R.id.tv_cookingTime);
        layoutLine = dialog.findViewById(R.id.layout_line);
        lineComment = dialog.findViewById(R.id.line_comment);
        layoutMaterial = dialog.findViewById(R.id.layout_material);
        layoutMaking = dialog.findViewById(R.id.layout_making);
        btnBack = dialog.findViewById(R.id.btn_back);
        tvComment = dialog.findViewById(R.id.tv_comment);
        tvNoneComment = dialog.findViewById(R.id.tv_noneComment);
        layoutComment = dialog.findViewById(R.id.layout_comment);
        layoutAddComment = dialog.findViewById(R.id.layout_addComment);
        btn_showMore = dialog.findViewById(R.id.btn_showMore);
        tv_smBtn = dialog.findViewById(R.id.tv_smBtn);
        btnSend = dialog.findViewById(R.id.btn_send);
        btnSave = dialog.findViewById(R.id.btn_save);
        img_iconBtn = dialog.findViewById(R.id.img_iconBtn);

        edtContent = dialog.findViewById(R.id.edt_content);

        binhLuanDao = new BinhLuanDao(context);
    }

    private void ChiTiet() {
        ArrayList<DanhSachNguyenLieu> lstDSNL = congThuc.getLstNguyenLieu();
        for (DanhSachNguyenLieu dsnl:lstDSNL){
            View view = LayoutInflater.from(context).inflate(R.layout.item_search_history,null);
            TextView tvName = view.findViewById(R.id.tv_history);
            TextView tvMass = view.findViewById(R.id.tv_mass);
            new Service().SetMass(nguyenLieuDao,tvName,tvMass,dsnl.getIdNguyenLieu(),dsnl.getKhoiLuong());
            layoutMaterial.addView(view);
        }

        ArrayList<BuocLam> lstBuocLam = congThuc.getLstBuocLam();
        Collections.sort(lstBuocLam, (o1, o2) -> Integer.compare(o1.getThuTu(), o2.getThuTu()));
//        MakingAdapter makingAdapter = new MakingAdapter(context,lstBuocLam);
//        rcvMaking.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
//        rcvMaking.setLayoutManager(new GridLayoutManager(context,1));
//        rcvMaking.setNestedScrollingEnabled(false);
//        rcvMaking.setAdapter(makingAdapter);
        for (int i = 0; i < lstBuocLam.size(); i++) {
            BuocLam buocLam = lstBuocLam.get(i);
            if (buocLam != null){
                View view = LayoutInflater.from(context).inflate(R.layout.item_making,null);
                TextView tvLocation = view.findViewById(R.id.tv_location);
                EditText edtContent = view.findViewById(R.id.edt_content);
                ImageView imgPictureMaking = view.findViewById(R.id.img_selected_pictureMaking);
                Button btnRemoveMaking = view.findViewById(R.id.btn_removeMaking);

                tvLocation.setText(String.valueOf(buocLam.getThuTu()));
                edtContent.setText(buocLam.getNoiDung());

                Anh anhBuocLam = null;
                if (buocLam.getIdAnh() != null){
                    anhBuocLam = anhDao.getID(buocLam.getIdAnh());
                }
                btnRemoveMaking.setVisibility(View.GONE);
                edtContent.setFocusable(false);
                edtContent.setClickable(false);

                if (anhBuocLam != null) {
                    Glide.with(context).load(anhBuocLam.getUrl()).error(R.drawable.ic_picture).into(imgPictureMaking);
                    Anh finalAnhBuocLam = anhBuocLam;
                    imgPictureMaking.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new Service().DialogEnlarge(context, finalAnhBuocLam);
                        }
                    });
                } else {
                    imgPictureMaking.setVisibility(View.GONE);
                }
                layoutMaking.addView(view);
            }
        }
        loadComment(congThuc.getLstBinhLuan());
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edtContent.getText().toString().trim();
                if (!content.isEmpty()) {
                    NguoiDung nguoiDung = mainActivity.getUser();
                    BinhLuan binhLuan = new BinhLuan();
                    binhLuan.setId(0);
                    binhLuan.setIdCongThuc(congThuc.getId());
                    binhLuan.setNguoiDung(nguoiDung.getId());
                    binhLuan.setNoiDung(content);
                    binhLuanDao.insert(binhLuan);
                    Toast.makeText(context, "Đã thêm bình luận !", Toast.LENGTH_SHORT).show();
                    edtContent.setText("");
                    congThuc = congThucDao.getID(congThuc.getId());
                    layoutComment.removeAllViews();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("CONG_THUC");
                    databaseReference.child(congThuc.getId()).child("lstBinhLuan").setValue(binhLuanDao.getAllID(congThuc.getId()));
                    loadComment(congThuc.getLstBinhLuan());
                } else {
                    Toast.makeText(context, "Vui lòng nhập nội dung !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    boolean isExpanded = true;
    private void loadComment(ArrayList<BinhLuan> lstBinhLuan){
        if (congThuc.getLstBinhLuan() != null && !congThuc.getLstBinhLuan().isEmpty()) {
            btn_showMore.setVisibility(View.VISIBLE);
            tvNoneComment.setVisibility(View.GONE);
            layoutComment.setVisibility(View.VISIBLE);
            tvComment.setText("Bình luận(" + congThuc.getLstBinhLuan().size() + ")");
            for (int i = 0; i < lstBinhLuan.size(); i++){
                BinhLuan binhLuan = lstBinhLuan.get(i);
                NguoiDung userComment = nguoiDungDao.getID(String.valueOf(binhLuan.getNguoiDung()));
                View view = LayoutInflater.from(context).inflate(R.layout.item_comment,null);
                TextView tvContentComment = view.findViewById(R.id.tv_content);
                ImageView imgAvatarUserComment = view.findViewById(R.id.img_avatarUserComment);
                new Service().setAvatar(imgAvatarUserComment,userComment.getAvatar());
                tvContentComment.setText(binhLuan.getNoiDung());
                layoutComment.addView(view);
            }
            btn_showMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    collapseView(layoutComment);
                }
            });
            btn_showMore.callOnClick();

        } else {
            btn_showMore.setVisibility(View.GONE);
            tvNoneComment.setVisibility(View.VISIBLE);
            tvComment.setText("Bình luận(0)");
            layoutComment.setVisibility(View.GONE);
        }
    }
    private void collapseView(LinearLayout linearLayout) {
        if (linearLayout.getHeight() > 0) {
            tv_smBtn.setText("Xem thêm");
            img_iconBtn.setImageResource(R.drawable.baseline_keyboard_arrow_down);
            Animation slideDown = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.slide_out_down);
            linearLayout.startAnimation(slideDown);
            linearLayout.getLayoutParams().height = 0;
            linearLayout.requestLayout();
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                linearLayout.getChildAt(i).setVisibility(View.GONE);
            }
        } else {
            tv_smBtn.setText("Ẩn");
            img_iconBtn.setImageResource(R.drawable.baseline_keyboard_arrow_up);
            Animation slideUp = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.slide_in_down_comment);
            linearLayout.startAnimation(slideUp);
            linearLayout.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            linearLayout.requestLayout();
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                linearLayout.getChildAt(i).setVisibility(View.VISIBLE);
            }
        }
    }
    private void PopupMenu(View view){
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_more, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.timer_option) {
                    OpenDialogTimer();
                    return true;
                } else if (item.getItemId() == R.id.costEstimates_option) {

                    return true;
                } else if (item.getItemId() == R.id.calories_option) {
                    OpenDialogCalories();
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }
    View viewMain;
    TextView tvLeftCalo;
    EditText edt_calo,edt_quantity;
    LinearLayout layoutCalories;
    TextView tv_leftQuantity;
    AutoCompleteTextView actvNL;
    private void OpenDialogCalories() {
        ArrayList<NguyenLieu> lstNL = new ArrayList<>();
        final View dialogView = View.inflate(context,R.layout.dialog_calories,null);
        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);

        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(layoutParams);

        layoutCalories = dialog.findViewById(R.id.layout_calories);
        Button btn_addMaterial = dialog.findViewById(R.id.btn_addMaterial);
        Button btn_calculatorCalories = dialog.findViewById(R.id.btn_calculatorCalories);
        TextView tv_sum = dialog.findViewById(R.id.tv_sum);

        btn_addMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMain = LayoutInflater.from(context).inflate(R.layout.item_calories,null);
                layoutCalories.addView(viewMain);
                lstNL.add(new NguyenLieu());
                InitAndSetAdapter(lstNL);
            }
        });

        for (int i = 0; i < congThuc.getLstNguyenLieu().size(); i++){
            viewMain = LayoutInflater.from(context).inflate(R.layout.item_calories,null);
            initUICalories();
            DanhSachNguyenLieu dsnl = congThuc.getLstNguyenLieu().get(i);
            NguyenLieu nguyenLieu = nguyenLieuDao.getID(String.valueOf(dsnl.getIdNguyenLieu()));
            lstNL.add(i,nguyenLieu);
            actvNL.setText(nguyenLieu.getTen());
            edt_calo.setText(nguyenLieu.getCalo() + "");
            setUnit(tvLeftCalo,nguyenLieu);
            edt_quantity.setText(dsnl.getKhoiLuong() + "");
            new Service().SetMass(nguyenLieuDao,null,tv_leftQuantity,nguyenLieu.getId(),-1);
            layoutCalories.addView(viewMain);
            InitAndSetAdapter(lstNL);
        }

        btn_calculatorCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = layoutCalories.getChildCount();
                if (quantity > 0) {
                    ArrayList<Integer> lstCalo = new ArrayList<>();
                    for (int i = 0; i < layoutCalories.getChildCount(); i++) {
                        View view = layoutCalories.getChildAt(i);
                        EditText edtMass = view.findViewById(R.id.edt_quantity);
                        EditText edtCalo = view.findViewById(R.id.edt_calo);

                        String mass = edtMass.getText().toString().trim();
                        String calo = edtCalo.getText().toString().trim();
                        if (mass.isEmpty() || !mass.matches("[0-9]+")) {
                            edtMass.setError("Vui lòng kiểm tra lại thông tin !");
                            lstCalo.clear();
                            return;
                        }
                        if (calo.isEmpty() || !calo.matches("[0-9]+")) {
                            edtCalo.setError("Vui lòng kiểm tra lại thông tin !");
                            lstCalo.clear();
                            return;
                        }
                        lstCalo.add(i,(Integer.parseInt(mass) * Integer.parseInt(calo))/100);
                    }
                    ArrayList<String> lstHide = new ArrayList<>();
                    int sum = 0;
                    for (int i = 0; i < lstNL.size(); i++) {
                        int knl = lstNL.get(i).getKieu();
                        switch (knl){
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 6:
                            case 7:
                            case 8:
                            case 9:
                            case 10:
                            case 12:
                                sum += lstCalo.get(i);
                                break;
                            case 5:
                                lstHide.add(lstNL.get(i).getTen());
                                break;
                            default:break;
                        }
                    }
                    String textRecipe = getColoredSpanned("Tổng số calo cho món ăn này là: ", "#333333");
                    String textAdvantage = getColoredSpanned(String.valueOf(sum),"#FDA17A");
                    tv_sum.setText(Html.fromHtml(textRecipe + textAdvantage + " calo."));
                    TextView tv_note = dialog.findViewById(R.id.tv_note);
                    tv_note.setVisibility(View.VISIBLE);
                    tv_note.setText(context.getResources().getText(R.string.text_note));
                    if (!lstHide.isEmpty()) {
                        String arr = "";
                        for (String s:lstHide) {
                            arr += s + ", ";
                        }
                        String s1 = getColoredSpanned("- Không bao gồm: ", "#333333");
                        String textHide = getColoredSpanned(arr, "#FDA17A");
                        tv_note.setText(Html.fromHtml(s1 + textHide + "<br/> - " + context.getResources().getText(R.string.text_note)));
                    }
                } else {
                    Toast.makeText(context, "Vui lòng thêm nguyên liệu !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_calculatorCalories.callOnClick();

        dialog.show();
    }
    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }
    private void initUICalories() {
        tvLeftCalo = viewMain.findViewById(R.id.tv_leftCalo);
        edt_calo = viewMain.findViewById(R.id.edt_calo);
        tv_leftQuantity = viewMain.findViewById(R.id.tv_leftQuantity);
        edt_quantity = viewMain.findViewById(R.id.edt_quantity);
        actvNL = viewMain.findViewById(R.id.edt_materialName);
    }
    private void InitAndSetAdapter(ArrayList<NguyenLieu> lstNL){
        for (int i = 0; i < layoutCalories.getChildCount(); i++) {
            viewMain = layoutCalories.getChildAt(i);
            int index = layoutCalories.indexOfChild(viewMain);
            Log.e("INDEX CHECK",index + "");
            initUICalories();
            viewMain.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    lstNL.remove(index);
                    layoutCalories.removeView(viewMain);
                    InitAndSetAdapter(lstNL);
                    return true;
                }
            });
            NLAdapter knlAdapter = new NLAdapter(context, R.layout.item_selected_spinner_knl, mainActivity.getAllNguyenLieu());
            actvNL.setAdapter(knlAdapter);
            actvNL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    NguyenLieu nguyenLieu1 = nguyenLieuDao.getTen(actvNL.getText().toString().trim());
                    lstNL.add(index,nguyenLieu1);
                    edt_calo.setText(nguyenLieu1.getCalo() + "");
                    setUnit(tvLeftCalo,nguyenLieu1);
                    new Service().SetMass(nguyenLieuDao,null,tv_leftQuantity,nguyenLieu1.getId(),-1);
                }
            });
        }
    }
    private void setUnit(TextView tv, NguyenLieu nguyenLieu){
        int typeMaterial = nguyenLieu.getKieu();
        switch (typeMaterial){
            case 1:
            case 2:
            case 3:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
                tv.setText("calo\n/100gram");
                break;
            case 4:
                tv.setText("calo\n/1quả");
                break;
            case 5:
                tv.setText("calo\n/100ml");
                break;
            default:break;
        }
    }
    private int selectedTimeInMinutes = 0;
    private void OpenDialogTimer() {
        final View dialogView = View.inflate(context,R.layout.dialog_timer,null);
        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);

        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(layoutParams);

        LinearLayout layout_timer = dialog.findViewById(R.id.layout_timer);
        Button btn_start = dialog.findViewById(R.id.btn_start);

        final NumberPicker numberPicker = new NumberPicker(context);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(60);
        numberPicker.setValue(selectedTimeInMinutes);
        numberPicker.setWrapSelectorWheel(true);
        layout_timer.addView(numberPicker);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTimeInMinutes = numberPicker.getValue();
                startTimer();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void startTimer (){
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE);
        }
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, selectedTimeInMinutes * 60 * 1000, pendingIntent);
    }
}