package cn.demo.quickdemo.ui.account.presenter;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.demo.quickdemo.ui.account.bean.LoginBean;
import cn.demo.quickdemo.ui.account.contract.LoginContract;
import cn.demo.quickdemo.ui.account.model.LoginModel;
import cn.plugin.core.api.RetrofitCallBack;
import cn.plugin.core.base.BasePresenter;
import cn.plugin.core.bean.BaseTResultBean;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    private LoginContract.Model mModel;

    public LoginPresenter() {
        mModel = new LoginModel();
    }


    @Override
    public void signIn(String phoneNum, String password) {
        JSONObject user = new JSONObject();
        try {
            user.put("username", phoneNum);
            user.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mModel.signIn(user.toString(), new RetrofitCallBack<BaseTResultBean<List<LoginBean>>>() {
            @Override
            public void onStart() {
                if (isViewAttached()) {
                    getMvpView().showLoading();
                }
            }

            @Override
            public void onEnd() {
                if (isViewAttached()) {
                    getMvpView().hideLoading();
                }
            }

            @Override
            public void onResultSuccess(BaseTResultBean<List<LoginBean>> data, String response) {
                if (isViewAttached()) {
                    getMvpView().signInSuccess(data.getData());
                }
            }

            @Override
            public void onResultError(int resultCode, String message) {
                if (isViewAttached()) {
                    getMvpView().signInError(resultCode, message);
                }
            }
        });
    }

    @Override
    public void fastSignIn(String json) {
//        mModel.fastSignIn(json, new RetrofitCallBack<BaseTResultBean>() {
//            @Override
//            public void onStart() {
//                if (isViewAttached()) {
//                    getMvpView().showLoading();
//                }
//            }
//
//            @Override
//            public void onEnd() {
//                if (isViewAttached()) {
//                    getMvpView().hideLoading();
//                }
//            }
//
//            @Override
//            public void onResultSuccess(BaseTResultBean data, String response) {
//                if (isViewAttached()) {
//                    getMvpView().fastSignInSuccess(response);
//                }
//            }
//
//            @Override
//            public void onResultError(int resultCode, String message) {
//                if (isViewAttached()) {
//                    getMvpView().showMessage(resultCode, message);
//                }
//            }
//        });
    }

//    @Override
//    public void getCode(String json) {
//        mModel.getCode(json, new RetrofitCallBack<BaseTResultBean>() {
//            @Override
//            public void onStart() {
//                if (isViewAttached()) {
//                    getMvpView().showLoading();
//                }
//            }
//
//            @Override
//            public void onEnd() {
//                if (isViewAttached()) {
//                    getMvpView().hideLoading();
//                }
//            }
//
//            @Override
//            public void onResultSuccess(BaseTResultBean data, String response) {
//                if (isViewAttached()) {
//                    getMvpView().getCodeSuccess(response);
//                }
//            }
//
//            @Override
//            public void onResultError(int resultCode, String message) {
//                if (isViewAttached()) {
//                    getMvpView().getCodeError(resultCode, message);
//                }
//            }
//        });
//    }

    @Override
    public void checkUpdate(String url) {
//        mModel.checkUpdate(url, new RetrofitCallBack<BaseTResultBean<UpdateBean>>() {
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onEnd() {
//
//            }
//
//            @Override
//            public void onResultSuccess(BaseTResultBean<UpdateBean> data, String response) {
//                if (isViewAttached()) {
//                    getMvpView().checkUpdateSuccess(data.getData());
//                }
//            }
//
//            @Override
//            public void onResultError(int resultCode, String message) {
//                if (isViewAttached()) {
//                    getMvpView().onFail();
//                    getMvpView().showMessage(resultCode, message);
//                }
//            }
//        });
    }
}