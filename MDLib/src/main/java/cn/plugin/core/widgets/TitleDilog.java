package cn.plugin.core.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.util.DensityUtil;

import cn.plugin.core.R;


/**
 * Created by Yang_Yang on 2018/12/29 0029
 */
public class TitleDilog extends Dialog implements View.OnClickListener {
    private static final String TAG = "TitleDilog";
    private TextView mTVtips, mTVtip, mTVsubmit, mTVcancle;
    private Context context;
    private ImageView mIVshow;
    private String hint = "";
    private boolean mCancelable = true;

    public TitleDilog(Context context) {
        super(context, R.style.NoDialogTitleView);
        this.context = context;
        setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_title);
        initViews();
        setListener();
    }

    @Override
    public void show() {
        super.show();
        setParams(0.75);
    }

    public void show(String tips) {
        super.show();
        mTVtips.setText(tips);
        setParams(0.75);
    }


    public void show(SpannableString tips) {
        super.show();
        mTVtips.setText(tips);
        setParams(0.75);
    }

    /**
     * 中间提示文字显示位置
     */
   public void  showTextPosition(){
       mTVtip.setGravity(Gravity.LEFT|Gravity.CENTER);
   }

    private void setParams(double rote) {
        Window window = this.getWindow();
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.widthPixels * rote); // 宽度设置为屏幕的0.65
        window.setAttributes(p);
    }

    private void initViews() {
        mTVtips = findViewById(R.id.tips_dialog_layout_TV_tips);
        mTVtip = findViewById(R.id.tips_dialog_layout_TV_tip);
        mTVsubmit = findViewById(R.id.tips_dialog_layout_TV_sure);
        mTVcancle = findViewById(R.id.tips_dialog_layout_TV_cancle);
        mIVshow = findViewById(R.id.iv_reserve_success);
    }

    private void setListener() {
        mTVsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTipsDialogClickListener != null) {
                    onTipsDialogClickListener.onSureClick();
                }
                if (isShowing()) {
                    dismiss();
                }
            }
        });

        mTVcancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTipsDialogClickListener != null) {
                    onTipsDialogClickListener.onCancleClick();
                }
                if (isShowing()) {
                    dismiss();
                }
            }
        });
    }

    public void setTips(String tips) {
        this.setTips(tips, true);
    }

    public void setTips(CharSequence tips) {
        this.setTips(tips.toString(), true);
    }

    public void setTips(int tipsId) {
//      this.setTips(context.getResources().getString(tipsId),false);
        this.setTips(context.getResources().getString(tipsId), true);
    }

    public void setTips(int tipsId, boolean isCenter) {
        this.setTips(context.getResources().getString(tipsId), isCenter);
    }

    public void setTips(String tips, boolean tipCenter) {
        if (!isShowing()) {
            show();
        }
        mTVtips.setText(tips);
        if (tipCenter) {
            mTVtips.setGravity(Gravity.CENTER);
        }
        showCancle(false);
    }

    public void setTipsHtml(Spanned spanned) {
        if (!isShowing()) {
            show();
        }
        mTVtips.setText(spanned);
        mTVtips.setGravity(Gravity.CENTER);
        showCancle(false);
    }

    public void setTVtip(String mWarn) {
        mTVtip.setText(mWarn);
    }


    public void showSuccessIm(boolean isShow) {
        mIVshow.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public void showCancle(boolean showCancle) {
        mTVcancle.setVisibility(showCancle ? View.VISIBLE : View.GONE);
    }

    public void setSureText(String text) {
        mTVsubmit.setText(text);
    }

    public String getSureText() {
        return mTVsubmit.getText().toString();
    }

    public void setCancleText(String text) {
        mTVcancle.setText(text);
    }
    //设置字体大小
    public void setTextSize(float titleSize, float contentSize) {
        mTVtips.setTextSize(DensityUtil.px2dp(titleSize));
        mTVtip.setTextSize(DensityUtil.px2dp(contentSize));
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0 && mCancelable) {
            this.dismiss();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
        this.mCancelable = flag;
    }

    private TipsDialog.OnTipsDialogClickListener onTipsDialogClickListener;

    public void setOnTipsDialogClickListener(TipsDialog.OnTipsDialogClickListener onTipsDialogClickListener) {
        this.onTipsDialogClickListener = onTipsDialogClickListener;
    }

    public interface OnTipsDialogInterfaceClickListener {
        void onSureClick();

        void onCancleClick();
    }

    public static abstract class OnTipsDialogClickListener implements TipsDialog.OnTipsDialogInterfaceClickListener {
        @Override
        public void onCancleClick() {
        }
    }

    /**
     * 文字变色
     *
     * @param detail
     */
    public void setDetail(Spanned detail) {
        if (TextUtils.isEmpty(detail)) {
            defaultHint();
        } else {
            mTVtips.setText(detail);
            mTVtips.setTextColor(context.getResources().getColor(R.color.item_title_TextColor));
        }
    }

    public void defaultHint() {
        mTVtips.setText(hint);
        mTVtips.setTextColor(context.getResources().getColor(R.color.hint_textColor));
    }
}