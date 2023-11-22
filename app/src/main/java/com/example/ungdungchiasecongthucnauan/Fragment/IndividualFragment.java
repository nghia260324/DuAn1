package com.example.ungdungchiasecongthucnauan.Fragment;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.ungdungchiasecongthucnauan.Adapter.ChangeAvatarAdapter;
import com.example.ungdungchiasecongthucnauan.Adapter.ViewPagerAdapter;
import com.example.ungdungchiasecongthucnauan.Dao.NguoiDungDao;
import com.example.ungdungchiasecongthucnauan.IReturnString;
import com.example.ungdungchiasecongthucnauan.MainActivity;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;
import com.example.ungdungchiasecongthucnauan.R;
import com.example.ungdungchiasecongthucnauan.Service;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IndividualFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IndividualFragment extends Fragment {
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
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    TextView tvHoten,tvEmail;
    ImageView imgUser;
    MainActivity mainActivity;
    ArrayList<String> lstAvatar;
    NguoiDungDao nguoiDungDao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_individual, container, false);

        initUI(view);

        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPagerAdapter.addFragment(new FragmentCaNhan(),"Cá nhân");
        viewPagerAdapter.addFragment(new FragmentCongThucDaLuu(), "Công thức đã lưu");
        if (mainActivity.getUser().getPhanQuyen() != 1) {
            viewPagerAdapter.addFragment(new FragmentCongThucCuaToi(), "Công thức của tôi");
        }
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDialogChangeAvatar();
            }
        });

        setDataUser();
        return view;
    }
    int selectedAvatar = 0;

    private void OpenDialogChangeAvatar() {
        selectedAvatar = mainActivity.getUser().getAvatar() - 1;
        final View dialogView = View.inflate(getContext(),R.layout.dialog_change_avatar,null);
        final Dialog dialog = new Dialog(getContext());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);

        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(layoutParams);

        Button btn_save = dialog.findViewById(R.id.btn_save);
        RecyclerView rcvChangeAvatar = dialog.findViewById(R.id.rcv_changeAvatar);
        ChangeAvatarAdapter changeAvatarAdapter = new ChangeAvatarAdapter(getContext(), lstAvatar, mainActivity, new IReturnString() {
            @Override
            public void ReturnString(String value) {
                selectedAvatar = lstAvatar.indexOf(value);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rcvChangeAvatar.setLayoutManager(linearLayoutManager);
        rcvChangeAvatar.setLayoutManager(new GridLayoutManager(getContext(),3));
        rcvChangeAvatar.setAdapter(changeAvatarAdapter);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NguoiDung nguoiDung = mainActivity.getUser();
                if (selectedAvatar != nguoiDung.getAvatar() - 1) {
                    nguoiDung.setAvatar(selectedAvatar + 1);
                    nguoiDungDao.update(nguoiDung);
                    Toast.makeText(getContext(), "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
                    setDataUser();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void initUI(View view) {
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        tvHoten = view.findViewById(R.id.tvHoten);
        tvEmail = view.findViewById(R.id.tvEmail);
        imgUser = view.findViewById(R.id.imgUser);

        mainActivity = (MainActivity) getActivity();
        nguoiDungDao = new NguoiDungDao(getContext());
        lstAvatar = new ArrayList<>();
        lstAvatar.add("avatar1");
        lstAvatar.add("avatar2");
        lstAvatar.add("avatar3");
        lstAvatar.add("avatar4");
        lstAvatar.add("avatar5");
        lstAvatar.add("avatar6");
        lstAvatar.add("avatar7");
        lstAvatar.add("avatar8");
        lstAvatar.add("avatar9");
        lstAvatar.add("avatar10");
    }

    private void setDataUser(){
        NguoiDung nguoiDung = mainActivity.getUser();
        tvEmail.setText(nguoiDung.getEmail());
        tvHoten.setText(nguoiDung.getHoTen());
        new Service().setAvatar(imgUser,nguoiDung.getAvatar());
    }

}