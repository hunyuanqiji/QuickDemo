package cn.demo.quickdemo.update;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.HashMap;

import cn.demo.quickdemo.R;
import cn.demo.quickdemo.update.bean.UpdateBean;
import cn.demo.quickdemo.update.model.UpdateModel;
import cn.plugin.core.BaseConfig;
import cn.plugin.core.api.RetrofitCallBack;
import cn.plugin.core.api.RetrofitResultListener;
import cn.plugin.core.bean.BaseTResultBean;
import cn.plugin.core.ui.attachment.model.AttachmentDownloadModel;
import cn.plugin.core.utils.ABAppUtil;

/**
 * 检查更新下载APK
 * Created by NJQ on 2017/4/7.
 */

public class DownLoadDialog extends Dialog {
    private static final String TAG = "DownLoadDialog";

    private static final int UPDATE_PROGRESS = 0;
    private static final int UPDATE_FAIL = 1;
    private static final int UPDATE_SUCCESS = 2;

    private Context context;
    private ProgressBar mPB;
    private TextView mTVsize, mTVupdate, mTVinto;
    private File loadApp;

    private AttachmentDownloadModel mDownloadModel;
    private UpdateModel mUpdateModel;

    private boolean success = false;
    private String path;

    private UpdateHandler mHandler = new UpdateHandler(this);

    private static class UpdateHandler extends Handler {

        private final WeakReference<DownLoadDialog> downLoadDialog;

        private UpdateHandler(DownLoadDialog dialog) {
            this.downLoadDialog = new WeakReference<>(dialog);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            DownLoadDialog dialog = downLoadDialog.get();
            if (dialog != null) {
                switch (msg.what) {
                    case UPDATE_PROGRESS:
                        dialog.mPB.setProgress(msg.arg1);
                        dialog.mTVsize.setText((String) msg.obj);
                        break;
                    case UPDATE_FAIL:
                        dialog.mTVupdate.setVisibility(View.VISIBLE);
                        dialog.mTVupdate.setClickable(true);
                        dialog.mTVupdate.setText("立即下载");
                        if (dialog.onInstallPermissionListener != null) {
                            dialog.onInstallPermissionListener.onError();
                        }
                        break;
                    case UPDATE_SUCCESS:
                        File data = (File) msg.obj;
                        dialog.loadApp = data;
                        dialog.success = true;
                        Log.e(TAG, "onSuccess: 成功了");
                        if (dialog.onInstallPermissionListener != null) {
                            Log.e(TAG, "onSuccess: 回调");
                            dialog.onInstallPermissionListener.onInstallSuccess(data);
                        }
                        dialog.dismiss();
                        break;
                }
            }
        }
    }

    public DownLoadDialog(Context context) {
        super(context, R.style.NoDialogTitleView);
        this.context = context;
        setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_dialog_layout);
        initViews();

        setListener();
    }

    @Override
    public void show() {
        super.show();
        setParams(0.75);
    }

    private void setParams(double rote) {
        Window window = this.getWindow();
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.widthPixels * rote); // 宽度设置为屏幕的0.65
        window.setAttributes(p);
    }

    private void initViews() {
        mDownloadModel = new AttachmentDownloadModel();
        mUpdateModel = new UpdateModel();

        mPB = findViewById(R.id.download_dialog_layout_progressbar);
        mPB.setMax(100);
        mTVsize = findViewById(R.id.download_dialog_layout_TV_size);
        mTVupdate = findViewById(R.id.download_dialog_layout_TV_sure);
        mTVinto = findViewById(R.id.download_dialog_layout_TV_into);
        mTVinto.setVisibility(View.GONE);
        mTVupdate.setVisibility(View.VISIBLE);
    }

    public void downLoadApp(final String loadUrl) {
        mTVupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //有新版本
                loadApp(loadUrl);
            }
        });
    }

    private void loadApp(String loadUrl) {
        path = loadUrl;
        mTVupdate.setText("正在更新");
        mTVupdate.setClickable(false);

        String savePath = Environment.getExternalStorageDirectory() + File.separator + context.getPackageName() + File.separator + "doctor.apk";

        mDownloadModel.download(loadUrl, savePath, new HashMap<String, String>(), new RetrofitResultListener<File>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onEnd() {

            }

            @Override
            public void onSuccess(File data) {
                Message msg = new Message();
                msg.what = UPDATE_SUCCESS;
                msg.obj = data;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFailure(String message) {
                Message msg = new Message();
                msg.what = UPDATE_FAIL;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onProgress(int progress, int total, int percent) {
                Log.e(TAG, "progress:" + progress + ",total:" + total + ",percent:" + percent);
                Message msg = new Message();
                msg.what = UPDATE_PROGRESS;
                msg.arg1 = percent;
                msg.obj = getFormatSize(progress) + "/" + getFormatSize(total);
                mHandler.sendMessage(msg);
            }
        });
    }

    @Override
    public void dismiss() {
        mDownloadModel.cancelDownload(path);
        super.dismiss();
    }

    public Intent getFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        /*   intent.addCategory("android.intent.category.DEFAULT");
         */
   /*     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri photoURI = getUri(intent, file);
        intent.setDataAndType(photoURI, "application/vnd.android.package-archive");*/


        try {

            //兼容7.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri photoURI = getUri(intent, file);
                intent.setDataAndType(photoURI, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            if (context.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
                context.startActivity(intent);
            }
        } catch (Throwable e) {
            e.printStackTrace();

        }

        return intent;

    }

    /**
     * 跳转到设置-允许安装未知来源-页面
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ((Activity) context).startActivityForResult(intent, 0x35);
    }

    private Uri getUri(Intent intent, File file) {
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //判断版本是否在7.0以上
            uri = FileProvider.getUriForFile(context,
                    context.getPackageName() + ".provider",
                    file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(file);
        }

        return uri;
    }


    private void setListener() {
        mTVupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUpdateModel.checkUpdate(BaseConfig.SIGN_IN_URL, new RetrofitCallBack<BaseTResultBean<UpdateBean>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onEnd() {

                    }

                    @Override
                    public void onResultSuccess(BaseTResultBean<UpdateBean> data, String response) {
                        if (data.getData() != null) {
                            int versionCodeInt = data.getData().getVersion_code();
                            if (versionCodeInt > ABAppUtil.getAppVersionCode()) {
                                //有新版本
                                String loadUrl = data.getData().getDownload_url();
                                loadApp(loadUrl);
                            }
                        }
//                        try {
//                            JSONObject object = new JSONObject(response);
//                            String result = object.getString("result");
//                            if (result.equals("200")) {
//                                String versionCodeStr = object.getJSONObject("data").getString("versionCode");
//                                double versionCodeInt = Double.parseDouble(versionCodeStr);
//                                if (versionCodeInt > ABAppUtil.getAppVersionCode()) {
//                                    //有新版本
//                                    String loadUrl = object.getJSONObject("data").getString("downloadurl");
//                                    loadApp(loadUrl);
//                                }
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                    }

                    @Override
                    public void onResultError(int resultCode, String message) {

                    }
                });
            }
        });

        mTVinto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadApp != null && success) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(loadApp), "application/vnd.android.package-archive");
                    dismiss();
                    context.startActivity(intent);
                }
                if (isShowing()) {
                    dismiss();
                }
            }
        });
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return size + "B";
            return "0.0M";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "K";
//            return  "0.0M";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "M";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    public void setTips(String tips) {
        this.setTips(tips, false);
    }

    public void setTips(int tipsId) {
        this.setTips(context.getResources().getString(tipsId), false);
    }

    public void setTips(int tipsId, boolean isCenter) {
        this.setTips(context.getResources().getString(tipsId), isCenter);
    }

    public void setTips(String tips, boolean tipCenter) {
        if (!isShowing()) {
            show();
        }
        if (tipCenter) {
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }


    private OnTipsDialogClickListener onTipsDialogClickListener;

    public void setOnTipsDialogClickListener(OnTipsDialogClickListener onTipsDialogClickListener) {
        this.onTipsDialogClickListener = onTipsDialogClickListener;
    }

    public interface OnTipsDialogInterfaceClickListener {
        void onSureClick();

        void onCancleClick();
    }

    public static abstract class OnTipsDialogClickListener implements OnTipsDialogInterfaceClickListener {
        @Override
        public void onCancleClick() {
        }
    }

    private OnInstallPermissionListener onInstallPermissionListener;

    public void setOnInstallPermissionListener(OnInstallPermissionListener onInstallPermissionListener) {
        this.onInstallPermissionListener = onInstallPermissionListener;
    }

    public interface OnInstallPermissionListener {
        void onInstallSuccess(File file);

        void onError();
    }
}