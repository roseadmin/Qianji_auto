package cn.dreamn.qianji_auto.utils.runUtils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.annotations.NonNull;

/**
 * 格式化时间 工具类
 */
public class DateUtils {


    public static String getTime(String s) {
        return getTime(s, 0);
    }

    public static String getTime(String s, int day) {
        long time = System.currentTimeMillis();
        time = time + (long) day * 24 * 60 * 60 * 1000;
        return (new SimpleDateFormat(s, Locale.getDefault())).format(new Date(time));
    }


    public static String getShortTime(long date, String format) {

        return (new SimpleDateFormat(format, Locale.getDefault())).format(new Date(date));
    }

    public static String getTime(String s, long time) {
        return (new SimpleDateFormat(s, Locale.getDefault())).format(new Date(time));
    }

    /*
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String time, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date date = simpleDateFormat.parse(time);
            assert date != null;
            return date.getTime();
        } catch (Throwable e) {
            return 0;
        }
    }

    public static String stampToDate(long time, String format) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        Date date = new Date(time);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String stampToDate(String time, String format) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        Date date = new Date(Long.parseLong(time));
        res = simpleDateFormat.format(date);
        return res;
    }

    public static long getAnyTime(@NonNull String time) {
        long t;
        try {
            if (time.contains("undefined")) {
                throw new Throwable("not useful date");
            }
            if ((time.length() == 10 || time.length() == 13) && !time.contains(" ")) {
                //数字类型的时间戳
                t = Long.parseLong(time);
            } else if (time.contains(":")) {
                String[] t2 = time.split(":");
                String format = "HH:mm";
                if (t2.length != 1) {
                    format = "HH:mm:ss";
                }
                //yyyy-MM-dd HH:mm:ss
                if (time.contains(" ") && time.contains("-")) {
                    format = "yyyy-MM-dd " + format;
                }
                t = dateToStamp(time, format);
            } else {
                throw new Throwable("not useful date");
            }
        } catch (Throwable e) {
            t = dateToStamp(getTime("yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
        }

        return t;
    }

}
