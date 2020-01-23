package cn.demo.quickdemo.update.model;

import org.json.JSONException;
import org.json.JSONObject;

import cn.demo.quickdemo.update.api.MainService;
import cn.demo.quickdemo.update.bean.UpdateBean;
import cn.plugin.core.api.RetrofitCallBack;
import cn.plugin.core.bean.BaseTResultBean;
import cn.plugin.core.utils.ABAppUtil;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class UpdateModel {

    public void checkUpdate(String url, final RetrofitCallBack<BaseTResultBean<UpdateBean>> result) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("app_name", "MADDA");
            jsonObject.put("device_type", 0);
            jsonObject.put("version", ABAppUtil.getAppVersion() + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //添加到RequestBody中
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        Call<BaseTResultBean<UpdateBean>> call = MainService.getUpdateService(url).checkUpdate(body);
        result.onStart();
        call.enqueue(new Callback<BaseTResultBean<UpdateBean>>() {
            @Override
            public void onResponse(Call<BaseTResultBean<UpdateBean>> call, retrofit2.Response<BaseTResultBean<UpdateBean>> response) {
                result.onSuccess(response.body());
                result.onEnd();
            }

            @Override
            public void onFailure(Call<BaseTResultBean<UpdateBean>> call, Throwable t) {
                result.onFailure(t.getMessage());
                result.onEnd();
            }
        });
    }
}
