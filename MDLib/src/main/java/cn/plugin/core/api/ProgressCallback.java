package cn.plugin.core.api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 类描述：用于进度展示的callback
 * Created by 宁家琦 on 2018/1/11 0011 09:12
 */

public abstract class ProgressCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful()) {
            onSuccess(call, response);
        } else {
            onFailure(call, new Throwable(response.message()));
        }
    }

    public abstract void onSuccess(Call<T> call, Response<T> response);

    //用于进度的回调
    public abstract void onLoading(long total, long progress) ;
}
