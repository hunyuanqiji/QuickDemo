package cn.plugin.core.widgets.wheelview;

import android.content.Context;
import android.content.DialogInterface;

/**
 * 年月日时分 picker
 * Created by NJQ on 2018/7/11.
 */

public class DateTimePickerBuilder {
    private DateTimePicker dateTimePicker;

    public DateTimePickerBuilder(Context context) {
        dateTimePicker = new DateTimePicker(context);
        dateTimePicker.setOnDateClickSureListener(new OnDateTimeClickSureListener() {
            @Override
            public void onSureClick(String year, String month, String day, String hour, String minutes) {
                if (onDateTimeClickSureListener != null) {
                    onDateTimeClickSureListener.onSureClick(year, month, day, hour, minutes);
                }
            }
        });
    }

    public void show() {
        if (dateTimePicker != null) {
            if (!dateTimePicker.isShowing()) {
                dateTimePicker.show();
            }
        }
    }

    public DateTimePickerBuilder setStartYear(String startYear) {
        dateTimePicker.setStartYear(startYear);
        return this;
    }

    public DateTimePickerBuilder setStartMonth(String startMonth) {
        dateTimePicker.setStartMonth(startMonth);
        return this;
    }

    public DateTimePickerBuilder setStartDay(String startDay) {
        dateTimePicker.setStartDay(startDay);
        return this;
    }

    public DateTimePickerBuilder setEndYear(String endYear) {
        dateTimePicker.setEndYear(endYear);
        return this;
    }

    public DateTimePickerBuilder setEndMonth(String endMonth) {
        dateTimePicker.setEndMonth(endMonth);
        return this;
    }

    public DateTimePickerBuilder setEndDay(String endDay) {
        dateTimePicker.setEndDay(endDay);
        return this;
    }

    public DateTimePickerBuilder setSelectedYear(String selectedYear) {
        dateTimePicker.setSelectedYear(selectedYear);
        return this;
    }

    public DateTimePickerBuilder setSelectedMonth(String selectedMonth) {
        dateTimePicker.setSelectedMonth(selectedMonth);
        return this;
    }

    public DateTimePickerBuilder setSelectedDay(String selectedDay) {
        dateTimePicker.setSelectedDay(selectedDay);
        return this;
    }

    public DateTimePickerBuilder setDateShow(boolean isShow) {
        dateTimePicker.setRldate(isShow);
        return this;
    }

    public DateTimePickerBuilder setSelectedHour(String selectedHour) {
        dateTimePicker.setSelectedHour(selectedHour);
        return this;
    }

    public DateTimePickerBuilder setSelectedMinutes(String selectedMinutes) {
        dateTimePicker.setSelectedMinutes(selectedMinutes);
        return this;
    }

    /**
     * 显示时间倒序显示
     * @param isReverseOrder
     * @return
     */
    public DateTimePickerBuilder setReverseOrder(boolean isReverseOrder) {
        dateTimePicker.setReverseOrder(isReverseOrder);
        return this;
    }

    private OnDateTimeClickSureListener onDateTimeClickSureListener;

    public DateTimePickerBuilder setOnDateTimeClickSureListener(OnDateTimeClickSureListener listener) {
        this.onDateTimeClickSureListener = listener;
        return this;
    }

    public DateTimePickerBuilder setOnDismissListener(DialogInterface.OnDismissListener listener){
        dateTimePicker.setOnDismissListener(listener);
        return this;
    }
}
