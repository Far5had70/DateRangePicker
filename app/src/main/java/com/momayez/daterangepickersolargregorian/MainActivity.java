package com.momayez.daterangepickersolargregorian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.momayez.gregoriansolardaterangepicker.DatePicker;
import com.momayez.gregoriansolardaterangepicker.DateRangePicker;
import com.momayez.gregoriansolardaterangepicker.Interface.OnChoose;
import com.momayez.gregoriansolardaterangepicker.Interface.OnChooseListener;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.datePickerBtn).setOnClickListener(this);
        findViewById(R.id.dateRangePickerBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.datePickerBtn:
                datePicker();
                break;
            case R.id.dateRangePickerBtn:
                dateRangePicker();
                break;
        }
    }

    private void datePicker() {
        DatePicker datePicker = new DatePicker()
                .setCurrentDate(Calendar.getInstance())
                .setMinYear(1370)
                .setMaxYear(1399)
                .setSolarDate(true)
                .setTypeface(Typeface.createFromAsset(getAssets(), "iran_sans.ttf"))
                .setBackgroundColor(getResources().getColor(R.color.green))
                .setButtonTextColor(getResources().getColor(R.color.white))
                .setTabTextColor(getResources().getColor(R.color.white))
                .setTabSelectedTextColor(getResources().getColor(R.color.white))
                .setTabIndicatorColor(getResources().getColor(R.color.white))
                .setWheelTextColor(getResources().getColor(R.color.white_smoke))
                .setWheelTextColorSelected(getResources().getColor(R.color.white))
                .setWheelTextSize(18)
                .setTextSize(15)
                .setCornerRadius(40)
                .setPositiveText("تائید")
                .setNegativeText("انصراف")
                .setTabText("تاریخ")
                .setPositiveDrawable(getResources().getDrawable(R.drawable.ic_tick))
                .setNegativeDeawable(getResources().getDrawable(R.drawable.ic_mult))
                .setCurrentItem(1)
                .setOnChooseListener(new OnChooseListener() {
                    @Override
                    public void date(Calendar calendar, boolean isSolarDate, int year, int month, int day, String fullDate) {
                        Log.e(TAG, "date: " + calendar.get(Calendar.YEAR)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.DAY_OF_MONTH) + "  -   " + isSolarDate + "  -   " + year+ "  -   " +month+ "  -   " +day+ "  -   " +fullDate  );
                    }

                    @Override
                    public void cancel() {

                    }
                });
        datePicker.show(getSupportFragmentManager(), datePicker.getTag());
    }

    private void dateRangePicker() {
        DateRangePicker dateRangePicker = new DateRangePicker()
                .setCurrentToDate(Calendar.getInstance())
                .setCurrentFromDate(Calendar.getInstance())
                .setMinYear(2001)
                .setMaxYear(2020)
                .setSolarDate(false)
                .setTypeface(Typeface.createFromAsset(getAssets(), "iran_sans.ttf"))
                .setBackgroundColor(getResources().getColor(R.color.green))
                .setButtonTextColor(getResources().getColor(R.color.white))
                .setTabTextColor(getResources().getColor(R.color.white))
                .setTabSelectedTextColor(getResources().getColor(R.color.white))
                .setTabIndicatorColor(getResources().getColor(R.color.white))
                .setWheelTextColor(getResources().getColor(R.color.white_smoke))
                .setWheelTextColorSelected(getResources().getColor(R.color.white))
                .setWheelTextSize(18)
                .setTextSize(15)
                .setCornerRadius(40)
                .setPositiveText("تائید")
                .setNegativeText("انصراف")
                .setFromDateText("از تاریخ")
                .setToDateText("تا تاریخ")
                .setPositiveDrawable(getResources().getDrawable(R.drawable.ic_tick))
                .setNegativeDeawable(getResources().getDrawable(R.drawable.ic_mult))
                .setCurrentItem(1)
                .setOnChooseListener(new OnChoose() {
                    @Override
                    public void fromDate(Calendar calendar, boolean isSolarDate, int year, int month, int day, String fullDate) {
                        Log.e(TAG, "fromDate: " + calendar.get(Calendar.YEAR)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.DAY_OF_MONTH) + "  -   " + isSolarDate + "  -   " + year+ "  -   " +month+ "  -   " +day+ "  -   " +fullDate  );
                    }

                    @Override
                    public void toDate(Calendar calendar, boolean isSolarDate, int year, int month, int day, String fullDate) {
                        Log.e(TAG, "toDate: " + calendar.get(Calendar.YEAR)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.DAY_OF_MONTH) + "  -   " + isSolarDate + "  -   " + year+ "  -   " +month+ "  -   " +day+ "  -   " +fullDate  );
                    }

                    @Override
                    public void cancel() {

                    }
                });
        dateRangePicker.show(getSupportFragmentManager(), dateRangePicker.getTag());
    }
}
