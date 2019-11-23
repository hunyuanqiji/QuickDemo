package cn.plugin.core.listener;

/**
 * Created by asmin on 2017/4/14.
 */

public interface PermissionsResultListener {
    void onPermissionGranted();

    void onPermissionDenied();
}
