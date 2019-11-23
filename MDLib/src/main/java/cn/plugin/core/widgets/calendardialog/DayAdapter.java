package cn.plugin.core.widgets.calendardialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import cn.plugin.core.R;
import cn.plugin.core.listener.OnCalendarItemClickListener;


/**
 * Created by Administrator on 2018/8/14.
 */

public class DayAdapter extends RecyclerView.Adapter {
    private static final String TAG = "DayAdapter";
    private Context context;
    private List<CanlendarBean.DayBean> mDatas;
    private Calendar calendar;
    private int year;
    private int month;
    private int day;

    private OnCalendarItemClickListener onCalendarItemClickListener;

    public DayAdapter(Context context, List<CanlendarBean.DayBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public void setOnCalendarItemClickListener(OnCalendarItemClickListener onCalendarItemClickListener) {
        this.onCalendarItemClickListener = onCalendarItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DayViewHolder(LayoutInflater.from(context).inflate(R.layout.item_day,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DayViewHolder){
            ((DayViewHolder) holder).bindView(position);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null?0:mDatas.size();
    }

    private class DayViewHolder extends RecyclerView.ViewHolder{
        private TextView mTV;
        private ImageView mIV;

        public DayViewHolder(View itemView) {
            super(itemView);
            mTV = itemView.findViewById(R.id.item_day_TV);
            mIV = itemView.findViewById(R.id.item_day_selected);
        }

        private void bindView(final int position){
            final CanlendarBean.DayBean dayBean = mDatas.get(position);
            if (dayBean.getYear() == year){
                if (dayBean.getMonth() == month){
                    if (dayBean.getDay() <= day){
                        dayBean.setEnable(true);
                    }
                }else if (dayBean.getMonth() < month){
                    dayBean.setEnable(true);
                }
            }else if (dayBean.getYear() < year){
                dayBean.setEnable(true);
            }

            mTV.setText(dayBean.getDay()+"");
            if (dayBean.isSelected()){
                mIV.setVisibility(View.VISIBLE);
                mTV.setTextColor(context.getResources().getColor(R.color.white));
            }else {
                mTV.setTextColor(context.getResources().getColor(dayBean.isEnable()?R.color.item_title_TextColor:R.color.hint_textColor));
                mIV.setVisibility(View.GONE);
            }

            mTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dayBean.isEnable()){
                        if (onCalendarItemClickListener != null){
                            onCalendarItemClickListener.onDateClick(dayBean.getYear(),dayBean.getMonth(),dayBean.getDay());
                        }
                    }
                    if (onItemDayClickListener != null){
                        onItemDayClickListener.onItemDayClick(position);
                    }
                }
            });
        }
    }

    private OnItemDayClickListener onItemDayClickListener;

    public void setOnItemDayClickListener(OnItemDayClickListener onItemDayClickListener) {
        this.onItemDayClickListener = onItemDayClickListener;
    }

    public interface OnItemDayClickListener{
        void onItemDayClick(int position);
    }
}
