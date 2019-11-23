package cn.plugin.core.api;


import android.text.TextUtils;

import cn.plugin.core.api.base.BaseApi;
import cn.plugin.core.api.service.AttachmentDownloadService;

/**
 * 通用接口
 * Created by NJQ on 2016/11/10.
 */
public class BaseMainService {

    private static AttachmentDownloadService attachmentService;

    /**
     * 附件下载
     * @param url
     * @param callback
     * @return
     */
    public static AttachmentDownloadService getAttachmentDownloadService(String url, ProgressCallback callback) {
        if (TextUtils.isEmpty(url)){
            // BaseUrl不能为空，设个假的，用不到
            url = "https://apitest.mdruby.cn/";
        }
        attachmentService = BaseApi.retrofitByProgress(url, callback).create(AttachmentDownloadService.class);

        return attachmentService;
    }
}
