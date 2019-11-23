package cn.plugin.core.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.lib.WheelView;

import cn.plugin.core.R;


/**
 * Created by Went_Gone on 2017/8/24.
 */

public class ItemLabelWheelViewLayout extends RelativeLayout {
    private WheelView mWheelView;
    private TextView mTVlabel;
    private boolean showLabel;
    private String labelStr;

    public ItemLabelWheelViewLayout(Context context) {
        this(context,null);
    }

    public ItemLabelWheelViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ItemLabelWheelViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.item_label_wheelview_layout,this);
        mWheelView = findViewById(R.id.item_label_wheelview_WV);
        mTVlabel = findViewById(R.id.item_label_wheelview_TV);
        initAttrs(context,attrs);
        mTVlabel.setVisibility(showLabel?VISIBLE:GONE);
        if (showLabel && !TextUtils.isEmpty(labelStr)){
            mTVlabel.setText(labelStr);
        }
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.ItemLabelWheelViewLayout);
        showLabel = array.getBoolean(R.styleable.ItemLabelWheelViewLayout_showLabel,true);
        labelStr = array.getString(R.styleable.ItemLabelWheelViewLayout_itemlabel);
        array.recycle();
    }

    public WheelView getWheelView() {
        return mWheelView;
    }

    public TextView getTVlabel() {
        return mTVlabel;
    }

    public String getLabel(){
        return mTVlabel.getText().toString();
    }

    public void setLabel(String label){
        mTVlabel.setText(label);
    }
}
