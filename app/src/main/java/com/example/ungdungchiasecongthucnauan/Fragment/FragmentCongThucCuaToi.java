package com.example.ungdungchiasecongthucnauan.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ungdungchiasecongthucnauan.Adapter.MyRecipeAdapter;
import com.example.ungdungchiasecongthucnauan.MainActivity;
import com.example.ungdungchiasecongthucnauan.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCongThucCuaToi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCongThucCuaToi extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentCongThucCuaToi() {
        // Required empty public constructor
    }

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


    RecyclerView rcvMyRecipe;
    MainActivity mainActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cong_thuc_cua_toi, container, false);
        initUI(view);
        SetAdapterRCV();
        return view;
    }

    private void SetAdapterRCV() {
        MyRecipeAdapter myRecipeAdapter = new MyRecipeAdapter(getContext(),mainActivity.lstCongThuc,mainActivity);
        rcvMyRecipe.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcvMyRecipe.setLayoutManager(new GridLayoutManager(getContext(),2));
        rcvMyRecipe.setAdapter(myRecipeAdapter);
    }

    private void initUI(View view) {
        mainActivity = (MainActivity) getActivity();

        rcvMyRecipe = view.findViewById(R.id.rcv_myRecipe);
    }
}