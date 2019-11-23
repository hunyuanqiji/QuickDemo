package cn.plugin.core.utils;

import android.content.ContentResolver;
import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具管理类
 * Created by Went_Gone on 2017/5/17.
 */

public class DateUtil {

    private static final String TAG = DateUtil.class.getSimpleName();

    private static DateUtil util;

    public static DateUtil getInstance(){
        if (util == null){
            synchronized (DateUtil.class){
                if (util == null){
                    util = new DateUtil();
                }
            }
        }
        return util;
    }
    /**
     * 判断时间是否在[startTime, endTime]区间，注意时间格式要一致
     * @param nowTime
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isEffectiveDate(String nowTime, String startTime, String endTime,String dateFormat) {
        DateFormat df = new SimpleDateFormat(dateFormat);

        Date nowDate = null;
        Date startDate = null;
        Date endDate = null;
        try {
            nowDate = df.parse(nowTime);
            startDate = df.parse(startTime);
            endDate = df.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (nowDate.getTime() == startDate.getTime()
                || nowDate.getTime() == endDate.getTime()) {
            return true;
        }


        Calendar date = Calendar.getInstance();
        date.setTime(nowDate);


        Calendar begin = Calendar.getInstance();
        begin.setTime(startDate);


        Calendar end = Calendar.getInstance();
        end.setTime(endDate);


        return date.after(begin) && date.before(end);
    }
    /**
     *获取一个月后的日期
     * @param date 传入的日期
     * @return
     */
    public static String getMonthLater(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, +1);
        calendar.add(Calendar.DATE, -1);
        String monthAgo = simpleDateFormat.format(calendar.getTime());
        return monthAgo;
    }


    /**
     *获取一个月后的日期
     * @param date 传入的日期
     * @return
     */
    public static String getSameMonth (Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        String monthAgo = simpleDateFormat.format(calendar.getTime());
        return monthAgo;
    }
    /**
     * 判断是否周六日
     * @return
     */
    public static boolean isRestDay(Calendar cal){
        boolean restday = false;



        if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY) {
         restday=true;
        }
        return restday;
    }
    /**
     * 判断是否周五六日
     * @return
     */
    public static boolean isRestDays(Calendar cal){
        boolean restday = false;



        if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY||cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY) {
            restday=true;
        }
        return restday;
    }

    /**
     * 是否24小时制
     * @param context
     * @return
     */
    public static boolean is24HourSet(Context context){
        ContentResolver cv = context.getContentResolver();
        String strTimeFormat = android.provider.Settings.System.getString(cv,
                android.provider.Settings.System.TIME_12_24);

        return strTimeFormat.equals("24");
    }

    /**
     * 计算岁数，从当前日期开始算，小于1周显示几天，小于一月显示几周，小于一年显示几个月，大于1年显示几岁
     * @param birthYear
     * @param birthMonth
     * @param birthDay
     * @return
     */
    public static String getAgeFromNow(int birthYear, int birthMonth, int birthDay){
        String age;

        Calendar birthday = new GregorianCalendar(birthYear, birthMonth, birthDay);//2010年10月12日，month从0开始
        Calendar now = Calendar.getInstance();
        int dayOffset = now.get(Calendar.DAY_OF_MONTH) - birthday.get(Calendar.DAY_OF_MONTH);
        int monthOffset = (now.get(Calendar.MONTH) + 1) - birthday.get(Calendar.MONTH);
        int yearOffset = now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
        //按照减法原理，先day相减，不够向month借；然后month相减，不够向year借；最后year相减。
        if (dayOffset < 0) {
            monthOffset -= 1;
            now.add(Calendar.MONTH, -1);//得到上一个月，用来得到上个月的天数。
            dayOffset = dayOffset + now.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        if (monthOffset < 0) {
            monthOffset = (monthOffset + 12) % 12;
            yearOffset--;
        }
//        LogUtil.e(TAG, "年龄：" + yearOffset + "年" + monthOffset + "月" + dayOffset + "天");

        if (yearOffset > 0){
            age = yearOffset + "岁";
        }else {
            if (monthOffset > 0){
                age = monthOffset + "个月";
            }else {
                if (dayOffset > 7){
                    age = dayOffset / 7 + "周";
                }else {
                    age = dayOffset + "天";
                }
            }
        }
        LogUtil.e(TAG, "getAgeFromNow : " + age);

        return age;
    }


    /**
     * 计算岁数，从调查日期开始算，小于1周显示几天，小于一月显示几周，小于一年显示几个月，大于1年显示几岁
     * @return
     */
    public static String getAgeFromSearchDate(String date1, String date2){
        String age;

        Calendar birthday = new GregorianCalendar(Integer.parseInt(date1.split("-")[0]),
                Integer.parseInt(date1.split("-")[1]), Integer.parseInt(date1.split("-")[2]));//2010年10月12日，month从0开始
        Calendar searchDay = new GregorianCalendar(Integer.parseInt(date2.split("-")[0]),
                Integer.parseInt(date2.split("-")[1]), Integer.parseInt(date2.split("-")[2]));
        int dayOffset = searchDay.get(Calendar.DAY_OF_MONTH) - birthday.get(Calendar.DAY_OF_MONTH);
        int monthOffset = searchDay.get(Calendar.MONTH) - birthday.get(Calendar.MONTH);
        int yearOffset = searchDay.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
        //按照减法原理，先day相减，不够向month借；然后month相减，不够向year借；最后year相减。
        if (dayOffset < 0) {
            monthOffset -= 1;
            searchDay.add(Calendar.MONTH, -1);//得到上一个月，用来得到上个月的天数。
            dayOffset = dayOffset + searchDay.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        if (monthOffset < 0) {
            monthOffset = (monthOffset + 12) % 12;
            yearOffset--;
        }

        if (yearOffset > 0){
            age = yearOffset + "岁";
        }else {
            if (monthOffset > 0){
                age = monthOffset + "个月";
            }else {
                if (dayOffset > 7){
                    age = dayOffset / 7 + "周";
                }else {
                    age = dayOffset + "天";
                }
            }
        }
        LogUtil.e(TAG, "getAgeFromNow : " + age);

        return age;
    }

    /**
     * 计算两个日期之前相隔的月数
     * @return
     */
    public static int getMonthSpace(String date1, String date2){
        int month = 0;

        Calendar birthday = new GregorianCalendar(Integer.parseInt(date1.split("-")[0]),
                Integer.parseInt(date1.split("-")[1]), Integer.parseInt(date1.split("-")[2]));//2010年10月12日，month从0开始
        Calendar searchDay = new GregorianCalendar(Integer.parseInt(date2.split("-")[0]),
                Integer.parseInt(date2.split("-")[1]), Integer.parseInt(date2.split("-")[2]));
        int dayOffset = searchDay.get(Calendar.DAY_OF_MONTH) - birthday.get(Calendar.DAY_OF_MONTH);
        int monthOffset = searchDay.get(Calendar.MONTH) - birthday.get(Calendar.MONTH);
        int yearOffset = searchDay.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
        //按照减法原理，先day相减，不够向month借；然后month相减，不够向year借；最后year相减。
        if (dayOffset < 0) {
            monthOffset -= 1;
            searchDay.add(Calendar.MONTH, -1);//得到上一个月，用来得到上个月的天数。
            dayOffset = dayOffset + searchDay.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        if (monthOffset < 0) {
            monthOffset = (monthOffset + 12) % 12;
            yearOffset--;
        }

        if (yearOffset > 0){
            month = yearOffset * 12 + monthOffset;
        }else {
            if (monthOffset > 0){
                month = monthOffset;
            }
        }

        return month;
    }
}
