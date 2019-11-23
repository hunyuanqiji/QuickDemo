package cn.plugin.core.listener;

/**
 * Created by Administrator on 2018/8/14.
 */

public interface OnCalendarItemClickListener {
    /**
     * 选择日期
     * @param year
     * @param month
     * @param day
     */
    void onDateClick(int year, int month, int day);
}
