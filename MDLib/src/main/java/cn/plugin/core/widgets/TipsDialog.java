package cn.plugin.core.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import cn.plugin.core.R;


/**
 * Created by asmin on 2017/4/7.
 */

public class TipsDialog extends Dialog implements View.OnClickListener{
    private static final String TAG = "TipsDialog";

    private TextView mTVtips,mTVsubmit,mTVcancle;
    private Context context;
    private boolean mCancelable = true;

    public TipsDialog(Context context) {
        super(context, R.style.NoDialogTitleView);
        this.context = context;
        setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tips_dialog_layout);
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

    private void setParams(double rote) {
        Window window = this.getWindow() ;
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.widthPixels * rote); // 宽度设置为屏幕的0.65
        window.setAttributes(p);
    }

    private void initViews() {
        mTVtips = (TextView) findViewById(R.id.tips_dialog_layout_TV_tips);
        mTVsubmit = (TextView) findViewById(R.id.tips_dialog_layout_TV_sure);
        mTVcancle = (TextView) findViewById(R.id.tips_dialog_layout_TV_cancle);
    }

    private void setListener() {
        mTVsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTipsDialogClickListener!=null){
                    onTipsDialogClickListener.onSureClick();
                }
                if (isShowing()){
                    dismiss();
                }
            }
        });

        mTVcancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTipsDialogClickListener!=null){
                    onTipsDialogClickListener.onCancleClick();
                }
                if (isShowing()){
                    dismiss();
                }
            }
        });
    }

    public void setTips(String tips){
      this.setTips(tips,true);
    }
    public void setTips(CharSequence tips){
      this.setTips(tips.toString(),true);
    }
    public void setTips(int tipsId){
//      this.setTips(context.getResources().getString(tipsId),false);
      this.setTips(context.getResources().getString(tipsId),true);
    }
    public void setTips(int tipsId,boolean isCenter){
      this.setTips(context.getResources().getString(tipsId),isCenter);
    }
    public void setTips(String tips,boolean tipCenter){
        if (!isShowing()){
            show();
        }
        mTVtips.setText(tips);
        if (tipCenter){
            mTVtips.setGravity(Gravity.CENTER);
        }
        showCancle(false);
    }

    public void setTipsHtml(Spanned spanned){
        if (!isShowing()){
            show();
        }
        mTVtips.setText(spanned);
        mTVtips.setGravity(Gravity.CENTER);
        showCancle(false);
    }


    public void showCancle(boolean showCancle){
        mTVcancle.setVisibility(showCancle? View.VISIBLE:View.GONE);
    }

    public void setSureText(String text){
        mTVsubmit.setText(text);
    }
    public String getSureText(){
        return mTVsubmit.getText().toString();
    }

    public void setCancleText(String text){
        mTVcancle.setText(text);
    }

   /* public void setShowStyle(){
        Window window = this.getWindow();
        // 可以在此设置显示动画
        WindowManager.LayoutParams wl = window.getAttributes();
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        wl.x = 0;
        wl.y = wm.getDefaultDisplay().getHeight()/2;
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        this.onWindowAttributesChanged(wl);
    }*/

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
        switch (view.getId()){
        }
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
        this.mCancelable = flag;
    }

    private OnTipsDialogClickListener onTipsDialogClickListener;

    public void setOnTipsDialogClickListener(OnTipsDialogClickListener onTipsDialogClickListener) {
        this.onTipsDialogClickListener = onTipsDialogClickListener;
    }

    public interface OnTipsDialogInterfaceClickListener{
        void onSureClick();
        void onCancleClick();
    }

    public static abstract class OnTipsDialogClickListener implements OnTipsDialogInterfaceClickListener{
        @Override
        public void onCancleClick() {
        }
    }
}