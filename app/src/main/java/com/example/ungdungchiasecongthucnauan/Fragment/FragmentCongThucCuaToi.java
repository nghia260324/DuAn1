package com.example.ungdungchiasecongthucnauan.Fragment;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ungdungchiasecongthucnauan.Adapter.LoaiCongThucAdapter;
import com.example.ungdungchiasecongthucnauan.Adapter.MyRecipeAdapter;
import com.example.ungdungchiasecongthucnauan.Adapter.NLAdapter;
import com.example.ungdungchiasecongthucnauan.Dao.AnhDao;
import com.example.ungdungchiasecongthucnauan.Dao.BinhLuanDao;
import com.example.ungdungchiasecongthucnauan.Dao.BuocLamDao;
import com.example.ungdungchiasecongthucnauan.Dao.CongThucDao;
import com.example.ungdungchiasecongthucnauan.Dao.DanhSachNguyenLieuDao;
import com.example.ungdungchiasecongthucnauan.Dao.LoaiCongThucDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguyenLieuDao;
import com.example.ungdungchiasecongthucnauan.IOpenEdit;
import com.example.ungdungchiasecongthucnauan.MainActivity;
import com.example.ungdungchiasecongthucnauan.Model.Anh;
import com.example.ungdungchiasecongthucnauan.Model.BuocLam;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.DanhSachNguyenLieu;
import com.example.ungdungchiasecongthucnauan.Model.LoaiCongThuc;
import com.example.ungdungchiasecongthucnauan.Model.NguyenLieu;
import com.example.ungdungchiasecongthucnauan.R;
import com.example.ungdungchiasecongthucnauan.Service;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCongThucCuaToi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCongThucCuaToi extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCongThucCuaToi.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCongThucCuaToi newInstance(String param1, String param2) {
        FragmentCongThucCuaToi fragment = new FragmentCongThucCuaToi();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    ProgressDialog progressDialog;
    RecyclerView rcvMyRecipe;
    MainActivity mainActivity;
    AnhDao anhDao;
    LoaiCongThucDao loaiCongThucDao;
    NguyenLieuDao nguyenLieuDao;
    CongThucDao congThucDao;
    BuocLamDao buocLamDao;
    BinhLuanDao binhLuanDao;
    DanhSachNguyenLieuDao dsnlDao;
    Uri mImageBannerURL;
    ImageButton imgSelectedPicture;
    LoaiCongThuc loaiCongThuc = new LoaiCongThuc();
    ArrayList<DanhSachNguyenLieu> lstDSNL;
    ArrayList<BuocLam> lstBuocLam;
    ArrayList<Uri> lstUri;
    LinearLayout layoutMaterialEdit;
    LinearLayout layoutMakingEdit;
    private static final int PICK_IMAGE_REQUEST = 1;
    boolean selectedImgBanner = false;
    boolean selectedImgMaking = false;
    int INDEX_SELECTED_IMG = -1;
    ArrayList<Anh> lstAnh;
    ImageView imgSelectedPictureMaking;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private StorageTask storageTask;
    String time,foodRation,foodName;
    MyRecipeAdapter myRecipeAdapter;

    @Override
    public void onResume() {
        super.onResume();
        SetAdapterRCV(mainActivity.GetRecipes());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cong_thuc_cua_toi, container, false);
        initUI(view,getContext());

        SetAdapterRCV(mainActivity.myRecipes);

        return view;
    }

    public void SetAdapterRCV(ArrayList<CongThuc> lstCT) {
        myRecipeAdapter = new MyRecipeAdapter(getContext(), lstCT, mainActivity, new IOpenEdit() {
            @Override
            public void IOpenDialogEdit(CongThuc congThuc) {
                OpenDialogEdit(getContext(), congThuc);
            }
            @Override
            public void IOpenDialogDelete(CongThuc congThuc) {
                OpenDialogDelete(congThuc);
            }
        });
        rcvMyRecipe.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcvMyRecipe.setLayoutManager(new GridLayoutManager(getContext(),2));
        rcvMyRecipe.setAdapter(myRecipeAdapter);
    }

    private void OpenDialogDelete(CongThuc congThuc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Bạn có chắc muốn xóa công thức này không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ArrayList<String> lstRecipeViewed = (ArrayList<String>) new Service().readFile(getContext(),"recipe_viewed.txt");
                if (lstRecipeViewed.contains(congThuc.getId())) {
                    lstRecipeViewed.remove(congThuc.getId());
                    new Service().writeFile(getContext(),"recipe_viewed.txt",lstRecipeViewed);
                }
                if (congThuc.getIdAnh() != null) {
                    anhDao.delete(congThuc.getId());
                }
                anhDao.delete(congThuc.getIdAnh());
                if (congThuc.getLstBinhLuan() != null) {
                    binhLuanDao.deleteAllByCongThucId(congThuc.getId());
                }
                for (BuocLam buocLam:congThuc.getLstBuocLam()) {
                    if (buocLam.getIdAnh() != null) {
                        Anh anh = anhDao.getID(buocLam.getIdAnh());
                        anhDao.delete(anh.getId());
                    }
                }
                buocLamDao.deleteAllByCongThucId(congThuc.getId());
                dsnlDao.delete(congThuc.getId());
                congThucDao.delete(congThuc.getId());
                databaseReference = FirebaseDatabase.getInstance().getReference("CONG_THUC");
                databaseReference.child(congThuc.getId()).setValue(null);
                Toast.makeText(getContext(), "Xóa thành công !", Toast.LENGTH_SHORT).show();
                Reload();
                dialog.dismiss();
            }
        }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void OpenDialogEdit(Context context,CongThuc congThuc) {
        lstDSNL = congThuc.getLstNguyenLieu();
        lstBuocLam = congThuc.getLstBuocLam();
        View dialogView = View.inflate(context,R.layout.dialog_create_recipes,null);
        Dialog dialog = new Dialog(context);

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

        imgSelectedPicture = dialog.findViewById(R.id.img_selected_picture);

        imgSelectedPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFile();selectedImgBanner = true;
            }
        });

        layoutMaterialEdit = dialog.findViewById(R.id.layout_material);
        layoutMakingEdit = dialog.findViewById(R.id.layout_making);
        EditText edtFoodName = dialog.findViewById(R.id.edt_foodName);
        EditText edtFoodRation = dialog.findViewById(R.id.edt_ration);
        EditText edtTime = dialog.findViewById(R.id.edt_time);
        AutoCompleteTextView type = dialog.findViewById(R.id.type);
        ImageButton btnCloseDialog = dialog.findViewById(R.id.btn_closeDialog);
        Button btnAddMaterial = dialog.findViewById(R.id.btn_addMaterial);
        Button btnAddMaking = dialog.findViewById(R.id.btn_addMaking);
        Button btnSave = dialog.findViewById(R.id.btn_save);

        LoaiCongThucAdapter loaiCongThucAdapter = new LoaiCongThucAdapter(getContext(), R.layout.item_spinner_decentralization_selected, mainActivity.getAllLoaiCongThuc());
        type.setAdapter(loaiCongThucAdapter);
        type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ten = type.getText().toString().trim();
                LoaiCongThucDao loaiCongThucDao = new LoaiCongThucDao(getContext());
                loaiCongThuc = loaiCongThucDao.getTen(ten);
            }
        });
        btnAddMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DanhSachNguyenLieu dsnl = new DanhSachNguyenLieu();
                lstDSNL.add(dsnl);
                View view = LayoutInflater.from(context).inflate(R.layout.item_material,null);
                Button btnRemoveMaterial = view.findViewById(R.id.btn_removeMaterial);
                RemoveMaterial(btnRemoveMaterial,dsnl,view);

                TextView tvUnit = view.findViewById(R.id.tv_unit);

                AutoCompleteTextView actvNL = view.findViewById(R.id.edt_materialName);
                SetAutoCompleteTextView(actvNL,dsnl,tvUnit);

                layoutMaterialEdit.addView(view);
            }
        });
        btnAddMaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = null;
                Anh anh = new Anh();
                BuocLam buocLam = new BuocLam();
                lstBuocLam.add(buocLam);
                lstAnh.add(anh);
                lstUri.add(uri);

                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_making, null);
                TextView tvLocation = view.findViewById(R.id.tv_location);
                tvLocation.setText(String.valueOf(layoutMakingEdit.getChildCount() + 1));

                imgSelectedPictureMaking = view.findViewById(R.id.img_selected_pictureMaking);
                imgSelectedPictureMaking.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgSelectedPictureMaking = v.findViewById(R.id.img_selected_pictureMaking);
                        selectedImgMaking = true;
                        INDEX_SELECTED_IMG = layoutMakingEdit.indexOfChild(view);
                        OpenFile();
                    }
                });
                Button btnRemoveMakingEdit = view.findViewById(R.id.btn_removeMaking);
                RemoveMaking(btnRemoveMakingEdit,view);

                layoutMakingEdit.addView(view);
            }
        });
        btnCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Anh anhCT = new Anh();
        if (congThuc.getIdAnh() != null) {
            anhCT = anhDao.getID(congThuc.getIdAnh());
        }
        Glide.with(context).load(anhCT.getUrl()).error(R.drawable.logoapp).into(imgSelectedPicture);

        edtFoodName.setText(congThuc.getTen());
        if (congThuc.getKhauPhan() != -1) {
            edtFoodRation.setText(congThuc.getKhauPhan() + "");
        }
        if (congThuc.getThoiGianNau() != -1) {
            edtTime.setText(congThuc.getThoiGianNau() + "");
        }
        type.setText(loaiCongThucDao.getID(String.valueOf(congThuc.getIdLoai())).getTenLoai());
        loaiCongThuc = loaiCongThucDao.getID(String.valueOf(congThuc.getIdLoai()));

        for (DanhSachNguyenLieu dsnl:lstDSNL){
            NguyenLieu nguyenLieu = nguyenLieuDao.getID(String.valueOf(dsnl.getIdNguyenLieu()));
            View view = LayoutInflater.from(context).inflate(R.layout.item_material,null);
            TextView tvUnit = view.findViewById(R.id.tv_unit);
            AutoCompleteTextView actvNL = view.findViewById(R.id.edt_materialName);
            Button btnRemoveMaterial = view.findViewById(R.id.btn_removeMaterial);

            SetAutoCompleteTextView(actvNL,dsnl,tvUnit);
            EditText edtMass = view.findViewById(R.id.edt_mass);
            RemoveMaterial(btnRemoveMaterial,dsnl,view);
            edtMass.setText(dsnl.getKhoiLuong() + "");
            actvNL.setText(nguyenLieu.getTen());
            layoutMaterialEdit.addView(view);
        }

        Collections.sort(lstBuocLam, (o1, o2) -> Integer.compare(o1.getThuTu(), o2.getThuTu()));
        for (int i = 0; i < lstBuocLam.size(); i++) {
            BuocLam buocLam = lstBuocLam.get(i);
            Anh anh = new Anh();
            if (buocLam.getIdAnh() != null){
                anh = anhDao.getID(buocLam.getIdAnh());
            }
            lstUri.add(null);
            lstAnh.add(anh);
            View view = LayoutInflater.from(context).inflate(R.layout.item_making, null);
            TextView tvLocation = view.findViewById(R.id.tv_location);
            TextView edtContentEdit = view.findViewById(R.id.edt_content);
            imgSelectedPictureMaking = view.findViewById(R.id.img_selected_pictureMaking);
            imgSelectedPictureMaking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgSelectedPictureMaking = v.findViewById(R.id.img_selected_pictureMaking);
                    selectedImgMaking = true;
                    INDEX_SELECTED_IMG = layoutMakingEdit.indexOfChild(view);
                    OpenFile();
                }
            });
            Button btnRemoveMakingEdit = view.findViewById(R.id.btn_removeMaking);

            RemoveMaking(btnRemoveMakingEdit,view);

            tvLocation.setText(String.valueOf(i + 1));
            Glide.with(context).load(anh.getUrl()).error(R.drawable.ic_picture).into(imgSelectedPictureMaking);
            edtContentEdit.setText(buocLam.getNoiDung());
            layoutMakingEdit.addView(view);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodName = edtFoodName.getText().toString().trim();
                foodRation = edtFoodRation.getText().toString().trim();
                time = edtTime.getText().toString().trim();

                if (foodName.isEmpty()) {
                    edtFoodName.setError("Vui lòng nhập tên !");
                    return;
                }
                if (!foodRation.isEmpty() && !foodRation.matches("[0-9]+")) {
                    edtFoodRation.setError("Khẩu phần không hợp lệ !");
                    return;
                }
                if (!time.isEmpty() && !time.matches("[0-9]+")) {
                    edtTime.setError("Thời gian không hợp lệ !");
                    return;
                }
                if (loaiCongThuc == null) {
                    type.setError("Vui lòng chọn loại công thức !");
                    return;
                }
                if (ValidateMaterial(layoutMaterialEdit) && ValidateMaking(layoutMakingEdit)){
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Đang lưu công thức . . .");
                    progressDialog.show();
                    SaveDataToFirebase(lstAnh.get(0),lstUri.get(0),0,lstBuocLam.size() - 1,dialog,congThuc);
                }
            }
        });
        dialog.show();
    }
    private boolean ValidateMaterial(LinearLayout layout){
        boolean check = false;
        if (lstDSNL.isEmpty()){
            Toast.makeText(getContext(), "Hãy thêm một vài nguyên liệu trước khi lưu !", Toast.LENGTH_SHORT).show();
            check = false;
        } else {
            for (int i = 0; i < layout.getChildCount();i++){
                View viewMaterial = layout.getChildAt(i);
                AutoCompleteTextView edtMaterialName = viewMaterial.findViewById(R.id.edt_materialName);
                EditText edtMass = viewMaterial.findViewById(R.id.edt_mass);
                String name = edtMaterialName.getText().toString().trim();
                String mass = edtMass.getText().toString().trim();
                if (!name.isEmpty() && !mass.isEmpty()) {
                    if (!mass.matches("[0-9]+")) {
                        edtMass.setError("Khối lượng không hợp lệ !");
                        check = false;
                    } else if (lstDSNL.get(i).getIdNguyenLieu() == 0) {
//                        Toast.makeText(getContext(), "Nguyên liệu không hợp lệ !", Toast.LENGTH_SHORT).show();
                        edtMaterialName.setError("Nguyên liệu không hợp lệ !");
                        check = false;
                    } else {
                        check = true;
                        lstDSNL.get(i).setKhoiLuong(Integer.parseInt(mass));
                    }
                } else {
                    if (name.isEmpty()) {
                        edtMaterialName.setError(getContext().getResources().getString(R.string.is_empty));
                    }
                    if (mass.isEmpty()){
                        edtMass.setError(getContext().getResources().getString(R.string.is_empty));
                    }
                    check = false;
                    break;
                }

            }
        }
        return check;
    }
    private boolean ValidateMaking(LinearLayout layout){
        boolean check = false;
        if (lstBuocLam.isEmpty()) {
            Toast.makeText(mainActivity, "Hãy thêm bước làm cho công thức của bạn !", Toast.LENGTH_SHORT).show();
            check = false;
        } else {
            for (int i = 0; i < layout.getChildCount();i++){
                View viewMaking = layout.getChildAt(i);
                EditText edtContent = viewMaking.findViewById(R.id.edt_content);
                String content = edtContent.getText().toString().trim();
                if (!content.isEmpty()) {
                    check = true;
                } else {
                    edtContent.setError(getContext().getResources().getString(R.string.is_empty));
                    check = false;
                    break;
                }
            }
        }

        return check;
    }
    private void SaveDataToFirebaseBanner(CongThuc congThuc,Dialog dialog){
        if (mImageBannerURL != null) {
            StorageReference reference = storageReference.child(System.currentTimeMillis() + "." + getFileExtention(mImageBannerURL));
            storageTask = reference.putFile(mImageBannerURL).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String getID = databaseReference.push().getKey();
                            Anh anh = new Anh();
                            if (congThuc.getIdAnh() != null) {
                                anh = anhDao.getID(congThuc.getIdAnh());
                                anh.setUrl(uri.toString());
                                databaseReference.child(anh.getId()).setValue(anh);
                                anhDao.update(anh);
                                setInfCongThuc(congThuc,null);
                            } else {
                                anh.setId(getID);
                                anh.setUrl(uri.toString());
                                databaseReference.child(getID).setValue(anh);
                                anhDao.insert(anh);
                                setInfCongThuc(congThuc,getID);
                            }
                            for (int i = 0; i < lstDSNL.size(); i++) {
                                if (lstDSNL.get(i).getIdCongThuc() != null) {
                                    dsnlDao.update(lstDSNL.get(i));
                                } else {
                                    lstDSNL.get(i).setIdCongThuc(congThuc.getId());
                                    dsnlDao.insert(lstDSNL.get(i));
                                }
                            }
                            for (int i = 0; i < lstBuocLam.size(); i++) {
                                if (lstBuocLam.get(i).getIdCongThuc() != null) {
                                    buocLamDao.update(lstBuocLam.get(i));
                                } else {
                                    lstBuocLam.get(i).setIdCongThuc(congThuc.getId());
                                    buocLamDao.insert(lstBuocLam.get(i));
                                }
                            }
//                            ResetData();
                            progressDialog.dismiss();
                            dialog.dismiss();
                            Toast.makeText(getContext(), "Lưu thành công !", Toast.LENGTH_SHORT).show();
                            CongThuc ctSave = congThucDao.getID(congThuc.getId());
                            databaseReference = FirebaseDatabase.getInstance().getReference("CONG_THUC");
                            databaseReference.child(ctSave.getId()).setValue(ctSave);
                            Reload();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            setInfCongThuc(congThuc,null);
            for (int i = 0; i < lstDSNL.size(); i++) {
                if (lstDSNL.get(i).getIdCongThuc() != null) {
                    dsnlDao.update(lstDSNL.get(i));
                } else {
                    lstDSNL.get(i).setIdCongThuc(congThuc.getId());
                    dsnlDao.insert(lstDSNL.get(i));
                }
            }
            for (int i = 0; i < lstBuocLam.size(); i++) {
                if (lstBuocLam.get(i).getIdCongThuc() != null) {
                    buocLamDao.update(lstBuocLam.get(i));
                } else {
                    lstBuocLam.get(i).setIdCongThuc(congThuc.getId());
                    buocLamDao.insert(lstBuocLam.get(i));
                }
            }
//            ResetData();
            progressDialog.dismiss();
            dialog.dismiss();
            Toast.makeText(getContext(), "Lưu thành công !", Toast.LENGTH_SHORT).show();
            CongThuc ctSave = congThucDao.getID(congThuc.getId());
            databaseReference = FirebaseDatabase.getInstance().getReference("CONG_THUC");
            databaseReference.child(ctSave.getId()).setValue(ctSave);
            Reload();
        }
    }

    private void Reload() {
        SetAdapterRCV(mainActivity.GetRecipes());
    }
    private void SaveDataToFirebase(Anh anh, Uri uri,int pos,int size,Dialog dialog,CongThuc congThuc){
        if (uri != null) {
            StorageReference reference = storageReference.child(System.currentTimeMillis() + "." + getFileExtention(uri));
            storageTask = reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String getID = databaseReference.push().getKey();
                            anh.setUrl(uri.toString());
                            if (lstBuocLam.get(pos).getIdCongThuc() != null && lstBuocLam.get(pos).getIdAnh() != null) {
                                anhDao.update(anh);
                                databaseReference.child(anh.getId()).setValue(anh);
                            } else {
                                anh.setId(getID);
                                lstBuocLam.get(pos).setIdAnh(getID);
                                anhDao.insert(anh);
                                databaseReference.child(getID).setValue(anh);
                            }
                            lstBuocLam.get(pos).setIdAnh(anh.getId());
                            setInfBuocLam(layoutMakingEdit,pos);
                            if (pos == size) {
                                SaveDataToFirebaseBanner(congThuc,dialog);
                            } else {
                                SaveDataToFirebase(lstAnh.get(pos + 1),lstUri.get(pos + 1),pos + 1,size,dialog,congThuc);
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            setInfBuocLam(layoutMakingEdit,pos);
            if (pos == (lstBuocLam.size() - 1)) {
                SaveDataToFirebaseBanner(congThuc,dialog);
            } else {
                SaveDataToFirebase(lstAnh.get(pos + 1),lstUri.get(pos + 1),pos + 1,size,dialog,congThuc);
            }
        }
    }
    private void ResetData() {
        foodName = "";
        foodRation = "";
        time = "";
        mImageBannerURL = null;
        lstDSNL.clear();
        lstBuocLam.clear();
        lstUri.clear();
        lstAnh.clear();
    }
    private void setInfCongThuc(CongThuc congThuc,String getID){
        congThuc.setTen(foodName);
        if (congThuc.getIdAnh() == null && getID != null) {
            congThuc.setIdAnh(getID);
        }
        if (!foodRation.isEmpty()) {
            congThuc.setKhauPhan(Integer.parseInt(foodRation));
        }
        if (!time.isEmpty()){
            congThuc.setThoiGianNau(Integer.parseInt(time));
        }
        if (congThuc.getIdLoai() != loaiCongThuc.getId()) {
            congThuc.setIdLoai(loaiCongThuc.getId());
        }
        congThucDao.update(congThuc);
    }
    private void setInfBuocLam(LinearLayout layout,int pos){
        View viewMaterial = layout.getChildAt(pos);
        TextView tvLocation = viewMaterial.findViewById(R.id.tv_location);
        EditText edtContent = viewMaterial.findViewById(R.id.edt_content);
        lstBuocLam.get(pos).setNoiDung(edtContent.getText().toString());
        lstBuocLam.get(pos).setThuTu(Integer.parseInt(tvLocation.getText().toString()));
    }
    private String getFileExtention(Uri uri){
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mine = MimeTypeMap.getSingleton();
        return uri != null ? mine.getExtensionFromMimeType(contentResolver.getType(uri)):null;
    }
    private void RemoveMaking(Button btnRemoveMakingEdit, View view) {
        btnRemoveMakingEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = layoutMakingEdit.indexOfChild(view);
                if (lstBuocLam.get(index).getIdCongThuc() != null) {
                    anhDao.delete(lstAnh.get(index).getId());
                    buocLamDao.delete(String.valueOf(lstBuocLam.get(index).getId()));
                }
                lstUri.remove(index);
                lstAnh.remove(index);
                lstBuocLam.remove(index);
                layoutMakingEdit.removeView(view);
                for(int i = 0; i < layoutMakingEdit.getChildCount(); i++) {
                    View viewMaking = layoutMakingEdit.getChildAt(i);
                    TextView tvLocation = viewMaking.findViewById(R.id.tv_location);
                    tvLocation.setText(String.valueOf(i + 1));
                }
            }
        });
    }
    private void RemoveMaterial(Button btnRemoveMaterial, DanhSachNguyenLieu dsnl,View view) {
        btnRemoveMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dsnl.getIdCongThuc() != null) {
                    dsnlDao.delete(String.valueOf(dsnl.getId()));
                }
                lstDSNL.remove(dsnl);
                layoutMaterialEdit.removeView(view);
                for (DanhSachNguyenLieu danhSachNguyenLieu:lstDSNL){
                    Log.e("DSNL",danhSachNguyenLieu.toString());
                }
            }
        });
    }
    private void SetAutoCompleteTextView(AutoCompleteTextView actvNL,DanhSachNguyenLieu dsnl,TextView tvUnit) {
        NLAdapter knlAdapter = new NLAdapter(getContext(), R.layout.item_selected_spinner_knl, mainActivity.getAllNguyenLieu());
        actvNL.setAdapter(knlAdapter);
        actvNL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NguyenLieuDao nguyenLieuDao = new NguyenLieuDao(getContext());
                dsnl.setIdNguyenLieu(nguyenLieuDao.getTen(actvNL.getText().toString().trim()).getId());
                new Service().SetMass(nguyenLieuDao,null,tvUnit,dsnl.getIdNguyenLieu(),-1);
            }
        });
    }
    private void OpenFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            if (selectedImgBanner) {
                mImageBannerURL = data.getData();
                Glide.with(getActivity()).load(mImageBannerURL).error(R.drawable.img_selected_picture).into(imgSelectedPicture);
                selectedImgBanner = false;
            }
            if (selectedImgMaking){
                Uri uri = data.getData();
                if (uri != null) {
                    lstUri.remove(INDEX_SELECTED_IMG);
                    lstUri.add(INDEX_SELECTED_IMG,uri);
                }
                Glide.with(getActivity()).load(uri).error(R.drawable.ic_picture).into(imgSelectedPictureMaking);
                selectedImgMaking = false;
            }
        }
    }
    private void initUI(View view,Context context) {
        progressDialog = new ProgressDialog(context);
        storageReference = FirebaseStorage.getInstance().getReference("UPLOAD_IMAGE");
        databaseReference = FirebaseDatabase.getInstance().getReference("ALL_IMAGE");
        mainActivity = (MainActivity) getActivity();

        rcvMyRecipe = view.findViewById(R.id.rcv_myRecipe);

        anhDao = new AnhDao(context);
        nguyenLieuDao = new NguyenLieuDao(context);
        loaiCongThucDao = new LoaiCongThucDao(context);
        congThucDao = new CongThucDao(context);
        buocLamDao = new BuocLamDao(context);
        binhLuanDao = new BinhLuanDao(context);
        dsnlDao = new DanhSachNguyenLieuDao(context);
        lstDSNL = new ArrayList<>();
        lstBuocLam = new ArrayList<>();
        lstAnh = new ArrayList<>();
        lstUri = new ArrayList<>();
    }
}