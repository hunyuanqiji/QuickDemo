package cn.plugin.core.utils;


import android.annotation.SuppressLint;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.plugin.core.widgets.calendardialog.CanlendarBean;
import cn.plugin.core.widgets.wheelview.util.DateUtils;

/**
 * Created by Administrator on 2018/8/7.
 */

public class CalendarUtil {
    private static final String TAG = "CalendarUtil";
    private static CalendarUtil util;
    public static CalendarUtil getInstance(){
        if (util == null){
            synchronized (CalendarUtil.class){
                if (util == null){
                    util = new CalendarUtil();
                }
            }
        }
        return util;
    }

    @SuppressLint("SimpleDateFormat")
    public static int getDate(String formatStr, Date date) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return Integer.parseInt(format.format(date));
    }
    public int weekOfDay(int year,int month,int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month-1,day);
        int week = calendar.get(Calendar.DAY_OF_WEEK)-1;
        return week;
    }

    public List<String> days(int year,int month,int day){
        List<String> days = new ArrayList<>();

        int preYear = year;
        int preMonth = month -1;

        int nextYear = year;
        int nextMonth = month+1;

        if (month==1){
            preYear = year-1;
            preMonth = 12;
        }
        if (month == 12){
            nextYear = year+1;
            nextMonth = 1;
        }

        int preMonthDayCount = DateUtils.calculateDaysInMonth(preYear, preMonth);
        int weekOfDay1 = weekOfDay(year, month, 1);

        for (int i = preMonthDayCount-weekOfDay1+1; i <= preMonthDayCount; i++) {
            days.add(i+"");
        }
        /*for (int i = preMonthDayCount; i >preMonthDayCount-weekOfDay1 ; i--) {
            days.add((i)+"");
        }*/

        int nowMonthDayCount = DateUtils.calculateDaysInMonth(year, month);

        for (int i = 1; i <= nowMonthDayCount; i++) {
            days.add((i)+"");
        }

        int nextMonthDayCount = DateUtils.calculateDaysInMonth(nextYear, nextMonth);

        int daysSize = days.size();

        for (int i = 1; i <= 42-daysSize; i++) {
            days.add((i)+"");
        }

        return days;
    }


    public List<Integer> daysInt(int year,int month,int day){
        List<String> days = days(year, month, day);
        List<Integer> daysInt = new ArrayList<>();
        for (String dayString:
             days) {
            daysInt.add(Integer.parseInt(dayString));
        }
        return daysInt;
    }

    public CanlendarBean getCalendarByDate(int year, int month){
        List<String> days = new ArrayList<>();
        CanlendarBean bean = new CanlendarBean();
        bean.setYear(year);
        bean.setMonth(month);
        List<CanlendarBean.DayBean> dayBeans = new ArrayList<>();
        CanlendarBean.DayBean dayBean = null;

        int preYear = year;
        int preMonth = month -1;

        int nextYear = year;
        int nextMonth = month+1;

        if (month==1){
            preYear = year-1;
            preMonth = 12;
        }
        if (month == 12){
            nextYear = year+1;
            nextMonth = 1;
        }

        int preMonthDayCount = DateUtils.calculateDaysInMonth(preYear, preMonth);
        int weekOfDay1 = weekOfDay(year, month, 1);

        for (int i = preMonthDayCount-weekOfDay1+1; i <= preMonthDayCount; i++) {
            days.add(i+"");
            dayBean = new CanlendarBean.DayBean();
            dayBean.setYear(preYear);
            dayBean.setMonth(preMonth);
            dayBean.setDay(i);
            dayBeans.add(dayBean);
        }

        int nowMonthDayCount = DateUtils.calculateDaysInMonth(year, month);

        for (int i = 1; i <= nowMonthDayCount; i++) {
            days.add((i)+"");
            dayBean = new CanlendarBean.DayBean();
            dayBean.setYear(year);
            dayBean.setMonth(month);
            dayBean.setDay(i);
            dayBeans.add(dayBean);
        }

        int daysSize = days.size();

        for (int i = 1; i <= 42-daysSize; i++) {
            days.add((i)+"");
            dayBean = new CanlendarBean.DayBean();
            dayBean.setYear(nextYear);
            dayBean.setMonth(nextMonth);
            dayBean.setDay(i);
            dayBeans.add(dayBean);
        }

       /* for (String day :
                days) {
            dayBean = new CanlendarBean.DayBean();
            dayBean.setDay(Integer.parseInt(day));
            dayBeans.add(dayBean);
        }*/
        bean.setDays(dayBeans);
        Log.e(TAG, "getCalendarByDate: "+dayBeans.size() );
        return bean;
    }

    public List<CanlendarBean> getCalendars3ByCurrent(int year, int month){
        List<CanlendarBean> beans = new ArrayList<>();
        int preYear = year;
        int preMonth = month-1;
        int nextYear = year;
        int nextMonth = month+1;
        if (month == 12){
            nextMonth = 1;
            nextYear = year+1;
        }
        if (month == 1){
            preMonth = 12;
            preYear = year - 1;
        }

        CanlendarBean calendarPre = getCalendarByDate(preYear, preMonth);
        CanlendarBean calendarNow = getCalendarByDate(year, month);
        CanlendarBean calendarNext = getCalendarByDate(nextYear, nextMonth);
        beans.add(calendarPre);
        beans.add(calendarNow);
        beans.add(calendarNext);
        return beans;
    }

    public void clear(){
        util = null;
    }

    /**
     * 判断两个日期的大小
     * @param nowDate
     * @param selectDate
     * @return
     */
    public static boolean compare_date(String nowDate, String selectDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(nowDate);
            Date dt2 = df.parse(selectDate);
            if (dt1.getTime() >dt2.getTime()) {
                return false;
            } else if (dt1.getTime() <=dt2.getTime()) {
                return true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
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


}
