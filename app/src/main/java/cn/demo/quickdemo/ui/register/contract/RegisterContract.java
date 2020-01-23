package cn.demo.quickdemo.ui.register.contract;

import java.util.List;

import cn.demo.quickdemo.ui.account.bean.LoginBean;
import cn.plugin.core.api.RetrofitCallBack;
import cn.plugin.core.base.MVPView;
import cn.plugin.core.bean.BaseTResultBean;

public interface RegisterContract {

    interface View extends MVPView {

        /**
         * 注册成功
         */
        void registerSuccess(List<LoginBean> bean);

        /**
         * 登录失败
         */
        void registerError(int resultCode, String message);

        /**
         * 获取注册验证码
         */
        void getRegisterVerificationError(int resultCode, String message);

        /**
         * 获取注册验证码
         */
        void getRegisterVerificationSuccess();
    }

    interface Presenter {

        /**
         * 注册
         */
        void register(String phoneNum,
                      String password,
                      String verificationCode,
                      String platformKey);

        /**
         * 获取注册验证码
         */
        void getRegisterVerification(String platfromKey,
                                     String phone);
    }

    interface Model {

        /**
         * 注册
         */
        void register(String json,
                      RetrofitCallBack<BaseTResultBean<List<LoginBean>>> result);

        /**
         * 获取注册验证码
         */
        void getRegisterVerification(String platfromKey,
                                     String phone,
                                     RetrofitCallBack<BaseTResultBean> result);
    }
}
