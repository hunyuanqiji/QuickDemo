package cn.plugin.core.api;

import android.widget.Toast;

import com.google.gson.Gson;

import cn.plugin.core.BaseCoreApp;
import cn.plugin.core.R;
import cn.plugin.core.bean.BaseTResultBean;
import cn.plugin.core.utils.NetWorkUtils;

/**
 * 类描述：接口请求自定义回调
 * Created by 宁家琦 on 2017/3/30 0030 08:37
 */
public abstract class RetrofitCallBack<T extends BaseTResultBean> implements RetrofitResultListener<T> {

    private static final String TAG = RetrofitCallBack.class.getSimpleName();

    @Override
    public void onStart() {

    }

    @Override
    public void onSuccess(T data) {
        if (data != null) {
            if (data.getResult() == ResultCode.SUCCESS) {
                onResultSuccess(data, new Gson().toJson(data));
            } else {
                onResultError(data.getResult(), data.getMessage());
            }
        } else {
            // 404,500 data为null
            onResultError(ResultCode.ERROR, BaseCoreApp.getAppContext().getString(R.string.try_again_later));
        }
    }

    @Override
    public void onFailure(String message) {
        // 无网提示
        if (!NetWorkUtils.isNetConnected(BaseCoreApp.getAppContext())) {
            Toast.makeText(BaseCoreApp.getAppContext(), BaseCoreApp.getAppContext().getString(R.string.no_net_work), Toast.LENGTH_SHORT).show();
            onNoNetWork();
        } else {
            onResultError(ResultCode.ERROR, BaseCoreApp.getAppContext().getString(R.string.try_again_later));
        }

    }

    @Override
    public void onEnd() {
    }

    @Override
    public void onProgress(int progress, int total, int percent) {

    }

    // 无网操作，关闭刷新状态
    public void onNoNetWork() {
    }

    public abstract void onResultSuccess(T data, String response);

    public abstract void onResultError(int resultCode, String message);

    public interface ResultCode {
        int ERROR = -1;
        int SUCCESS = 200;
    }
}
