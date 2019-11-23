package cn.plugin.core.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.ButterKnife;
import cn.plugin.core.R;
import cn.plugin.core.listener.PermissionsResultListener;
import cn.plugin.core.utils.ABAppUtil;
import cn.plugin.core.utils.AppManager;
import cn.plugin.core.utils.AuthorityUnit;
import cn.plugin.core.widgets.CustomLoadingDialog;

/**
 * Activity的基类 新
 * Created by 宁家琦 on 2017/1/10.
 */
public abstract class MVPBaseActivity<V extends MVPView, P extends BasePresenter<V>> extends AppCompatActivity
        implements ViewListener, MVPView, OnRefreshListener, OnLoadmoreListener {

    public static final String TAG = MVPBaseActivity.class.getSimpleName();

    public static final int REQUEST_REFRESH = 100;      // 请求刷新
    public static final int REQUEST_FINISH = 101;       // 请求关闭

    protected SmartRefreshLayout mRefreshLayout;
    protected Context mContext;
    protected CustomLoadingDialog mLoadingDialog;
    protected PermissionsResultListener mListener;
    protected P mPresenter;

    protected int mRequestCode;
    protected int page = 1;
    protected int pageSize = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = this;
        mLoadingDialog = (CustomLoadingDialog) getLoadingDialog();
        mLoadingDialog.setCanClickOutside(false);

        mPresenter = createPresenter();
        //创建Presenter
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }

        initBeforData(savedInstanceState);
        initBeforData();
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        getRefreshLayout();
        if (mRefreshLayout != null) {
            mRefreshLayout.setOnLoadmoreListener(this);
            mRefreshLayout.setOnRefreshListener(this);
        }
        initViews();
        setListeners();
        AppManager.getAppManager().addActivity(this);

        // 去掉警告提示框
        closeAndroidPDialog();
    }

    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract P createPresenter();

    public void initBeforData(Bundle savedInstanceState) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getAppManager().finishActivity(this);
    }

    public void come_back(View view) {
        onBackPressed();
        ABAppUtil.hideSoftInput(mContext);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        Log.e(TAG, "onBackPressed: -------Destroy()-----");
        super.onDestroy();
    }

    @Override
    public void onFail() {

    }

    @Override
    public void showMessage(int resultCode, String message) {

    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = (CustomLoadingDialog) getLoadingDialog();
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    @Override
    public Dialog getLoadingDialog() {
        return mLoadingDialog = new CustomLoadingDialog(mContext);
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null) {
            if (mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            }
        }
    }

    @Override
    public SmartRefreshLayout getRefreshLayout() {
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        return mRefreshLayout;
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {

    }

    public void finishRefreshAndLoading() {
        if (mRefreshLayout != null) {
            if (mRefreshLayout.isRefreshing()) {
                mRefreshLayout.finishRefresh(0);
            } else if (mRefreshLayout.isLoading()) {
                mRefreshLayout.finishLoadmore(0);
            }
        }
    }

    /**
     * 其他 Activity 继承 BaseActivity 调用 performRequestPermissions 方法
     *
     * @param desc        首次申请权限被拒绝后再次申请给用户的描述提示
     * @param permissions 要申请的权限数组
     * @param requestCode 申请标记值
     * @param listener    实现的接口
     */
    public void performRequestPermissions(String desc, String[] permissions, int requestCode,
                                          PermissionsResultListener listener, Context context) {
        if (permissions == null || permissions.length == 0) {
            return;
        }
        mRequestCode = requestCode;
        mListener = listener;
        if (Build.VERSION.SDK_INT >= 23) {
            if (AuthorityUnit.getsInstance().checkEachSelfPermission(permissions, context)) {//
                // 检查是否声明了权限
                AuthorityUnit.getsInstance().requestEachPermissions(desc, permissions,
                        requestCode, mContext);
            } else {
                // 已经申请权限
                if (mListener != null) {
                    mListener.onPermissionGranted();
                }
            }
        } else {
            if (mListener != null) {
                mListener.onPermissionGranted();
            }
        }
    }
}
