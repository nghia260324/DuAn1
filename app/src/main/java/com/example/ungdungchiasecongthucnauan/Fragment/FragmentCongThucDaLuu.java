package com.example.ungdungchiasecongthucnauan.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ungdungchiasecongthucnauan.Adapter.RecipeListAdapter;
import com.example.ungdungchiasecongthucnauan.Dao.DanhSachCongThucDao;
import com.example.ungdungchiasecongthucnauan.MainActivity;
import com.example.ungdungchiasecongthucnauan.Model.DanhSachCongThuc;
import com.example.ungdungchiasecongthucnauan.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCongThucDaLuu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCongThucDaLuu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentCongThucDaLuu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCongThucDaLuu.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCongThucDaLuu newInstance(String param1, String param2) {
        FragmentCongThucDaLuu fragment = new FragmentCongThucDaLuu();
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

    RecyclerView rcvSavedRecipe;
    DanhSachCongThucDao dsctDao;
    MainActivity mainActivity;

    @Override
    public void onResume() {
        super.onResume();
        SetRecipeListAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cong_thuc_da_luu, container, false);

        initUI(view,getContext());
        SetRecipeListAdapter();
        return view;
    }
    private void SetRecipeListAdapter(){
        ArrayList<DanhSachCongThuc> lstDSCT = (ArrayList<DanhSachCongThuc>) dsctDao.getAllIdUser(String.valueOf(mainActivity.getUser().getId()));
        RecipeListAdapter recipeListAdapter = new RecipeListAdapter(getContext(),lstDSCT,mainActivity);
        rcvSavedRecipe.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcvSavedRecipe.setLayoutManager(new GridLayoutManager(getContext(),2));
        rcvSavedRecipe.setAdapter(recipeListAdapter);
    }

    private void initUI(View view, Context context) {
        rcvSavedRecipe = view.findViewById(R.id.rcv_savedRecipe);

        dsctDao = new DanhSachCongThucDao(context);
        mainActivity = (MainActivity) getActivity();
    }
}