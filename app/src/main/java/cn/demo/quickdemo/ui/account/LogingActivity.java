package cn.demo.quickdemo.ui.account;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.demo.quickdemo.R;
import cn.demo.quickdemo.ui.account.bean.LoginBean;
import cn.demo.quickdemo.ui.account.contract.LoginContract;
import cn.demo.quickdemo.ui.account.presenter.LoginPresenter;
import cn.demo.quickdemo.ui.main.MainActivity;
import cn.demo.quickdemo.update.DownLoadDialog;
import cn.demo.quickdemo.update.bean.UpdateBean;
import cn.demo.quickdemo.utils.ClickUtils;
import cn.demo.quickdemo.utils.ConfigString;
import cn.demo.quickdemo.utils.SpUtil;
import cn.plugin.core.BaseConfig;
import cn.plugin.core.base.MVPBaseActivity;
import cn.plugin.core.utils.AESEncryptor;
import cn.plugin.core.utils.AesUtils;
import cn.plugin.core.utils.ToastUtil;
import cn.plugin.core.utils.UrlUtil;
import cn.plugin.core.widgets.SingleDialog;
import cn.plugin.core.widgets.TipsDialog;

/**
 * 登入
 * Created by Yang_Yang on 2019/2/1 0001
 */
public class LogingActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter>
        implements LoginContract.View, View.OnClickListener {
    final private static int LOGIN_MAX_COUNT = 5;
    final private static int LOGIN_MIN_HOURS = 24;
    final private static String mSeed = "LPJlpj!@#2016";
    private static final int DOWNLOAD_REQUEST_CODE = 0;

    private TextView mButtonForget;
    private Button mButtonLogin;
    private EditText mAccounts;
    private EditText mPass;
    private CheckBox mCheckPass;

    private Dialog dialog;
    private String strUserName;
    private int REST_PASSWORD = 0X56;
    private SingleDialog mSingleDialog;
    private TipsDialog mTipsDialog;
    private Intent intent;
    private boolean mPasswordEmpty;
    private DownLoadDialog mDownLoadDialog;
    private int INTO_FAST_LOGIN_REQUEST_CODE = 0X78;
    private SharedPreferences.Editor editor;
    private long iDateLocked = 0;
    private boolean isDisplaypassword;
    private ImageView imPassword;
    private String path;
    private File mAppFile;
    private LoginBean.PlatformlistBean mPlatformBean;
    private UpdateBean mUpdateBean;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    private int fullInfo = 0; // 0 未完善信息；1已完善信息

    @Override
    public void initBeforData() {
        intent = getIntent();
        mPasswordEmpty = intent.getBooleanExtra("password_empty", false);
        editor = preferences.edit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_password_loging;
    }


    @Override
    public void initViews() {
        mButtonForget = findViewById(R.id.buttonForget);
        mButtonForget.setOnClickListener(this);
        mButtonLogin = findViewById(R.id.buttonLogout);
        mButtonLogin.setOnClickListener(this);
        mAccounts = findViewById(R.id.editTextAccounts);
        mPass = findViewById(R.id.editTextPassword);
        imPassword = findViewById(R.id.iv_password);
        mCheckPass = findViewById(R.id.checkBoxAutoSavePassword);
        imPassword.setOnClickListener(this);
        preferences = getSharedPreferences(ConfigString.USER_INFO_KEY, MODE_PRIVATE);
        //是否记住密码
        boolean flag = preferences.getBoolean("APP_AUTO_PASS", false);
        mCheckPass.setChecked(flag);
        strUserName = preferences.getString("APP_USER_NAME", "");
        String strUserPassword = preferences.getString("APP_USER_PASS", "");
        try {
            //对密码进行解密
            if (Build.VERSION.SDK_INT >= 28) {
                strUserPassword = AesUtils.decrypt(mSeed, strUserPassword);
            } else {
                strUserPassword = AESEncryptor.decrypt(mSeed, strUserPassword);
            }
            if (flag) {
                if (mCheckPass.isChecked() && !mPasswordEmpty) {
                    mPass.setText(strUserPassword);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAccounts.setText(strUserName);
        mTipsDialog = new TipsDialog(mContext);
    }

    @Override
    public void setListeners() {
        checkAppVersionCode();
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean(ConfigString.HAVA_WECHAT_URL, true);
        edit.apply();
    }

    private void checkPermissions(String path) {
        if ((ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        ) {
            downLoad(path);
        } else {
            ActivityCompat.requestPermissions((Activity) mContext,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, DOWNLOAD_REQUEST_CODE);
        }
    }


    private void downLoad(String path) {
        mDownLoadDialog = new DownLoadDialog(mContext);
        mDownLoadDialog.setCancelable(!mUpdateBean.isForce_update());
        mDownLoadDialog.show();
        mDownLoadDialog.downLoadApp(path);

        mDownLoadDialog.setOnInstallPermissionListener(new DownLoadDialog.OnInstallPermissionListener() {
            @Override
            public void onInstallSuccess(File file) {
//                SystemClock.sleep(500);
                mAppFile = file;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //兼容8.0
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        boolean hasInstallPermission = mContext.getPackageManager().canRequestPackageInstalls();
                        if (!hasInstallPermission) {

                            //注意这个是8.0新API
                            Uri packageURI = Uri.parse("package:" + getPackageName());
                            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                            startActivityForResult(intent, ConfigString.INSTALL_PERMISSOION_REQUEST_CODE);
                            return;
                        }
                    }
                }
                UrlUtil.installAPK(mAppFile, mContext);
            }

            @Override
            public void onError() {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonForget:
                //重置密码界面
                goResetPasswordActivity();
                break;
            case R.id.buttonLogout:
                //进入首页界面
                if (!ClickUtils.isFastDoubleClick()) {
                    goHomePageActivity();
                }
                break;

            case R.id.iv_password:
                isDisplaypassword = !isDisplaypassword;
                mPass.setTransformationMethod(isDisplaypassword ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                imPassword.setImageDrawable(getResources().getDrawable(isDisplaypassword ? R.mipmap.display_password : R.mipmap.hidden_password));
                break;
            default:
                break;
        }
    }



    /**
     * 去重置密码界面
     *
     * @author Went_Gone
     * @time 2017/3/23 13:02
     */
    public void goResetPasswordActivity() {
//        Intent intent = new Intent(this, RevisePasswordActivity.class);
//        startActivityForResult(intent, REST_PASSWORD);
    }

    /**
     * 去首页
     *
     * @author Went_Gone
     * @time 2017/3/23 13:02
     */
    public void goHomePageActivity() {
        if (mAccounts.getText().toString().equals("")) {
            Toast.makeText(this, "请填写注册用户名！", Toast.LENGTH_LONG).show();
            mAccounts.requestFocus();
            return;
        }
        if (mPass.getText().toString().equals("")) {
            Toast.makeText(this, "请输入密码！", Toast.LENGTH_LONG).show();
            mPass.requestFocus();
            return;
        }


        strUserName = preferences.getString("APP_USER_NAME", "");
        final SharedPreferences.Editor editor = preferences.edit();
        //不是一个账号的话就把错误的次数和限制全不清0
        if (!mAccounts.getText().toString().equals(strUserName)) {
            editor.putLong(ConfigString.APP_PASS_ERROR_DATELOCK, 0);
            editor.putInt("APP_PASS_ERROR_COUNTER", 0);
            editor.commit();
        }
        // 判断密码输入限制是否正常
        long iDateLocked = preferences.getLong(ConfigString.APP_PASS_ERROR_DATELOCK, 0);//是否限制登陆
        int iPassErr;
        if (iDateLocked > 0) {
            //限制24个小时
            if (System.currentTimeMillis() - iDateLocked < LOGIN_MIN_HOURS * 60 * 60 * 1000) {
                mTipsDialog = new TipsDialog(mContext);
                mTipsDialog.setTips("您的账户已被锁定，建议您重新找回密码，或者" + LOGIN_MIN_HOURS + "小时之后再次输入。" + "\n剩余时间："
                        + (LOGIN_MIN_HOURS - (System.currentTimeMillis() - iDateLocked)
                        / (60 * 60 * 1000)) + "小时");
                return;
            } else {
                //达到限制时间了，限制和错误次数清零
                iDateLocked = 0;
                iPassErr = 0;
                editor.putLong(ConfigString.APP_PASS_ERROR_DATELOCK, iDateLocked);
                editor.putInt("APP_PASS_ERROR_COUNTER", iPassErr);
            }
        }
        editor.putString("APP_USER_NAME", mAccounts.getText().toString());
        String str = "";
        try {
            if (Build.VERSION.SDK_INT >= 28) {

                str = AesUtils.encrypt(mSeed, mPass.getText().toString());
            } else {
                str = AESEncryptor.encrypt(mSeed, mPass.getText().toString());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.putString("APP_USER_PASS", str);
        editor.putBoolean(ConfigString.APP_LOGIN_STATUS, false);
        editor.commit();
        if (mCheckPass.isChecked()) {
            editor.putBoolean("APP_AUTO_PASS", true);
            editor.commit();
        } else {
            editor.putBoolean("APP_AUTO_PASS", false);
            editor.commit();
        }
        final String strAccounts = mAccounts.getText().toString();
        final String strPass = mPass.getText().toString();
        mPresenter.signIn(strAccounts, strPass);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REST_PASSWORD) {
            if (resultCode == RESULT_OK) {
                mPass.setText("");
            }
        }
        if (requestCode == INTO_FAST_LOGIN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                startActivity(new Intent(mContext, MainActivity.class));
                 finish();
            }
        }

        if (requestCode == ConfigString.INSTALL_PERMISSOION_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                UrlUtil.installAPK(mAppFile, mContext);
            }
        }
    }

    /**
     * 检查版本升级
     */
    private void checkAppVersionCode() {
        mPresenter.checkUpdate(BaseConfig.SIGN_IN_URL);
    }

    @Override
    public void onDestroy() {
        if (mDownLoadDialog != null && mDownLoadDialog.isShowing()) {
            mDownLoadDialog.dismiss();
        }
        super.onDestroy();
    }

    @Override
    public void signInSuccess(List<LoginBean> beans) {

        iDateLocked = 0;
        final List<LoginBean.PlatformlistBean> mPlatformList = new ArrayList<>();
        MobclickAgent.onProfileSignIn(mAccounts.getText().toString());
        editor.putLong(ConfigString.APP_PASS_ERROR_DATELOCK, iDateLocked);
        editor.putString("APP_LOGIN_NAME", mAccounts.getText().toString());
        String str = "";
        try {
            //进行加密
            if (Build.VERSION.SDK_INT >= 28) {
                str = AesUtils.encrypt(mSeed, mPass.getText().toString());
            } else {
                str = AESEncryptor.encrypt(mSeed, mPass.getText().toString());
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

        editor.putString(ConfigString.INFO_BUTTON_PASSWORD, mPass.getText().toString());

        editor.putInt(ConfigString.PLATFORM_COUNT, mPlatformList.size());
        //存贮token
        LoginBean.PlatformlistBean platformlistBean = mPlatformList.get(0);
        SpUtil.putString(mContext, SpUtil.KEY_TOKEN, platformlistBean.getToken());
//        if (mPlatformList.size() > 1) {
//            ObjectStoreUtil.saveObject(mContext, ConfigString.Key.PLATFORM_LIST, beans);
//            SpUtil.putString(mContext, SpUtil.KEY_LOGIN_PLATFORM_LIST, new Gson().toJson(beans));
//
//            //多个平台
//            mSingleDialog = new SingleDialog(getActivity());
//            mSingleDialog.show(mPlatformStrList, true);
//            mSingleDialog.setDefault(0);
//            mSingleDialog.setNoDismiss(true);
//            mSingleDialog.setOnClickSureListener(new SingleDialog.OnClickSureListener() {
//                @Override
//                public void onClick(int position) {
////                    Editor editor = preferences.edit();
//                    LoginBean.PlatformlistBean platformlistBean = mPlatformList.get(position);
//                    SpUtil.putString(mContext, SpUtil.KEY_CURRENT_LOGIN_PLATFORM, new Gson().toJson(platformlistBean));
//                    fullInfo = platformlistBean.getFullInfo();
//                    if (fullInfo == 0) {
//                        SpUtil.putString(mContext, SpUtil.KEY_TOKEN, platformlistBean.getToken());
//                        Intent intent = new Intent();
//                        intent.setClass(getActivity(), PerfectInfoActivity.class);
//                        startActivity(intent);
//                    } else {
//                        savePlatForm(platformlistBean);
//                    }
//
//                }
//            });
//        } else {
//            //一个平台
//            if (mPlatformList.size() > 0) {
//                LoginBean.PlatformlistBean platformlistBean = mPlatformList.get(0);
//                SpUtil.putString(mContext, SpUtil.KEY_CURRENT_LOGIN_PLATFORM, new Gson().toJson(platformlistBean));
//                fullInfo = platformlistBean.getFullInfo();
//                if (fullInfo == 0) {
//                    SpUtil.putString(mContext, SpUtil.KEY_TOKEN, platformlistBean.getToken());
//                    Intent intent = new Intent();
//                    intent.setClass(getActivity(), PerfectInfoActivity.class);
//                    startActivity(intent);
//                } else {
//                    savePlatForm(platformlistBean);
//                }
//            }
//
//        }
        // }

//    private void savePlatForm(LoginBean.PlatformlistBean platformlistBean) {
//        if (platformlistBean.getStatus() != 0) {
//            ToastUtil.getShortToastByString(mContext, getString(R.string.user_ailure));
//            return;
//        } else {
//            mPlatformBean = platformlistBean;
//            mPlatformUri = mPlatformBean.getPlatformUri();
//            SpUtil.putString(mContext, SpUtil.KEY_TOKEN, platformlistBean.getToken());
//            mPresenter.getLincese(mPlatformUri);
//
//        }

    }


    @Override
    public void signInError(int resultCode, String message) {

        mTipsDialog.setTips("用户名或密码错误");
    }

//    @Override
//    public void checkUpdateSuccess(UpdateBean bean) {
//        if (bean != null) {
//            mUpdateBean = bean;
//            int versionCodeInt = bean.getVersion_code();
//            if (versionCodeInt > ABAppUtil.getAppVersionCode()) {
//                //有新版本
//                String loadUrl = bean.getDownload_url();
//                path = loadUrl;
//                checkPermissions(loadUrl);
//            }
//        }
//    }

    @Override
    public void fastSignInSuccess(String response) {

    }

    @Override
    public void getCodeSuccess(String response) {

    }

    @Override
    public void getCodeError(int resultCode, String message) {

    }

    @Override
    public void showMessage(int resultCode, String message) {
        ToastUtil.getShortToastByString(mContext, message);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == DOWNLOAD_REQUEST_CODE) {
            if ((grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                downLoad(path);
            } else {
                Toast.makeText(mContext, "已拒绝权限,部分功能无法使用", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 登入成功
     */
    @Override
    public void getLinceseSuccess() {
//        LoginBean.PlatformlistBean platformlistBean = mPlatformBean;
//        ObjectStoreUtil.saveObject(mContext, ConfigString.Key.PLATFORM_BEAN, platformlistBean);
//
//        editor.putString(ConfigString.USER_PLATFORM_URL_KEY, platformlistBean.getPlatformUri());
//        editor.putString(ConfigString.APP_USERINDEX_PLATFORM_ACCESSSKEY, platformlistBean.getPlatfromAccessKey());
//        editor.putString(ConfigString.USER_ID_KEY, platformlistBean.getPlatfromUserId());
////     editor.putString("APP_USERINDEX_PLATFORM_NAME",platformlistBean.getPlatformName());
//        editor.putString(ConfigString.USER_ROLE_TYPE, platformlistBean.getPlatfromRoleType());
//        editor.putString(ConfigString.APP_USERINDEX_REALNAME, platformlistBean.getRealName());
//        editor.putString(ConfigString.APP_USERINDEX_WECHAT_URL, platformlistBean.getWeChatUrl());
//        editor.putBoolean(ConfigString.AUTO_LOGIN, true);
////
//        if (platformlistBean.getWeChatName() != null) {
//            editor.putString(ConfigString.APP_USERINDEX_WECHAT_NAME, platformlistBean.getWeChatName());
//        }
        //保存登陆成功的状态
        editor.putBoolean(ConfigString.APP_LOGIN_STATUS, mCheckPass.isChecked());
        editor.putBoolean("OFFLINE_FLAG", false);
        //离线的数据保存
        editor.putString("OFFLINE_LOGIN_NAME", mAccounts.getText().toString());
        String strPass = "";
        try {
            //  strPass = AesUtils.encrypt(mSeed, mPass.getText().toString());
            strPass = AESEncryptor.encrypt(mSeed, mPass.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.putString("OFFLINE_LOGIN_PASS", strPass);
        editor.putString(ConfigString.INFO_BUTTON_NAME, mAccounts.getText().toString());
        editor.commit();

        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.putExtra(MainActivity.INTENT_FROM_TYPE, MainActivity.LOGIN_ACTIVITY);
        startActivity(intent);
        finish();
    }

    @Override
    public void getLinceseError(int resultCode, String message) {
        TipsDialog tipsDialog = new TipsDialog(mContext);
        tipsDialog.setTips(message);
    }
}

