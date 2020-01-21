package com.momayez.gregoriansolardaterangepicker.Adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.momayez.gregoriansolardaterangepicker.DateRangePickerFragment;

public class DateRangePickerAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public DateRangePickerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
            case 1:
                return new DateRangePickerFragment(position);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}

