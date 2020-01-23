package cn.demo.quickdemo.update.api.service;



import cn.demo.quickdemo.update.bean.UpdateBean;
import cn.plugin.core.bean.BaseTResultBean;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by YY
 * on 2017/4/5/005.
 */

public interface UpdateService {

    /**
     * 检查版本升级
     * {
     * "app_name":"MADDA",
     * "device_type":1,
     * "version":"11"
     * }
     */
    @POST("checkUpdate")
    Call<BaseTResultBean<UpdateBean>> checkUpdate(@Body RequestBody value);
}
