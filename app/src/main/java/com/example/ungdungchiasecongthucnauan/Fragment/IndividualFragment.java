package com.example.ungdungchiasecongthucnauan.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.ungdungchiasecongthucnauan.Adapter.ViewPagerAdapter;
import com.example.ungdungchiasecongthucnauan.Adapter.ViewPagerBottomNavigationAdapter;
import com.example.ungdungchiasecongthucnauan.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IndividualFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IndividualFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerBottomNavigationAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //aa
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IndividualFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IndividualFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IndividualFragment newInstance(String param1, String param2) {
        IndividualFragment fragment = new IndividualFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_individual, container, false);
        tabLayout=(TabLayout) view.findViewById(R.id.tabLayout);
        viewPager=(ViewPager) view.findViewById(R.id.viewPager);

        viewPagerBottomNavigationAdapter = new ViewPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);


        viewPagerBottomNavigationAdapter.addFragment(new FragmentCaNhan(),"Cá nhân");
        viewPagerBottomNavigationAdapter.addFragment(new FragmentCongThucDaLuu(), "Công thức đã lưu");
        viewPagerBottomNavigationAdapter.addFragment(new FragmentCongThucCuaToi(), "Công thức của tôi");

        viewPager.setAdapter(viewPagerBottomNavigationAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}