package cn.plugin.core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import cn.plugin.core.listener.PermissionsResultListener;


/**
 * Created by Yang_Yang on 2018/10/12 0012
 */
public class AuthorityUnit {
    private PermissionsResultListener mListener;
    private static AuthorityUnit sIAuthorityUnit = new AuthorityUnit();

    public static AuthorityUnit getsInstance() {
        return sIAuthorityUnit;
    }




    /**
     * 检察每个权限是否申请
     *
     * @param permissions
     * @return true 需要申请权限,false 已申请权限
     */
    public boolean checkEachSelfPermission(String[] permissions, Context context) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查回调结果
     *
     * @param grantResults
     * @return
     */
    public static boolean checkEachPermissionsGranted(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 申请权限前判断是否需要声明
     *
     * @param desc
     * @param permissions
     * @param requestCode
     */
    public void requestEachPermissions(String desc, String[] permissions, int requestCode, Context context) {
        if (shouldShowRequestPermissionRationale(permissions,context)) {// 需要再次声明
            showRationaleDialog(desc, permissions, requestCode,context);
        } else {
            ActivityCompat.requestPermissions((Activity) context, permissions, requestCode);
        }
    }

    /**
     * 再次申请权限时，是否需要声明
     *
     * @param permissions
     * @return
     */
    private boolean shouldShowRequestPermissionRationale(String[] permissions,Context context) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 弹出声明的 Dialog
     *
     * @param desc
     * @param permissions
     * @param requestCode
     */
    private void showRationaleDialog(String desc, final String[] permissions, final int requestCode, final Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("权限")
                .setMessage(desc)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions((Activity) context, permissions, requestCode);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

}
