package cn.plugin.core.widgets;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.internal.pathview.PathsView;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import cn.plugin.core.R;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**新的下拉刷新布局
 * Created by Yang_Yang on 2018/10/16 0016
 */
public class UperRefreshHeader extends RelativeLayout implements com.scwang.smartrefresh.layout.api.RefreshHeader {

    public static String REFRESH_HEADER_PULLDOWN = "下拉可以刷新";
    public static String REFRESH_HEADER_REFRESHING = "正在刷新数据中...";
    public static String REFRESH_HEADER_RELEASE = "释放立即刷新";
    public static String REFRESH_HEADER_FINISH = "刷新完成";
    public static String REFRESH_HEADER_FAILED = "刷新失败";

    private String KEY_LAST_UPDATE_TIME = "LAST_UPDATE_TIME";

    private Date mLastTime;
    private TextView mHeaderText;
    private TextView mLastUpdateText;
    private PathsView mArrowView;
    private ImageView mProgressView;
    private DateFormat mFormat = new SimpleDateFormat("上次更新 M-d HH:mm", Locale.CHINA);
    private SpinnerStyle mSpinnerStyle = SpinnerStyle.Translate;
    private SharedPreferences mShared;
    private AnimationDrawable animaition;

    //<editor-fold desc="RelativeLayout">
    public UperRefreshHeader(Context context) {
        super(context);
        this.initView(context, null, 0);
    }

    public UperRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(context, attrs, 0);
    }

    public UperRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        DensityUtil density = new DensityUtil();

        setMinimumHeight(density.dip2px(80));

        LinearLayout layout = new LinearLayout(context);
        layout.setId(android.R.id.widget_frame);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.setOrientation(LinearLayout.VERTICAL);
        mHeaderText = new TextView(context);
        mHeaderText.setText(REFRESH_HEADER_PULLDOWN);
        mHeaderText.setTextColor(0xff666666);
        mHeaderText.setTextSize(14);

        mLastUpdateText = new TextView(context);
        mLastUpdateText.setTextColor(0xff7c7c7c);
        mLastUpdateText.setTextSize(12);
        mLastUpdateText.setVisibility(GONE);
        LinearLayout.LayoutParams lpHeaderText = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        layout.addView(mHeaderText, lpHeaderText);
        LinearLayout.LayoutParams lpUpdateText = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        layout.addView(mLastUpdateText, lpUpdateText);
        LayoutParams lpHeaderLayout = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpHeaderLayout.addRule(CENTER_IN_PARENT);
        addView(layout,lpHeaderLayout);

        mProgressView = new ImageView(context);
        mProgressView.setBackgroundResource(R.drawable.loading_show);
        animaition = (AnimationDrawable)mProgressView.getBackground();
        LayoutParams lpProgress = new LayoutParams(density.dip2px(20), density.dip2px(20));
        lpProgress.rightMargin = density.dip2px(10);
        lpProgress.addRule(CENTER_VERTICAL);
        lpProgress.addRule(LEFT_OF, android.R.id.widget_frame);
        addView(mProgressView, lpProgress);

        mArrowView = new PathsView(context);
        mArrowView.parserColors(0xff666666);
        mArrowView.parserPaths("M20,12l-1.41,-1.41L13,16.17V4h-2v12.17l-5.58,-5.59L4,12l8,8 8,-8z");
        addView(mArrowView, lpProgress);

        if (isInEditMode()) {
            mArrowView.setVisibility(GONE);
            mHeaderText.setText(REFRESH_HEADER_REFRESHING);
        } else {
            mProgressView.setVisibility(GONE);
        }

        TypedArray ta = context.obtainStyledAttributes(attrs, com.scwang.smartrefresh.layout.R.styleable.ClassicsHeader);

        mSpinnerStyle = SpinnerStyle.values()[ta.getInt(com.scwang.smartrefresh.layout.R.styleable.ClassicsHeader_srlClassicsSpinnerStyle,mSpinnerStyle.ordinal())];

        int primaryColor = ta.getColor(com.scwang.smartrefresh.layout.R.styleable.ClassicsHeader_srlPrimaryColor, 0);
        int accentColor = ta.getColor(com.scwang.smartrefresh.layout.R.styleable.ClassicsHeader_srlAccentColor, 0);
        if (primaryColor != 0) {
            if (accentColor != 0) {
                setPrimaryColors(primaryColor, accentColor);
            } else {
                setPrimaryColors(primaryColor);
            }
        } else if (accentColor != 0) {
            setPrimaryColors(0, accentColor);
        }

        ta.recycle();

        try {//try 不能删除-否则会出现兼容性问题
            if (context instanceof FragmentActivity) {
                FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
                if (manager != null) {
                    List<Fragment> fragments = manager.getFragments();
                    if (fragments != null && fragments.size() > 0) {
                        setLastUpdateTime(new Date());
                        return;
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        KEY_LAST_UPDATE_TIME += context.getClass().getName();
        mShared = context.getSharedPreferences("ClassicsHeader", Context.MODE_PRIVATE);
        setLastUpdateTime(new Date(mShared.getLong(KEY_LAST_UPDATE_TIME, System.currentTimeMillis())));
    }

    //</editor-fold>

    //<editor-fold desc="RefreshHeader">
    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {
    }

    @Override
    public void onPullingDown(float percent, int offset, int headHeight, int extendHeight) {
    }

    @Override
    public void onReleasing(float percent, int offset, int headHeight, int extendHeight) {

    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int headHeight, int extendHeight) {
        animaition.start();
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        animaition.stop();
        mProgressView.setVisibility(GONE);
        if (success) {
            mHeaderText.setText(REFRESH_HEADER_FINISH);
            setLastUpdateTime(new Date());
        } else {
            mHeaderText.setText(REFRESH_HEADER_FAILED);
        }
        return 500;//延迟500毫秒之后再弹回
    }

    @Override
    public void setPrimaryColors(int... colors) {
        if (colors.length > 1) {
            if (!(getBackground() instanceof BitmapDrawable)) {
                setBackgroundColor(colors[0]);
            }
            mArrowView.parserColors(colors[1]);
            mHeaderText.setTextColor(colors[1]);
            mLastUpdateText.setTextColor(colors[1]&0x00ffffff|0x99000000);
        } else if (colors.length > 0) {
            if (!(getBackground() instanceof BitmapDrawable)) {
                setBackgroundColor(colors[0]);
            }
            if (colors[0] == 0xffffffff) {
                mArrowView.parserColors(0xff666666);
                mHeaderText.setTextColor(0xff666666);
                mLastUpdateText.setTextColor(0xff666666&0x00ffffff|0x99000000);
            } else {
                mArrowView.parserColors(0xffffffff);
                mHeaderText.setTextColor(0xffffffff);
                mLastUpdateText.setTextColor(0xaaffffff);
            }
        }
    }

    @NonNull
    public View getView() {
        return this;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return mSpinnerStyle;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case None:
                restoreRefreshLayoutBackground();
            case PullDownToRefresh:
                mHeaderText.setText(REFRESH_HEADER_PULLDOWN);
                mArrowView.setVisibility(VISIBLE);
                mProgressView.setVisibility(GONE);
                mArrowView.animate().rotation(0);
                break;
            case Refreshing:
                mHeaderText.setText(REFRESH_HEADER_REFRESHING);
                mProgressView.setVisibility(VISIBLE);
                mArrowView.setVisibility(GONE);
                break;
            case ReleaseToRefresh:
                mHeaderText.setText(REFRESH_HEADER_RELEASE);
                mArrowView.animate().rotation(180);
                replaceRefreshLayoutBackground(refreshLayout);
                break;
        }
    }
    //</editor-fold>

    //<editor-fold desc="background">
    private Runnable restoreRunable;
    private void restoreRefreshLayoutBackground() {
        if (restoreRunable != null) {
            restoreRunable.run();
            restoreRunable = null;
        }
    }

    private void replaceRefreshLayoutBackground(final RefreshLayout refreshLayout) {
        if (restoreRunable == null && mSpinnerStyle == SpinnerStyle.FixedBehind) {
            restoreRunable = new Runnable() {
                Drawable drawable = refreshLayout.getLayout().getBackground();
                @Override
                public void run() {
                    refreshLayout.getLayout().setBackgroundDrawable(drawable);
                }
            };
            refreshLayout.getLayout().setBackgroundDrawable(getBackground());
        }
    }
    //</editor-fold>

    //<editor-fold desc="API">
    public UperRefreshHeader setLastUpdateTime(Date time) {
        mLastTime = time;
        mLastUpdateText.setText(mFormat.format(time));
        if (mShared != null && !isInEditMode()) {
            mShared.edit().putLong(KEY_LAST_UPDATE_TIME, time.getTime()).apply();
        }
        return this;
    }

    public UperRefreshHeader setTimeFormat(DateFormat format) {
        mFormat = format;
        mLastUpdateText.setText(mFormat.format(mLastTime));
        return this;
    }

    public UperRefreshHeader setSpinnerStyle(SpinnerStyle style) {
        this.mSpinnerStyle = style;
        return this;
    }


}
