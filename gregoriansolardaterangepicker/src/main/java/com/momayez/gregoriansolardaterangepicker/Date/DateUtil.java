package com.momayez.gregoriansolardaterangepicker.Date;

import android.content.Context;

import java.util.Calendar;

public class DateUtil {

    public static String[] persianMonth = {
            "فروردین",
            "اردیبهشت",
            "خرداد",
            "تیر",
            "مرداد",
            "شهریور",
            "مهر",
            "آبان",
            "آذر",
            "دی",
            "بهمن",
            "اسفند"
    };

    public static String[] gregorianMonth = {
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    };

    private PersianDate persianDate = new PersianDate();
    private Context context;

    public DateUtil(Context context) {
        this.context = context;
    }

    public static PersianDate convertCalendarToPersianDate(Calendar calendar){
        PersianDate persianDate = new PersianDate();
        persianDate.setGrgDay(calendar.get(Calendar.DAY_OF_MONTH));
        persianDate.setGrgMonth(calendar.get(Calendar.MONTH)+1);
        persianDate.setGrgYear(calendar.get(Calendar.YEAR));
        return persianDate;
    }

    public static String convertIntToShamsiDate(int Day, int Month, int Year) {
        String month;
        String day;

        if (Month <= 9) {
            month = "0" + Month;
        } else {
            month = String.valueOf(Month);
        }

        if (Day <= 9) {
            day = "0" + Day;
        } else {
            day = String.valueOf(Day);
        }
        return Year + "/" + month + "/" + day;
    }
}

