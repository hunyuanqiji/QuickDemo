package cn.demo.quickdemo.update.api;


import cn.demo.quickdemo.update.api.service.UpdateService;
import cn.plugin.core.api.base.BaseApi;

/**
 * 更新模块
 * Created by NJQ on 2016/11/10.
 */
public class MainService {

    private static UpdateService updateService;

    public static UpdateService getUpdateService(String url) {
//        if (updateService == null) {
            updateService = BaseApi.retrofit(url).create(UpdateService.class);
//        }
        return updateService;
    }


}
