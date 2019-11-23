package cn.plugin.core;

import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.tencent.smtt.sdk.QbSdk;

import cn.plugin.core.utils.CrashHandler;
import cn.plugin.core.widgets.UperRefreshFooter;
import cn.plugin.core.widgets.UperRefreshHeader;

public class BaseCoreApp extends MultiDexApplication {

    public static boolean DEBUG = true;       // 是否调试模式
    private static Context mContext;
    private static BaseCoreApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = this.getApplicationContext();
        initRefresh();

        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                //x5内核初始化完成回调接口，此接口回调并表示已经加载起来了x5，有可能特殊情况下x5内核加载失败，切换到系统内核。
                //                Toast.makeText(MainApp.this, "加载起来了x5", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                //                Log.e("@@","加载内核是否成功:"+b);
                //                Toast.makeText(MainApp.this, "加载x5"+b,Toast.LENGTH_LONG).show();
            }
        });

        if (DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);
        CrashHandler.getsInstance().init(this);
    }

    /**
     * 初始化刷新与加载
     */
    private void initRefresh() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                //指定为经典Header，默认是 贝塞尔雷达Header
                return new UperRefreshHeader(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new UperRefreshFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 下载图片默认路径
     *
     * @return
     */
    public static String getDownloadImagePath() {
        return Environment.getExternalStorageDirectory() + "/DCIM/Camera/";
    }

    public static BaseCoreApp getInstance() {
        return instance;
    }

    public static Context getAppContext() {
        return mContext;
    }
}
