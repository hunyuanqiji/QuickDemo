package cn.plugin.core.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 本地缓存类
 *
 * @author songying
 */
public class BaseSpUtil {

    private static SharedPreferences sp;
    private static String SP_NAME = "MADDA";

    public final static String SP_GUIDE_NAME = "MADDA_GUIDE";
    public final static String KEY_USER_BEAN = "KEY_USER_BEAN";// 用户信息
    public final static String KEY_TOKEN = "KEY_TOKEN";

    public static void initSpUtil(String spName){
        SP_NAME = spName;
    }

    public static void putString(Context context, String key, String value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key, String defaultValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static void putInt(Context context, String key, int value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, String key, int defaultValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    public static void putLong(Context context, String key, long value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putLong(key, value).commit();
    }

    public static long getLong(Context context, String key, long defaultValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getLong(key, defaultValue);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static void putBoolean(Context context, String spName, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, Context
                .MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String spName, String key, boolean
            defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, Context
                .MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static void clear() {
        if (sp != null) {
            sp.edit().clear().apply();
        }
    }
}
