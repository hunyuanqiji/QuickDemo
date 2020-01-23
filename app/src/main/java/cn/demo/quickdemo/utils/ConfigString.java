package cn.demo.quickdemo.utils;

import cn.plugin.core.utils.BaseConfigString;

/**
*app中静态常量的管理
*@author Went_Gone
*@time 2017/3/14 16:35
*/
public class ConfigString extends BaseConfigString {
    /**
     * 所有消息开关的key
     */
    public static final String ALL_NOTICE_SWITCH_KEY = "ALL_NOTICE_SWITCH";

    /**
     * 视频路径
     */
    public static final String VIDEO_PATH = "video_path";

    /**
     * 随访通知开关
     */
    public static final String NOTIFICATION_FOLLOW_UP_SWITCH_KEY = "notification_follow_up_switch_key";

    /**
     * App登录状态
     */
    public static final String APP_LOGIN_STATUS = "APP_LOGIN_STATUS";

    /**
     * 用户角色类型type的key
     */
    public static final String USER_ROLE_TYPE = "platfromRoleType";

    /**
     * ClientId的key
     */
    public static final String CLIENT_ID_KEY = "client_id";

    /**
     * 随访通知的Action
     */
    public static final String REMIND_NOTIFICATION_ACTION = "cn.mdruby.doctork.RemindBroadcastReceiver";

    /**
     * infobutton密码标示
     */
    public static final String INFO_BUTTON_PASSWORD = "info_button_pw";

    /**
     * infobutton用户名标示
     */
    public static final String INFO_BUTTON_NAME = "APP_USERINDEX_USERNAME";

    /**
     * 密码或用户名错误，被锁的标示
     */
    public static final String APP_PASS_ERROR_DATELOCK = "APP_PASS_ERROR_DATELOCK";

    /**
     * 自动登录的标示
     */
    public static final String AUTO_LOGIN = "auto_login";

    /**
     * 协和营养是否有活动标示
     * 要删除
     */
    public static final String APP_XIEHET_ACTIVITY = "APP_XIEHET_ACTIVITY";

    /**
     * 是否存在微信url
     */
    public static final String HAVA_WECHAT_URL = "have_wechat_url";

    /**
     * 平台个数的key
     */
    public static String PLATFORM_COUNT = "platform_count";

    /**
     * 平台的列表
     */
    public static String PLATFORM_JSONARRAY = "platform_jsonarray";

    /**
     * 已切换平台的key
     */
    public static String IS_CHANGE_PLATFORM = "is_change_platform";

    /**
     * 是否勾选用户协议
     */
    public final static String KEY_CHECK_PROTOCOL = "KEY_CHECK_PROTOCOL";

    public interface RequestCode {

        int ADD_UPDATE_PATIENT_WEIGHT_LOSS = 1;

        /**
         * 忘记密码 重置密码
         */
        int FORGET_PASSWORD_REQUEST_CODE = 2;

        /**
         * 快速登录
         */
        int FAST_SIGNIN_REQUEST_CODE = 3;

        /**
         * 患者详情
         */
        int ADD_PATIENT_REQUEST_CODE = 4;

        /**
         * 展示图片
         */
        int SHOW_IMAGE_REQUEST_CODE = 7;
    }

    public interface Key {
        String LOGIN_BEAN = "LOGIN_BEAN";
        String PLATFORM_BEAN = "Platform_Bean";
        String PLATFORM_LIST = "platform_list";
        String SESSION_ID = "session_id";
        String  QR_CODE_BEAN= "QR_Code";
    }

    public interface IntentKey {
        String USER_BEAN = "user_bean";
        String MEDIA_BEAN = "media_bean";
        String HEALTH_SUMMARY = "health_summary";
    }
}
