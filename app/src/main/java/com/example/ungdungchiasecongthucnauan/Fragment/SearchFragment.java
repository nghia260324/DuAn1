package com.example.ungdungchiasecongthucnauan.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ungdungchiasecongthucnauan.Adapter.NLAdapter;
import com.example.ungdungchiasecongthucnauan.Adapter.RCVLoaiCongThucAdapter;
import com.example.ungdungchiasecongthucnauan.Adapter.RecipeViewedAdapter;
import com.example.ungdungchiasecongthucnauan.Adapter.SearchAdapter;
import com.example.ungdungchiasecongthucnauan.Adapter.SearchSuggestionAdapter;
import com.example.ungdungchiasecongthucnauan.ChiTietCongThuc;
import com.example.ungdungchiasecongthucnauan.Dao.CongThucDao;
import com.example.ungdungchiasecongthucnauan.Dao.NguyenLieuDao;
import com.example.ungdungchiasecongthucnauan.IReturnDone;
import com.example.ungdungchiasecongthucnauan.IReturnString;
import com.example.ungdungchiasecongthucnauan.MainActivity;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.R;
import com.example.ungdungchiasecongthucnauan.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
    TextView tvNotFound;
    private static final String PATH_SEARCH_HISTORY = "search_history.txt";
    MainActivity mainActivity;
    EditText edtSearch,edtSearchDialog;
    ArrayList<String> lstSuggestSearch;
    RecyclerView rcvFormulaType,rcvViewedRecipe;
    ProgressDialog progressDialog;
    TextView tvNone,tvNoneRecipe;
    LinearLayout layoutHistory;
    CongThucDao congThucDao;

    ArrayList<CongThuc> lstCongThuc = new ArrayList<>();
    @Override
    public void onResume() {
        super.onResume();
        lstCongThuc = (ArrayList<CongThuc>) congThucDao.getAllStatus();
        SetRCVRecipeViewed();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        initUI(view);
        lstCongThuc = mainActivity.lstCongThuc;
        edtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    edtSearch.clearFocus();
                    OpenDialogSearch();
                }
            }
        });

        RCVLoaiCongThucAdapter rcvLoaiCongThucAdapter = new RCVLoaiCongThucAdapter(getContext(), mainActivity.lstLoaiCongThuc,mainActivity);
        rcvFormulaType.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        View itemLayout = LayoutInflater.from(getContext()).inflate(R.layout.item_formula_type,null);
        itemLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int itemHeight = itemLayout.getMeasuredHeight();
        int recyclerViewHeight = (int) Math.ceil((mainActivity.lstLoaiCongThuc.size() / 2.0) * itemHeight);
        ViewGroup.LayoutParams params = rcvFormulaType.getLayoutParams();
        params.height = recyclerViewHeight + itemHeight/2;
        rcvFormulaType.setLayoutParams(params);
        rcvFormulaType.setLayoutManager(new GridLayoutManager(getContext(),2));
        rcvFormulaType.setAdapter(rcvLoaiCongThucAdapter);

        ShowSearchHistory();

        SetRCVRecipeViewed();
        return view;
    }

    private void SetRCVRecipeViewed() {
        ArrayList<CongThuc> lstCongThuc = mainActivity.lstViewedRecipe(getContext());
        if (!lstCongThuc.isEmpty()) {
            tvNoneRecipe.setVisibility(View.GONE);
            rcvViewedRecipe.setVisibility(View.VISIBLE);
            RecipeViewedAdapter recipeViewedAdapter = new RecipeViewedAdapter(getContext(),lstCongThuc,mainActivity);
            rcvViewedRecipe.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
            rcvViewedRecipe.setAdapter(recipeViewedAdapter);
        } else {
            tvNoneRecipe.setVisibility(View.VISIBLE);
            rcvViewedRecipe.setVisibility(View.GONE);
        }
    }
    private void ShowSearchHistory() {
        ArrayList<String> lstSearchHistory = (ArrayList<String>) new Service().readFile(getContext(),PATH_SEARCH_HISTORY);
        if (!lstSearchHistory.isEmpty()) {
            tvNone.setVisibility(View.GONE);
            layoutHistory.setVisibility(View.VISIBLE);
            layoutHistory.removeAllViews();
            for (String s : lstSearchHistory) {
                View itemHistoryView = LayoutInflater.from(getContext()).inflate(R.layout.item_search_history,null);
                TextView tvName = itemHistoryView.findViewById(R.id.tv_history);
                tvName.setText(s);
                layoutHistory.addView(itemHistoryView);
            }
        } else {
            tvNone.setVisibility(View.VISIBLE);
            layoutHistory.setVisibility(View.GONE);
        }
    }

    private void initUI(View view) {
        congThucDao = new CongThucDao(getContext());
        mainActivity = (MainActivity) getActivity();
        edtSearch = view.findViewById(R.id.edt_search);

        lstSuggestSearch = new ArrayList<>();
        rcvFormulaType = view.findViewById(R.id.rcv_formula_type);
        rcvViewedRecipe = view.findViewById(R.id.rcv_viewedRecipe);
        progressDialog = new ProgressDialog(getContext());

        tvNone = view.findViewById(R.id.tv_none);
        tvNoneRecipe = view.findViewById(R.id.tv_noneRecipe);
        layoutHistory = view.findViewById(R.id.layout_history);
    }
    boolean isExpanded = true;

    private void OpenDialogSearch() {
        ArrayList<Integer> lstIDMaterial = new ArrayList<>();
        ArrayList<String> lstMaterialName = new ArrayList<>();
        final View dialogView = View.inflate(getContext(),R.layout.dialog_search,null);
        final Dialog dialog = new Dialog(getContext());

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

        edtSearchDialog = dialog.findViewById(R.id.edt_searchDialog);
        edtSearchDialog.requestFocus();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        ImageButton btnBackDialogSearch = dialog.findViewById(R.id.btn_backDialogSearch);
        RecyclerView rcvSearchSuggestion = dialog.findViewById(R.id.rcv_searchSuggestion);
        LinearLayout layoutMaterial = dialog.findViewById(R.id.layout_material);
        Button btnAddMaterial = dialog.findViewById(R.id.btn_addMaterial);
        TextView tvAllMaterial = dialog.findViewById(R.id.tv_allMaterial);

        RelativeLayout layoutMaterialMain = dialog.findViewById(R.id.layout_materialMain);
        LinearLayout layoutShowSearch = dialog.findViewById(R.id.layout_showSearch);
        ImageView imgDown = dialog.findViewById(R.id.img_down);
        Button btnSave = dialog.findViewById(R.id.btn_save);
        tvNotFound = dialog.findViewById(R.id.tv_notFound);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < lstIDMaterial.size(); i++){
                    Log.e("lstIDMaterial", "" + lstIDMaterial.get(i));
                }
                layoutShowSearch.callOnClick();
                tvAllMaterial.setVisibility(View.VISIBLE);
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < lstMaterialName.size(); i++) {
                    if (i > 0) {
                        stringBuilder.append(";");
                    }
                    stringBuilder.append(lstMaterialName.get(i));
                }
                tvAllMaterial.setText("Nguyên liệu (" + stringBuilder + ")");
                ArrayList<CongThuc> lstCongThuc = (ArrayList<CongThuc>) congThucDao.getCongThucByIngredientIds(lstIDMaterial);
                SearchAdapter searchAdapter = new SearchAdapter(getContext(), lstCongThuc, new IReturnDone() {
                    @Override
                    public void IReturnDone(Context context, CongThuc congThuc) {
                        OpenDialogRecipeDetails(context,congThuc);
                    }
                });
                rcvSearchSuggestion.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                rcvSearchSuggestion.setLayoutManager(new GridLayoutManager(getContext(),1));
                rcvSearchSuggestion.setAdapter(searchAdapter);
            }
        });
        layoutShowSearch.setOnClickListener(v -> {
            if (isExpanded) {
                collapseView(layoutMaterialMain);
                imgDown.animate().rotation(-90).start();
            } else {
                expandView(layoutMaterialMain);
                imgDown.animate().rotation(0).start();
            }
            isExpanded = !isExpanded;
        });
        layoutShowSearch.callOnClick();
        btnAddMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parentView = LayoutInflater.from(getContext()).inflate(R.layout.item_material,null);
                LinearLayout layoutMass = parentView.findViewById(R.id.layout_mass);
                layoutMass.setVisibility(View.GONE);
                layoutMaterial.addView(parentView);
                lstIDMaterial.add(-1);
                lstMaterialName.add("");
                Button btnRemove = parentView.findViewById(R.id.btn_removeMaterial);

                btnRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int index = layoutMaterial.indexOfChild(parentView);
                        layoutMaterial.removeView(parentView);
                        lstIDMaterial.remove(index);
                        lstMaterialName.remove(index);
                    }
                });

                AutoCompleteTextView actvNL = parentView.findViewById(R.id.edt_materialName);
                NLAdapter knlAdapter = new NLAdapter(getContext(), R.layout.item_selected_spinner_knl, mainActivity.getAllNguyenLieu());
                actvNL.setAdapter(knlAdapter);
                actvNL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        NguyenLieuDao nguyenLieuDao = new NguyenLieuDao(getContext());
                        int index = layoutMaterial.indexOfChild(parentView);
                        lstIDMaterial.remove(index);
                        lstMaterialName.remove(index);
                        lstIDMaterial.add(index, nguyenLieuDao.getTen(actvNL.getText().toString().trim()).getId());
                        lstMaterialName.add(index,actvNL.getText().toString().trim());
                    }
                });
            }
        });

        edtSearchDialog.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                lstSuggestSearch.clear();
                if (tvAllMaterial.getVisibility() != View.GONE) {
                    tvAllMaterial.setVisibility(View.GONE);
                }
                if (isExpanded) {
                    layoutShowSearch.callOnClick();
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String value = s.toString().toLowerCase().trim();
//                ArrayList<CongThuc> lstCongThuc = mainActivity.GetRecipes();
                for (int i = 0; i < lstCongThuc.size();i++){
                    CongThuc congThuc = lstCongThuc.get(i);
                    if (congThuc.getTen().toLowerCase().trim().contains(value)) {
                        lstSuggestSearch.add(congThuc.getTen());
                    }
                }
                SearchSuggestionAdapter searchSuggestionAdapter = new SearchSuggestionAdapter(lstSuggestSearch, new IReturnString() {
                    @Override
                    public void ReturnString(String value) {
                        Service service = new Service();
                        ArrayList<String> lstSearchHistory = (ArrayList<String>) service.readFile(getContext(),PATH_SEARCH_HISTORY);
                        if (!lstSearchHistory.contains(value)) {
                            if (!lstSearchHistory.isEmpty()) {
                                if (lstSearchHistory.size() >= 5) {
                                    lstSearchHistory.remove(0);
                                }
                            }
                            lstSearchHistory.add(0,value);
                            service.writeFile(getContext(),PATH_SEARCH_HISTORY,lstSearchHistory);
                            ShowSearchHistory();
                        }

                        edtSearchDialog.setText(value);
                        ArrayList<CongThuc> lstCongThucSearch = searchCongThuc(value,lstCongThuc);
                        SearchAdapter searchAdapter = new SearchAdapter(getContext(), lstCongThucSearch, new IReturnDone() {
                            @Override
                            public void IReturnDone(Context context, CongThuc congThuc) {
                                OpenDialogRecipeDetails(context,congThuc);
                            }
                        });
                        rcvSearchSuggestion.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                        rcvSearchSuggestion.setLayoutManager(new GridLayoutManager(getContext(),1));
                        rcvSearchSuggestion.setAdapter(searchAdapter);
                    }
                });
                rcvSearchSuggestion.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                rcvSearchSuggestion.setAdapter(searchSuggestionAdapter);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtSearchDialog.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (edtSearchDialog.getRight() - edtSearchDialog.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        edtSearchDialog.setText("");
                        return true;
                    }
                }
                return false;
            }
        });
        btnBackDialogSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        edtSearchDialog.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                    String value = edtSearchDialog.getText().toString().trim();
                    ArrayList<CongThuc> lstCongThucSearch = searchCongThuc(value,lstCongThuc);
                    SearchAdapter searchAdapter = new SearchAdapter(getContext(), lstCongThucSearch,null);
                    rcvSearchSuggestion.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                    rcvSearchSuggestion.setLayoutManager(new GridLayoutManager(getContext(),1));
                    rcvSearchSuggestion.setAdapter(searchAdapter);
                    return true;
                }
                return false;
            }
        });
        dialog.show();
    }

    private void OpenDialogRecipeDetails(Context context,CongThuc congThuc) {
        ChiTietCongThuc chiTietCongThuc = new ChiTietCongThuc(context,congThuc,mainActivity);
        chiTietCongThuc.OpenDialogCreateRecipes();
        ArrayList<String> lstRecipeViewed = (ArrayList<String>) new Service().readFile(context,"recipe_viewed.txt");

        if (!lstRecipeViewed.contains(congThuc.getId())) {
            if (!lstRecipeViewed.isEmpty()) {
                if (lstRecipeViewed.size() >= 5){
                    lstRecipeViewed.remove(0);
                }
            }
            lstRecipeViewed.add(0,congThuc.getId());
            new Service().writeFile(context,"recipe_viewed.txt",lstRecipeViewed);
            SetRCVRecipeViewed();
        }
    }

    private void expandView(View view) {
        TransitionManager.beginDelayedTransition((ViewGroup) view, new Slide(Gravity.TOP).setDuration(300));
        view.setVisibility(View.VISIBLE);
    }
    private void collapseView(View view) {
        TransitionManager.beginDelayedTransition((ViewGroup) view, new Slide(Gravity.BOTTOM).setDuration(300));
        view.setVisibility(View.GONE);
    }

    private ArrayList<CongThuc> searchCongThuc(String value,ArrayList<CongThuc> lstCongThuc) {
        String[] arrInputValue = value.split(" ");
        Map<String, Integer> wordCountMap = new HashMap<>();
        ArrayList<CongThuc> lstSearch = new ArrayList<>();
        for (CongThuc congThuc:lstCongThuc){
            for (String s: arrInputValue){
                if (congThuc.getTen().toLowerCase().contains(s.toLowerCase()) && congThuc.getTrangThai() == 1) {
                    lstSearch.add(congThuc);
                    break;
                }
            }
        }
        if (lstSearch.isEmpty()) {
            tvNotFound.setVisibility(View.VISIBLE);
        } else tvNotFound.setVisibility(View.GONE);

        for (String s:arrInputValue){
            for (CongThuc congThuc:lstSearch) {
                int count = 0;
                if (congThuc.getTen().toLowerCase().contains(s.toLowerCase())) {
                    count++;
                }
                wordCountMap.put(congThuc.getId(),count);
            }
        }
        Collections.sort(lstSearch, new Comparator<CongThuc>() {
            @Override
            public int compare(CongThuc o1, CongThuc o2) {
                return wordCountMap.get(o1.getId()) > wordCountMap.get(o2.getId())?1:-1;
            }
        });
        for (CongThuc congThuc: lstSearch){
            Log.e("Công thức search","" + congThuc.toString());
        }
        return lstSearch;
    }
}