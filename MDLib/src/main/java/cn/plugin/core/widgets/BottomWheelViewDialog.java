package cn.plugin.core.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.lib.WheelView;

import java.util.ArrayList;
import java.util.List;

import cn.plugin.core.R;


/**
 * Created by Went_Gone on 2017/8/23.
 */

public class BottomWheelViewDialog extends Dialog{
    private Context context;
    private List<String> mDatas;
    private TextView mTVcancle,mTVsure,mTVtitle;
    private ItemLabelWheelViewLayout mILWVLayout;
    private WheelView mWheelView;

    public BottomWheelViewDialog(@NonNull Context context) {
        super(context, R.style.MyDailog);
        this.context = context;
    }

    public BottomWheelViewDialog(@NonNull Context context, List<String> mDatas) {
        super(context,R.style.MyDailog);
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_dialog_wheelview_layout);
        initViews();
        setListener();
    }

    private void initViews() {
        mTVcancle = findViewById(R.id.item_dialog_wheelview_layout_TV_cancle);
        mTVtitle = findViewById(R.id.item_dialog_wheelview_layout_TV_title);
        mTVsure = findViewById(R.id.item_dialog_wheelview_layout_TV_sure);

        mILWVLayout = findViewById(R.id.item_dialog_wheelview_layout_ILWVLayout_right);

        mWheelView = mILWVLayout.getWheelView();
    }

    private void setListener() {
        mTVsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomWheelViewCurrentListener!=null){
                    bottomWheelViewCurrentListener.getCurrentItem(mWheelView.getCurrentItem());
                    bottomWheelViewCurrentListener.getCurrentItemStr(mDatas.get(mWheelView.getCurrentItem()));
                }
                if (isShowing()){
                    dismiss();
                }
            }
        });
        mTVcancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setCurrentItem(int currentItem){
        mWheelView.setCurrentItem(currentItem);
    }

    public void setLabel(String label){
        mILWVLayout.setLabel(label);
    }

    public void setTitle(String title){
        mTVtitle.setText(title==null?"":title);
    }

    @Override
    public void show(){
        super.show();

        if (mDatas == null){
            mDatas = new ArrayList<>();
        }

        initWheelView(mWheelView, mDatas);

       setParams(1.0);
    }

    private void setParams(double rote) {
        Window window = this.getWindow() ;
        window.setGravity( Gravity.BOTTOM);
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.widthPixels * rote); // 宽度设置为屏幕的0.65
        window.setAttributes(p);
    }

    private void initWheelView(WheelView wheelView, List<String> mDatas){
        wheelView.setCyclic(false);
        wheelView.setTextColorCenter(context.getResources().getColor(R.color.title_bar));
        wheelView.setTextSize(20);
        wheelView.setDividerColor(context.getResources().getColor(R.color.title_bar));
        if (mDatas==null){
            mDatas = new ArrayList<>();
        }
        wheelView.setAdapter(new ArrayWheelAdapter(mDatas));
    }

    private BottomWheelViewCurrentListener bottomWheelViewCurrentListener;

    public void setBottomWheelViewCurrentListener(BottomWheelViewCurrentListener bottomWheelViewCurrentListener) {
        this.bottomWheelViewCurrentListener = bottomWheelViewCurrentListener;
    }

    public interface BottomWheelViewCurrentListener{
        void getCurrentItemStr(String data);
        void getCurrentItem(int position);
    }
}
