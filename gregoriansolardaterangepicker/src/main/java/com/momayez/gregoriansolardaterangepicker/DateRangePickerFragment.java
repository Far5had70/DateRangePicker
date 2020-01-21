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

import static com.momayez.gregoriansolardaterangepicker.DateRangePicker.WheelTextColor;
import static com.momayez.gregoriansolardaterangepicker.DateRangePicker.WheelTextColorSelected;
import static com.momayez.gregoriansolardaterangepicker.DateRangePicker.WheelTextSize;
import static com.momayez.gregoriansolardaterangepicker.DateRangePicker.fromDate;
import static com.momayez.gregoriansolardaterangepicker.DateRangePicker.isSolarDate;
import static com.momayez.gregoriansolardaterangepicker.DateRangePicker.mDayFrom;
import static com.momayez.gregoriansolardaterangepicker.DateRangePicker.mDayTo;
import static com.momayez.gregoriansolardaterangepicker.DateRangePicker.mMonthFrom;
import static com.momayez.gregoriansolardaterangepicker.DateRangePicker.mMonthTo;
import static com.momayez.gregoriansolardaterangepicker.DateRangePicker.mYearFrom;
import static com.momayez.gregoriansolardaterangepicker.DateRangePicker.mYearTo;
import static com.momayez.gregoriansolardaterangepicker.DateRangePicker.maxYear;
import static com.momayez.gregoriansolardaterangepicker.DateRangePicker.minYear;
import static com.momayez.gregoriansolardaterangepicker.DateRangePicker.pdFrom;
import static com.momayez.gregoriansolardaterangepicker.DateRangePicker.pdTo;
import static com.momayez.gregoriansolardaterangepicker.DateRangePicker.toDate;

public class DateRangePickerFragment extends Fragment {

    private View view;
    private int sourcePosition;
    private WheelPickerView dayWP , monthWP , yearWP;

    public DateRangePickerFragment(int sourcePosition) {
        this.sourcePosition = sourcePosition;
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

            pdFrom.setGrgDay(fromDate.get(Calendar.DAY_OF_MONTH));
            pdFrom.setGrgMonth(fromDate.get(Calendar.MONTH)+1);
            pdFrom.setGrgYear(fromDate.get(Calendar.YEAR));
            pdTo.setGrgDay(toDate.get(Calendar.DAY_OF_MONTH));
            pdTo.setGrgMonth(toDate.get(Calendar.MONTH)+1);
            pdTo.setGrgYear(toDate.get(Calendar.YEAR));

            mDayFrom = pdFrom.getShDay();
            mMonthFrom = pdFrom.getShMonth()-1;
            mYearFrom = pdFrom.getShYear();

            mDayTo = pdTo.getShDay();
            mMonthTo = pdTo.getShMonth()-1;
            mYearTo = pdTo.getShYear();

            if (sourcePosition == 1){
                adjustYear(mYearFrom);
                adjustMonth(mMonthFrom);
                adjustDay(mDayFrom);
            }else if (sourcePosition == 0){
                adjustYear(mYearTo);
                adjustMonth(mMonthTo);
                adjustDay(mDayTo);
            }
        }else {
            mDayFrom = fromDate.get(Calendar.DAY_OF_MONTH);
            mMonthFrom = fromDate.get(Calendar.MONTH);
            mYearFrom = fromDate.get(Calendar.YEAR);

            mDayTo = toDate.get(Calendar.DAY_OF_MONTH);
            mMonthTo = toDate.get(Calendar.MONTH);
            mYearTo = toDate.get(Calendar.YEAR);

            if (sourcePosition == 1){
                adjustYear(mYearFrom);
                adjustMonth(mMonthFrom);
                adjustDay(mDayFrom);
            }else if (sourcePosition == 0){
                adjustYear(mYearTo);
                adjustMonth(mMonthTo);
                adjustDay(mYearTo);
            }
        }
    }

    private void adjustDay(int day) {
        int dayOfMonth;
        if (isSolarDate){
            dayOfMonth = sourcePosition == 1 ? pdFrom.getMonthDays() : pdTo.getMonthDays();
        }else {
            dayOfMonth = sourcePosition == 1 ? fromDate.getActualMaximum(Calendar.DAY_OF_MONTH) : toDate.getActualMaximum(Calendar.DAY_OF_MONTH);
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
                if (sourcePosition==1){
                    if (isSolarDate){
                        mDayFrom = Integer.parseInt(stringList.get(option));
                    }else {
                        mDayFrom = Integer.parseInt(stringList.get(option));
                        fromDate.set(mYearFrom , mMonthFrom-1, mDayFrom);
                    }

                }else if(sourcePosition == 0){
                    if (isSolarDate){
                        mDayTo = Integer.parseInt(stringList.get(option));
                    }else {
                        mDayTo = Integer.parseInt(stringList.get(option));
                        toDate.set(mYearTo , mMonthTo-1, mDayTo);
                    }

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
                if (sourcePosition==1){
                    if (isSolarDate){
                        mMonthFrom = option+1;
                        pdFrom.setShMonth(mMonthFrom);
                    }else {
                        mMonthFrom = option+1;
                        fromDate.set(mYearFrom , option, mDayFrom);
                    }
                    adjustDay(mDayFrom);
                }else if (sourcePosition==0){
                    if (isSolarDate){
                        mMonthTo = option+1;
                        pdTo.setShMonth(mMonthTo);
                    }else {
                        mMonthTo = option+1;
                        toDate.set(mYearTo , option, mDayTo);
                    }
                    adjustDay(mDayTo);
                }
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
                if (sourcePosition==0){
                    if (isSolarDate){
                        mYearTo = Integer.valueOf(strings.get(option));
                        pdTo.setShYear(mYearTo);
                    }else {
                        mYearTo = Integer.valueOf(strings.get(option));
                        toDate.set(mYearTo , mMonthTo-1, mDayTo);
                    }
                    adjustDay(mDayTo);
                }else if (sourcePosition==1){
                    if (isSolarDate){
                        mYearFrom = Integer.valueOf(strings.get(option));
                        pdFrom.setShYear(mYearFrom);
                    }else {
                        mYearFrom = Integer.valueOf(strings.get(option));
                        fromDate.set(mYearFrom , mMonthFrom-1, mDayFrom);
                    }
                    adjustDay(mDayFrom);
                }
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
