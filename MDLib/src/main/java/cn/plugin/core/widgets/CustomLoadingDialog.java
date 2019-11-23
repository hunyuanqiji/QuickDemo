package cn.plugin.core.widgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import cn.plugin.core.R;


/**
 * Created by Went_Gone on0 2017/11/28.
 */

public class CustomLoadingDialog extends Dialog {
    private Context context;
    private ImageView mIVloading;
    private View mRoot;
    private TextView mTvShow;
    private boolean canClickOutside = true;
    private AnimationDrawable animaition;

    public void setCanClickOutside(boolean canClickOutside) {
        this.canClickOutside = canClickOutside;
    }

    public CustomLoadingDialog(@NonNull Context context) {
        this(context, 0);
    }

    public CustomLoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, R.style.NoDialogTitleView);
        this.context = context;
        setContentView(R.layout.layout_loading_ui);
        initViews();
        animaition.start();

        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        setCanceledOnTouchOutside(canClickOutside);// 设置点击屏幕Dialog消失
       lp.alpha = 0.7f; // 透明度
        dialogWindow.setAttributes(lp);

        mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                animaition.stop();
            }
        });

        mIVloading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initViews() {
        mIVloading = findViewById(R.id.layout_loading_ui_IV);
        mRoot = findViewById(R.id.layout_loading_ui_Root);
        mTvShow = (TextView) findViewById(R.id.tv_show);
        mIVloading.setBackgroundResource(R.drawable.loading_show);
        animaition = (AnimationDrawable) mIVloading.getBackground();
    }

    private void setAnimation() {
        final RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(2500);//设置动画持续时间
        animation.setRepeatCount(Animation.INFINITE);
        mIVloading.setAnimation(animation);
        /** 开始动画 */
        animation.startNow();
    }

    /**
     * 文字显示
     * @param isShow
     */
    public void setTvShow(boolean isShow, String message){
        mTvShow.setVisibility(isShow?View.VISIBLE:View.GONE);
        mTvShow.setText(message);
   }

    @Override
    public void show() {
        if (!isShowing()) {
            super.show();
        }
        // setAnimation();
        animaition.start();
        animaition.setOneShot(false);
    }


}
