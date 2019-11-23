package cn.plugin.core.widgets.wheelview;

import android.content.Context;
import android.content.DialogInterface;

/**
 * 包含全年的年月显示
 * Created by NJQ on 2018/7/11.
 */
public class FullYearDatePickerBuilder {
    private FullYearDatePicker fullYearDatePicker;

    public FullYearDatePickerBuilder(Context context) {
        fullYearDatePicker = new FullYearDatePicker(context);
        fullYearDatePicker.setOnDateClickSureListener(new OnDateClickSureListener() {
            @Override
            public void onSureClick(String year, String month, String day) {
                if (onDateClickSureListener != null) {
                    onDateClickSureListener.onSureClick(year, month, day);
                }
            }
        });
    }

    public void show() {
        if (fullYearDatePicker != null) {
            if (!fullYearDatePicker.isShowing()) {
                fullYearDatePicker.show();
            }
        }
    }

    public FullYearDatePickerBuilder setStartYear(String startYear) {
        fullYearDatePicker.setStartYear(startYear);
        return this;
    }

    public FullYearDatePickerBuilder setStartMonth(String startMonth) {
        fullYearDatePicker.setStartMonth(startMonth);
        return this;
    }

    public FullYearDatePickerBuilder setEndYear(String endYear) {
        fullYearDatePicker.setEndYear(endYear);
        return this;
    }

    public FullYearDatePickerBuilder setEndMonth(String endMonth) {
        fullYearDatePicker.setEndMonth(endMonth);
        return this;
    }

    public FullYearDatePickerBuilder setSelectedYear(String selectedYear) {
        fullYearDatePicker.setSelectedYear(selectedYear);
        return this;
    }

    public FullYearDatePickerBuilder setSelectedMonth(String selectedMonth) {
        fullYearDatePicker.setSelectedMonth(selectedMonth);
        return this;
    }

    /**
     * 显示时间倒序显示
     * @param isReverseOrder
     * @return
     */
    public FullYearDatePickerBuilder setReverseOrder(boolean isReverseOrder) {
        fullYearDatePicker.setReverseOrder(isReverseOrder);
        return this;
    }

    /**
     * 是否设置截止年份的截止月份
     * @param isEndMonth
     * @return
     */
    public FullYearDatePickerBuilder setIsEndMonth(boolean isEndMonth) {
        fullYearDatePicker.setIsEndMonth(isEndMonth);
        return this;
    }
    private OnDateClickSureListener onDateClickSureListener;

    public FullYearDatePickerBuilder setOnDateClickSureListener(OnDateClickSureListener onDateClickSureListener) {
        this.onDateClickSureListener = onDateClickSureListener;
        return this;
    }

    public FullYearDatePickerBuilder setOnDismissListener(DialogInterface.OnDismissListener listener){
        fullYearDatePicker.setOnDismissListener(listener);
        return this;
    }
}
