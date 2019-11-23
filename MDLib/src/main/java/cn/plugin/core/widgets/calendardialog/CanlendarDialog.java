package cn.plugin.core.widgets.calendardialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.plugin.core.R;
import cn.plugin.core.listener.OnCalendarItemClickListener;
import cn.plugin.core.utils.CalendarUtil;

import static cn.plugin.core.widgets.calendardialog.ViewPagerLayoutManager.HORIZONTAL;


/**
 * Created by asmin on 2017/4/7.
 */

public class CanlendarDialog extends Dialog implements View.OnClickListener{
    private static final String TAG = "TipsDialog";
    private Context context;
    private RecyclerView mRV;
    private int year;
    private int month;
    private int day;
    private List<CanlendarBean> mCanlendars;
    private CanlendarAdapter mAdapter;
    private TextView mTVDate;
    private ImageView mIVPreMonth;
    private ImageView mIVPreYear;
    private ImageView mIVNextMonth;
    private ImageView mIVNextYear;
    private CanlendarBean.DayBean currentDay;
    private OnCalendarItemClickListener onCalendarItemClickListener;

    public CanlendarDialog(Context context) {
        super(context, R.style.NoDialogTitleView);
        this.context = context;
        setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        mCanlendars = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.canlendar_dialog_layout);

        long l = System.currentTimeMillis();
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
        SimpleDateFormat formatDay = new SimpleDateFormat("dd");

        if (year == 0){
            year = Integer.parseInt(formatYear.format(new Date(l)));
        }

        if (month == 0){
            month = Integer.parseInt(formatMonth.format(new Date(l)));
        }

        if (day == 0){
            day = Integer.parseInt(formatDay.format(new Date(l)));
        }


        currentDay = new CanlendarBean.DayBean();
        currentDay.setYear(year);
        currentDay.setMonth(month);
        currentDay.setDay(day);


        initViews();
        mCanlendars.addAll(CalendarUtil.getInstance().getCalendars3ByCurrent(year,month));
        mTVDate.setText(mCanlendars.get(1).getYear()+"-"+(mCanlendars.get(1).getMonth()<10?("0"+mCanlendars.get(1).getMonth()):mCanlendars.get(1).getMonth()+""));

        CanlendarBean canlendarBean = mCanlendars.get(1);
        for (int i = 0; i < canlendarBean.getDays().size(); i++) {
            CanlendarBean.DayBean dayBean = canlendarBean.getDays().get(i);
            if (dayBean.getYear() == year && dayBean.getMonth() == month && dayBean.getDay() == day){
                dayBean.setSelected(true);
            }
        }

        setListener();
    }

    @Override
    public void show() {
        super.show();
        setParams(0.75);
//        setParams(0.5);
    }

    public void setOnCalendarItemClickListener(OnCalendarItemClickListener onCalendarItemClickListener) {
        this.onCalendarItemClickListener = onCalendarItemClickListener;
    }

    private void setParams(double rote) {
        Window window = this.getWindow() ;
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.widthPixels * rote); // 宽度设置为屏幕的0.65
        window.setAttributes(p);
    }

    private void initViews() {
        mRV = findViewById(R.id.canlendar_RV);
        mTVDate = findViewById(R.id.canlendar_TV_Date);
        mIVPreYear = findViewById(R.id.canlendar_IV_PreYear);
        mIVPreMonth = findViewById(R.id.canlendar_IV_PreMonth);
        mIVNextMonth = findViewById(R.id.canlendar_IV_NextMonth);
        mIVNextYear = findViewById(R.id.canlendar_IV_NextYear);
    }

    private void setListener() {
        ScaleLayoutManager ScaleLayoutManager = new ScaleLayoutManager(context, 0, HORIZONTAL);
        mRV.setLayoutManager(ScaleLayoutManager);
        mRV.setNestedScrollingEnabled(false);
        mAdapter = new CanlendarAdapter(context,mCanlendars,currentDay);
        mRV.setAdapter(mAdapter);
        CenterSnapHelper centerSnapHelper = new CenterSnapHelper();
        centerSnapHelper.attachToRecyclerView(mRV);
        ScaleLayoutManager.setOnPageChangeListener(new ViewPagerLayoutManager.OnPageChangeListener() {
            @Override
            public void onPageSelected(final int position) {
                if (position != 1){
                    CanlendarBean canlendarBean = mCanlendars.get(position);
                    mTVDate.setText(canlendarBean.getYear()+"-"+(canlendarBean.getMonth()<10?("0"+canlendarBean.getMonth()):canlendarBean.getMonth()+""));
                    mCanlendars.clear();
                    mCanlendars.addAll(CalendarUtil.getInstance().getCalendars3ByCurrent(canlendarBean.getYear(),canlendarBean.getMonth()));

                    for (int i = 0; i < mCanlendars.get(1).getDays().size(); i++) {
                        CanlendarBean.DayBean dayBean = mCanlendars.get(1).getDays().get(i);
                        if (dayBean.getYear() == year && dayBean.getMonth() == month && dayBean.getDay() == day){
                            dayBean.setSelected(true);
                        }
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mRV.scrollToPosition(1);
                            mAdapter.notifyDataSetChanged();
                        }
                    },20);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mRV.scrollToPosition(1);

        mIVPreYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CanlendarBean canlendarBean = mCanlendars.get(1);
                getCanlendarScrollToPosition(canlendarBean.getYear()-1,canlendarBean.getMonth());
            }
        });

        mIVNextYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CanlendarBean canlendarBean = mCanlendars.get(1);
                getCanlendarScrollToPosition(canlendarBean.getYear()+1,canlendarBean.getMonth());
            }
        });

        mIVPreMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CanlendarBean canlendarBean = mCanlendars.get(1);
                int yearPre = canlendarBean.getYear();
                int monthPre = canlendarBean.getMonth()-1;
                if (canlendarBean.getMonth() == 1){
                    monthPre = 12;
                    yearPre = yearPre-1;
                }
                getCanlendarScrollToPosition(yearPre,monthPre);
            }
        });

        mIVNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CanlendarBean canlendarBean = mCanlendars.get(1);
                int yearNext = canlendarBean.getYear();
                int monthNext = canlendarBean.getMonth()+1;
                if (canlendarBean.getMonth() == 12){
                    monthNext = 1;
                    yearNext = yearNext+1;
                }
                getCanlendarScrollToPosition(yearNext,monthNext);
            }
        });

        mAdapter.setOnCalendarItemClickListener(new OnCalendarItemClickListener() {
            @Override
            public void onDateClick(int year, int month, int day) {

                currentDay.setYear(year);
                currentDay.setMonth(month);
                currentDay.setDay(day);
                CanlendarDialog.this.year = year;
                CanlendarDialog.this.month = month;
                CanlendarDialog.this.day = day;

                Log.e(TAG, "onDateClick: "+year+month+day );

                if (onCalendarItemClickListener != null){
                    onCalendarItemClickListener.onDateClick(year,month,day);
                }
                mAdapter.notifyDataSetChanged();
//                mAdapter.notifyItemChanged(1);
            }
        });
    }

    private void getCanlendarScrollToPosition(int year,int month) {
        mCanlendars.clear();

        mCanlendars.addAll(CalendarUtil.getInstance().getCalendars3ByCurrent(year,month));

        for (int i = 0; i < mCanlendars.get(1).getDays().size(); i++) {
            CanlendarBean.DayBean dayBean = mCanlendars.get(1).getDays().get(i);
            if (dayBean.getYear() == this.year && dayBean.getMonth() == this.month && dayBean.getDay() == day){
                dayBean.setSelected(true);
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRV.scrollToPosition(1);
                mTVDate.setText(mCanlendars.get(1).getYear()+"-"+(mCanlendars.get(1).getMonth()<10?("0"+mCanlendars.get(1).getMonth()):mCanlendars.get(1).getMonth()+""));
                mAdapter.notifyDataSetChanged();
            }
        },20);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            this.dismiss();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
        }
    }


    public int getCurrentViewIndex() {
        int firstVisibleItem = ((LinearLayoutManager)mRV.getLayoutManager()).findFirstVisibleItemPosition();
        int lastVisibleItem = ((LinearLayoutManager)mRV.getLayoutManager()).findLastVisibleItemPosition();
        int currentIndex = firstVisibleItem;
        int lastHeight = 0;
        for (int i = firstVisibleItem; i <= lastVisibleItem; i++) {
            View view = mRV.getLayoutManager().getChildAt(i - firstVisibleItem);
            if (null == view) {
                continue;
            }
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            Rect localRect = new Rect();
            view.getLocalVisibleRect(localRect);
            int showHeight = localRect.bottom - localRect.top;
            if (showHeight > lastHeight) {
                currentIndex = i;
                lastHeight = showHeight;
            }
        }

        if (currentIndex < 0) {
            currentIndex = 0;
        }
        return currentIndex;
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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}