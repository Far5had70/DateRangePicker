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
import com.momayez.gregoriansolardaterangepicker.Adapter.DatePickerAdapter;
import com.momayez.gregoriansolardaterangepicker.Date.DateUtil;
import com.momayez.gregoriansolardaterangepicker.Date.PersianDate;
import com.momayez.gregoriansolardaterangepicker.Interface.OnChooseListener;

import java.util.Calendar;

public class DatePicker extends DialogFragment implements View.OnClickListener {

    public static Typeface typeface;
    static int mDayTo, mMonthTo, mYearTo, minYear , maxYear;
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
    private String toDateText;

    static int WheelTextColor = -1;
    static int WheelTextColorSelected = -1;
    static int WheelTextSize = -1;
    private int SetCurrentItem = 1;
    private int CornerRadius = 20;

    static boolean isSolarDate;

    private Drawable PositiveDrawable;
    private Drawable NegativeDeawable;

    static Calendar toDate;
    static PersianDate pdTo;

    private OnChooseListener onChoose;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Theme_MaterialComponents);
        View view = inflater.inflate(R.layout.view_date_picker, container, false);
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

        if (toDate == null) {
            toDate = Calendar.getInstance();
        }

        if (pdTo == null) {
            pdTo = new PersianDate();
        }

        if (ButtonTextColor != -1) {
            positiveBtn.setTextColor(ButtonTextColor);
            negativeBtn.setTextColor(ButtonTextColor);
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
    }

    private void setupTab() {
        DatePickerAdapter adapter = new DatePickerAdapter(getChildFragmentManager(), 1);
        tabLayout.addTab(tabLayout.newTab().setText(toDateText));
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabTextColors(TabTextColor , TabSelectedTextColor);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TextView tv = (TextView)LayoutInflater.from(getContext()).inflate(R.layout.custom_tab,null);
            tabLayout.getTabAt(i).setCustomView(tv);
            if (typeface!= null) tv.setTypeface(typeface);
            if (TextSize != -1) tv.setTextSize(TextSize);
            if (ButtonTextColor != -1) tv.setTextColor(ButtonTextColor);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT - 1;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
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

            PersianDate mToDate = new PersianDate();
            mToDate.setShDay(mDayTo);
            mToDate.setShMonth(mMonthTo);
            mToDate.setShYear(mYearTo);

            Calendar toCalendar = Calendar.getInstance();
            toCalendar.set(mToDate.getGrgYear() , mToDate.getGrgMonth()==0?11:mToDate.getGrgMonth()-1 , mToDate.getGrgDay());

            onChoose.date(toCalendar , isSolarDate , mToDate.getShYear() , mToDate.getShMonth() , mToDate.getShDay() , DateUtil.convertIntToShamsiDate(mToDate.getShDay() , mToDate.getShMonth() , mToDate.getShYear()));
        }else {
            Calendar toCalendar = Calendar.getInstance();
            toCalendar.set(mYearTo , mMonthTo-1 , mDayTo);

            onChoose.date(toCalendar , isSolarDate , mYearTo , mMonthTo , mDayTo , DateUtil.convertIntToShamsiDate(mDayTo , mMonthTo , mYearTo));
        }
    }

    private void negativeClick() {
        onChoose.cancel();
        dismiss();
    }


    public DatePicker setSolarDate(boolean isSolarDate) {
        DatePicker.isSolarDate = isSolarDate;
        return this;
    }

    public DatePicker setCurrentDate(Calendar toDate) {
        DatePicker.toDate = toDate;
        return this;
    }

    public DatePicker setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public DatePicker setButtonTextColor(int ButtonTextColor) {
        this.ButtonTextColor = ButtonTextColor;
        return this;
    }

    public DatePicker setTabTextColor(int TabTextColor) {
        this.TabTextColor = TabTextColor;
        return this;
    }

    public DatePicker setTabSelectedTextColor(int TabSelectedTextColor) {
        this.TabSelectedTextColor = TabSelectedTextColor;
        return this;
    }

    public DatePicker setTabIndicatorColor(int TabIndicatorColor) {
        this.TabIndicatorColor = TabIndicatorColor;
        return this;
    }

    public DatePicker setWheelTextColor(int WheelTextColor) {
        DatePicker.WheelTextColor = WheelTextColor;
        return this;
    }

    public DatePicker setWheelTextColorSelected(int WheelTextColorSelected) {
        DatePicker.WheelTextColorSelected = WheelTextColorSelected;
        return this;
    }

    public DatePicker setWheelTextSize(int WheelTextSize) {
        DatePicker.WheelTextSize = WheelTextSize;
        return this;
    }

    public DatePicker setTypeface(Typeface typeface) {
        DatePicker.typeface = typeface;
        return this;
    }

    public DatePicker setPositiveText(String positiveTxt) {
        this.positiveTxt = positiveTxt;
        return this;
    }

    public DatePicker setNegativeText(String negativeTxt) {
        this.negativeTxt = negativeTxt;
        return this;
    }

    public DatePicker setTabText(String toDateText) {
        this.toDateText = toDateText;
        return this;
    }

    public DatePicker setPositiveDrawable(Drawable PositiveDrawable) {
        this.PositiveDrawable = PositiveDrawable;
        return this;
    }

    public DatePicker setNegativeDeawable(Drawable NegativeDeawable) {
        this.NegativeDeawable = NegativeDeawable;
        return this;
    }

    public DatePicker setCurrentItem(int SetCurrentItem) {
        this.SetCurrentItem = SetCurrentItem;
        return this;
    }

    public DatePicker setOnChooseListener(OnChooseListener onChoose) {
        this.onChoose = onChoose;
        return this;
    }

    public DatePicker setCornerRadius(int CornerRadius) {
        this.CornerRadius = CornerRadius;
        return this;
    }

    public DatePicker setMinYear(int minYear) {
        DatePicker.minYear = minYear;
        return this;
    }

    public DatePicker setMaxYear(int maxYear) {
        DatePicker.maxYear = maxYear;
        return this;
    }

    public DatePicker setTextSize(float TextSize) {
        this.TextSize = TextSize;
        return this;
    }
}
