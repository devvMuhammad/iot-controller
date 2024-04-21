package com.innovativesolutions.iotcontroller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyFragmentAdapter extends FragmentStateAdapter {
        private static final int NUM_PAGES = 3;
        public MyFragmentAdapter(FragmentActivity fm) {
            super(fm);
        }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AllDevicesFragment();
            case 1:
                return new ActiveDevicesFragment();
            case 2:
                return new InactiveDevicesFragment();
            default:
                throw new IllegalArgumentException("Invalid position: " + position);
        }
    }

        @Override
        public int getItemCount() {
            return NUM_PAGES; // Number of tabs
        }

}
