/**
 * 20150108 add by 梁华山
 * 项目中使用到的日期处理方法
 */
package com.zlm.hello.spring.cloud.alibaba.nacos.provider.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DateUtil {

    public final static String DATE_FORMAT_1001 = "yyyy年MM月dd日 HH:mm";
    public final static String DATE_FORMAT_1002 = "yyyyMMdd";
    public final static String DATE_FORMAT_1003 = "yyyy-MM-dd";
    public final static String DATE_FORMAT_1004 = "yyyyMMddHHmmss";
    public final static String DATE_FORMAT_1005 = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_FORMAT_1006 = "yyyy年MM月dd日";
    public final static String DATE_FORMAT_1007 = "yyyyMMddHHmmssSSS";


    public static final String START_TIME = "000000000";
    public static final String END_TIME = "235959999";
    public static final Long DAY_MILLISECOND  = 60 * 60 * 24 * 1000L;

    /**
     * 获取当前时间 传入所需的时间格式时间
     * ：格式: yyyyMMdd or yyyy-MM-dd yyyyMMddHHmmssSSS 或者yyyyMMdd 或者yyyyMMddHHmmss等
     *
     * @param geshi
     * @return
     */
    public static String getCurrentDate(String geshi) {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(geshi);
        GregorianCalendar calendar = new GregorianCalendar();
        return bartDateFormat.format(calendar.getTime());
    }

    /**
     * 长整型时间戳转日期
     *
     * @param time  20180913114604524
     * @param geshi 为空返回（yyyy-MM-dd HH:mm:ss） 不为空，根据传入的格式返回
     * @return
     */
    public static String getCurrentDate(Long time, String geshi) {
        String date = "";
        if (null == time) {
            return "";
        }
        if (StringUtils.isBlank(geshi)) {
            geshi = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        try {
            date = dateToString(format.parse(time.toString()), geshi);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取当前时间字符串，格式为yyyyMMddHHmmssSSS
     *
     * @return Long类型时间串
     */
    public static Long getCurrentDate1() {
        return Long.valueOf(getCurrentDate("yyyyMMddHHmmssSSS"));
    }

    /**
     * 获取当前时间字符串，格式为yyyyMMddHHmmssSSS
     *
     * @return 字符串类型时间串
     */
    public static String getCurrentDateStr() {
        return getCurrentDate("yyyyMMddHHmmssSSS");
    }

    /**
     * 根据所要的数据格式 和 对日期如 年 或 年月 的前几天或者 后几天
     *
     * @param geshi    所需要的时间格式 (yyyyMMdd, yyyy-MM-dd ,yyyyMM or yyyy ....)
     * @param timeType 所需要时间 （YEAR :1, MONTH:2 ,DAY_OF_MONTH:5,DAY_OF_WEEK:7）
     * @param num      整数
     * @return
     */
    public static String getCurrentDate(String geshi, int timeType, int num) {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(geshi);
        Calendar calendar = Calendar.getInstance();
        //		Log.debug(calendar.YEAR);
        calendar.add(timeType, num);
        return bartDateFormat.format(calendar.getTime());
    }

    /**
     * 比较两个timestamp类型的日期，格式：yyyy-mm-dd hh:mm:ss
     *
     * @param val1
     * @param val2
     * @return
     */
    public static int compareTo(String val1, String val2) {
        String format = "yyyy-mm-dd hh:mm:ss";
        return compareTo(val1, val2, format);
    }

    /**
     * 比较两个日期，
     *
     * @param val1
     * @param val2
     * @param format 格式如：yyyy-mm-dd hh:mm:ss，yyyymmdd,yyyymmddhhmmssSSS....
     * @return
     */
    public static int compareTo(String val1, String val2, String format) {
        SimpleDateFormat TimestampDF = new SimpleDateFormat(format);
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        try {
            Date date1 = TimestampDF.parse(val1);
            calendar1.setTime(date1);
            Date date2 = TimestampDF.parse(val2);
            calendar2.setTime(date2);
        } catch (Exception e) {

        }

        return calendar1.compareTo(calendar2);
    }

    /**
     * 日期时间加减
     *
     * @param dateTime  日期
     * @param formatStr 日期格式:yyyyMMddHHmmssSSS 或者yyyyMMdd 或者yyyyMMddHHmmss等
     * @param timeType  Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND
     * @param timeType  所需要时间 （YEAR :1, MONTH:2 ,DAY_OF_MONTH:5,DAY_OF_WEEK:7）
     * @param amount    要加减的值，加为正，减为负
     * @return 对应部分加减后的字符串, 格式对应 传入dateTime的格式
     */
    public static String dateTimeAdd(String dateTime, String formatStr, int timeType, int amount) {
        try {
            SimpleDateFormat bartDateFormat = new SimpleDateFormat(formatStr);
            Date date = bartDateFormat.parse(dateTime);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(timeType, amount);
            return bartDateFormat.format(calendar.getTime());
        } catch (Exception e) {
            return formatStr;
        }
    }

    /**
     * 根据传入的日期计算出指定日期
     *
     * @param date
     * @param formatStr
     * @param timeType
     * @param amount
     * @return
     */
    public static String dateTimeAdd(Date date, String formatStr, int timeType, int amount) {
        try {
            SimpleDateFormat bartDateFormat = new SimpleDateFormat(formatStr);
            //            Date date = bartDateFormat.parse(dateTime);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(timeType, amount);
            return bartDateFormat.format(calendar.getTime());
        } catch (Exception e) {
            return formatStr;
        }
    }

    /**
     * 增加天数
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date dayAdd(Date date, int amount) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, amount);
        return calendar.getTime();
    }

    /**
     * 增加天数
     *
     * @param date
     * @param amount
     * @return
     */
    public static Long dayAdd(Long date, int amount) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(date));
        calendar.add(Calendar.DAY_OF_MONTH, amount);
        return calendar.getTimeInMillis();
    }

    /**
     * 计算日期相差天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differDay(Date date1, Date date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            date1 = sdf.parse(sdf.format(date1));
            date2 = sdf.parse(sdf.format(date2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));

    }

    /**
     * 计算日期相差天数
     *
     * @param begin
     * @param end
     * @return
     */
    public static Integer differDay(String begin, String end) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date1 = new Date();
        Date date2 = new Date();
        try {
            date1 = sdf.parse(begin);
            date2 = sdf.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));

    }

    /**
     * 日期减法
     *
     * @param dateStr1  时间1字符串
     * @param dateStr2  时间2字符串
     * @param formatStr 时间1及时间2的格式
     * @return dateStr1－dateStr2的间隔秒数
     */
    public static BigDecimal dateSubtract(String dateStr1, String dateStr2, String formatStr) {
        SimpleDateFormat df = new SimpleDateFormat(formatStr);
        try {
            Date d1 = df.parse(dateStr1);
            Date d2 = df.parse(dateStr2);
            //1小时=60分钟=3600秒=3600000
            double mins = 1000L;
            //long day= 24L * 60L * 60L * 1000L;计算天数之差
            BigDecimal bf = new BigDecimal((d1.getTime() - d2.getTime()) / mins);
            return bf.setScale(0, BigDecimal.ROUND_HALF_UP);
        } catch (Exception e) {
            //Log.error(e.toString());
            return new BigDecimal(0);
        }
    }

    /**
     * 字符串日期转timestamp
     *
     * @param ：dt格式必须为 yyyy-MM-dd HH:mm:ss.SSS或者yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long String2Timestamp(String dt) {
        try {
            //			Timestamp ts = new Timestamp(System.currentTimeMillis());  
            Timestamp ts = Timestamp.valueOf(dt);
            return ts.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * timestamp转字符串日期
     *
     * @param geshi     日期格式，如： yyyyMMdd or yyyy-MM-dd
     * @param timestamp
     * @return
     */
    public static String timestamp2String(long timestamp, String geshi) {
        SimpleDateFormat sdf = new SimpleDateFormat(geshi);
        return sdf.format(Long.valueOf(timestamp));
    }

    /**
     * 把字段串形的日期去掉分隔符返回
     *
     * @param date (2005-08-23)
     * @return (20050823)
     */
    public static String stringDateDeleteSign(String date) {
        if (date == null || date.trim().length() < 10)
            return date;
        String rDate = date.substring(0, 4) + date.substring(5, 7) + date.substring(8, 10);
        return rDate;
    }

    /**
     * 把字段串形的日期加上分隔符返回
     *
     * @param date (20050823)
     * @return (2005 - 08 - 23)
     */
    public static String stringDateAddSign(String date) {
        if (date == null || date.trim().length() < 8)
            return date;
        String rDate = String.format("%s-%s-%s", date.substring(0, 4), date.substring(4, 6), date.substring(6, 8));
        return rDate;
    }

    /**
     * 把字段串形的日期时间加上分隔符返回
     *
     * @param date (20050823154123456)
     * @return (2005 - 08 - 23 15 : 41 : 23 : 456)
     */
    public static String stringDateTimeAddSign(String date) {
        if (date == null || date.trim().length() < 4)
            return date;
        String rDate = date.substring(0, 4) + "-" + date.substring(4, 6);
        if (date.trim().length() > 6)
            rDate += "-" + date.substring(6, 8);
        if (date.trim().length() > 8)
            rDate += " " + date.substring(8, 10);
        if (date.trim().length() > 10)
            rDate += ":" + date.substring(10, 12);
        if (date.trim().length() > 12)
            rDate += ":" + date.substring(12, 14);
        if (date.trim().length() > 14)
            rDate += ":" + date.substring(14, 17);
        return rDate;
    }

    public static String formatTime(String time) {
        if (time == null || time.trim().length() < 3)
            return time;
        if (time.trim().length() == 3) {
            return time.substring(0, 1) + ":" + time.substring(1);
        }
        if (time.trim().length() == 4) {
            return time.substring(0, 2) + ":" + time.substring(2);
        }
        return time;
    }

    /**
     * 日期/时间，转换为时间戳
     *
     * @param datetime
     * @param format   格式
     * @return
     */
    public static Long string2Timestamp(String datetime, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern(format);
            Date date = sdf.parse(datetime);
            return Long.valueOf(date.getTime());
        } catch (ParseException pe) {
            return Long.valueOf(0);
        }
    }

    /**
     * 取本周7天的第一天（周一的日期）
     *
     * @param formatStr 返回的日期格式
     * @return
     */
    public static String getNowWeekBegin(String formatStr) {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        int mondayPlus = 1 - dayOfWeek;

        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);

        SimpleDateFormat bartDateFormat = new SimpleDateFormat(formatStr);
        return bartDateFormat.format(currentDate.getTime());

    }

    /**
     * 字符串转日期
     *
     * @param strDate
     * @return
     */
    public static Date convertString2Date(Object strDate) {
        SimpleDateFormat sdf = null;
        Date date = null;
        String tmpValue = String.valueOf(strDate);
        if (null == tmpValue)
            return date;

        if (tmpValue.matches("[0-9]{2,4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}")) {

            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        } else if (tmpValue.matches("[0-9]{2,4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}")) {

            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        } else if (tmpValue.matches("[0-9]{2,4}-[0-9]{1,2}-[0-9]{1,2}")) {

            sdf = new SimpleDateFormat("yyyy-MM-dd");

        } else if (tmpValue.matches("[0-9]{2,4}[0-9]{1,2}[0-9]{1,2}")) {

            sdf = new SimpleDateFormat("yyyyMMdd");
        } else if (tmpValue.matches("[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}")) {

            sdf = new SimpleDateFormat("HH:mm:ss");

        } else if (tmpValue.matches("[0-9]{2,4}[0-9]{1,2}[0-9]{1,2}[0-9]{1,2}[0-9]{1,2}[0-9]{1,2}")) {
            sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        } else if (tmpValue.matches("[0-9]{2,4}/[0-9]{1,2}/[0-9]{1,2}")) {
            sdf = new SimpleDateFormat("yyyy/MM/dd");
        }
        if (sdf == null)
            return null;
        try {
            date = sdf.parse(tmpValue);
        } catch (ParseException e) {

        }
        return date;
    }

    /**
     * 时间戳转成日期字符串
     *
     * @param timeMillis
     * @param formatStr
     * @return
     */
    public static String refFormatNowDate(Long timeMillis, String formatStr) {
        Date nowTime = new Date(timeMillis);
        SimpleDateFormat sdFormatter = new SimpleDateFormat(formatStr);
        String retStrFormatNowDate = sdFormatter.format(nowTime);
        return retStrFormatNowDate;
    }

    /**
     * 将日期类转化成字符串
     *
     * @param date      日期
     * @param dateStyle 转换的格式  yyyyMMdd,  yyyy/MM/dd ....
     * @return 转换后的字符串
     * @throws Exception
     */
    public static String dateToString(Date date, String dateStyle) {
        SimpleDateFormat format = null;
        String dateStr = "";
        try {
            format = new SimpleDateFormat(dateStyle);
            dateStr = format.format(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dateStr;
    }

    /**
     * 取指定时间所在月份最后一天
     *
     * @param dateStyle
     * @return
     */
    public static String getMonthLastDay(Date date, String dateStyle) {
        SimpleDateFormat format = new SimpleDateFormat(dateStyle);
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return format.format(ca.getTime());

    }

    /**
     * 截取日
     * 20150615
     *
     * @param date
     * @return 15
     */
    public static Integer getDay(Integer date) {
        return new Integer(date.toString().substring(6));
    }

    /**
     * 截取月
     *
     * @param date
     * @param date null:当前月份  其他:传入日期月份
     * @return
     */
    public static Integer getMonth(Integer date) {
        if (null == date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            return Integer.parseInt(sdf.format(new Date()));
        }
        return new Integer(date.toString().substring(0, 6));
    }

    /**
     * 当天距离结束的时间
     * @param endAt
     * @return 返回：6天23小时23分
     */
    public static String getDistanceEnd(Long endAt, String dateStyle) {

        SimpleDateFormat sdf = new SimpleDateFormat(dateStyle);
        Long between = null;
        try {
            between = (sdf.parse(endAt.toString()).getTime() - System.currentTimeMillis()) / 1000;//除以1000是为了转换成秒
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long day1 = between / (24 * 3600);
        long hour1 = between % (24 * 3600) / 3600;
        long minute1 = between % 3600 / 60;
        //long second1 = between % 60/60;
        return day1 + "天" + hour1 + "小时" + minute1 + "分";
    }


    /**
     * 两个时间的差值 用小时 天  月 年表示
     *
     * @param createAt
     * @return 返回  几小时  今天   几个月  几年
     */
    public static String getInterval(Long createAt) {

        if (createAt == null) {
            return "";
        }
        // 定义最终返回的结果字符串。
        String interval = null;
        Long currentTi = System.currentTimeMillis();
        long zero = currentTi / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset(); //今天零点
        long yesterdayZero = zero - 24 * 60 * 60 * 1000; //昨天零点
        long millisecond = currentTi - createAt;

        long second = millisecond / 1000;

        if (second <= 0) {
            second = 0;
        }
        if (second == 0) {
            interval = "刚刚";
        } else if (second < 30) {
            interval = second + "秒以前";
        } else if (second >= 30 && second < 60) {
            interval = "半分钟前";
        } else if (second >= 60 && second < 60 * 60) {//大于1分钟 小于1小时
            long minute = second / 60;
            interval = minute + "分钟前";
        } else if (second >= 60 * 60 && second < 60 * 60 * 24) {//大于1小时 小于24小时
            long hour = (second / 60) / 60;
            if (hour <= 3) {
                interval = hour + "小时前";
            } else {
                if (createAt >= zero) {
                    interval = "今天" + refFormatNowDate(createAt, "HH:mm");
                } else {
                    interval = "昨天" + refFormatNowDate(createAt, "HH:mm");
                }
            }
        } else if (second >= 60 * 60 * 24 && second <= 60 * 60 * 24 * 2) {//大于1D 小于2D
            if (createAt >= yesterdayZero) {
                interval = "昨天" + refFormatNowDate(createAt, "HH:mm");
            } else {
                interval = "前天" + refFormatNowDate(createAt, "HH:mm");
            }
        } else if (second >= 60 * 60 * 24 * 2 && second <= 60 * 60 * 24 * 7) {//大于2D小时 小于 7天
            long day = ((second / 60) / 60) / 24;
            interval = day + "天前";
        } else if (second >= 60 * 60 * 24 * 7) {//大于7天小于365天
            String s = refFormatNowDate(createAt, "yyyy");
            String currTime = refFormatNowDate(System.currentTimeMillis(), "yyyy");
            if (StringUtil.equals(currTime, s)) {
                interval = refFormatNowDate(createAt, "MM.dd HH:mm");
            } else {
                interval = refFormatNowDate(createAt, "yyyy.MM.dd");
            }
        } else {
            interval = "0";
        }
        return interval;
    }

    /**
     * 字符串类型时间转long型日期，用户SQL查询where语句后
     *
     * @param time
     * @param endTime 是否是结束时间
     *                false 传入时间为2018-10-08 时会默认拼接00:00:00:000  返回 20181001000000000
     *                true  传入时间为2018-10-01 时会默认拼接23:59:59:999  返回 20181008235959999
     * @return
     */
    public static Long getLongDateByString(String time, boolean endTime) {
        if (null == time) {
            return null;
        }
        String tmpValue = time;
        SimpleDateFormat tmpSdf = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date = null;

        try {
            if (tmpValue.matches("[0-9]{2,4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}")) {
                if (endTime) {
                    tmpValue = tmpValue + ":59999";
                    tmpSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssSSS");
                } else {
                    tmpSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                }
                date = tmpSdf.parse(tmpValue);
            } else if (tmpValue.matches("[0-9]{2,4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}")) {
                if (endTime) {
                    tmpValue = tmpValue + "999";
                    tmpSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssSSS");
                } else {
                    tmpSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                }
                date = tmpSdf.parse(tmpValue);
            } else if (tmpValue.matches("[0-9]{2,4}-[0-9]{1,2}-[0-9]{1,2}")) {
                if (endTime) {
                    tmpValue = tmpValue + " 23:59:59999";
                    tmpSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssSSS");
                } else {
                    tmpSdf = new SimpleDateFormat("yyyy-MM-dd");
                }
                date = tmpSdf.parse(tmpValue);
            } else if (tmpValue.matches("[0-9]{2,4}[0-9]{1,2}[0-9]{1,2}")) {
                if (endTime) {
                    tmpValue = tmpValue + "23:59:59999";
                    tmpSdf = new SimpleDateFormat("yyyyMMddHH:mm:ssSSS");
                } else {
                    tmpSdf = new SimpleDateFormat("yyyyMMdd");
                }
                date = tmpSdf.parse(tmpValue);

            } else if (tmpValue.matches("[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}")) {
                //                tmpSdf = new SimpleDateFormat("HH:mm:ss");

            } else if (tmpValue.matches("[0-9]{2,4}[0-9]{1,2}[0-9]{1,2}[0-9]{1,2}[0-9]{1,2}[0-9]{1,2}")) {
                if (endTime) {
                    tmpValue = tmpValue + "23:59:59999";
                    tmpSdf = new SimpleDateFormat("yyyyMMddHH:mm:ssSSS");
                } else {
                    tmpSdf = new SimpleDateFormat("yyyyMMddHHmmss");
                }
                date = tmpSdf.parse(tmpValue);

            } else if (tmpValue.matches("[0-9]{2,4}/[0-9]{1,2}/[0-9]{1,2}")) {
                if (endTime) {
                    tmpValue = tmpValue + " 23:59:59999";
                    tmpSdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ssSSS");
                } else {
                    tmpSdf = new SimpleDateFormat("yyyy/MM/dd");
                }
                date = tmpSdf.parse(tmpValue);
            }
            if (tmpSdf == null) {
                return null;
            }
            return Long.parseLong(sdf.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取音频的播放时长
     *
     * @param times 毫秒
     * @return 分：秒
     */
    public static String runningTime(String times) {
        if (StringUtil.isEmpty(times)) {
            return "0:0";
        }
        Long time = Long.valueOf(times);
        Long h = time / 3600;
        Long m = (time - h * 3600) / 60;
        Long s = (time - h * 3600) % 60;
        Long m1 = h * 60 + m;
        return m1 + ":" + s;
    }

    /**
     * 获取两个时间差几天
     *
     * @param startTi 开始时间
     * @param endTi   结束时间
     * @return
     */
    public static Long getDayValue(Long startTi, Long endTi) {
        if (null == startTi || null == endTi) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            Date d1 = sdf.parse(String.valueOf(startTi));
            Date d2 = sdf.parse(String.valueOf(endTi));
            long differ_time = d2.getTime() - d1.getTime();
            return Double.valueOf(Math.ceil(differ_time / Double.valueOf(DAY_MILLISECOND))).longValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得当天0点时间
     *
     * @author：gj
     * @date: 2017/3/10
     * @time: 12:29
     **/
    public static Long getTimesmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 获取星期几
     *
     * @author：gj
     * @date: 2017/3/10
     * @time: 14:19
     **/
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 1、本日内的消息显示为：xx：xx
     * <p>
     * 2、昨天的消息显示为：昨天
     * <p>
     * 3、七天的消息显示为：星期一/星期二/星期三/星期四/星期五/星期六/星期日
     * <p>
     * 4、超出一周的消息显示为：年-月-日
     *
     * @param time
     * @return
     */
    public static String getTimePoint1(Long time) {
        if (time == null) {
            return "";
        }
        //现在时间
        Long now = System.currentTimeMillis();
        DateFormat df;
        int day = (60 * 60 * 24) * 1000;
        String pointText = "1970-01-01";

        //时间点比当天零点早
        if (time <= now && time != null) {
            Date date = new Date(time);
            if (time < getTimesmorning()) {
                //比昨天零点晚
                if (time >= getTimesmorning() - day) {
                    pointText = "昨天";
                    return pointText;
                    //比昨天零点早
                } else {
                    //比七天前的零点晚，显示星期几
                    if (time >= getTimesmorning() - 6 * day) {
                        return getWeekOfDate(date);
                    } else {//显示具体日期
                        df = new SimpleDateFormat("yyyy-MM-dd");
                        pointText = df.format(date);
                        return pointText;
                    }
                }
            } else {//无日期时间,当天内具体时间
                df = new SimpleDateFormat("HH:mm");
                pointText = df.format(date);
                return pointText;
            }
        }
        return pointText;
    }

    /**
     * 1、本日内的消息显示为：xx：xx
     * <p>
     * 2、昨天的消息显示为：昨天 xx:xx
     * <p>
     * 3、七天的消息显示为：星期一/星期二/星期三/星期四/星期五/星期六/星期日 xx:xx
     * <p>
     * 4、超出一周的消息显示为：年-月-日 xx:xx
     *
     * @param time
     * @return
     */
    public static String getTimePoint2(Long time) {
        //现在时间
        Long now = System.currentTimeMillis();
        DateFormat df;
        int day = (60 * 60 * 24) * 1000;
        String pointText = "1970-01-01";

        //时间点比当天零点早
        if (time <= now && time != null) {
            Date date = new Date(time);
            df = new SimpleDateFormat("HH:mm");
            String timeStr = df.format(date);
            if (time < getTimesmorning()) {
                //比昨天零点晚
                if (time >= getTimesmorning() - day) {
                    pointText = "昨天" + " " + timeStr;
                    return pointText;
                    //比昨天零点早
                } else {
                    //比七天前的零点晚，显示星期几
                    if (time >= getTimesmorning() - 6 * day) {
                        return getWeekOfDate(date) + " " + timeStr;
                    } else {//显示具体日期
                        df = new SimpleDateFormat("yyyy-MM-dd");
                        pointText = df.format(date);
                        return pointText + " " + timeStr;
                    }
                }
            } else {//无日期时间,当天内具体时间
                return timeStr;
            }
        }
        return pointText;
    }

    /**
     * 获取当前日期的前几天或后几天时间 （基于jdk1.8版本）
     *
     * @param days    0:当前时间； -1:前一天；-2:前两天...； 1:后一天； 2: 后两天....
     * @param pattern 格式
     * @return
     */
    public static String getDifferTime(long days, String pattern) {
        return LocalDateTime.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取指定日期的前几天或后几天时间 （基于jdk1.8版本）
     *
     * @param date
     * @param days    0:当前时间； -1:前一天；-2:前两天...； 1:后一天； 2: 后两天....
     * @param pattern 格式
     * @return
     */
    public static String getDifferTime(Date date, long days, String pattern) {
        return date2LocalDateTime(date).plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 根据当前日期得到前几天的日期集合，包含当天 
     * @param fewDay 几天
     * @param pattern 格式化
     * @return
     */
    public static List<String> getLastFewDays(int fewDay, String pattern) {
        List<String> days = new ArrayList<>();
        for (int i = 0; i < fewDay; i++) {
            days.add(LocalDateTime.now().minusDays(i).format(DateTimeFormatter.ofPattern(pattern)));
        }
        Collections.reverse(days);
        return days;
    }
    

    /**
     * Date转换为LocalDateTime
     *
     * @param date
     * @author Lister
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * 获取当前日期的所在周的周一到周日的日期集合，格式为 yyyyMMdd
     *
     * @return
     * @author Lister
     */
    public static List<Long> nowDateWeeklyRange() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int dayOfWeek = weeklyConvert(calendar.get(Calendar.DAY_OF_WEEK));
        List<Long> dates = new ArrayList<>();
        for (int i = dayOfWeek - 1, day = -1; i > 0; i--, day--) {
            dates.add(Long.valueOf(getDifferTime(day, DateUtil.DATE_FORMAT_1002)));
        }
        Collections.reverse(dates);
        for (int i = dayOfWeek, day = 0; i <= 7; i++, day++) {
            dates.add(Long.valueOf(getDifferTime(day, DateUtil.DATE_FORMAT_1002)));
        }
        return dates;
    }

    /**
     * 将日期初始为最开始的时间
     * @param date 格式yyyyMMddHHmmssSSS
     * @return
     */
    public static Long everyDayStart(String date){
        return Long.valueOf(date.replace(date.substring(8,17),START_TIME));
    }

    /**
     * 将日期初始为最后的时间
     * @param date 格式yyyyMMddHHmmssSSS
     * @return
     */
    public static Long everyDayEnd(String date){
        return Long.valueOf(date.replace(date.substring(8,17),END_TIME));
    }

    /**
     * 星期转换，外国星期转中国
     *
     * @param week
     * @return
     * @author Lister
     */
    public static Integer weeklyConvert(Integer week) {
        Map<Integer, Integer> weekMap = new HashMap<>(7);
        weekMap.put(1, 7);
        weekMap.put(2, 1);
        weekMap.put(3, 2);
        weekMap.put(4, 3);
        weekMap.put(5, 4);
        weekMap.put(6, 5);
        weekMap.put(7, 6);
        week = weekMap.get(week);
        if (week == null) {
            week = 1;
        }
        return week;
    }

    /**
     * 前端时间参数转化为后端时间格式
     * @param timeStr
     * @return
     */
    public static Long timeStr2Long(String timeStr) {
        Date date = DateUtil.convertString2Date(timeStr);
        if (date != null) {
            String time = DateUtil.dateToString(date, "yyyyMMddHHmmssSSS");
            return Long.valueOf(time);
        }
        return null;
    }

    public static void main(String[] args) {
        //System.out.println(getLastFewDays(7, DateUtil.DATE_FORMAT_1002));
//        List<String> collect = DateUtil.getLastFewDays(7, DateUtil.DATE_FORMAT_1002).stream().map(s -> {
//            return s + DateUtil.END_TIME;
//        }).collect(Collectors.toList());
//        System.out.println(collect);
//        long l = System.currentTimeMillis();
//        Long dayValue = getDayValue(20190923163238575L, getCurrentDate1());
//        System.out.println(dayValue);
    }

}
