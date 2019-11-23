package cn.plugin.core.widgets.wheelview;

import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Administrator on 2018/7/11.
 */

public class CustomDatePickerBuilder {
    private CustomDatePicker customDatePicker;

    public CustomDatePickerBuilder(Context context) {
        customDatePicker = new CustomDatePicker(context);
        customDatePicker.setOnDateClickSureListener(new OnDateClickSureListener() {
            @Override
            public void onSureClick(String year, String month, String day) {
                if (onDateClickSureListener != null) {
                    onDateClickSureListener.onSureClick(year, month, day);
                }
            }
        });
    }

    public void show() {
        if (customDatePicker != null) {
            if (!customDatePicker.isShowing()) {
                customDatePicker.show();
            }
        }
    }

    public CustomDatePickerBuilder setStartYear(String startYear) {
        customDatePicker.setStartYear(startYear);
        return this;
    }

    public CustomDatePickerBuilder setStartMonth(String startMonth) {
        customDatePicker.setStartMonth(startMonth);
        return this;
    }

    public CustomDatePickerBuilder setStartDay(String startDay) {
        customDatePicker.setStartDay(startDay);
        return this;
    }

    public CustomDatePickerBuilder setEndYear(String endYear) {
        customDatePicker.setEndYear(endYear);
        return this;
    }

    public CustomDatePickerBuilder setEndMonth(String endMonth) {
        customDatePicker.setEndMonth(endMonth);
        return this;
    }

    public CustomDatePickerBuilder setEndDay(String endDay) {
        customDatePicker.setEndDay(endDay);
        return this;
    }

    public CustomDatePickerBuilder setSelectedYear(String selectedYear) {
        customDatePicker.setSelectedYear(selectedYear);
        return this;
    }

    public CustomDatePickerBuilder setSelectedMonth(String selectedMonth) {
        customDatePicker.setSelectedMonth(selectedMonth);
        return this;
    }

    public CustomDatePickerBuilder setSelectedDay(String selectedDay) {
        customDatePicker.setSelectedDay(selectedDay);
        return this;
    }

    public CustomDatePickerBuilder setDateShow(boolean isShow) {
        customDatePicker.setRldate(isShow);
        return this;
    }

    /**
     * 显示时间倒序显示
     * @param isReverseOrder
     * @return
     */
    public CustomDatePickerBuilder setReverseOrder(boolean isReverseOrder) {
        customDatePicker.setReverseOrder(isReverseOrder);
        return this;
    }

    /**
     * 是否设置截止年份的截止月份
     * @param isEndMonth
     * @return
     */
    public CustomDatePickerBuilder setIsEndMonth(boolean isEndMonth) {
        customDatePicker.setIsEndMonth(isEndMonth);
        return this;
    }
    private OnDateClickSureListener onDateClickSureListener;

    public CustomDatePickerBuilder setOnDateClickSureListener(OnDateClickSureListener onDateClickSureListener) {
        this.onDateClickSureListener = onDateClickSureListener;
        return this;
    }

    public CustomDatePickerBuilder setOnDismissListener(DialogInterface.OnDismissListener listener){
        customDatePicker.setOnDismissListener(listener);
        return this;
    }
}
