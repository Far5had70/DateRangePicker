package com.momayez.gregoriansolardaterangepicker;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.momayez.gregoriansolardaterangepicker.Adapter.DateRangePickerAdapter;
import com.momayez.gregoriansolardaterangepicker.Date.DateUtil;
import com.momayez.gregoriansolardaterangepicker.Date.PersianDate;
import com.momayez.gregoriansolardaterangepicker.Interface.OnChoose;

import java.util.Calendar;

public class DateRangePicker extends DialogFragment implements View.OnClickListener {

    public static Typeface typeface;
    static int mDayFrom, mMonthFrom, mYearFrom, mDayTo, mMonthTo, mYearTo, minYear , maxYear;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View root;

    private int backgroundColor = -1;
    private int ButtonTextColor = -1;
    private int TabTextColor = -1;
    private float TextSize = -1;
    private int TabSelectedTextColor = -1;
    private int TabIndicatorColor = -1;

    private String positiveTxt , negativeTxt;
    private String fromDateText , toDateText;

    static int WheelTextColor = -1;
    static int WheelTextColorSelected = -1;
    static int WheelTextSize = -1;
    private int SetCurrentItem = 1;
    private int CornerRadius = 20;

    static boolean isSolarDate;

    private Drawable PositiveDrawable;
    private Drawable NegativeDeawable;

    static Calendar fromDate;
    static Calendar toDate;

    static PersianDate pdFrom;
    static PersianDate pdTo;

    private OnChoose onChoose;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Theme_MaterialComponents);
        View view = inflater.inflate(R.layout.view_date_range_picker, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initialData(view);
        return view;
    }

    private void initialData(View view) {
        MaterialButton positiveBtn = view.findViewById(R.id.positiveBtn);
        MaterialButton negativeBtn = view.findViewById(R.id.negativeBtn);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        root = view.findViewById(R.id.root);

        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);

        setupTab();

        if (fromDate == null) {
            fromDate = Calendar.getInstance();
        }
        if (toDate == null) {
            toDate = Calendar.getInstance();
        }

        if (pdFrom == null) {
            pdFrom = new PersianDate();
        }
        if (pdTo == null) {
            pdTo = new PersianDate();
        }

        if (ButtonTextColor != -1) {
            positiveBtn.setTextColor(ButtonTextColor);
            negativeBtn.setTextColor(ButtonTextColor);
            positiveBtn.setIconTintResource(R.color.white);
            negativeBtn.setIconTintResource(R.color.white);
        }

        if (typeface != null) {
            positiveBtn.setTypeface(typeface);
            negativeBtn.setTypeface(typeface);
        }

        if (TabIndicatorColor != -1) {
            tabLayout.setSelectedTabIndicatorColor(TabIndicatorColor);
        }

        if (positiveTxt != null) {
            positiveBtn.setText(positiveTxt);
        }

        if (negativeTxt != null) {
            negativeBtn.setText(negativeTxt);
        }

        if (PositiveDrawable != null) {
            positiveBtn.setIcon(PositiveDrawable);
        }

        if (NegativeDeawable != null) {
            negativeBtn.setIcon(NegativeDeawable);
        }

        if (TextSize != -1) {
            positiveBtn.setTextSize(TextSize);
            negativeBtn.setTextSize(TextSize);
        }

        if (backgroundColor != -1) {
            positiveBtn.setBackgroundColor(backgroundColor);
            negativeBtn.setBackgroundColor(backgroundColor);
        }
    }

    private void setupTab() {
        DateRangePickerAdapter adapter = new DateRangePickerAdapter(getChildFragmentManager(), 2);
        tabLayout.addTab(tabLayout.newTab().setText(toDateText));
        tabLayout.addTab(tabLayout.newTab().setText(fromDateText));
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setCurrentItem(SetCurrentItem);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TextView tv = (TextView)LayoutInflater.from(getContext()).inflate(R.layout.custom_tab,null);
            tabLayout.getTabAt(i).setCustomView(tv);
            if (typeface!= null) tv.setTypeface(typeface);
            if (TextSize != -1) tv.setTextSize(TextSize);
            if (ButtonTextColor != -1) tv.setTextColor(ButtonTextColor);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    viewPager.setCurrentItem(0);
                } else if (position == 1) {
                    viewPager.setCurrentItem(1);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        viewPager.setCurrentItem(0);
                        break;
                    case 1:
                        viewPager.setCurrentItem(1);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.setTabTextColors(TabTextColor , TabSelectedTextColor);
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT - 1;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        GradientDrawable bgShape = new GradientDrawable();
        bgShape.setCornerRadius(CornerRadius);
        if (backgroundColor != -1) {
            bgShape.setColor(backgroundColor);
        } else {
            bgShape.setColor(getResources().getColor(R.color.white));
        }
        root.setBackground(bgShape);
        tabLayout.setBackground(bgShape);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.positiveBtn) {
            positiveClick();
        } else if (id == R.id.negativeBtn) {
            negativeClick();
        }
    }

    private void positiveClick() {
        if (isSolarDate){

            PersianDate mFromDate = new PersianDate();
            mFromDate.setShDay(mDayFrom);
            mFromDate.setShMonth(mMonthFrom);
            mFromDate.setShYear(mYearFrom);

            PersianDate mToDate = new PersianDate();
            mToDate.setShDay(mDayTo);
            mToDate.setShMonth(mMonthTo);
            mToDate.setShYear(mYearTo);

            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.set(mFromDate.getGrgYear() , mFromDate.getGrgMonth()==0?11:mFromDate.getGrgMonth()-1 , mFromDate.getGrgDay());

            Calendar toCalendar = Calendar.getInstance();
            toCalendar.set(mToDate.getGrgYear() , mToDate.getGrgMonth()==0?11:mToDate.getGrgMonth()-1 , mToDate.getGrgDay());

            onChoose.fromDate(fromCalendar , isSolarDate , mFromDate.getShYear() , mFromDate.getShMonth() , mFromDate.getShDay() , DateUtil.convertIntToShamsiDate(mFromDate.getShDay() , mFromDate.getShMonth() , mFromDate.getShYear()));
            onChoose.toDate(toCalendar , isSolarDate , mToDate.getShYear() , mToDate.getShMonth() , mToDate.getShDay() , DateUtil.convertIntToShamsiDate(mToDate.getShDay() , mToDate.getShMonth() , mToDate.getShYear()));
        }else {
            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.set(mYearFrom , mMonthFrom-1 , mDayFrom);

            Calendar toCalendar = Calendar.getInstance();
            toCalendar.set(mYearTo , mMonthTo-1 , mDayTo);

            onChoose.fromDate(fromCalendar , isSolarDate , mYearFrom , mMonthFrom , mDayFrom , DateUtil.convertIntToShamsiDate(mDayFrom , mMonthFrom , mYearFrom));
            onChoose.toDate(toCalendar , isSolarDate , mYearTo , mMonthTo , mDayTo , DateUtil.convertIntToShamsiDate(mDayTo , mMonthTo , mYearTo));
        }
        dismiss();
    }

    private void negativeClick() {
        onChoose.cancel();
        dismiss();
    }


    public DateRangePicker setSolarDate(boolean isSolarDate) {
        DateRangePicker.isSolarDate = isSolarDate;
        return this;
    }

    public DateRangePicker setCurrentFromDate(Calendar fromDate) {
        DateRangePicker.fromDate = fromDate;
        return this;
    }

    public DateRangePicker setCurrentToDate(Calendar toDate) {
        DateRangePicker.toDate = toDate;
        return this;
    }

    public DateRangePicker setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public DateRangePicker setButtonTextColor(int ButtonTextColor) {
        this.ButtonTextColor = ButtonTextColor;
        return this;
    }

    public DateRangePicker setTabTextColor(int TabTextColor) {
        this.TabTextColor = TabTextColor;
        return this;
    }

    public DateRangePicker setTabSelectedTextColor(int TabSelectedTextColor) {
        this.TabSelectedTextColor = TabSelectedTextColor;
        return this;
    }

    public DateRangePicker setTabIndicatorColor(int TabIndicatorColor) {
        this.TabIndicatorColor = TabIndicatorColor;
        return this;
    }

    public DateRangePicker setWheelTextColor(int WheelTextColor) {
        DateRangePicker.WheelTextColor = WheelTextColor;
        return this;
    }

    public DateRangePicker setWheelTextColorSelected(int WheelTextColorSelected) {
        DateRangePicker.WheelTextColorSelected = WheelTextColorSelected;
        return this;
    }

    public DateRangePicker setWheelTextSize(int WheelTextSize) {
        DateRangePicker.WheelTextSize = WheelTextSize;
        return this;
    }

    public DateRangePicker setTypeface(Typeface typeface) {
        DateRangePicker.typeface = typeface;
        return this;
    }

    public DateRangePicker setPositiveText(String positiveTxt) {
        this.positiveTxt = positiveTxt;
        return this;
    }

    public DateRangePicker setNegativeText(String negativeTxt) {
        this.negativeTxt = negativeTxt;
        return this;
    }

    public DateRangePicker setFromDateText(String fromDateText) {
        this.fromDateText = fromDateText;
        return this;
    }

    public DateRangePicker setToDateText(String toDateText) {
        this.toDateText = toDateText;
        return this;
    }

    public DateRangePicker setPositiveDrawable(Drawable PositiveDrawable) {
        this.PositiveDrawable = PositiveDrawable;
        return this;
    }

    public DateRangePicker setNegativeDeawable(Drawable NegativeDeawable) {
        this.NegativeDeawable = NegativeDeawable;
        return this;
    }

    public DateRangePicker setCurrentItem(int SetCurrentItem) {
        this.SetCurrentItem = SetCurrentItem;
        return this;
    }

    public DateRangePicker setOnChooseListener(OnChoose onChoose) {
        this.onChoose = onChoose;
        return this;
    }

    public DateRangePicker setCornerRadius(int CornerRadius) {
        this.CornerRadius = CornerRadius;
        return this;
    }

    public DateRangePicker setMinYear(int minYear) {
        DateRangePicker.minYear = minYear;
        return this;
    }

    public DateRangePicker setMaxYear(int maxYear) {
        DateRangePicker.maxYear = maxYear;
        return this;
    }

    public DateRangePicker setTextSize(float TextSize) {
        this.TextSize = TextSize;
        return this;
    }
}
