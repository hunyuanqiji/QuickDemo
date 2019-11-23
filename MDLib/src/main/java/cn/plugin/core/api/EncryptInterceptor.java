package cn.plugin.core.api;

import com.dev.des3.Des3;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

import cn.plugin.core.BaseCoreApp;
import cn.plugin.core.BaseConfig;
import cn.plugin.core.utils.LogUtil;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 加密解密拦截器
 * Created by yy on 2018/9/28.
 **/
public class EncryptInterceptor implements Interceptor {

    private static final String TAG = EncryptInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //这个是请求的url，也就是咱们前面配置的baseUrl
        String url = request.url().toString();
        //这个是请求方法
        String method = request.method();

        LogUtil.e(TAG, "加密前onStart: " + url);
        if (method.equals("GET")) {
            request = encrypt(request); // 加密方法
        }else {
            if (BaseCoreApp.DEBUG && request.body() != null){
                Buffer buffer = new Buffer();
                request.body().writeTo(buffer);

                MediaType contentType = request.body().contentType();
                if (contentType != null) {
                    Charset charset = contentType.charset(Charset.forName("UTF-8"));
                    if (charset != null){
                        LogUtil.e(TAG, "POST参数: " + buffer.readString(charset));
                    }
                }
            }
        }
        Response response = chain.proceed(request);
        response = decrypt(response);

        return response;
    }

    // 加密
    private Request encrypt(Request request) throws IOException {
        String originStr = getStringBuilder(request);
        String encryptStr = "";
        try {
            //加密操作
            encryptStr = Des3.encodeBase64(originStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpUrl.Builder builder = request.url().newBuilder();
        for (String str : request.url().queryParameterNames()) {
            builder.removeAllQueryParameters(str);
        }
        HttpUrl httpUrl = builder
                .addQueryParameter("code", encryptStr)
                .addQueryParameter("version", BaseConfig.VERSION_NAME)
                .build();
        request = request.newBuilder().url(httpUrl).build();

        LogUtil.e(TAG, "加密后onStart: " + httpUrl.url().toString());

        return request;
    }

    // 解密
    private Response decrypt(Response response) throws IOException {
        if (response.isSuccessful()) {
            //the response data
            ResponseBody body = response.body();
            BufferedSource source = body.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            Charset charset = Charset.defaultCharset();
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            String string = buffer.clone().readString(charset);
            //解密
            JSONObject object = null;
            try {
                object = new JSONObject(string);
                if (!object.isNull("data")) {
                    String data = object.getString("data");
                    if (object.isNull("isdes")) {

                    } else {
                        if (object.getBoolean("isdes")) {
                            //对返回数据解密
                            data = Des3.decodeBase64(data);//解密
                            object.put("data", data.startsWith("[") ? new JSONArray(data) : new JSONObject(data));
                        }
                    }
                    LogUtil.e(TAG, "onSuccess: data= " + data);
                }

                ResponseBody responseBody = ResponseBody.create(contentType, object.toString());
                response = response.newBuilder().body(responseBody).build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    private String getStringBuilder(Request request) {
        HttpUrl httpUrl = request.url();
        StringBuilder result = new StringBuilder();
        for (String key : httpUrl.queryParameterNames()) {
            if (result.length() > 0) {
                result.append("&");
            }
            if (!key.equals("")) {
                String value = httpUrl.queryParameter(key);
                result.append(key).append("=").append(value);
            }
        }
        return result.toString();
    }
}
