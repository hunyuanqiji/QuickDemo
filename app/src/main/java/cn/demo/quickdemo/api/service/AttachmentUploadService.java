package cn.demo.quickdemo.api.service;

import java.util.List;

import cn.plugin.core.bean.BaseTResultBean;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by NJQ on 2017/5/6.
 */

public interface AttachmentUploadService {

    /**
     * 上传多媒体资料
     */
    @Multipart
    @POST("rest/practitioners/{accounts}/files/{patientId}/upload/2")
    Call<BaseTResultBean> uploadMedia(@Path("accounts") String accounts, @Path("patientId") String patientId, @Part List<MultipartBody.Part> file);
}
