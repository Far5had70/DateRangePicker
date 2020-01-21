package com.momayez.gregoriansolardaterangepicker.Adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.momayez.gregoriansolardaterangepicker.DatePickerFragment;

public class DatePickerAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public DatePickerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        return new DatePickerFragment();
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}

