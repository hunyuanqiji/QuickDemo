package cn.plugin.core.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.plugin.core.R;
import cn.plugin.core.widgets.CustomLoadingDialog;

/**
 * Fragment基类 新
 * Created by 宁家琦 on 2018/6/25.
 */
public abstract class MVPBaseFragment<V extends MVPView, P extends BasePresenter<V>> extends Fragment
        implements ViewListener,MVPView,OnRefreshListener,OnLoadmoreListener{

    protected static final String TAG = MVPBaseFragment.class.getSimpleName();

    public static final int REQUEST_REFRESH = 100;      // 请求刷新
    public static final int REQUEST_FINISH = 101;       // 请求关闭

    private View rootView;
    protected SmartRefreshLayout mRefreshLayout;
    protected Context mContext;
    protected CustomLoadingDialog mLoadingDialog;
    protected P mPresenter;
    private Unbinder unbinder;

    protected int page = 1;
    protected int pageSize = 15;
    private boolean mIsViewCreated;
    private boolean mIsUIVisible;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MVP
        mPresenter = createPresenter();//创建Presenter
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

    protected abstract P createPresenter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        initBeforData();
        initBeforData(savedInstanceState);
        rootView = inflater.inflate(getLayoutId(),null);
        unbinder = ButterKnife.bind(this, rootView);
        getRefreshLayout();
        initViews(rootView);
        if (mRefreshLayout != null){
            mRefreshLayout.setOnLoadmoreListener(this);
            mRefreshLayout.setOnRefreshListener(this);
        }
        setListeners();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsViewCreated = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            mIsUIVisible = true;
            lazyLoad();
        }else {
            mIsUIVisible = false;
        }
    }

    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (mIsViewCreated && mIsUIVisible) {
            lazyLoadData();
            // 数据加载完毕,恢复标记,防止重复加载
            mIsViewCreated = false;
            mIsUIVisible = false;
        }
    }

    @Override
    public void initViews() {
    }

    public void initBeforData(Bundle savedInstanceState) {
    }

    /**
     * 懒加载数据，用于Viewpager中fragment显示时再进行加载数据的请求
     */
    public void lazyLoadData(){}
    /**
     * 初始化View
     * @param view
     */
    public abstract void initViews(View view);

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLoadingDialog != null){
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void onFail() {

    }

    @Override
    public void showMessage(int resultCode, String message) {

    }

    @Override
    public Dialog getLoadingDialog() {
        return mLoadingDialog = new CustomLoadingDialog(mContext);
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null){
            if (mLoadingDialog.isShowing()){
                mLoadingDialog.dismiss();
            }
        }
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null){
            mLoadingDialog = (CustomLoadingDialog) getLoadingDialog();
        }
        if (!mLoadingDialog.isShowing()){
            mLoadingDialog.show();
        }
    }

    @Override
    public SmartRefreshLayout getRefreshLayout() {
        mRefreshLayout = rootView.findViewById(R.id.SmartRefreshLayout);
        return mRefreshLayout;
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {

    }

    public void finishRefreshAndLoading(){
        if (mRefreshLayout != null){
            if (mRefreshLayout.isRefreshing()){
                mRefreshLayout.finishRefresh(0);
            }else if (mRefreshLayout.isLoading()){
                mRefreshLayout.finishLoadmore(0);
            }
        }
    }
}
