package cn.plugin.core.utils;

/**
 * Created by 冯超 on 2017/3/6.
 */
public class ButtonUtils {
    private static long lastClickTime;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime <3000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
