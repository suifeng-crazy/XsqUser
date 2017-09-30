package com.example.xsq.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.WeakHashMap;

/**
 * Created by S_Y_H on 2016/11/15.23:58
 * 时间工具类
 */

public class DateUtils {

    private static String[] WEEK = new String[]{"日", "一", "二", "三", "四", "五", "六"};

    private static final long ONE_SECOND = 1000;
    private static final long ONE_MINUTE = ONE_SECOND * 60;
    private static final long ONE_HOUR = ONE_MINUTE * 60;
    private static final long ONE_DAY = ONE_HOUR * 24;
    private static WeakHashMap<String, SimpleDateFormat> sFormation = new WeakHashMap<>();

    /**
     * 指定源格式，转换成 目标格式
     *
     * @param data   后台获取的数据
     * @param source 指定源格式
     * @param result 结果格式
     */
    public static String formatData(@NonNull String data, @NonNull String source, @NonNull String result) {
        SimpleDateFormat dateFormatSource = sFormation.get(source);
        if (null == dateFormatSource) {
            dateFormatSource = new SimpleDateFormat(source, Locale.CHINA);
            sFormation.put(source, dateFormatSource);
        }

        if (TextUtils.equals(source, result)) {
            try {
                return dateFormatSource.format(dateFormatSource.parse(data));
            } catch (ParseException e) {
                return "";
            }
        }

        SimpleDateFormat dateFormatResult = sFormation.get(result);
        if (null == dateFormatResult) {
            dateFormatResult = new SimpleDateFormat(result, Locale.CHINA);
            sFormation.put(result, dateFormatResult);
        }

        try {
            return dateFormatResult.format(dateFormatSource.parse(data));
        } catch (ParseException e) {
            return "";
        }
    }

    public static String formatData(Date date, @NonNull String source) {
        SimpleDateFormat dateFormatSource = sFormation.get(source);
        if (null == dateFormatSource) {
            dateFormatSource = new SimpleDateFormat(source, Locale.CHINA);
            sFormation.put(source, dateFormatSource);
        }
        return dateFormatSource.format(date);
    }

    @Nullable
    public static Date getData(String date, String format) {
        if (TextUtils.isEmpty(date)) {
            return null;
        }
        try {
            SimpleDateFormat dateFormatSource = sFormation.get(format);
            if (null == dateFormatSource) {
                dateFormatSource = new SimpleDateFormat(format, Locale.CHINA);
                sFormation.put(format, dateFormatSource);
            }
            return dateFormatSource.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    @NonNull
    public static Date getDataDef(String date, String format, @NonNull Date def) {
        if (TextUtils.isEmpty(date)) {
            return def;
        }
        try {
            SimpleDateFormat dateFormatSource = sFormation.get(format);
            if (null == dateFormatSource) {
                dateFormatSource = new SimpleDateFormat(format, Locale.CHINA);
                sFormation.put(format, dateFormatSource);
            }
            return dateFormatSource.parse(date);
        } catch (Exception e) {
            return def;
        }
    }

    /**
     * String 转换 Date
     *
     * @param str    str
     * @param format format
     * @return Date
     */
    public static Date string2Date(String str, String format) {
        try {
            return new SimpleDateFormat(format, Locale.CHINA).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * String 转 String
     *
     * @param str          需要转换的数据
     * @param format       format 转换的日期格式
     * @param secondFormat secondFormat 时间格式
     * @return String
     */
    public static String string2String(String str, String format, String secondFormat) {
        try {
            return date2String(new SimpleDateFormat(format, Locale.CHINA).parse(str).getTime(), secondFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date().toString();
    }

    /**
     * Date（long） 转换 String
     *
     * @param time   time
     * @param format format
     * @return String
     */
    public static String date2String(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        return sdf.format(time);
    }

    /**
     * long 去除 时分秒
     * 时分秒全部为0
     *
     * @param date date
     * @return long
     */
    public static long getYearMonthDay(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取目标时间和当前时间之间的差距
     *
     * @param date date
     * @return String
     */
    public static String getTimestampString(Date date) {
        Date curDate = new Date();
        long splitTime = curDate.getTime() - date.getTime();
        if (splitTime < (30 * ONE_DAY)) {
            if (splitTime < ONE_MINUTE) {
                return "刚刚";
            }
            if (splitTime < ONE_HOUR) {
                return String.format(Locale.CHINA, "%d分钟前", splitTime / ONE_MINUTE);
            }

            if (splitTime < ONE_DAY) {
                return String.format(Locale.CHINA, "%d小时前", splitTime / ONE_HOUR);
            }

            return String.format(Locale.CHINA, "%d天前", splitTime / ONE_DAY);
        }
        String result;
        result = "M月d日";
        return (new SimpleDateFormat(result, Locale.CHINA)).format(date);
    }

    /**
     * 24小时制 转换 12小时制
     *
     * @param time time
     * @return String
     */
    public static String time24To12(String time) {
        String str[] = time.split(":");
        int h = Integer.valueOf(str[0]);
        int m = Integer.valueOf(str[1]);
        String sx;
        if (h < 1) {
            h = 12;
            sx = "上午";
        } else if (h < 12) {
            sx = "上午";
        } else if (h < 13) {
            sx = "下午";
        } else {
            sx = "下午";
            h -= 12;
        }
        return String.format(Locale.CHINA, "%d:%02d%s", h, m, sx);
    }

    /**
     * Date 转换 HH
     *
     * @param date date
     * @return String
     */
    public static String date2HH(Date date) {
        return new SimpleDateFormat("HH", Locale.CHINA).format(date);
    }

    /**
     * Date 转换 HH:mm:ss
     *
     * @param date date
     * @return String
     */
    public static String date2HHmm(Date date) {
        return new SimpleDateFormat("HH:mm", Locale.CHINA).format(date);
    }

    /**
     * Date 转换 HH:mm:ss
     *
     * @param date date
     * @return String
     */
    public static String date2HHmmss(Date date) {
        return new SimpleDateFormat("HH:mm:ss", Locale.CHINA).format(date);
    }

    /**
     * Date 转换 MM.dd
     *
     * @param date date
     * @return String
     */
    public static String date2MMdd(Date date) {
        return new SimpleDateFormat("MM.dd", Locale.CHINA).format(date);
    }

    /**
     * Date 转换 yyyy.MM.dd
     *
     * @param date date
     * @return String
     */
    public static String date2yyyyMMdd(Date date) {
        return new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA).format(date);
    }

    /**
     * Date 转换 yyyy-MM
     */
    public static String date2yyyyMM(Date date) {
        String t = new SimpleDateFormat("yyyy-MM", Locale.CHINA).format(date);
        String[] split = t.split("-");
        if (split.length == 2) {
            int tp = Integer.parseInt(split[1]);
            ++tp;
            split[1] = String.valueOf(tp);
            if (split[1].length() == 1) {
                split[1] = "0" + split[1];
            }
            if (tp == 13) {
                return (Integer.parseInt(split[0]) + 1) + "-01";
            }
            return split[0] + "-" + split[1];
        }
        return t;
    }

    public static String date2YyyyMMddHHmm(Date date) {
        if (null == date) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).format(date);
    }

    public static String date2YyyyMMddHHmmss(Date date) {
        if (null == date) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(date);
    }

    /**
     * Date 转换 yyyy-MM
     * 获取当前年月
     *
     * @param date
     * @return
     */
    public static String date3yyyyMM(Date date) {
        return new SimpleDateFormat("yyyy-MM", Locale.CHINA).format(date);
    }

    /**
     * Date 转换 MM月dd日 星期
     *
     * @param date date
     * @return String
     */
    public static String date2MMddWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return new SimpleDateFormat("MM月dd日 星期", Locale.CHINA).format(date) + WEEK[dayOfWeek - 1];
    }

    /**
     * Date 转换 yyyy年MM月dd日 星期
     *
     * @param date date
     * @return String
     */
    public static String date2yyyyMMddWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return new SimpleDateFormat("yyyy年MM月dd日 星期", Locale.CHINA).format(date) + WEEK[dayOfWeek - 1];
    }

}
