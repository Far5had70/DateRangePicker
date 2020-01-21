package com.momayez.gregoriansolardaterangepicker.Interface;

import java.util.Calendar;

public interface OnChoose {
    void fromDate(Calendar calendar , boolean isSolarDate , int year , int month , int day , String fullDate);
    void toDate(Calendar calendar , boolean isSolarDate , int year , int month , int day , String fullDate);
    void cancel();
}
