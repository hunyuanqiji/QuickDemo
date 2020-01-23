package cn.demo.quickdemo.ui.account.api;


import cn.demo.quickdemo.ui.account.api.service.AccountService;
import cn.plugin.core.api.base.BaseApi;

/**
 * 定时问卷模块接口
 * Created by NJQ on 2016/11/10.
 */
public class MainService {

    private static AccountService accountService;

    public static AccountService getAccountService() {
//        if (accountService == null) {
            accountService = BaseApi.retrofit().create(AccountService.class);
//        }
        return accountService;
    }


}
