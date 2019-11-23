package cn.plugin.core.api.service;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by NJQ on 2017/5/6.
 */

public interface AttachmentDownloadService {

    /**
     * 下载文件
     *
     * @param fileUrl
     * @return
     */
    @Streaming
    @GET
    Call<ResponseBody> download(@Url String fileUrl, @QueryMap HashMap<String, String> params);
}
