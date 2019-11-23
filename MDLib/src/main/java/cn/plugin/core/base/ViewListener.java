package cn.plugin.core.base;

/**
 * 控件的初始化等操作接口
 * Created by Went_Gone on 2017/5/3.
 */
public interface ViewListener {
    /**
     * 初始化数据
     */
    void initBeforData();

    /**
     * 获取布局id
     * @return 所在布局的id
     */
    int getLayoutId();

    /**
     * 初始化控件
     */
    void initViews();

    /**
     * 给控件设置监听
     */
    void setListeners();
}
