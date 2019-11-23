package cn.plugin.core.ui.attachment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import cn.plugin.core.BaseCoreApp;
import cn.plugin.core.R;
import cn.plugin.core.api.RetrofitResultListener;
import cn.plugin.core.base.BasePresenter;
import cn.plugin.core.base.MVPBaseActivity;
import cn.plugin.core.ui.attachment.adapter.AttachmentDownloadPagerAdapter;
import cn.plugin.core.ui.attachment.bean.AttachmentEntity;
import cn.plugin.core.ui.attachment.model.AttachmentDownloadModel;
import cn.plugin.core.utils.FileUtil;
import cn.plugin.core.utils.ToastUtil;
import cn.plugin.core.utils.UrlUtil;

/**
 * 附件下载/浏览
 * Created by NJQ on 2018/11/11 0011
 */
public class AttachmentDownloadActivity extends MVPBaseActivity {

    public static final String KEY_DATA = "KEY_DATA";
    public static final String KEY_POSITION = "KEY_POSITION";
    public static final String KEY_SHOW_OPERATE = "KEY_SHOW_OPERATE";

    public static final int OPERATE_NONE = -1;
    public static final int OPERATE_ALL = 0;
    public static final int OPERATE_DOWNLOAD = 1;
    public static final int OPERATE_PLAY = 2;
    public static final int OPERATE_DELETE = 3;
    public static final int OPERATE_DOWNLOAD_PLAY = 4;
    public static final int OPERATE_DOWNLOAD_DELETE = 5;
    public static final int OPERATE_PLAY_DELETE = 6;

    private ViewPager viewpager;
    private TextView currentPageTv;
    private TextView totalPageTv;
    private Button fileDownloadBf;
    private Button filePlayBf;
    private Button fileDeleteBf;

    private AttachmentDownloadModel mModel;
    private ArrayList<AttachmentEntity> mDatasList = new ArrayList<>();
    public int mPosition;               // 一进来选中的位置
    private int mOperate;
    private int mCurrentPos;            // 当前选中的位置
    private String mFileSavePath;       // 文件存储位置

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initBeforData() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (getIntent() != null) {
            mDatasList = (ArrayList<AttachmentEntity>) getIntent().getSerializableExtra(KEY_DATA);
            mPosition = getIntent().getIntExtra(KEY_POSITION, 0);
            mOperate = getIntent().getIntExtra(KEY_SHOW_OPERATE, OPERATE_NONE);
            mCurrentPos = mPosition;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_attachment_download;
    }

    @Override
    public void initViews() {
        mModel = new AttachmentDownloadModel();

        viewpager = findViewById(R.id.viewpager);
        currentPageTv = findViewById(R.id.current_page_tv);
        totalPageTv = findViewById(R.id.total_page_tv);
        fileDownloadBf = findViewById(R.id.file_download_bf);
        filePlayBf = findViewById(R.id.file_play_bf);
        fileDeleteBf = findViewById(R.id.file_delete_bf);

        PagerAdapter adapter = new AttachmentDownloadPagerAdapter(this, mDatasList);
        viewpager.setAdapter(adapter);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPos = position;
                setSavePathByPos(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ((AttachmentDownloadPagerAdapter) adapter).setOnClickPhotoViewListener(new AttachmentDownloadPagerAdapter.OnClickPhotoViewListener() {
            @Override
            public void onClickPhotoView() {
                onBackPressed();
            }
        });
        setSavePathByPos(mPosition);
        totalPageTv.setText(mDatasList.size() + "");

        switch (mOperate) {
            case OPERATE_NONE:
                fileDownloadBf.setVisibility(View.GONE);
                filePlayBf.setVisibility(View.GONE);
                fileDeleteBf.setVisibility(View.GONE);
                break;
            case OPERATE_ALL:
                fileDownloadBf.setVisibility(View.VISIBLE);
                filePlayBf.setVisibility(View.VISIBLE);
                fileDeleteBf.setVisibility(View.VISIBLE);
                break;
            case OPERATE_DOWNLOAD:
                fileDownloadBf.setVisibility(View.VISIBLE);
                filePlayBf.setVisibility(View.GONE);
                fileDeleteBf.setVisibility(View.GONE);
                break;
            case OPERATE_PLAY:
                fileDownloadBf.setVisibility(View.GONE);
                filePlayBf.setVisibility(View.VISIBLE);
                fileDeleteBf.setVisibility(View.GONE);
                break;
            case OPERATE_DELETE:
                fileDownloadBf.setVisibility(View.GONE);
                filePlayBf.setVisibility(View.GONE);
                fileDeleteBf.setVisibility(View.VISIBLE);
                break;
            case OPERATE_DOWNLOAD_PLAY:
                fileDownloadBf.setVisibility(View.VISIBLE);
                filePlayBf.setVisibility(View.VISIBLE);
                fileDeleteBf.setVisibility(View.GONE);
                break;
            case OPERATE_DOWNLOAD_DELETE:
                fileDownloadBf.setVisibility(View.VISIBLE);
                filePlayBf.setVisibility(View.GONE);
                fileDeleteBf.setVisibility(View.VISIBLE);
                break;
            case OPERATE_PLAY_DELETE:
                fileDownloadBf.setVisibility(View.GONE);
                filePlayBf.setVisibility(View.VISIBLE);
                fileDeleteBf.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void setListeners() {
        fileDownloadBf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttachmentEntity entity = mDatasList.get(mCurrentPos);
                download(entity.getFilePath());
            }
        });
        filePlayBf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mFileSavePath)) {
                    onOpenFile(mFileSavePath);
                }
            }
        });
        fileDeleteBf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                onBackPressed();
            }
        });
    }

    private void setSavePathByPos(int position) {
        if (position >= mDatasList.size())
            return;

        AttachmentEntity entity = mDatasList.get(position);
        currentPageTv.setText((position + 1) + "");
        viewpager.setCurrentItem(position);
    }

    private void download(String filePath) {
        if (!filePath.startsWith("http")) {
            return;
        }

        mFileSavePath = BaseCoreApp.getDownloadImagePath() + System.currentTimeMillis() + "" + new Random().nextInt(100000) + ".jpg";
        mModel.download(filePath, mFileSavePath, new HashMap<String, String>(), new RetrofitResultListener<File>() {

            @Override
            public void onStart() {
                showLoading();
            }

            @Override
            public void onEnd() {
                hideLoading();
            }

            @Override
            public void onSuccess(File data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //发送相册通知
                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        Uri uri = Uri.fromFile(new File(mFileSavePath));
                        intent.setData(uri);
                        getApplication().sendBroadcast(intent);
                        ToastUtil.getShortToastByString(AttachmentDownloadActivity.this, "保存成功");
                        fileDownloadBf.setVisibility(View.GONE);
                        //                        filePlayBf.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onFailure(String message) {

            }

            @Override
            public void onProgress(int fileSize, int progress, int percent) {

            }
        });
    }

    /**
     * 打开文件
     *
     * @param fileName
     */
    private void onOpenFile(String fileName) {
        File file = new File(fileName);

        // 获取文件file的MIME类型
        String type = FileUtil.getMIMEType(file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri data;
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
            data = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(file);
        }
        intent.setDataAndType(data, type);
        startActivity(intent);
    }
}
