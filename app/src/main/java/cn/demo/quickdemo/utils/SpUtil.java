package cn.demo.quickdemo.utils;

import cn.plugin.core.utils.BaseSpUtil;

/**
 * 本地缓存类
 *
 * @author songying
 */
public class SpUtil extends BaseSpUtil {
    public final static String KEY_PATIENT_GROUP_MANAGE_ROLE = "KEY_PATIENT_GROUP_MANAGE_ROLE";// 医生是否有管理患者组的权限
    public final static String KEY_PATIENT_LIST_CACHE = "KEY_PATIENT_LIST_CACHE";// 首页患者列表缓存
    public final static String KEY_LOGIN_PLATFORM_LIST = "KEY_LOGIN_PLATFORM_LIST";// 登录平台列表

    public static final String KEY_SAVE_ADD_PATIENT_GROUP = "KEY_SAVE_ADD_PATIENT_GROUP";// 保存添加患者组的preferences的key
    public static final String KEY_SAVE_ADD_PATIENT = "KEY_SAVE_ADD_PATIENT";// 保存添加患者信息的preferences的key
    public final static String KEY_READ_AGREEMENT = "KEY_READ_AGREEMENT";// 阅读协议缓存
    public final static String KEY_QR_BEAN = "KEY_QR_BEAN";// 二维码实体类
    public final static String KEY_MAIN_GUIDE_CACHE = "KEY_MAIN_GUIDE_CACHE";// 首页引导缓存
    public final static String KEY_PERSONAL_GUIDE_CACHE = "KEY_PERSONAL_GUIDE_CACHE";// 我的引导缓存

    public final static String KEY_WAIT_RECEPTION_DETAIL_GUIDE_CACHE = "KEY_WAIT_RECEPTION_DETAIL_GUIDE_CACHE";// 待接诊详情引导缓存
    public final static String KEY_PERFECT_INFO_FINISH = "KEY_PERFECT_INFO_FINISH";
}
