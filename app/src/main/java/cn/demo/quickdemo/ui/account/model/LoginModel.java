package cn.demo.quickdemo.ui.account.model;

import java.util.List;

import cn.demo.quickdemo.ui.account.api.MainService;
import cn.demo.quickdemo.ui.account.bean.LoginBean;
import cn.demo.quickdemo.ui.account.contract.LoginContract;
import cn.plugin.core.api.RetrofitCallBack;
import cn.plugin.core.bean.BaseTResultBean;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 当前类注释：
 * PackageName：com.runsoft.systemv5.ui.location
 * Created by Qyang on 17/4/28
 * Email: yczx27@163.com
 */
public class LoginModel implements LoginContract.Model {


    @Override
    public void signIn(String json, final RetrofitCallBack<BaseTResultBean<List<LoginBean>>> result) {
        //添加到RequestBody中
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Call<BaseTResultBean<List<LoginBean>>> call = MainService.getAccountService().signIn(body);
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

}
