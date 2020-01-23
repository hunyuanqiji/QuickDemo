package cn.demo.quickdemo.ui.account.contract;


import java.util.List;
import cn.demo.quickdemo.ui.account.bean.LoginBean;
import cn.plugin.core.api.RetrofitCallBack;
import cn.plugin.core.base.MVPView;
import cn.plugin.core.bean.BaseTResultBean;


public interface LoginContract {

    interface View extends MVPView {

        /**
         * 登录成功
         */
        void signInSuccess(List<LoginBean> bean);

        /**
         * 登录失败
         */
        void signInError(int resultCode, String message);

        /**
         * 检查版本升级成功
         */
//        void checkUpdateSuccess(UpdateBean bean);
//
        /**
         * 快速登录成功
         */
        void fastSignInSuccess(String response);

        /**
         * 获取验证码成功
         */
        void getCodeSuccess(String response);

        /**
         * 获取验证码失败
         */
        void getCodeError(int resultCode, String message);

        /**
         * 验证license成功
         */
        void getLinceseSuccess();

        /**
         * 验证license失败
         */
        void getLinceseError(int resultCode, String message);
    }

    interface Presenter {

        /**
         * 登录
         */
        void signIn(String phoneNum, String password);

        /**
         * 检查版本升级
         */
        void checkUpdate(String url);

        /**
         * 快速登录
         */
        void fastSignIn(String json);

        /**
         * 获取验证码
         */
//        void getCode(String json);

//        /**
//         * 验证license
//         */
//        void getLincese(String platformUrl);
    }

    interface Model {

        /**
         * 登录
         */
        void signIn(String json, RetrofitCallBack<BaseTResultBean<List<LoginBean>>> result);

        /**
         * 检查版本升级
         */
//        void checkUpdate(String url, RetrofitCallBack<BaseTResultBean<UpdateBean>> result);

        /**
         * 快速登录
         */
//        void fastSignIn(String json, RetrofitCallBack<BaseTResultBean> result);

        /**
         * 获取验证码
         */
//        void getCode(String json, RetrofitCallBack<BaseTResultBean> result);


    }
}