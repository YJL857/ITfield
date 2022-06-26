package com.jinliang.unit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author yejinliang
 * @create 2022-06-26 18:09
 */
public class timeUnit {
    //普通时间转为UTC
    public static String localToUTC(String localTimeStr) {
        try {
            Date localDate = getLocalSDF().parse(localTimeStr);
            return getUTCSDF().format(localDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    //UTC转为普通时间
    public static String utcToLocal(String utcTimeStr) {
        try {
            Date date = getUTCSDF().parse(utcTimeStr);
            return getLocalSDF().format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static SimpleDateFormat getLocalSDF() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    private static SimpleDateFormat getUTCSDF() {
        SimpleDateFormat utcSDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        utcSDF.setTimeZone(TimeZone.getTimeZone("UTC"));
        return utcSDF;
    }
}
