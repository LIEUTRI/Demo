package com.lieutri.myapp.components;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.lieutri.myapp.fragments.Scrolling2Fragment;
import com.lieutri.myapp.fragments.ScrollingFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerFragmentAdapter extends FragmentStateAdapter {
    private List<Fragment> fragmentList = new ArrayList<>();

    public ViewPagerFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        fragmentList.add(new ScrollingFragment());
        fragmentList.add(new Scrolling2Fragment());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1: return fragmentList.get(1);
            default: return fragmentList.get(0);
        }
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
