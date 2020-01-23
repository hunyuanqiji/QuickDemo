package cn.demo.quickdemo.ui.register.model;

import java.util.List;

import cn.demo.quickdemo.ui.account.bean.LoginBean;
import cn.demo.quickdemo.ui.register.api.MainService;
import cn.demo.quickdemo.ui.register.contract.RegisterContract;
import cn.plugin.core.api.RetrofitCallBack;
import cn.plugin.core.bean.BaseTResultBean;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterModel implements RegisterContract.Model {


    @Override
    public void register(String json, final RetrofitCallBack<BaseTResultBean<List<LoginBean>>> result) {
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Call<BaseTResultBean<List<LoginBean>>> call = MainService.getRegisterService().register(body);
        result.onStart();
        call.enqueue(new Callback<BaseTResultBean<List<LoginBean>>>() {
            @Override
            public void onResponse(Call<BaseTResultBean<List<LoginBean>>> call, Response<BaseTResultBean<List<LoginBean>>> response) {
                result.onSuccess(response.body());
                result.onEnd();
            }

            @Override
            public void onFailure(Call<BaseTResultBean<List<LoginBean>>> call, Throwable t) {
                result.onFailure(t.getMessage());
                result.onEnd();
            }
        });
    }

    @Override
    public void getRegisterVerification(String platfromKey, String phone,
                                        final RetrofitCallBack<BaseTResultBean> result) {
        Call<BaseTResultBean> call = MainService.getRegisterService()
                .getRegisterVerification(
                        platfromKey,
                        phone);
        result.onStart();
        call.enqueue(new Callback<BaseTResultBean>() {
            @Override
            public void onResponse(Call<BaseTResultBean> call, Response<BaseTResultBean> response) {
                result.onSuccess(response.body());
                result.onEnd();
            }

            @Override
            public void onFailure(Call<BaseTResultBean> call, Throwable t) {
                result.onFailure(t.getMessage());
                result.onEnd();
            }
        });
    }
}
