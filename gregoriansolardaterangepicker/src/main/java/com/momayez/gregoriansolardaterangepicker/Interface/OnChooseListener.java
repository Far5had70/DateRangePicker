package com.momayez.gregoriansolardaterangepicker.Interface;

import java.util.Calendar;

public interface OnChooseListener {
    void date(Calendar calendar, boolean isSolarDate, int year, int month, int day, String fullDate);
    void cancel();
}
