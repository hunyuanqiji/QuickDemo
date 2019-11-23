package cn.plugin.core.utils;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by yy on 2019/1/17.
 **/
public class BitmapUtil {

    /**
     * 获取一个 View 的缓存视图
     *  (前提是这个View已经渲染完成显示在页面上)
     * @param view
     * @return
     */
    public static Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }
}
