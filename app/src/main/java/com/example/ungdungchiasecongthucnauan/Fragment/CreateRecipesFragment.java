package com.example.ungdungchiasecongthucnauan.Fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
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

import com.bumptech.glide.Glide;
import com.example.ungdungchiasecongthucnauan.Adapter.LoaiCongThucAdapter;
import com.example.ungdungchiasecongthucnauan.Adapter.NLAdapter;
import com.example.ungdungchiasecongthucnauan.Dao.AnhDao;
import com.example.ungdungchiasecongthucnauan.Dao.BuocLamDao;
import com.example.ungdungchiasecongthucnauan.Dao.CongThucDao;
import com.example.ungdungchiasecongthucnauan.Dao.DanhSachNguyenLieuDao;
import com.example.ungdungchiasecongthucnauan.Dao.LoaiCongThucDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguyenLieuDao;
import com.example.ungdungchiasecongthucnauan.MainActivity;
import com.example.ungdungchiasecongthucnauan.Model.Anh;
import com.example.ungdungchiasecongthucnauan.Model.BuocLam;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.DanhSachNguyenLieu;
import com.example.ungdungchiasecongthucnauan.Model.KieuNguyenLieu;
import com.example.ungdungchiasecongthucnauan.Model.LoaiCongThuc;
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
import java.util.Date;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateRecipesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateRecipesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateRecipesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateRecipesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateRecipesFragment newInstance(String param1, String param2) {
        CreateRecipesFragment fragment = new CreateRecipesFragment();
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
    private static final int PICK_IMAGE_REQUEST = 1;
    MainActivity mainActivity;
    Button btnCreateRecipes,btnAddMaking,btnAddMaterial,btnSave;
    ArrayList<KieuNguyenLieu> lstKNL;
    ImageButton imgSelectedPicture,btnCloseDialog;
    ImageView imgSelectedPictureMaking;
    LinearLayout layoutMaterial,layoutMaking;
    ArrayList<Uri> lstUri;
    ArrayList<DanhSachNguyenLieu> lstDanhSachNguyenLieu;
    ArrayList<BuocLam> lstBuocLam;
    ArrayList<Anh> lstAnh;
    private ProgressDialog progressDialog;
    private Uri mImageBannerURL;
    private int INDEX_SELECTED_IMG = -1;
    boolean selectedImgBanner = false;
    boolean selectedImgMaking = false;
    private StorageReference storageReference;
    private StorageTask storageTask;
    CongThuc congThuc = new CongThuc();
    CongThucDao congThucDao;
    BuocLamDao buocLamDao;
    DanhSachNguyenLieuDao dsnlDao;
    AnhDao anhDao;
    NguyenLieuDao nguyenLieuDao;

    LoaiCongThuc loaiCongThuc;

    private DatabaseReference databaseReference;
    String time,foodRation,foodName,idRecipes;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_recipes, container, false);

        initUI(view);

        btnCreateRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                congThuc = new CongThuc();
                OpenDialogCreateRecipes();
            }
        });
        return view;
    }
    private void OpenDialogCreateRecipes() {
        ResetData();
        idRecipes = UUID.randomUUID().toString();
        lstAnh = new ArrayList<>();
        lstUri = new ArrayList<>();
        lstBuocLam = new ArrayList<>();
        lstDanhSachNguyenLieu = new ArrayList<>();

        final View dialogView = View.inflate(getContext(),R.layout.dialog_create_recipes,null);
        final Dialog dialog = new Dialog(getContext());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);

        dialog.getWindow().getAttributes().windowAnimations = R.style.showDialog;

        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(layoutParams);

        btnCloseDialog = dialog.findViewById(R.id.btn_closeDialog);

        layoutMaterial = dialog.findViewById(R.id.layout_material);
        layoutMaking = dialog.findViewById(R.id.layout_making);
        btnAddMaterial = dialog.findViewById(R.id.btn_addMaterial);
        btnAddMaking = dialog.findViewById(R.id.btn_addMaking);
        imgSelectedPicture = dialog.findViewById(R.id.img_selected_picture);
        btnSave = dialog.findViewById(R.id.btn_save);
        EditText edtFoodName = dialog.findViewById(R.id.edt_foodName);
        EditText edtFoodRation = dialog.findViewById(R.id.edt_ration);
        EditText edtTime = dialog.findViewById(R.id.edt_time);

        AutoCompleteTextView type = dialog.findViewById(R.id.type);
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
        imgSelectedPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgSelectedPictureMaking = v.findViewById(R.id.img_selected_pictureMaking);
                OpenFile();selectedImgBanner = true;
            }
        });
        btnCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnAddMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DanhSachNguyenLieu dsnl = new DanhSachNguyenLieu();
                dsnl.setIdCongThuc(idRecipes);
                lstDanhSachNguyenLieu.add(dsnl);

                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_material, null);
                layoutMaterial.addView(view);

                for (int i = 0; i < layoutMaterial.getChildCount(); i++){
                    int index = i;
                    View viewMaterial = layoutMaterial.getChildAt(index);

                    Button btnRemoveMaterial = viewMaterial.findViewById(R.id.btn_removeMaterial);
                    btnRemoveMaterial.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            layoutMaterial.removeView(viewMaterial);
                            lstDanhSachNguyenLieu.remove(index);
                        }
                    });

                    TextView tvUnit = viewMaterial.findViewById(R.id.tv_unit);

                    AutoCompleteTextView actvNL = viewMaterial.findViewById(R.id.edt_materialName);
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
            }
        });
        btnAddMaterial.callOnClick();
        btnAddMaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuocLam buocLam = new BuocLam();
                lstBuocLam.add(buocLam);
                Anh anh = new Anh();
                lstAnh.add(anh);
                Uri uri = null;
                lstUri.add(uri);

                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_making, null);
                layoutMaking.addView(view);

                for (int i = 0; i < layoutMaking.getChildCount(); i++){
                    int index = i;
                    View viewMaking = layoutMaking.getChildAt(index);
                    TextView tvLocation = viewMaking.findViewById(R.id.tv_location);
                    tvLocation.setText(String.valueOf(index + 1));

                    Button btnRemoveMaking = viewMaking.findViewById(R.id.btn_removeMaking);
                    btnRemoveMaking.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int indexRemove = layoutMaking.indexOfChild(view);
                            layoutMaking.removeView(view);
                            lstBuocLam.remove(indexRemove);
                            lstAnh.remove(indexRemove);
                            lstUri.remove(indexRemove);

                            for(int i = 0; i < layoutMaking.getChildCount(); i++) {
                                View viewMaking = layoutMaking.getChildAt(i);
                                TextView tvLocation = viewMaking.findViewById(R.id.tv_location);
                                tvLocation.setText(String.valueOf(i + 1));
                            }
                        }
                    });

                    imgSelectedPictureMaking = viewMaking.findViewById(R.id.img_selected_pictureMaking);
                    imgSelectedPictureMaking.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            imgSelectedPictureMaking = v.findViewById(R.id.img_selected_pictureMaking);
                            selectedImgMaking = true;
                            INDEX_SELECTED_IMG = index;
                            OpenFile();
                        }
                    });
                }
            }
        });
        btnAddMaking.callOnClick();
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
                if (ValidateMaterial(layoutMaterial) && ValidateMaking(layoutMaking)){
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Đang lưu công thức . . .");
                    progressDialog.show();
                    SaveDataToFirebase(lstAnh.get(0),lstUri.get(0),0,lstBuocLam.size() - 1,dialog);
                }
            }
        });
        dialog.show();
    }
    private boolean ValidateMaterial(LinearLayout layout){
        boolean check = false;
        if (lstDanhSachNguyenLieu.isEmpty()){
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
                    } else if (lstDanhSachNguyenLieu.get(i).getIdNguyenLieu() == 0) {
//                        Toast.makeText(getContext(), "Nguyên liệu không hợp lệ !", Toast.LENGTH_SHORT).show();
                        edtMaterialName.setError("Nguyên liệu không hợp lệ !");
                        check = false;
                    } else {
                        check = true;
                        lstDanhSachNguyenLieu.get(i).setKhoiLuong(Integer.parseInt(mass));
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
    private void OpenFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }
    private void initUI(View view) {
        mainActivity = (MainActivity) getActivity();
        btnCreateRecipes = view.findViewById(R.id.btn_createRecipes);

        lstKNL = new ArrayList<>();
        lstKNL = mainActivity.getAllKieuNguyenLieu();
        lstUri = new ArrayList<>();
        lstDanhSachNguyenLieu = new ArrayList<>();
        lstBuocLam = new ArrayList<>();
        lstAnh = new ArrayList<>();

        storageReference = FirebaseStorage.getInstance().getReference("UPLOAD_IMAGE");
        databaseReference = FirebaseDatabase.getInstance().getReference("ALL_IMAGE");

        progressDialog = new ProgressDialog(getContext());
        congThucDao = new CongThucDao(getContext());
        anhDao = new AnhDao(getContext());
        buocLamDao = new BuocLamDao(getContext());
        dsnlDao = new DanhSachNguyenLieuDao(getContext());
        nguyenLieuDao = new NguyenLieuDao(getContext());
    }
    private void ResetData() {
        foodName = "";
        foodRation = "";
        time = "";
        mImageBannerURL = null;
        lstDanhSachNguyenLieu.clear();
        lstUri.clear();
        lstBuocLam.clear();
        lstAnh.clear();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            if (selectedImgBanner){
                mImageBannerURL = data.getData();
                Glide.with(getActivity()).load(mImageBannerURL).error(R.drawable.img_selected_picture).into(imgSelectedPicture);
                selectedImgBanner = false;
            }
            if (selectedImgMaking){
                Uri uri = data.getData();
                if (uri != null) {
                    lstUri.add(INDEX_SELECTED_IMG,uri);
                    lstUri.remove(INDEX_SELECTED_IMG + 1);
                }
                Glide.with(getActivity()).load(uri).error(R.drawable.ic_picture).into(imgSelectedPictureMaking);
                selectedImgMaking = false;
            }
        }
    }
    private String getFileExtention(Uri uri){
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mine = MimeTypeMap.getSingleton();
        return uri != null ? mine.getExtensionFromMimeType(contentResolver.getType(uri)):null;
    }
    private void SaveDataToFirebaseBanner(Dialog dialog){
        if (mImageBannerURL != null) {
            StorageReference reference = storageReference.child(System.currentTimeMillis() + "." + getFileExtention(mImageBannerURL));
            storageTask = reference.putFile(mImageBannerURL).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Anh anh = new Anh();
                            String getID = databaseReference.push().getKey();
                            anh.setId(getID);
                            anh.setUrl(uri.toString());
                            databaseReference.child(getID).setValue(anh);
                            anhDao.insert(anh);
                            setInfCongThuc(getID);
                            for (int i = 0; i < lstDanhSachNguyenLieu.size(); i++) {
                                dsnlDao.insert(lstDanhSachNguyenLieu.get(i));
                            }
                            for (int i = 0; i < lstBuocLam.size(); i++) {
                                buocLamDao.insert(lstBuocLam.get(i));
                            }
                            ResetData();
                            progressDialog.dismiss();
                            dialog.dismiss();
                            Toast.makeText(getContext(), "Lưu thành công !", Toast.LENGTH_SHORT).show();
                            databaseReference = FirebaseDatabase.getInstance().getReference("CONG_THUC");
                            databaseReference.child(congThuc.getId()).setValue(congThuc);
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
            setInfCongThuc(null);
            for (int i = 0; i < lstDanhSachNguyenLieu.size(); i++) {
                dsnlDao.insert(lstDanhSachNguyenLieu.get(i));
                Log.e("Nguyên liệu","" + lstDanhSachNguyenLieu.get(i).toString());
            }
            for (int i = 0; i < lstBuocLam.size(); i++) {
                buocLamDao.insert(lstBuocLam.get(i));
            }
            ResetData();
            progressDialog.dismiss();
            dialog.dismiss();
            Toast.makeText(getContext(), "Lưu thành công !", Toast.LENGTH_SHORT).show();
            databaseReference = FirebaseDatabase.getInstance().getReference("CONG_THUC");
            databaseReference.child(congThuc.getId()).setValue(congThuc);
        }
    }
    private void SaveDataToFirebase(Anh anh, Uri uri,int pos,int size,Dialog dialog){
        if (uri != null) {
            StorageReference reference = storageReference.child(System.currentTimeMillis() + "." + getFileExtention(uri));
            storageTask = reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String getID = databaseReference.push().getKey();
                            anh.setId(getID);
                            anh.setUrl(uri.toString());
                            databaseReference.child(getID).setValue(anh);
                            anhDao.insert(anh);
                            lstBuocLam.get(pos).setIdAnh(getID);
                            setInfBuocLam(layoutMaking,pos);
                            if (pos == size) {
                                SaveDataToFirebaseBanner(dialog);
                            } else {
                                SaveDataToFirebase(lstAnh.get(pos + 1),lstUri.get(pos + 1),pos + 1,size,dialog);
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
            lstBuocLam.get(pos).setIdAnh(null);
            setInfBuocLam(layoutMaking,pos);
            if (pos == size) {
                SaveDataToFirebaseBanner(dialog);
            } else {
                SaveDataToFirebase(lstAnh.get(pos + 1),lstUri.get(pos + 1),pos + 1,size,dialog);
            }
        }
    }
    private void setInfBuocLam(LinearLayout layout,int pos){
        View viewMaterial = layout.getChildAt(pos);
        TextView tvLocation = viewMaterial.findViewById(R.id.tv_location);
        EditText edtContent = viewMaterial.findViewById(R.id.edt_content);
        lstBuocLam.get(pos).setIdCongThuc(idRecipes);
        lstBuocLam.get(pos).setNoiDung(edtContent.getText().toString());
        lstBuocLam.get(pos).setThuTu(Integer.parseInt(tvLocation.getText().toString()));
    }
    private void setInfCongThuc(String getID){
        congThuc.setId(idRecipes);
        congThuc.setTen(foodName);
        congThuc.setIdAnh(getID);
        congThuc.setIdNguoiDung(mainActivity.getUser().getId());
        if (!foodRation.isEmpty()) {
            congThuc.setKhauPhan(Integer.parseInt(foodRation));
        } else congThuc.setKhauPhan(-1);
        if (!time.isEmpty()){
            congThuc.setThoiGianNau(Integer.parseInt(time));
        } else congThuc.setThoiGianNau(-1);
        congThuc.setNgayTao(new Date());
        congThuc.setIdLoai(loaiCongThuc.getId());
        congThuc.setTrangThai(0);
        congThucDao.insert(congThuc);
    }
}