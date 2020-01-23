package cn.demo.quickdemo.ui.register.api.service;


import java.util.List;

import cn.demo.quickdemo.ui.account.bean.LoginBean;
import cn.plugin.core.bean.BaseTResultBean;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by yy
 * on 2017/4/5/005.
 */

public interface RegisterService {

    /**
     * 注册
     */
    @POST("rest/account/regist")
    Call<BaseTResultBean<List<LoginBean>>> register(@Body RequestBody value);


    /**
     * 获取注册验证码
     */
    @POST("rest/verificationcode/hmprovider/apply/{platfromKey}/{phone}")
    Call<BaseTResultBean> getRegisterVerification(@Path("platfromKey") String platfromKey,
                                                  @Path("phone") String phone);

}
