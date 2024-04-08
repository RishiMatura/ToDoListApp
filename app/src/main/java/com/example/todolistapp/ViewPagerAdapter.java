package com.example.todolistapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0)
            return new RecyclerViewFragment();

        else
            return new RecyclerViewFragment();
    }
    @Override
    public int getItemCount() {  //Give the number of tabs in the activity
        return 2;
    }
}
