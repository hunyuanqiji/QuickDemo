package cn.plugin.core.widgets.calendardialog;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/8/14.
 */

public class CanlendarBean {
    private int year;
    private int month;
    private List<DayBean> days;

    public static class DayBean implements Serializable{
        private int year;
        private int month;
        private int day;
        private boolean isSelected;
        private boolean isEnable;

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public boolean isEnable() {
            return isEnable;
        }

        public void setEnable(boolean enable) {
            isEnable = enable;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<DayBean> getDays() {
        return days;
    }

    public void setDays(List<DayBean> days) {
        this.days = days;
    }
}
