package cn.plugin.core.widgets.wheelview;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.plugin.core.R;
import cn.plugin.core.widgets.wheelview.util.DateUtils;


/**
 * 包含全年的年月显示
 * Created by NJQ on 2018/7/11.
 */
public class FullYearDatePicker extends Dialog {
    private static final String TAG = "FullYearDatePicker";

    private Context mContext;
    private TextView mTVSure, mTVCancle;
    private WheelView mWheelYear;
    private WheelView mWheelMonth;

    private ArrayList<String> years;
    private ArrayList<String> months;
    private String startYear;
    private String startMonth;

    private String endYear;
    private String endMonth;

    private String selectedYear;
    private String selectedMonth;
    private int yearID;
    private int monthID;
    private boolean isReverseOrder = false;
    private boolean isEndMonth = false;

    public FullYearDatePicker(@NonNull Context context) {
        this(context, R.style.BottomDialogStyle);
    }

    public FullYearDatePicker(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 获取Window的LayoutParams
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = ViewGroup.LayoutParams.MATCH_PARENT;
        attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        // 一定要重新设置, 才能生效
        window.setAttributes(attributes);
        this.mContext = context;
        setContentView(R.layout.dialog_fullyear_date);
        initView();

        setListener();
    }

    private void setListener() {
        mTVSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String year = "";
                String month = "";
                String day = "";
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

                if (onDateClickSureListener != null) {
                    if (year.endsWith("年")){
                        year = year.replaceAll("年", "");
                    }
                    if (month.endsWith("月")){
                        month = month.replaceAll("月", "");
                    }
                    onDateClickSureListener.onSureClick(year, month, day);
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

        initYears();
    }

    private void initView() {
        mTVSure = findViewById(R.id.dialog_custom_date_layout_TV_Sure);
        mTVCancle = findViewById(R.id.dialog_custom_date_layout_TV_Cancle);
        mWheelYear = findViewById(R.id.WheelView_Year);
        mWheelMonth = findViewById(R.id.WheelView_Month);

        mWheelYear.setCyclic(false);
        mWheelMonth.setCyclic(false);
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
        for (int i = 0; i < yearList.size(); i++){
            yearList.set(i, yearList.get(i) + "年");
        }
        years.addAll(yearList);
    }

    private ArrayList<String> initMonths() {
        ArrayList<String> months = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            months.add((i + 1) + "");
        }
        if (!TextUtils.isEmpty(startMonth)) {
            if (!TextUtils.isEmpty(startYear)) {
                String selectedText = mWheelYear.getSelectedText().replaceAll("年", "");
                if (selectedText.equals(startYear) || TextUtils.isEmpty(selectedText)) {
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
                String selectedText = mWheelYear.getSelectedText();
                String wheelYearString = "";
                if (TextUtils.isEmpty(selectedText) && years.size() > 0) {
                    wheelYearString = years.get(mWheelYear.getDefaultIndex());
                } else {
                    wheelYearString = selectedText;
                }
                wheelYearString = wheelYearString.replaceAll("年", "");
                // TODO endYear与实际结束年份相差2
                if (wheelYearString.equals(String.valueOf(Integer.parseInt(endYear) + 2))) {
                    List<String> removes = new ArrayList<>();
                    if (!isEndMonth) {//是否需要截取最后月份
                        for (int i = months.indexOf(endMonth) + 1; i < months.size(); i++) {
                            removes.add(months.get(i));
                        }
                    } else {
                        if (years.size() == 1){
                            // 只有一年特殊处理，月份已经倒序
                            for (int i = months.indexOf(endMonth) + 1; i < months.size(); i++) {
                                removes.add(months.get(i));
                            }
                        }else {
                            for (int i = months.indexOf(endMonth) - 1; i >= 0; i--) {
                                removes.add(months.get(i));
                            }
                        }
                    }
                    months.removeAll(removes);
                }
            }
        }
        if (!TextUtils.isEmpty(mWheelYear.getSelectedText()) && isReverseOrder) {
            // 如果第一个月份是1，需要反转列表，否则不反转
            if (months.size() > 1 && Integer.parseInt(months.get(0)) < Integer.parseInt(months.get(months.size() - 1))) {
                Collections.reverse(months);
            }
        }
        for (int i = 0; i < months.size(); i++){
            months.set(i, months.get(i) + "月");
        }
        months.add(0, "全年");
        return months;
    }

    public void setStartYear(String year) {
        this.startYear = year;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public void setSelectedYear(String selectedYear) {
        this.selectedYear = selectedYear;
    }

    public void setSelectedMonth(String selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    /**
     * 时间是否倒序显示
     *
     * @param isReverseOrder
     */
    public void setReverseOrder(boolean isReverseOrder) {
        this.isReverseOrder = isReverseOrder;
    }

    /**
     * 是否设置截止年份的截止月份
     *
     * @param isEndMonth
     */
    public void setIsEndMonth(boolean isEndMonth) {
        this.isEndMonth = isEndMonth;
    }

    private OnDateClickSureListener onDateClickSureListener;

    public void setOnDateClickSureListener(OnDateClickSureListener onDateClickSureListener) {
        this.onDateClickSureListener = onDateClickSureListener;
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

        mWheelYear.setData(years);
        mWheelYear.setDefault(years.contains(selectedYear) ? years.indexOf(selectedYear) : 0);
        mWheelYear.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                mWheelMonth.refreshData(initMonths());
            }

            @Override
            public void selecting(int id, String text) {
                yearID = id;
            }
        });

        mWheelMonth.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {

            }

            @Override
            public void selecting(int id, String text) {
                monthID = id;
            }
        });

        months.addAll(initMonths());
        mWheelMonth.setData(months);
        mWheelMonth.setDefault(months.contains(selectedMonth) ? months.indexOf(selectedMonth) : 0);
    }
}
