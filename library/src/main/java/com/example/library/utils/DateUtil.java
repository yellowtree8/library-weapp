package com.example.library.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil
{
    public static Date millisecondToDate(Long milliSecond) {
        Date date = new Date();
        date.setTime(milliSecond);
        return date;
    }

    public static int isPM() {
        GregorianCalendar ca = new GregorianCalendar();
        System.out.println(ca.get(GregorianCalendar.AM_PM));
        return ca.get(GregorianCalendar.AM_PM);
    }

    public static Date day(Date date) {
        LocalDate localDate=date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return java.sql.Date.valueOf(localDate);
    }
}
