package cn.plugin.core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by Administrator on 2018/6/26 0026.
 */

public class UrlUtil {
    private static final String TAG = "UrlUtil";

    /**
     * 区别7.0以上url路径获取
     * @param intent
     * @param file
     * @param context
     * @return
     */
    private static Uri getUri(Intent intent, File file, Context context) {
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //判断版本是否在7.0以上
            uri =
                    FileProvider.getUriForFile(context,
                            context.getPackageName() + ".provider",
                            file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }
    /**
     * 获取文件Intent
     * @param file
     * @return
     */

    public static Intent getFileIntent(File file, Context context) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri photoURI = getUri(intent, file, context);
        intent.setDataAndType(photoURI, FileUtil.getMIMEType(file, context));
        return intent;
    }

    /**
     * 跳转到设置-允许安装未知来源-页面
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void startInstallPermissionSettingActivity(Activity activity) {
        //注意这个是8.0新API
        Uri packageURI = Uri.parse("package:"+activity.getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,packageURI);
        activity.startActivityForResult(intent, BaseConfigString.INSTALL_PERMISSOION_REQUEST_CODE);
    }


    public static void installAPK(File file,Context context){
        Uri uri = null;
        //兼容7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //判断版本是否在7.0以上
            uri =
                    FileProvider.getUriForFile(context,
                            context.getPackageName() + ".provider",
                            file);
        } else {
            uri = Uri.fromFile(file);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, context.getContentResolver().getType(uri));
        context.startActivity(intent);
    }

}
