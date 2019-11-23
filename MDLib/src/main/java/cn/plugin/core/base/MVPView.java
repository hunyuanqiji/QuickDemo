package cn.plugin.core.base;

import android.app.Dialog;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * Created by Administrator on 2018/6/21.
 */

public interface MVPView {

    /**
     * 执行请求失败逻辑
     */
    void onFail();

    /**
     * 显示消息
     * @param resultCode
     * @param message
     */
    void showMessage(int resultCode, String message);

    /**
     * 显示loading对话框
     */
    void showLoading();

    /**
     * 隐藏loading对话框
     */
    void hideLoading();

    Dialog getLoadingDialog();

    SmartRefreshLayout getRefreshLayout();
}
