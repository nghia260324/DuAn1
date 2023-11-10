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
import com.example.ungdungchiasecongthucnauan.AlphaVantageClient;
import com.example.ungdungchiasecongthucnauan.AlphaVantageService;
import com.example.ungdungchiasecongthucnauan.Dao.AnhDao;
import com.example.ungdungchiasecongthucnauan.Dao.BuocLamDao;
import com.example.ungdungchiasecongthucnauan.Dao.CongThucDao;
import com.example.ungdungchiasecongthucnauan.MainActivity;
import com.example.ungdungchiasecongthucnauan.Model.Anh;
import com.example.ungdungchiasecongthucnauan.Model.BuocLam;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.Model.KieuNguyenLieu;
import com.example.ungdungchiasecongthucnauan.Model.NguyenLieu;
import com.example.ungdungchiasecongthucnauan.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
    ArrayList<NguyenLieu> lstNguyenLieu;
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
    AnhDao anhDao;

    private DatabaseReference databaseReference;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
//                congThuc = new CongThuc();
//                OpenDialogCreateRecipes();


                AlphaVantageService service = AlphaVantageClient.getService();
                Call<ResponseBody> call = service.getPrice("GLOBAL_QUOTE", "ngô", "642JNK88J836TS1W");

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                String jsonData = response.body().string();
//                                String value = "";
//                                for (int i = 0; i < jsonData.length();i++){
//                                    value+= jsonData.charAt(i);
//                                }
//                                Log.e("Data trả về","" + value);
                                JSONObject jsonObject = new JSONObject(jsonData);

                                Log.e("Data trả về","" + jsonObject);
                                Log.e("Data trả về","" + jsonObject.length());

//                                if (jsonObject != null) {
//                                    Iterator<String> keys = jsonObject.keys();
//                                    keys.forEachRemaining(s -> {
//                                        Log.e("Giá gạo","Giá gạo:" + s.toLowerCase());
//                                    });
////                                    while(keys.hasNext()) {
////                                        String key = keys.next();
////                                        Log.e("Giá gạo","Giá gạo:" + key.toLowerCase() + " - " + key.va);
////                                    }
////                                    Log.e("Giá gạo","Giá gạo: " + jsonObject.getString("global quote"));
//                                } else {
//                                    Log.e("Giá gạo","Giá gạo: null");
//                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                    }
                });
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
        lstNguyenLieu = new ArrayList<>();

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

        imgSelectedPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                NguyenLieu nguyenLieu = new NguyenLieu();
                lstNguyenLieu.add(nguyenLieu);

                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_material, null);
                layoutMaterial.addView(view);

                for(int i = 0; i < layoutMaterial.getChildCount(); i++){
                    int indexRemove = i;
                    View viewMaterial = layoutMaterial.getChildAt(i);
                    Button btnRemoveMaterial = viewMaterial.findViewById(R.id.btn_removeMaterial);

//                    AutoCompleteTextView actvKNL = viewMaterial.findViewById(R.id.actv_knl);
//
//                    KNLAdapter knlAdapter = new KNLAdapter(getContext(),R.layout.item_selected_spinner_knl,lstKNL);
//                    actvKNL.setAdapter(knlAdapter);
                    btnRemoveMaterial.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            layoutMaterial.removeView(viewMaterial);
                            lstNguyenLieu.remove(indexRemove);
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
                for(int i = 0; i < layoutMaking.getChildCount(); i++){
                    int indexRemove = i;
                    View viewMaking = layoutMaking.getChildAt(i);
                    TextView tvLocation = viewMaking.findViewById(R.id.tv_location);
                    tvLocation.setText(String.valueOf(i + 1));
                    Button btnRemoveMaterial = viewMaking.findViewById(R.id.btn_removeMaking);
                    imgSelectedPictureMaking = viewMaking.findViewById(R.id.img_selected_pictureMaking);
                    btnRemoveMaterial.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            layoutMaking.removeView(viewMaking);
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
                    imgSelectedPictureMaking.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            imgSelectedPictureMaking = v.findViewById(R.id.img_selected_pictureMaking);
                            selectedImgMaking = true;
                            INDEX_SELECTED_IMG = indexRemove;
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

                if (ValidateMaterial(layoutMaterial) && ValidateMaking(layoutMaking)){
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Đang lưu công thức . . .");
                    progressDialog.show();
                    for (int i = 0; i < lstNguyenLieu.size(); i++) {

                    }
                    SaveDataToFirebase(lstAnh.get(0),lstUri.get(0),0,lstBuocLam.size() - 1,dialog);
                }
            }
        });
        dialog.show();
    }
    private boolean ValidateMaterial(LinearLayout layout){
        boolean check = false;
        for (int i = 0; i < layout.getChildCount();i++){
            View viewMaterial = layout.getChildAt(i);
            EditText edtMaterialName = viewMaterial.findViewById(R.id.edt_materialName);
            EditText edtMass = viewMaterial.findViewById(R.id.edt_mass);
            String name = edtMaterialName.getText().toString().trim();
            String mass = edtMass.getText().toString().trim();
            if (!name.isEmpty() && !mass.isEmpty()) {
                check = true;
            } else {
                if (name.isEmpty()) {
                    edtMaterialName.setError(getContext().getResources().getString(R.string.is_empty));
                }
                if (mass.isEmpty()){
                    edtMass.setError(getContext().getResources().getString(R.string.is_empty));
                }
                check = false;
            }
        }
        if (lstNguyenLieu.isEmpty()){
            Toast.makeText(getContext(), "Hãy thêm một vài nguyên liệu trước khi lưu !", Toast.LENGTH_SHORT).show();
            check = false;
        }
        return check;
    }
    private boolean ValidateMaking(LinearLayout layout){
        boolean check = false;
        for (int i = 0; i < layout.getChildCount();i++){
            View viewMaking = layout.getChildAt(i);
            EditText edtContent = viewMaking.findViewById(R.id.edt_content);
            String content = edtContent.getText().toString().trim();
            if (!content.isEmpty()) {
                check = true;
            } else {
                edtContent.setError(getContext().getResources().getString(R.string.is_empty));
                check = false;
            }
        }
        if (lstBuocLam.isEmpty()) {
            Toast.makeText(mainActivity, "Hãy thêm bước làm cho công thức của bạn !", Toast.LENGTH_SHORT).show();
            check = false;
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
        lstNguyenLieu = new ArrayList<>();
        lstBuocLam = new ArrayList<>();
        lstAnh = new ArrayList<>();

        storageReference = FirebaseStorage.getInstance().getReference("UPLOAD_IMAGE");
        databaseReference = FirebaseDatabase.getInstance().getReference("ALL_IMAGE");

        progressDialog = new ProgressDialog(getContext());
        congThucDao = new CongThucDao(getContext());
        anhDao = new AnhDao(getContext());
        buocLamDao = new BuocLamDao(getContext());

    }

    private void ResetData() {
        foodName = "";
        foodRation = "";
        time = "";
        mImageBannerURL = null;
        lstNguyenLieu.clear();
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
    private void SaveDataToFirebaseBanner(){
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
                            congThucDao.insert(congThuc);
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
            congThucDao.insert(congThuc);
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
                                SaveDataToFirebaseBanner();
                                Toast.makeText(mainActivity, "Lưu thành công !", Toast.LENGTH_SHORT).show();
                                ResetData();
                                progressDialog.dismiss();
                                dialog.dismiss();
                            } else {
                                SaveDataToFirebase(lstAnh.get(pos + 1),lstUri.get(pos + 1),pos + 1,lstBuocLam.size() - 1,dialog);
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
            if (pos == (lstBuocLam.size() - 1)) {
                SaveDataToFirebaseBanner();
                Toast.makeText(mainActivity, "Lưu thành công !", Toast.LENGTH_SHORT).show();
                ResetData();
                progressDialog.dismiss();
                dialog.dismiss();
            } else {
                SaveDataToFirebase(lstAnh.get(pos + 1),lstUri.get(pos + 1),pos + 1,lstBuocLam.size() - 1,dialog);
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
        buocLamDao.insert(lstBuocLam.get(pos));
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
        congThuc.setIdLoai(0);
        congThuc.setTrangThai(0);
    }
}