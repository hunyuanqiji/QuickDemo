package cn.demo.quickdemo.ui.account.api.service;


import java.util.List;

import cn.demo.quickdemo.ui.account.bean.LoginBean;
import cn.plugin.core.bean.BaseTResultBean;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by NJQ
 * on 2017/4/5/005.
 */

public interface AccountService {

    /**
     * 登录
     */
    @POST("rest/account/user/login")
    Call<BaseTResultBean<List<LoginBean>>> signIn(@Body RequestBody value);


}
