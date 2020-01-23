
package cn.demo.quickdemo.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.text.TextUtils;
import android.view.WindowManager;
import java.text.SimpleDateFormat;
import cn.demo.quickdemo.MainApp;
import cn.demo.quickdemo.R;
import cn.demo.quickdemo.ui.account.LogingActivity;
import cn.demo.quickdemo.utils.ConfigString;
import cn.demo.quickdemo.utils.SpUtil;
import cn.plugin.core.base.BasePresenter;
import cn.plugin.core.base.MVPBaseActivity;


/***
 *** 启动界面
 ***/
public class SplashActivity extends MVPBaseActivity {

    /**
     * Splash界面的展示时间
     */
    private static final long SHOW_TIME = 1000;
    private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private int count;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initBeforData() {
        SharedPreferences preferences = getSharedPreferences("APP_RUNNING_COUNT", MODE_PRIVATE);
        count = preferences.getInt("APP_RUNNING_COUNT", 0);
        Editor editor = preferences.edit();
        editor.putInt("APP_RUNNING_COUNT", ++count);
        editor.commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_start_splash;
    }

    @Override
    public void initViews() {

        // 避免从桌面启动程序后，会重新实例化入口类的activity
        if (!this.isTaskRoot()) { // 当前类不是该Task的根部，那么之前启动
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) { // 当前类是从桌面启动的
                    onBackPressed(); // finish掉该类，直接打开该Task中现存的Activity
                }
            }
        } else {
            goHomePage();
        }
    }

    @Override
    public void setListeners() {

    }



    private void goHomePage() {
        // 判断应用程序是否首次运行，如果是第一次运行则跳转到引导页面
        if (count == 1) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                    Intent intent = new Intent(SplashActivity.this, GuidePagesActivity.class);
//                    startActivity(intent);
//                    onBackPressed();
                }
            }, SHOW_TIME);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //先获取是否已经登陆，如果已经登陆跳转到主界面，如果不是跳转到登陆界面
                    SharedPreferences preferences = getSharedPreferences(ConfigString.USER_INFO_KEY, MODE_PRIVATE);
                    boolean autoLogin = preferences.getBoolean(ConfigString.AUTO_LOGIN, false);//查找项目中是否有自动登录的标示
                    Intent intent = new Intent();
                    if (autoLogin && !TextUtils.isEmpty(SpUtil.getString(MainApp.getAppContext(), SpUtil.KEY_TOKEN, ""))) {
                        //如果是自动登录,那么直接进入主页面
//                        if (SpUtil.getBoolean(MainApp.getAppContext(), SpUtil.KEY_PERFECT_INFO_FINISH, false)){
//                            intent.setClass(SplashActivity.this, MainActivity.class);
//                        }else {
//                            //本地缓存 注册成功，未完善信息，则跳到完善信息页面
//                            intent.setClass(SplashActivity.this, PerfectInfoActivity.class);
//                        }
                        intent.setClass(SplashActivity.this, MainActivity.class);
                        intent.putExtra(MainActivity.INTENT_FROM_TYPE, MainActivity.SPLASH_ACTIVITY);
                        WindowManager.LayoutParams attr = getWindow().getAttributes();
                        attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        getWindow().setAttributes(attr);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                    } else {
                        //如果不自动登录，那么直接进入登录页面
                        intent.setClass(SplashActivity.this, LogingActivity.class);
                    }
                    startActivity(intent);
                    onBackPressed();
                }
            }, SHOW_TIME);
        }
    }
}
