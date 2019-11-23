package cn.plugin.core.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.plugin.core.R;

/**
 * Created by Administrator on 2018/6/22.
 */

public class TitleBarLayout extends RelativeLayout {
    private TextView mTVTitle;
    private ImageView mIVComeBack;
    private String title;
    private boolean showComeBack;

    public TitleBarLayout(Context context) {
        this(context,null);
    }

    public TitleBarLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TitleBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.title_bar_layout_all,this);

        mTVTitle = findViewById(R.id.title_bar_layout_TV_Title);
        mIVComeBack = findViewById(R.id.title_bar_layout_IV_come_back);

        initAttrs(context,attrs);

        mTVTitle.setText(TextUtils.isEmpty(title)?"":title);
        mIVComeBack.setVisibility(showComeBack?VISIBLE:GONE);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.TitleBarLayout);
        showComeBack = array.getBoolean(R.styleable.TitleBarLayout_showComeBack,true);
        title = array.getString(R.styleable.TitleBarLayout_title);
        array.recycle();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        mTVTitle.setText(title);
    }

    public ImageView getIVComeBack(){
       if (mIVComeBack == null)
           mIVComeBack = findViewById(R.id.title_bar_layout_IV_come_back);

       return mIVComeBack;
    }
}
