package com.example.ungdungchiasecongthucnauan.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.ungdungchiasecongthucnauan.Adapter.CaNhanAdapter;
import com.example.ungdungchiasecongthucnauan.Dao.NguoiDungDao;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;
import com.example.ungdungchiasecongthucnauan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCaNhan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCaNhan extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentCaNhan() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCaNhan.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCaNhan newInstance(String param1, String param2) {
        FragmentCaNhan fragment = new FragmentCaNhan();
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
        View view=inflater.inflate(R.layout.fragment_ca_nhan, container, false);
        ListView listView=view.findViewById(R.id.lvcanhan);

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String email= user.getEmail();

        NguoiDungDao nguoiDungDao=new NguoiDungDao(getContext());
        NguoiDung nd=nguoiDungDao.getNguoiDungFromEmail(email);


        List<String> items = new ArrayList<>();
        items.add("Đổi mật khẩu");
        items.add("Tạo danh sách công thức mới");
        if (nd.getPhanQuyen()==0){
            items.add("Quản lý người dùng");
            items.add("Quản lý công thức");
        }
        items.add("Đăng xuất");

        CaNhanAdapter adapter = new CaNhanAdapter(getActivity(), items);
        listView.setAdapter(adapter);

        return view;
    }
}