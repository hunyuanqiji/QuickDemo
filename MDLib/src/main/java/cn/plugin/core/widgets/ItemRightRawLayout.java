package cn.plugin.core.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.plugin.core.R;


/**
 * 项目名称：v1.1.2
 * 类描述：带右箭头的条目组合控件
 * Created by Went_Gone on 2017/3/28
 */

public class ItemRightRawLayout extends RelativeLayout {
    private ImageView iv;
    private TextView tv;
    private TextView tvRight;
    private ImageView ivRight;
    private int imgRes;
    private int rightImgRes;
    private String title;
    private String rightText;
    private boolean showRight;
    private boolean showIcon;
    private boolean showRithtImageView;
    private boolean showRedPoint = false;

    public ItemRightRawLayout(Context context) {
        this(context,null);
    }

    public ItemRightRawLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ItemRightRawLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.myzone_item_layout,this);
        iv = findViewById(R.id.myzone_item_layout_IV_icon);
        tv = findViewById(R.id.myzone_item_layout_TV_title);
        tvRight = findViewById(R.id.myzone_item_layout_TV_right);
        ivRight = findViewById(R.id.myzone_item_layout_IV_right_raw);
        initAttrs(context,attrs);
        if (showIcon){
            iv.setVisibility(VISIBLE);
            iv.setImageResource(imgRes);
        }else {
            iv.setVisibility(GONE);
        }
        if(showRithtImageView){
            ivRight.setVisibility(VISIBLE);
        }else{
            ivRight.setVisibility(GONE);
        }
        tv.setText(title);
        if (showRight){
            tvRight.setVisibility(VISIBLE);
            tvRight.setText(rightText);
        }else {
            tvRight.setVisibility(GONE);
        }
        ivRight.setImageResource(rightImgRes);
       setShowRedPoint(showRedPoint);
    }

    /**
     *初始化属性
     *@author Went_Gone
     *@time 2017/3/28 13:48
     *@param context 上下文
     *@param attrs 属性
    */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.ItemRightRawLayout);
        imgRes = array.getResourceId(R.styleable.ItemRightRawLayout_item_icon,R.color.title_bar);
        title = array.getString(R.styleable.ItemRightRawLayout_title);
        showRight = array.getBoolean(R.styleable.ItemRightRawLayout_show_rightTV,false);
        rightText = array.getString(R.styleable.ItemRightRawLayout_rightText);
        showIcon = array.getBoolean(R.styleable.ItemRightRawLayout_showIcon,true);
        showRithtImageView = array.getBoolean(R.styleable.ItemRightRawLayout_irrl_showRightImageview,true);
        showRedPoint = array.getBoolean(R.styleable.ItemRightRawLayout_showRedPoint,false);
        rightImgRes = array.getResourceId(R.styleable.ItemRightRawLayout_rightImg,R.drawable.right_raw);
        array.recycle();
    }

    /**
     *给右边设置文字
     *@author Went_Gone
     *@time 2017/3/28 16:07
     *@param str 要设置的文字
    */
    public void setRightText(String str){
        if (tvRight.getVisibility() == VISIBLE){
            tvRight.setText(str);
        }
    }
    public void setTitle(String str){
        if (tv.getVisibility() == VISIBLE){
            tv.setText(str);
        }
    }

    public void setTextColor(){
        if (tvRight.getVisibility() == VISIBLE){
            tvRight.setTextColor(getResources().getColor(R.color.hint_textColor));
        }
    }

    /**
     * 展示右边的点
     * @param showRedPoint
     */
    public void setShowRedPoint(boolean showRedPoint) {
        if (showRedPoint){
            tvRight.setVisibility(VISIBLE);
            tvRight.setBackgroundResource(R.drawable.red_point_circle_background);
            tvRight.setTextSize(11);
            tvRight.setPadding(6,0,6,0);
            tvRight.setTextColor(Color.WHITE);
            tvRight.setGravity(Gravity.CENTER);
        }else {
            tvRight.setVisibility(GONE);
        }
    }

    /**
     * 显示右边对话框
     */
    public void setRightTVShow(boolean isShow){
        tvRight.setVisibility(isShow?VISIBLE:GONE);
    }

}
