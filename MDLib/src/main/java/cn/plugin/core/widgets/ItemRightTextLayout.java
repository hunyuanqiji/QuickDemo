package cn.plugin.core.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.plugin.core.R;


/**
 * 左右文字（我的版本号）的条目显示
 * Created by Yang_Yang on 2018/9/27 0027
 */
public class ItemRightTextLayout extends RelativeLayout {
    private TextView tv;
    private TextView tvRight;
    private String title;
    private int rightTextColor;

    public ItemRightTextLayout(Context context) {
        this(context, null);
    }

    public ItemRightTextLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemRightTextLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.item_right_text_layout, this);
        tv = findViewById(R.id.myzone_item_layout_TV_title);
        tvRight = findViewById(R.id.myzone_item_layout_TV_right);
        initAttrs(context, attrs);

        tv.setText(title);
        tvRight.setTextColor(rightTextColor);
    }

    /**
     * 初始化属性
     *
     * @param context 上下文
     * @param attrs   属性
     * @author Went_Gone
     * @time 2017/3/28 13:48
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ItemRightTextLayout);
        title = array.getString(R.styleable.ItemRightTextLayout_title);
        rightTextColor = array.getColor(R.styleable.ItemRightTextLayout_irtl_right_title_color, getResources().getColor(R.color.item_title_TextColor));
        array.recycle();
    }

    /**
     * 给右边设置文字
     *
     * @param str 要设置的文字
     * @author Went_Gone
     * @time 2017/3/28 16:07
     */
    public void setRightText(String str) {
        if (tvRight.getVisibility() == VISIBLE) {
            tvRight.setText(str);
        }
    }

    public void setTitle(String str) {
        if (tv.getVisibility() == VISIBLE) {
            tv.setText(str);
        }
    }

    /**
     * 显示右边对话框
     */
    public void setRightTVShow(boolean isShow) {
        tvRight.setVisibility(isShow ? VISIBLE : GONE);
    }

}
