package cn.demo.quickdemo.ui.register.presenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.demo.quickdemo.ui.account.bean.LoginBean;
import cn.demo.quickdemo.ui.register.contract.RegisterContract;
import cn.demo.quickdemo.ui.register.model.RegisterModel;
import cn.plugin.core.api.RetrofitCallBack;
import cn.plugin.core.base.BasePresenter;
import cn.plugin.core.bean.BaseTResultBean;

public class RegisterPresenter extends BasePresenter<RegisterContract.View>
        implements RegisterContract.Presenter {

    private RegisterContract.Model mModel;

    public RegisterPresenter() {
        mModel = new RegisterModel();
    }


    @Override
    public void register(String phoneNum, String password, String verificationCode,String platformKey) {
        JSONObject user = new JSONObject();
        try {
            user.put("phoneNo", phoneNum);
            user.put("password", password);
            user.put("password2", password);
            user.put("accessKey",platformKey );
            user.put("code", verificationCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mModel.register(user.toString(), new RetrofitCallBack<BaseTResultBean<List<LoginBean>>>() {
            @Override
            public void onStart() {
                getMvpView().showLoading();
            }

            @Override
            public void onEnd() {
                getMvpView().hideLoading();
            }

            @Override
            public void onResultSuccess(BaseTResultBean<List<LoginBean>> data, String response) {
                if (isViewAttached()) {
                    getMvpView().registerSuccess(data.getData());
                }
            }

            @Override
            public void onResultError(int resultCode, String message) {
                if (isViewAttached()) {
                    getMvpView().registerError(resultCode,message);
                }
            }
        });
    }

    @Override
    public void getRegisterVerification(String platfromKey, String phone) {
        mModel.getRegisterVerification(platfromKey, phone, new RetrofitCallBack<BaseTResultBean>() {
            @Override
            public void onStart() {
                getMvpView().showLoading();
            }

            @Override
            public void onEnd() {
                getMvpView().hideLoading();
            }
            @Override
            public void onResultSuccess(BaseTResultBean data, String response) {
                if (isViewAttached()) {
                    getMvpView().getRegisterVerificationSuccess();
                }
            }

            @Override
            public void onResultError(int resultCode, String message) {
                if (isViewAttached()) {
                    getMvpView().getRegisterVerificationError(resultCode,message);
                }
            }
        });
    }

}
