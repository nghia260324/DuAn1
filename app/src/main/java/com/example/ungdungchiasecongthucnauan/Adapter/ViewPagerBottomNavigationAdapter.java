package com.example.ungdungchiasecongthucnauan.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.ungdungchiasecongthucnauan.Fragment.CreateRecipesFragment;
import com.example.ungdungchiasecongthucnauan.Fragment.HomeFragment;
import com.example.ungdungchiasecongthucnauan.Fragment.IndividualFragment;
import com.example.ungdungchiasecongthucnauan.Fragment.SearchFragment;

public class ViewPagerBottomNavigationAdapter extends FragmentStateAdapter {
    public ViewPagerBottomNavigationAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new HomeFragment();
            case 1: return new SearchFragment();
            case 2: return new CreateRecipesFragment();
            case 3: return new IndividualFragment();
            default:break;
        }
        return new HomeFragment();
    }
    @Override
    public int getItemCount() {
        return 4;
    }
}
