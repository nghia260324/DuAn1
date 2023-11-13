package com.example.ungdungchiasecongthucnauan.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ungdungchiasecongthucnauan.Adapter.RCVLoaiCongThucAdapter;
import com.example.ungdungchiasecongthucnauan.Adapter.SearchAdapter;
import com.example.ungdungchiasecongthucnauan.Adapter.SearchSuggestionAdapter;
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
    private static final String PATH_SEARCH_HISTORY = "search_history.txt";
    MainActivity mainActivity;
    EditText edtSearch,edtSearchDialog;
    ArrayList<String> lstSuggestSearch;
    RecyclerView rcvFormulaType;
    ProgressDialog progressDialog;
    TextView tvNone;
    LinearLayout layoutHistory;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        initUI(view);
        edtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    edtSearch.clearFocus();
                    OpenDialogSearch();
                }
            }
        });

        RCVLoaiCongThucAdapter rcvLoaiCongThucAdapter = new RCVLoaiCongThucAdapter(getContext(), mainActivity.lstLoaiCongThuc);
        rcvFormulaType.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcvFormulaType.setLayoutManager(new GridLayoutManager(getContext(),2));
        rcvFormulaType.setNestedScrollingEnabled(false);
        rcvFormulaType.setAdapter(rcvLoaiCongThucAdapter);

        ShowSearchHistory();

        return view;
    }

    private void ShowSearchHistory() {
        ArrayList<String> lstSearchHistory = new Service().readFile(PATH_SEARCH_HISTORY);
        Log.e("Lst Search","" + lstSearchHistory);
        if (!lstSearchHistory.isEmpty()) {
            tvNone.setEnabled(false);
            layoutHistory.setEnabled(true);
            for (String s : lstSearchHistory) {
                View itemHistoryView = LayoutInflater.from(getContext()).inflate(R.layout.item_search_history,null);
                TextView tvName = itemHistoryView.findViewById(R.id.tv_history);
                tvName.setText(s);
                layoutHistory.addView(itemHistoryView);
            }
        } else {
            tvNone.setEnabled(true);
            layoutHistory.setEnabled(false);
        }
    }

    private void initUI(View view) {
        mainActivity = (MainActivity) getActivity();
        edtSearch = view.findViewById(R.id.edt_search);

        lstSuggestSearch = new ArrayList<>();
        rcvFormulaType = view.findViewById(R.id.rcv_formula_type);
        progressDialog = new ProgressDialog(getContext());

        tvNone = view.findViewById(R.id.tv_none);
        layoutHistory = view.findViewById(R.id.layout_history);
    }

    private void OpenDialogSearch() {
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

        edtSearchDialog.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                lstSuggestSearch.clear();
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String value = s.toString().toLowerCase().trim();
                for (int i = 0; i < mainActivity.lstCongThuc.size();i++){
                    CongThuc congThuc = mainActivity.lstCongThuc.get(i);
                    if (congThuc.getTen().toLowerCase().trim().contains(value)) {
                        lstSuggestSearch.add(congThuc.getTen());
                    }
                }
                SearchSuggestionAdapter searchSuggestionAdapter = new SearchSuggestionAdapter(lstSuggestSearch, new IReturnString() {
                    @Override
                    public void ReturnString(String value) {
                        Service service = new Service();
                        ArrayList<String> lstSearchHistory = service.readFile(PATH_SEARCH_HISTORY);
                        if (!lstSearchHistory.isEmpty() && lstSearchHistory.size() >= 5) {
                            lstSearchHistory.remove(0);
                        }
                        lstSearchHistory.add(value);
                        service.writeFile(PATH_SEARCH_HISTORY,lstSearchHistory);

                        edtSearchDialog.setText(value);
                        ArrayList<CongThuc> lstCongThucSearch = searchCongThuc(value,mainActivity.lstCongThuc);
                        SearchAdapter searchAdapter = new SearchAdapter(getContext(),lstCongThucSearch);
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
                    ArrayList<CongThuc> lstCongThucSearch = searchCongThuc(value,mainActivity.lstCongThuc);
                    SearchAdapter searchAdapter = new SearchAdapter(getContext(),lstCongThucSearch);
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

    private ArrayList<CongThuc> searchCongThuc(String value,ArrayList<CongThuc> lstCongThuc) {
        String[] arrInputValue = value.split(" ");
        Map<String, Integer> wordCountMap = new HashMap<>();
        ArrayList<CongThuc> lstSearch = new ArrayList<>();
        for (CongThuc congThuc:lstCongThuc){
            for (String s: arrInputValue){
                if (congThuc.getTen().toLowerCase().trim().contains(s.toLowerCase().trim())) {
                    lstSearch.add(congThuc);
                    break;
                }
            }
        }
        for (String s:arrInputValue){
            for (CongThuc congThuc:lstSearch) {
                int count = 0;
                if (congThuc.getTen().toLowerCase().trim().contains(s.toLowerCase().trim())) {
                    count++;
                }
                wordCountMap.put(congThuc.getId(),count);
            }
        }
        Collections.sort(lstSearch, new Comparator<CongThuc>() {
            @Override
            public int compare(CongThuc o1, CongThuc o2) {
                return wordCountMap.get(o1.getId()) < wordCountMap.get(o2.getId())?1:-1;
            }
        });
        for (CongThuc congThuc: lstSearch){
            Log.e("Công thức search","" + congThuc.toString());
        }
        return lstSearch;
    }
}