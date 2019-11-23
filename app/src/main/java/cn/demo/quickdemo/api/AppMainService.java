package cn.demo.quickdemo.api;


import android.text.TextUtils;

import cn.demo.quickdemo.api.service.AttachmentUploadService;
import cn.plugin.core.BaseConfig;
import cn.plugin.core.api.BaseMainService;
import cn.plugin.core.api.ProgressCallback;
import cn.plugin.core.api.base.BaseApi;

/**
 * 通用接口
 * Created by NJQ on 2016/11/10.
 */
public class AppMainService extends BaseMainService {

    private static AttachmentUploadService attachmentUploadService;

    /**
     * 附件上传
     * @param url
     * @param callback
     * @return
     */
    public static AttachmentUploadService getAttachmentUploadService(String url, ProgressCallback callback) {
        if (TextUtils.isEmpty(url)) {
            url = BaseConfig.BASE_URL;
        }
        attachmentUploadService = BaseApi.retrofitByProgress(url, callback).create(AttachmentUploadService.class);
        return attachmentUploadService;
    }
}
