package com.momayez.gregoriansolardaterangepicker;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.momayez.gregoriansolardaterangepicker.Date.DateUtil;
import com.momayez.gregoriansolardaterangepicker.wheel.WheelPickerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static com.momayez.gregoriansolardaterangepicker.DatePicker.WheelTextColor;
import static com.momayez.gregoriansolardaterangepicker.DatePicker.WheelTextColorSelected;
import static com.momayez.gregoriansolardaterangepicker.DatePicker.WheelTextSize;
import static com.momayez.gregoriansolardaterangepicker.DatePicker.isSolarDate;
import static com.momayez.gregoriansolardaterangepicker.DatePicker.mDayTo;
import static com.momayez.gregoriansolardaterangepicker.DatePicker.mMonthTo;
import static com.momayez.gregoriansolardaterangepicker.DatePicker.mYearTo;
import static com.momayez.gregoriansolardaterangepicker.DatePicker.maxYear;
import static com.momayez.gregoriansolardaterangepicker.DatePicker.minYear;
import static com.momayez.gregoriansolardaterangepicker.DatePicker.pdTo;
import static com.momayez.gregoriansolardaterangepicker.DatePicker.toDate;

public class DatePickerFragment extends Fragment {

    private View view;
    private WheelPickerView dayWP , monthWP , yearWP;

    public DatePickerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_date_range_picker, container, false);
        initialData(view);
        return view;
    }

    private void initialData(View view) {
        dayWP = view.findViewById(R.id.dayWP);
        monthWP = view.findViewById(R.id.monthWP);
        yearWP = view.findViewById(R.id.yearWP);
        adjustView();

        if (isSolarDate){
            pdTo.setGrgDay(toDate.get(Calendar.DAY_OF_MONTH));
            pdTo.setGrgMonth(toDate.get(Calendar.MONTH)+1);
            pdTo.setGrgYear(toDate.get(Calendar.YEAR));

            mDayTo = pdTo.getShDay();
            mMonthTo = pdTo.getShMonth()-1;
            mYearTo = pdTo.getShYear();

            adjustYear(mYearTo);
            adjustMonth(mMonthTo);
            adjustDay(mDayTo);

        }else {

            mDayTo = toDate.get(Calendar.DAY_OF_MONTH);
            mMonthTo = toDate.get(Calendar.MONTH);
            mYearTo = toDate.get(Calendar.YEAR);

            adjustYear(mYearTo);
            adjustMonth(mMonthTo);
            adjustDay(mDayTo);
        }
    }

    private void adjustDay(int day) {
        int dayOfMonth;
        if (isSolarDate){
            dayOfMonth = pdTo.getMonthDays();
        }else {
            dayOfMonth = toDate.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        final List<String> stringList = new ArrayList<>();
        for (int i = 1; i <= dayOfMonth; i++) {
            stringList.add("" + i);
        }
        dayWP.setPicker(stringList);
        dayWP.setCurrentItems(day-1);
        dayWP.setCyclic(true);
        dayWP.setOnOptionChangedListener(new WheelPickerView.OnOptionChangedListener() {
            @Override
            public void onOptionChanged(WheelPickerView view, int option) {
                if (isSolarDate){
                    mDayTo = Integer.parseInt(stringList.get(option));
                }else {
                    mDayTo = Integer.parseInt(stringList.get(option));
                    toDate.set(mYearTo , mMonthTo-1, mDayTo);
                }
            }
        });
    }

    private void adjustMonth(int month) {
        monthWP.setPicker(Arrays.asList(isSolarDate ? DateUtil.persianMonth : DateUtil.gregorianMonth));
        monthWP.setCurrentItems(month);
        monthWP.setCyclic(true);
        monthWP.setOnOptionChangedListener(new WheelPickerView.OnOptionChangedListener() {
            @Override
            public void onOptionChanged(WheelPickerView view, int option) {
                if (isSolarDate){
                    mMonthTo = option+1;
                    pdTo.setShMonth(mMonthTo);
                }else {
                    mMonthTo = option+1;
                    toDate.set(mYearTo , option, mDayTo);
                }
                adjustDay(mDayTo);
            }
        });
    }

    private void adjustYear(int year) {
        final List<String> strings = new ArrayList<>();
        int currentItem = 0;
        for (int i = minYear; i <= maxYear; i++) {
            strings.add("" + i);
            if (i == year){
                currentItem = i-minYear;
            }
        }
        yearWP.setPicker(strings);
        yearWP.setCurrentItems(currentItem);
        yearWP.setCyclic(true);
        yearWP.setOnOptionChangedListener(new WheelPickerView.OnOptionChangedListener() {
            @Override
            public void onOptionChanged(WheelPickerView view, int option) {
                if (isSolarDate){
                    mYearTo = Integer.valueOf(strings.get(option));
                    pdTo.setShYear(mYearTo);
                }else {
                    mYearTo = Integer.valueOf(strings.get(option));
                    toDate.set(mYearTo , mMonthTo-1, mDayTo);
                }
                adjustDay(mDayTo);
            }
        });
    }

    private void adjustView() {

        if (WheelTextColor != -1) {
            dayWP.settextColor(WheelTextColor);
            monthWP.settextColor(WheelTextColor);
            yearWP.settextColor(WheelTextColor);
        }

        if (WheelTextColorSelected != -1) {
            dayWP.setTextColorSelected(WheelTextColorSelected);
            monthWP.setTextColorSelected(WheelTextColorSelected);
            yearWP.setTextColorSelected(WheelTextColorSelected);
        }

        if (WheelTextSize != -1) {
            int pixel= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, WheelTextSize, getResources().getDisplayMetrics());
            dayWP.setTextSize(pixel);
            monthWP.setTextSize(pixel);
            yearWP.setTextSize(pixel);
        }
    }
}
