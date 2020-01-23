package cn.demo.quickdemo.ui.register.api;


import cn.demo.quickdemo.ui.register.api.service.RegisterService;
import cn.plugin.core.api.base.BaseApi;

/**
 * 定时问卷模块接口
 * Created by NJQ on 2016/11/10.
 */
public class MainService {


    private static RegisterService registerService;

    public static RegisterService getRegisterService() {
//        if (reminderService == null) {
        registerService = BaseApi.retrofit().create(RegisterService.class);
//        }
        return registerService;
    }


}
