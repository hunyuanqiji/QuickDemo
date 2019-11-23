package cn.plugin.core.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import cn.plugin.core.R;
import cn.plugin.core.listener.OnCheckedChangeListener;

/**
 * 项目名称：v1.1.2
 * 类描述：
 * Created by Went_Gone on 2017/3/28
 */

public class ItemSettingLayout extends RelativeLayout{
    private TextView mTV;
    private ToggleButton mTB;
    private ImageView mIVraw;
    private TextView mTVright;
    private boolean isChecked;
    private boolean showRaw;
    private boolean showRightText;
    private String title;
    private String rightText;
    private float textSize;
    private int textColor;

    public ItemSettingLayout(Context context) {
        this(context,null);
    }

    public ItemSettingLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ItemSettingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.setting_item_layout,this);
        mTV = findViewById(R.id.setting_item_layout_TV_title);
        mTVright = findViewById(R.id.setting_item_layout_TV_right);
        mTB = findViewById(R.id.setting_item_layout_TB);
        mIVraw = findViewById(R.id.setting_item_layout_IV_raw);
        initAttrs(context,attrs);
        if (showRaw){
            mTB.setVisibility(GONE);
            mIVraw.setVisibility(VISIBLE);
        }else {
            mTB.setVisibility(VISIBLE);
            mTB.setChecked(isChecked);
            mIVraw.setVisibility(GONE);
        }
        if (showRightText){
            mTVright.setVisibility(VISIBLE);
            mTVright.setText(rightText);
        }else {
            mTVright.setVisibility(GONE);
        }
        mTV.setText(title);
        mTV.setTextColor(textColor);
        mTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mTB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (onCheckedChangeListener!=null){
                    onCheckedChangeListener.onCheckedChangeListener(compoundButton,b);
                }
            }
        });

    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.ItemSettingLayout);
        title = array.getString(R.styleable.ItemSettingLayout_title);
        isChecked = array.getBoolean(R.styleable.ItemSettingLayout_checked_default,false);
        showRaw = array.getBoolean(R.styleable.ItemSettingLayout_showRaw,false);
        showRightText = array.getBoolean(R.styleable.ItemSettingLayout_show_rightTV,false);
        rightText = array.getString(R.styleable.ItemSettingLayout_rightText);
        textSize = array.getDimensionPixelSize(R.styleable.ItemSettingLayout_isl_title_size, context.getResources().getDimensionPixelSize(R.dimen.text_size_30px));
        textColor = array.getColor(R.styleable.ItemSettingLayout_isl_title_color, context.getResources().getColor(R.color.item_title_TextColor));
        array.recycle();
    }

    public boolean isChecked(){
        return mTB.isChecked() && this.getVisibility() == VISIBLE;
    }

    public void setChecked(boolean isChecked){
        mTB.setChecked(isChecked);
    }

    public void setTitle(String title){
        mTV.setText(title);
    }

    public void setRightText(String text){
        if (showRightText){
            mTVright.setText(text);
        }
    }

    public String getRightText(){
        return mTVright.getText().toString();
    }

    private OnCheckedChangeListener onCheckedChangeListener;

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }
}
