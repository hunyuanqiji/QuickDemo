package cn.plugin.core.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/6/21.
 *
 */
public class BasePresenter<V extends MVPView> {

    protected static final String TAG = "BasePresenter";

    /**
     * View 接口类型的弱引用
     */
    protected Reference<V> mViewRef;

    /**
     * Presenter与View建立连接
     *
     * @param mvpView 与此Presenter相对应的View
     */
    public void attachView(V mvpView) {
        this.mViewRef = new WeakReference<V>(mvpView);
    }

    /**
     * Presenter与View连接断开
     */
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    /**
     * 是否已经与View建立连接
     *
     * @return
     */
    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    /**
     * 获取当前连接的View
     *
     * @return
     */
    public V getMvpView() {
        return mViewRef.get();
    }
}