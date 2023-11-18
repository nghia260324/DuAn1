package com.example.ungdungchiasecongthucnauan.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.ungdungchiasecongthucnauan.Fragment.CreateRecipesFragment;
import com.example.ungdungchiasecongthucnauan.Fragment.HomeFragment;
import com.example.ungdungchiasecongthucnauan.Fragment.IndividualFragment;
import com.example.ungdungchiasecongthucnauan.Fragment.SearchFragment;
import com.example.ungdungchiasecongthucnauan.Model.NguoiDung;

public class ViewPagerBottomNavigationAdapter extends FragmentStateAdapter {
    NguoiDung nguoiDung;
    public ViewPagerBottomNavigationAdapter(@NonNull FragmentActivity fragmentActivity, NguoiDung nguoiDung) {
        super(fragmentActivity);
        this.nguoiDung = nguoiDung;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new HomeFragment();
            case 1: return new SearchFragment();
            case 2: if (nguoiDung.getPhanQuyen() != 1) {
                return new CreateRecipesFragment();
            }
            case 3: return new IndividualFragment();
            default:break;
        }
        return new HomeFragment();
    }
    @Override
    public int getItemCount() {
        return nguoiDung.getPhanQuyen()!=1?4:3;
    }
}
