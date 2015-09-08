package com.radioline.master.tools;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by master on 05.09.2015.
 */
public class DateTimeLibHelper {
    private static final String format = "yyyy-MM-dd";
    public static final long MILLISECS_PER_DAY = 24 * 60 * 60 * 1000;
    public static final int MILLISECS_PER_SEC = 1000;
    public static final int SECONDS_PER_MINUTE = 60;

    public static Date dateStrToDate(String dateStr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(DateTimeLibHelper.format);
        Date date = format.parse(dateStr);
        return date;
    }

    public static String dateToDateStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DateTimeLibHelper.format);
        String dateStr = format.format(date);
        return dateStr;
    }

    public static int dateDiff(Date sDate, Date eDate) {
        long s = sDate.getTime();
        long e = eDate.getTime();
        long diff = (e - s) / MILLISECS_PER_DAY;
        return (int)diff;
    }

    public static long secDiff(Date sDate, Date eDate) {
        long s = sDate.getTime();
        long e = eDate.getTime();
        long diff = (e - s) / MILLISECS_PER_SEC;
        return (long)diff;
    }

    public static long minDiff(Date sDate, Date eDate) {
        return secDiff(sDate,eDate)  / SECONDS_PER_MINUTE;
    }
}
