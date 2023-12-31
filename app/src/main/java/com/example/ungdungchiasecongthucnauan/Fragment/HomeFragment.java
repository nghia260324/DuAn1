package com.example.ungdungchiasecongthucnauan.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ungdungchiasecongthucnauan.Adapter.BannerAdapter;
import com.example.ungdungchiasecongthucnauan.Adapter.NewDishAdapter;
import com.example.ungdungchiasecongthucnauan.Dao.CongThucDao;
import com.example.ungdungchiasecongthucnauan.MainActivity;
import com.example.ungdungchiasecongthucnauan.Model.CongThuc;
import com.example.ungdungchiasecongthucnauan.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private String mParam1;
    private String mParam2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    MainActivity mainActivity;
    BannerAdapter bannerAdapter;
    NewDishAdapter newDishAdapter;
    RecyclerView rcv_banner,rcvNew;

    CongThucDao congThucDao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initUI(view);
        SetAdapterBanner();
        SetAdapterNew();

        return view;
    }

    private void SetAdapterNew() {
        newDishAdapter = new NewDishAdapter(getContext(),(ArrayList<CongThuc>) congThucDao.get10CongThucNew(),mainActivity);
        rcvNew.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcvNew.setLayoutManager(new GridLayoutManager(getContext(),2));
        rcvNew.setAdapter(newDishAdapter);
    }

    private void initUI(View view) {
        rcv_banner = view.findViewById(R.id.rcv_banner);
        rcvNew = view.findViewById(R.id.rcv_new);

        mainActivity = (MainActivity) getActivity();
        congThucDao = new CongThucDao(getContext());
    }

    private void SetAdapterBanner(){
        bannerAdapter= new BannerAdapter(getContext(),(ArrayList<CongThuc>) congThucDao.getTopCtBL(),mainActivity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcv_banner.setLayoutManager(linearLayoutManager);
        rcv_banner.setAdapter(bannerAdapter);
    }
    @Override
    public void onResume() {
        super.onResume();
        SetAdapterBanner();
        SetAdapterNew();
    }
}