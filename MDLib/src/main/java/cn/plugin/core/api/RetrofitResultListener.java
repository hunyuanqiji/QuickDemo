package cn.plugin.core.api;

/**
 * Created by zbmobi on 15/11/12.
 */
public interface RetrofitResultListener<T> {

    void onStart();
    void onEnd();
    void onSuccess(T data);
    void onFailure(String message);
    void onProgress(int progress, int total, int percent);
}
