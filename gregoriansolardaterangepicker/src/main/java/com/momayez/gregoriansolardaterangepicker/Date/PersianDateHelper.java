package com.momayez.gregoriansolardaterangepicker.Date;

public class PersianDateHelper {

    public static int getDayDateTime(String Date) {
        char q7 = Date.charAt(8);
        char q8 = Date.charAt(9);
        int day = Integer.parseInt(""+q7+""+q8);

        return day;
    }

    public static int getMonthDateTime(String Date) {
        char q5 = Date.charAt(5);
        char q6 = Date.charAt(6);

        return Integer.parseInt(""+q5+""+q6);
    }

    public static int getYearDateTime(String Date) {
        char q1 = Date.charAt(0);
        char q2 = Date.charAt(1);
        char q3 = Date.charAt(2);
        char q4 = Date.charAt(3);
        return Integer.parseInt(""+q1+""+q2+""+q3+""+q4);
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

    public static boolean isValidate(boolean mustPast , int day , int month , int year){
        PersianDate persianDate = new PersianDate();
        if (mustPast){
            if (year <= persianDate.getShYear()) {
                if (year == persianDate.getShYear()) {
                    if (month <= persianDate.getShMonth()) {
                        if (month == persianDate.getShMonth()) {
                            if (day <= persianDate.getShDay()) {
                                if (day == persianDate.getShDay()) {
                                    return true;
                                } else if (day < persianDate.getShDay()) {
                                    return true;
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        } else if (month < persianDate.getShMonth()) {
                            return true;
                        }
                    } else {
                        return false;
                    }
                } else if (year < persianDate.getShYear()) {
                    return true;
                }
            } else {
                return false;
            }
        }else {
            if (year >= persianDate.getShYear()) {
                if (year == persianDate.getShYear()) {
                    if (month >= persianDate.getShMonth()) {
                        if (month == persianDate.getShMonth()) {
                            if (day >= persianDate.getShDay()) {
                                if (day == persianDate.getShDay()) {
                                    return true;
                                } else if (day > persianDate.getShDay()) {
                                    return true;
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        } else if (month > persianDate.getShMonth()) {
                            return true;
                        }
                    } else {
                        return false;
                    }
                } else if (year > persianDate.getShYear()) {
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }

}
