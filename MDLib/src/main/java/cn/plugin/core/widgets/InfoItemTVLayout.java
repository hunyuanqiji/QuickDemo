package cn.plugin.core.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.util.DensityUtil;

import cn.plugin.core.R;

/**
 * 项目名称：v1.1.2
 * 类描述：带文字展示的条目
 * Created by Went_Gone on 2017/3/29
 */

public class InfoItemTVLayout extends RelativeLayout {
    private TextView mTVtitle, mTVcontent, mTVimportent;
    private ImageView rightArrow;
    private String hintStr, title;
    private float titleSize, contentSize;
    private boolean isDefault, importent, isShowRighIamge;
    private int titleColor;
    private int contentColor;

    public InfoItemTVLayout(Context context) {
        this(context, null);
    }

    public InfoItemTVLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InfoItemTVLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.info_item_tv_layout, this);
        mTVtitle = findViewById(R.id.info_item_tv_layout_TV_title);
        mTVimportent = findViewById(R.id.info_item_tv_layout_TV_import);
        mTVcontent = findViewById(R.id.info_item_tv_layout_TV_content);
        rightArrow = findViewById(R.id.info_item_tv_layout_IV_right_raw);
        initAttrs(context, attrs);
        mTVimportent.setVisibility(importent ? VISIBLE : GONE);
        rightArrow.setVisibility(isShowRighIamge ? GONE : VISIBLE);//右边箭头（默认显示的）
        mTVtitle.setText(title);
        titleSize = DensityUtil.px2dp(titleSize);
        mTVtitle.setTextSize(titleSize);
        contentSize = DensityUtil.px2dp(contentSize);
        mTVcontent.setTextSize(contentSize);
        if (hintStr != null) {
            mTVcontent.setText(hintStr);
            mTVcontent.setTextColor(getResources().getColor(R.color.hint_textColor));
        }

        mTVtitle.setTextColor(titleColor);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.InfoItemTVLayout);
        title = array.getString(R.styleable.InfoItemTVLayout_title);
        hintStr = array.getString(R.styleable.InfoItemTVLayout_hint_Str);
        importent = array.getBoolean(R.styleable.InfoItemTVLayout_importent, false);
        isShowRighIamge = array.getBoolean(R.styleable.InfoItemTVLayout_showRightImage, false);
        titleSize = array.getDimension(R.styleable.InfoItemTVLayout_titleSize, sp2px(15));
        contentSize = array.getDimension(R.styleable.InfoItemTVLayout_contentSize, sp2px(15));
        titleColor = array.getColor(R.styleable.InfoItemTVLayout_title_color, getResources().getColor(R.color.item_title_TextColor));
        contentColor = array.getColor(R.styleable.InfoItemTVLayout_content_color, getResources().getColor(R.color.item_title_TextColor));
        array.recycle();
    }

    public void setContent(String content, boolean isDefault) {
        mTVcontent.setText(content);
        if (content.equals(hintStr) || isDefault) {
            mTVcontent.setTextColor(getResources().getColor(R.color.hint_textColor));
        } else {
            mTVcontent.setTextColor(contentColor);
            //            mTVcontent.setTextColor(getResources().getColor(R.color.item_title_TextColor));
        }
    }

    public void setContent(String content) {
        if (content == null) {
            content = hintStr;
        }
        mTVcontent.setText(content);
        if (content.equals(hintStr) || isDefault) {
            mTVcontent.setTextColor(getResources().getColor(R.color.hint_textColor));
        } else {
            mTVcontent.setTextColor(contentColor);
            //            mTVcontent.setTextColor(getResources().getColor(R.color.item_title_TextColor));
        }

        if (content.equals("")) {
            mTVcontent.setTextColor(getResources().getColor(R.color.hint_textColor));
            mTVcontent.setText(hintStr);
        }
    }

    public String getContent() {
        if (mTVcontent.getText().equals(hintStr)) {
            return "";
        } else {
            return mTVcontent.getText().toString().trim();
        }
    }

    /**
     * sp转px
     *
     * @param spValue sp值
     * @return px值
     */
    public int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public void setRightTVGravity(int gravity) {
        mTVcontent.setGravity(gravity);
    }

    public void setTitleColorAndContentColor(int titleColor, int contentColor) {
        mTVcontent.setTextColor(getResources().getColor(contentColor));
        mTVtitle.setTextColor(getResources().getColor(titleColor));
    }

    public String getTitle() {
        return mTVtitle.getText().toString();
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTVtitle.setText(title);
        }
    }


    public void setRightArrowVisible(int visible) {
        rightArrow.setVisibility(visible);
    }
}
