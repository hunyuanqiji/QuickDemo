package cn.demo.quickdemo;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.lang.reflect.Field;

import cn.plugin.core.BaseConfig;
import cn.plugin.core.BaseCoreApp;
import cn.plugin.core.utils.LogUtil;

/**
 * Created by 冯超 on 2016/9/13.
 */
public class MainApp extends BaseCoreApp {

    @Override
    public void onCreate() {
        // 设置各个模块用到的URL
        DEBUG = BuildConfig.LOG_DEBUG;
        BaseConfig.BASE_URL = BuildConfig.BASE_URL;
        BaseConfig.VERSION_NAME = BuildConfig.VERSION_NAME;

        super.onCreate();
        // 友盟分享
        // 设置LOG开关，默认为false
        UMConfigure.setLogEnabled(DEBUG);
        try {
            Class<?> aClass = Class.forName("com.umeng.commonsdk.UMConfigure");
            Field[] fs = aClass.getDeclaredFields();
            for (Field f : fs) {
                LogUtil.e("xxxxxx", "ff=" + f.getName() + "   " + f.getType().getName());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
        UMConfigure.init(this, "5c3e9ffff1f55607c7000edc", "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
                "");
    }

    // 友盟分享平台设置
    {
        PlatformConfig.setWeixin("wxb263d2a3735b6d81", "30c91271cf36e858dbd7d1e3d3d7627f");
        PlatformConfig.setSinaWeibo("2857831852", "04c76415c590c6650dabc39c64ad3c8f", "http://sns.whalecloud.com");
    }
}
