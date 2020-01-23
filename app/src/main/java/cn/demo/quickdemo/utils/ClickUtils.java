package cn.demo.quickdemo.utils;

/**
 * Created by Went_Gone on 2017/12/20.
 */

public class ClickUtils {
    private static long lastClickTime;
    public static boolean isFastDoubleClick(){
        long time=System.currentTimeMillis();
        long timeD=time-lastClickTime;
//        Log.e("间隔时间",""+timeD);
        if(0<timeD&&timeD<800){
            return true;
        }else {
            lastClickTime=time;
            return false;
        }
    }

    private static boolean isClicked = false;
    public static boolean isDoubleClick(){
        return isClicked;
    }

    public static void setIsClicked(boolean isClicked) {
        ClickUtils.isClicked = isClicked;
    }
}
