package cn.plugin.core.widgets.calendardialog;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.plugin.core.R;
import cn.plugin.core.listener.OnCalendarItemClickListener;

/**
 * Created by Administrator on 2018/8/14.
 */

public class CanlendarAdapter extends RecyclerView.Adapter {
    private static final String TAG = "CanlendarAdapter";
    private Context context;
    private List<CanlendarBean> mDatas;
    private CanlendarBean.DayBean currentDay;
    private OnCalendarItemClickListener onCalendarItemClickListener;

    public CanlendarAdapter(Context context, List<CanlendarBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }
    public CanlendarAdapter(Context context, List<CanlendarBean> mDatas, CanlendarBean.DayBean currentDay) {
        this.context = context;
        this.mDatas = mDatas;
        this.currentDay = currentDay;
    }

    public void setOnCalendarItemClickListener(OnCalendarItemClickListener onCalendarItemClickListener) {
        this.onCalendarItemClickListener = onCalendarItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CanlendarViewHolder(LayoutInflater.from(context).inflate(R.layout.item_rv_child,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CanlendarViewHolder){
            ((CanlendarViewHolder) holder).bindView(position);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null?0:mDatas.size();
    }

    private class CanlendarViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView mRV;

        public CanlendarViewHolder(View itemView) {
            super(itemView);
            mRV = itemView.findViewById(R.id.item_rv_child);
        }

        private void bindView(int position){
            final CanlendarBean canlendarBean = mDatas.get(position);
          /*  for (CanlendarBean.DayBean bean
                    :canlendarBean.getDays()) {
                if (bean.getYear() == currentDay.getYear() &&
                        bean.getMonth() == currentDay.getMonth() &&
                        bean.getDay() == currentDay.getDay()){
                    bean.setSelected(true);
                }else {
                    bean.setSelected(false);
                }
            }*/
            mRV.setLayoutManager(new GridLayoutManager(context,7));
            final DayAdapter dayAdapter = new DayAdapter(context,canlendarBean.getDays());
            dayAdapter.setOnCalendarItemClickListener(new OnCalendarItemClickListener() {
                @Override
                public void onDateClick(int year, int month, int day) {
                    if (onCalendarItemClickListener != null){
                        onCalendarItemClickListener.onDateClick(year,month,day);
                    }
                }
            });

            dayAdapter.setOnItemDayClickListener(new DayAdapter.OnItemDayClickListener() {
                @Override
                public void onItemDayClick(int position) {
                    for (CanlendarBean.DayBean bean
                            :canlendarBean.getDays()) {
                        bean.setSelected(false);
                    }
                    canlendarBean.getDays().get(position).setSelected(true);
                }
            });
            mRV.setAdapter(dayAdapter);
        }
    }
}
