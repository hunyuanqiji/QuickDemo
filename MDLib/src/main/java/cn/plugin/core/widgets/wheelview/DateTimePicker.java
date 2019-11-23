package cn.plugin.core.widgets.wheelview;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.plugin.core.R;
import cn.plugin.core.widgets.wheelview.util.DateUtils;


/**
 * 年月日时分控件
 * Created by NJQ on 2018/7/11.
 */
public class DateTimePicker extends Dialog {
    private static final String TAG = "DateTimePicker";

    private WheelView mWheelYear;
    private WheelView mWheelMonth;
    private WheelView mWheelDay;
    private WheelView mWheelHour;
    private WheelView mWheelMinutes;
    private ArrayList<String> years;
    private ArrayList<String> months;
    private ArrayList<String> days;
    private ArrayList<String> hours;
    private ArrayList<String> minutes;
    private String startYear;
    private String startMonth;
    private String startDay;

    private String endYear;
    private String endMonth;
    private String endDay;

    private Context mCpntext;
    private String selectedYear;
    private String selectedMonth;
    private String selectedDay;
    private String selectedHour;
    private String selectedMinutes;
    private TextView mTVSure, mTVCancle;
    private RelativeLayout mRLdate, mRLdates, mRLyear;
    private int yearID;
    private int monthID;
    private int dayID;
    private boolean isReverseOrder = false;

    public DateTimePicker(@NonNull Context context) {
        this(context, R.style.BottomDialogStyle);
    }

    public DateTimePicker(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 获取Window的LayoutParams
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = ViewGroup.LayoutParams.MATCH_PARENT;
        attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        // 一定要重新设置, 才能生效
        window.setAttributes(attributes);
        this.mCpntext = context;
        setContentView(R.layout.dialog_datetime_picker);
        initView();

        setListener();

        long timeMillis = System.currentTimeMillis();
        SimpleDateFormat dateFormatYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MM");
        SimpleDateFormat dateFormatDay = new SimpleDateFormat("dd");

        /*if (TextUtils.isEmpty(selectedYear)){
            selectedYear = dateFormatYear.format(new Date(timeMillis));
        }
        if (TextUtils.isEmpty(selectedMonth)){
            selectedMonth = Integer.parseInt(dateFormatMonth.format(new Date(timeMillis)))+"";
        }
        if (TextUtils.isEmpty(selectedDay)){
            selectedDay = Integer.parseInt(dateFormatDay.format(new Date(timeMillis)))+"";
        }*/
    }

    private void setListener() {
        mTVSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String year = "";
                String month = "";
                String day = "";
                String hour = "";
                String minutes = "";
                if (TextUtils.isEmpty(mWheelYear.getSelectedText())) {
                    year = mWheelYear.getListSize() > yearID ? mWheelYear.getItemText(yearID) : "";
                } else {
                    year = mWheelYear.getSelectedText();
                }

                if (TextUtils.isEmpty(mWheelMonth.getSelectedText())) {
                    month = mWheelMonth.getListSize() > monthID ? mWheelMonth.getItemText(monthID) : "";
                } else {
                    month = mWheelMonth.getSelectedText();
                }

                if (TextUtils.isEmpty(mWheelDay.getSelectedText())) {
                    day = mWheelDay.getListSize() > dayID ? mWheelDay.getItemText(dayID) : "";
                } else {
                    day = mWheelDay.getSelectedText();
                }

                if (!TextUtils.isEmpty(mWheelHour.getSelectedText())) {
                    hour = mWheelHour.getSelectedText();
                }

                if (!TextUtils.isEmpty(mWheelMinutes.getSelectedText())) {
                    minutes = mWheelMinutes.getSelectedText();
                }

                if (onDateTimeClickSureListener != null) {
                    onDateTimeClickSureListener.onSureClick(year, month, day, hour, minutes);
                }
                if (isShowing()) {
                    dismiss();
                }
            }
        });

        mTVCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void initData() {
        years = new ArrayList<>();
        months = new ArrayList<>();
        days = new ArrayList<>();
        hours = new ArrayList<>();
        minutes = new ArrayList<>();

        initYears();
    }

    private void initView() {
        mTVSure = findViewById(R.id.dialog_custom_date_layout_TV_Sure);
        mTVCancle = findViewById(R.id.dialog_custom_date_layout_TV_Cancle);
        mRLdate = findViewById(R.id.rl_date);
        mRLdates = findViewById(R.id.rl_dates);
        mRLyear = findViewById(R.id.rl_yeear);

        mWheelYear = findViewById(R.id.WheelView_Year);
        mWheelMonth = findViewById(R.id.WheelView_Month);
        mWheelDay = findViewById(R.id.WheelView_Day);
        mWheelHour = findViewById(R.id.WheelView_Hour);
        mWheelMinutes = findViewById(R.id.WheelView_Minutes);

        mWheelYear.setCyclic(false);
        mWheelMonth.setCyclic(false);
        mWheelDay.setCyclic(false);
        mWheelHour.setCyclic(false);
        mWheelMinutes.setCyclic(false);

    }

    private void initYears() {
        int startYearInt;
        int endYearInt;
        ArrayList<String> yearList = new ArrayList<>();
        if (isReverseOrder) {
            startYearInt = 2018;
            endYearInt = 1960;
        } else {
            startYearInt = 2000;
            endYearInt = 2050;
        }

        if (!TextUtils.isEmpty(startYear) && !TextUtils.isEmpty(endYear)) {
            startYearInt = Integer.parseInt(startYear);
            endYearInt = Integer.parseInt(endYear);
            endYearInt += 1;
        }

        if (isReverseOrder) {
            for (int i = 0; i < startYearInt - endYearInt; i++) {
                yearList.add((startYearInt - i) + "");
            }

            if (!TextUtils.isEmpty(endYear)) {
                List<String> removes = new ArrayList<>();
                for (int i = 0; i < yearList.indexOf(endYear); i++) {
                    removes.add(yearList.get(i));
                }
                yearList.removeAll(removes);
            }
        } else {
            for (int i = 0; i < endYearInt - startYearInt; i++) {
                yearList.add((i + startYearInt) + "");
            }

            if (!TextUtils.isEmpty(startYear)) {
                List<String> removes = new ArrayList<>();
                for (int i = 0; i < yearList.indexOf(startYear); i++) {
                    removes.add(yearList.get(i));
                }
                yearList.removeAll(removes);
            }
        }

        /*if (!TextUtils.isEmpty(endYear)){
            List<String> removes = new ArrayList<>();

            Log.e(TAG, "initYears: 截止="+ yearList.indexOf(endYear)+1);
            for (int i = yearList.indexOf(endYear)+1; i < yearList.size(); i++) {
                removes.add(yearList.get(i));
                Log.e(TAG, "initYears: 测试:"+yearList.get(i));
            }
            yearList.removeAll(removes);
        }*/

        years.addAll(yearList);
    }

    private ArrayList<String> initMonths() {
        ArrayList<String> months = new ArrayList<>();
/*        if(isReverseOrder){
            for (int i = 12; i >0; i--) {
                months.add(i + "");
            }

        }else{
            for (int i = 0; i < 12; i++) {
                months.add((i + 1) + "");
            }

        }*/
        for (int i = 0; i < 12; i++) {
            months.add((i + 1) + "");
        }
        if (!TextUtils.isEmpty(startMonth)) {
            if (!TextUtils.isEmpty(startYear)) {
                if (mWheelYear.getSelectedText().equals(startYear) || TextUtils.isEmpty(mWheelYear.getSelectedText())) {
                    List<String> removes = new ArrayList<>();

                    if (isReverseOrder) {
                        for (int i = 0; i <= months.indexOf(startMonth); i++) {
                            removes.add(months.get(i));
                        }
                        Collections.reverse(removes);
                        months.clear();
                        months.addAll(removes);
                    } else {
                        for (int i = 0; i < months.indexOf(startMonth); i++) {
                            removes.add(months.get(i));
                        }
                        months.removeAll(removes);
                    }

                }
            }
        }

        if (!TextUtils.isEmpty(endMonth)) {
            if (!TextUtils.isEmpty(endYear)) {
                String wheelYearString = "";
                if (TextUtils.isEmpty(mWheelYear.getSelectedText()) && years.size() > 0) {
                    wheelYearString = years.get(mWheelYear.getDefaultIndex());
                } else {
                    wheelYearString = mWheelYear.getSelectedText();
                }
                if (wheelYearString.equals(endYear)) {
                    List<String> removes = new ArrayList<>();
                    for (int i = months.indexOf(endMonth) + 1; i < months.size(); i++) {
                        removes.add(months.get(i));
                    }
                    months.removeAll(removes);
                }
            }
        }
        if (!TextUtils.isEmpty(mWheelYear.getSelectedText()) && isReverseOrder) {
            Collections.reverse(months);
        }
        return months;
    }

    private ArrayList<String> initHours() {
        ArrayList<String> hours = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            hours.add((i + 1) + "");
        }
        if (isReverseOrder) {
            Collections.reverse(hours);
        }
        return hours;
    }

    private ArrayList<String> initMinutes() {
        ArrayList<String> minutes = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            minutes.add((i + 1) + "");
        }
        if (isReverseOrder) {
            Collections.reverse(minutes);
        }
        return minutes;
    }

    private ArrayList<String> changeDayData(int selectedYear, int selectedMonth) {
        ArrayList<String> days = new ArrayList<>();
        int maxDays = DateUtils.calculateDaysInMonth(selectedYear, selectedMonth);
        for (int i = 0; i < maxDays; i++) {
            days.add((i + 1) + "");
        }

//        List<String> removes = new ArrayList<>();
//        if (isReverseOrder) {
//            for (int i = 0; i < maxDays; i++) {
//                removes.add(days.get(i));
//            }
//            Collections.reverse(removes);
//            days.clear();
//            days.addAll(removes);
//        }

        if (!TextUtils.isEmpty(startDay)) {
            if (!TextUtils.isEmpty(startMonth)) {
                if (!TextUtils.isEmpty(startYear)) {
                    if ((TextUtils.isEmpty(mWheelYear.getSelectedText()) && TextUtils.isEmpty(mWheelMonth.getSelectedText()))
                            || (mWheelYear.getSelectedText().equals(startYear) && mWheelMonth.getSelectedText().equals(startMonth))) {//判断是否是选中当前日期，如果控件要求显示今日以前或者以后的日期时候，对立面数据要清除（如：今日3号，正序的话，1号与2号，不能出现在显示集合中
                        List<String> removes = new ArrayList<>();
                        if (isReverseOrder) {
                            for (int i = 0; i <= days.indexOf(startDay); i++) {
                                removes.add(days.get(i));
                            }
                            Collections.reverse(removes);
                            days.clear();
                            days.addAll(removes);
                        } else {
                            for (int i = 0; i < days.indexOf(startDay); i++) {
                                removes.add(days.get(i));
                            }
                            days.removeAll(removes);
                        }

                    } else if (!TextUtils.isEmpty(selectedYear + "")) {
                        List<String> removes = new ArrayList<>();
                        for (int m = 0; m < maxDays; m++) {
                            removes.add(days.get(m));
                        }
                        if (isReverseOrder) {
                            Collections.reverse(removes);
                        }
                        days.clear();
                        days.addAll(removes);
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(endDay)) {//滑动到截止日期后，清空后面数据，不让其显示
            if (!TextUtils.isEmpty(endMonth)) {
                if (!TextUtils.isEmpty(endYear)) {
                    if (mWheelYear.getSelectedText().equals(endYear) && mWheelMonth.getSelectedText().equals(endMonth)) {
                        List<String> removes = new ArrayList<>();
                        for (int i = days.indexOf(endDay) + 1; i < days.size(); i++) {
                            removes.add(days.get(i));
                        }
                        days.removeAll(removes);
                    }
                }
            }
        }
        return days;
    }

    public void setStartYear(String year) {
        this.startYear = year;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public void setSelectedYear(String selectedYear) {
        this.selectedYear = selectedYear;
    }

    public void setSelectedMonth(String selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    public void setSelectedDay(String selectedDay) {
        this.selectedDay = selectedDay;
    }

    /**
     * 时间是否倒序显示
     *
     * @param isReverseOrder
     */
    public void setReverseOrder(boolean isReverseOrder) {
        this.isReverseOrder = isReverseOrder;
    }

    public void setSelectedHour(String selectedHour) {
        this.selectedHour = selectedHour;
    }

    public void setSelectedMinutes(String selectedMinutes) {
        this.selectedMinutes = selectedMinutes;
    }

    /**
     * 是否隐藏日（保留年月）
     *
     * @param isShow
     */
    public void setRldate(Boolean isShow) {
        mRLdate.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mRLdates.setVisibility(isShow ? View.GONE : View.VISIBLE);
        mRLyear.setVisibility(isShow ? View.GONE : View.VISIBLE);
    }

    private OnDateTimeClickSureListener onDateTimeClickSureListener;

    public void setOnDateClickSureListener(OnDateTimeClickSureListener listener) {
        this.onDateTimeClickSureListener = listener;
    }

    @Override
    public void show() {
        super.show();
        initData();

        if (TextUtils.isEmpty(selectedYear)) {
            selectedYear = startYear;
        }
        if (TextUtils.isEmpty(selectedMonth)) {
            selectedMonth = startMonth;
        }
        if (TextUtils.isEmpty(selectedDay)) {
            selectedDay = startDay;
        }

        mWheelYear.setData(years);
        mWheelYear.setDefault(years.contains(selectedYear) ? years.indexOf(selectedYear) : 0);
        mWheelYear.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                mWheelMonth.refreshData(initMonths());
//                mWheelMonth.setDefault(0);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!TextUtils.isEmpty(mWheelYear.getSelectedText()) && !TextUtils.isEmpty(mWheelMonth.getSelectedText())) {
                            mWheelDay.refreshData(changeDayData(Integer.parseInt(mWheelYear.getSelectedText()), Integer.parseInt(mWheelMonth.getSelectedText())));
//                            mWheelDay.setDefault(0);
                        }
                    }
                }, 100);
            }

            @Override
            public void selecting(int id, String text) {
                yearID = id;
            }
        });

        final int maxDays = DateUtils.calculateDaysInMonth(Integer.parseInt(selectedYear), Integer.parseInt(selectedMonth));
        mWheelMonth.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                if (!TextUtils.isEmpty(mWheelYear.getSelectedText())) {
                    mWheelDay.refreshData(changeDayData(Integer.parseInt(mWheelYear.getSelectedText()), Integer.parseInt(text)));
                    mWheelDay.setDefault(isReverseOrder?maxDays:0);
                }
            }

            @Override
            public void selecting(int id, String text) {
                monthID = id;
            }
        });

        months.addAll(initMonths());
        mWheelMonth.setData(months);
        mWheelMonth.setDefault(months.contains(selectedMonth) ? months.indexOf(selectedMonth) : 0);

        mWheelDay.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {

            }

            @Override
            public void selecting(int id, String text) {
                dayID = id;
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                days = changeDayData(Integer.parseInt(selectedYear), Integer.parseInt(selectedMonth));
                mWheelDay.setData(days);
                mWheelDay.setDefault(days.contains(selectedDay) ? days.indexOf(selectedDay) : 0);
            }
        }, 10);

        hours.addAll(initHours());
        mWheelHour.setData(hours);
        mWheelHour.setDefault(hours.contains(selectedHour) ? hours.indexOf(selectedHour) : 0);

        minutes.addAll(initMinutes());
        mWheelMinutes.setData(minutes);
        mWheelMinutes.setDefault(minutes.contains(selectedMinutes) ? minutes.indexOf(selectedMinutes) : 0);
    }

}
