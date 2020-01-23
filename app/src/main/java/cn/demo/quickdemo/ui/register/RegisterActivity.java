package cn.demo.quickdemo.ui.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.demo.quickdemo.R;
import cn.demo.quickdemo.ui.account.bean.LoginBean;
import cn.demo.quickdemo.ui.main.MainActivity;
import cn.demo.quickdemo.ui.register.contract.RegisterContract;
import cn.demo.quickdemo.ui.register.presenter.RegisterPresenter;
import cn.demo.quickdemo.utils.ConfigString;
import cn.demo.quickdemo.utils.SpUtil;
import cn.plugin.core.BaseConfig;
import cn.plugin.core.base.MVPBaseActivity;
import cn.plugin.core.utils.AESEncryptor;
import cn.plugin.core.utils.AesUtils;
import cn.plugin.core.utils.CheckInfo;
import cn.plugin.core.utils.ToastUtil;
import cn.plugin.core.widgets.SingleDialog;
import cn.plugin.core.widgets.TipsDialog;

public class RegisterActivity extends MVPBaseActivity<RegisterContract.View, RegisterPresenter>
        implements RegisterContract.View {

    final private static String mSeed = "LPJlpj!@#2016";

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_verification_code)
    EditText mEtVerificationCode;
    @BindView(R.id.tv_verification_code)
    TextView mTvVerificationCode;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.et_password_confirm)
    EditText mEtPasswordConfirm;
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    @BindView(R.id.cb_protocol)
    CheckBox cbProtocol;
    @BindView(R.id.tv_user_protocol)
    TextView tvUserProtocol;
    @BindView(R.id.tv_secret_protocol)
    TextView tvSecretProtocol;

    private SharedPreferences.Editor editor;
    private SingleDialog mSingleDialog;
    private long iDateLocked = 0;
    private int fullInfo = 0; // 0 未完善信息；1已完善信息

    private int second = 60;
    private Handler mHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (second > 0) {
                mTvVerificationCode.setText(second + "s");
                changeIntervalTime();
                second -= 1;
            } else {
                mTvVerificationCode.setClickable(true);
                mTvVerificationCode.setText("获取验证码");
                second = 60;
            }
        }
    };

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mHandle.sendMessage(new Message());
        }
    };

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    public void initBeforData() {
        preferences = getSharedPreferences(ConfigString.USER_INFO_KEY, MODE_PRIVATE);
        editor = preferences.edit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void setListeners() {

    }

    @OnClick({R.id.iv_back, R.id.tv_verification_code, R.id.btn_register, R.id.tv_user_protocol, R.id.tv_secret_protocol})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_verification_code:
                if (second == 60) {
                    requestGetVerificationCode();
                }
                break;
            case R.id.btn_register:
                requestRegister();
                break;

        }
    }

    /**
     * 注册
     */
    private void requestRegister() {
        if (TextUtils.isEmpty(mEtPhone.getText())) {
            ToastUtil.getShortToastByString(RegisterActivity.this, "请填写手机号");
            return;
        }

        if (!CheckInfo.isMobilePhoneNO(mEtPhone.getText().toString())) {
            ToastUtil.getShortToastByString(RegisterActivity.this,
                    getResources().getString(R.string.patient_detial_tips_phonenum_regulate));
            return;
        }

        if (TextUtils.isEmpty(mEtVerificationCode.getText())) {
            ToastUtil.getShortToastByString(RegisterActivity.this, "请填写验证码");
            return;
        }

        if (TextUtils.isEmpty(mEtPassword.getText()) || TextUtils.isEmpty(mEtPasswordConfirm
                .getText())) {
            ToastUtil.getShortToastByString(RegisterActivity.this, "请填写密码");
            return;
        }

        String pwd = mEtPassword.getText().toString().trim();
        String confirmPwd = mEtPasswordConfirm.getText().toString().trim();
        if (!pwd.equals(confirmPwd)) {
            ToastUtil.getShortToastByString(RegisterActivity.this, "密码不一致");
            return;
        }

        if (!CheckInfo.checkPassword(pwd, this) ||
                !CheckInfo.checkPassword(confirmPwd, this)) {
            ToastUtil.getShortToastByString(RegisterActivity.this, "密码格式不正确");
            return;
        }
        if (!cbProtocol.isChecked()) {
            ToastUtil.getShortToastByString(RegisterActivity.this, "请勾选协议");
            return;
        }

        String phone = mEtPhone.getText().toString();
        String verificationCode = mEtVerificationCode.getText().toString();
        mPresenter.register(phone, pwd, verificationCode, BaseConfig.PLAT_FROM_KEY);
    }

    /**
     * 获取验证码
     */
    private void requestGetVerificationCode() {
        if (TextUtils.isEmpty(mEtPhone.getText())) {
            ToastUtil.getShortToastByString(RegisterActivity.this, "请填写手机号");
            return;
        }
        mTvVerificationCode.setClickable(false);
        String phone = mEtPhone.getText().toString();
        mPresenter.getRegisterVerification(BaseConfig.PLAT_FROM_KEY, phone);
    }


    @Override
    public void registerSuccess(List<LoginBean> beans) {
        iDateLocked = 0;
        final List<LoginBean.PlatformlistBean> mPlatformList = new ArrayList<>();
        MobclickAgent.onProfileSignIn(mEtPassword.getText().toString());
        editor.putLong(ConfigString.APP_PASS_ERROR_DATELOCK, iDateLocked);
        editor.putString("APP_LOGIN_NAME", mEtPassword.getText().toString());
        String str = "";
        try {
            //进行加密
            if (Build.VERSION.SDK_INT >= 28) {
                str = AesUtils.encrypt(mSeed, mEtPassword.getText().toString());
            } else {
                str = AESEncryptor.encrypt(mSeed, mEtPassword.getText().toString());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.putString("APP_LOGIN_PASS", str);
        editor.commit();

        List<String> mPlatformStrList = new ArrayList<>();
        for (int i = 0; i < beans.size(); i++) {
            LoginBean bean = beans.get(i);
            List<LoginBean.PlatformlistBean> platformlist = bean.getPlatformlist();
            mPlatformList.addAll(platformlist);
            for (int j = 0; j < platformlist.size(); j++) {
                mPlatformStrList.add(platformlist.get(j).getPlatformName());
            }
        }

        SpUtil.putBoolean(mContext, SpUtil.KEY_PERFECT_INFO_FINISH, true);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        onBackPressed();


//        if (mPlatformList.size() > 1) {
//            ObjectStoreUtil.saveObject(mContext, ConfigString.Key.PLATFORM_LIST, beans);
//            SpUtil.putString(mContext, SpUtil.KEY_LOGIN_PLATFORM_LIST, new Gson().toJson(beans));
//
//            //多个平台
//            mSingleDialog = new SingleDialog(this);
//            mSingleDialog.show(mPlatformStrList, true);
//            mSingleDialog.setDefault(0);
//            mSingleDialog.setNoDismiss(true);
//            mSingleDialog.setOnClickSureListener(new SingleDialog.OnClickSureListener() {
//                @Override
//                public void onClick(int position) {
////                    Editor editor = preferences.edit();
//                    LoginBean.PlatformlistBean platformlistBean = mPlatformList.get(position);
//                    fullInfo = platformlistBean.getFullInfo();
//                    savePlatForm(platformlistBean);
//                }
//            });
//        } else {
//            //一个平台
//            if (mPlatformList.size() > 0) {
//                LoginBean.PlatformlistBean platformlistBean = mPlatformList.get(0);
//                fullInfo = platformlistBean.getFullInfo();
//                savePlatForm(platformlistBean);
//            }
//        }
    }



    @Override
    public void registerError(int resultCode, String message) {
        mTipsDialog = new TipsDialog(this);
        mTipsDialog.setTips(message);
        mTipsDialog.show();
    }

    @Override
    public void getRegisterVerificationError(int resultCode, String message) {
        mTipsDialog = new TipsDialog(this);
        mTipsDialog.setTips(message);
        mTipsDialog.show();
        mTvVerificationCode.setClickable(true);
    }

    @Override
    public void getRegisterVerificationSuccess() {
        ToastUtil.getShortToastByString(RegisterActivity.this,
                "获取验证码成功");

        //启动倒计时
        changeIntervalTime();
    }

    /**
     * 显示时间倒数
     */
    private void changeIntervalTime() {
        mHandle.removeCallbacks(mRunnable);
        if (second > 0) {
            mHandle.postDelayed(mRunnable, 1000);
        }
    }
}
