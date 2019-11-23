package cn.plugin.core.api.base;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.plugin.core.utils.LogUtil;
import cn.plugin.core.BaseCoreApp;
import cn.plugin.core.BaseConfig;
import cn.plugin.core.api.EncryptInterceptor;
import cn.plugin.core.api.FileResponseBody;
import cn.plugin.core.api.ProgressCallback;
import cn.plugin.core.api.SSLSocketFactoryUtils;
import cn.plugin.core.utils.BaseSpUtil;
import cn.plugin.core.utils.NetWorkUtils;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zbmobi on 16/9/9.
 */
public class BaseApi {

    private static final String TAG = BaseApi.class.getSimpleName();

    //读超时长，单位：毫秒
    public static final int READ_TIME_OUT = 30 * 1000;
    //连接时长，单位：毫秒
    public static final int CONNECT_TIME_OUT = 30 * 1000;

    /*************************缓存设置*********************/
   /*
    1. noCache 不使用缓存，全部走网络
    2. noStore 不使用缓存，也不存储缓存
    3. onlyIfCached 只使用缓存
    4. maxAge 设置最大失效时间，失效则不使用 需要服务器配合
    5. maxStale 设置最大失效时间，失效则不使用 需要服务器配合 感觉这两个类似 还没怎么弄清楚，清楚的同学欢迎留言
    6. minFresh 设置有效时间，依旧如上
    7. FORCE_NETWORK 只走网络
    8. FORCE_CACHE 只走缓存*/

    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
     */
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     */
    private static final String CACHE_CONTROL_AGE = "max-age=0";

    public static Retrofit mRetrofit;
    private static HttpLoggingInterceptor logInterceptor = null;
    private static Interceptor headerInterceptor = null;
    private static OkHttpClient okHttpClient = null;

    private static String mBaseUrl = "";

    public static Retrofit retrofit() {
        //增加头部信息
        headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String token = BaseSpUtil.getString(BaseCoreApp.getAppContext(), BaseSpUtil.KEY_TOKEN, "");
                LogUtil.e(TAG, "token=" + token);
                Request build = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("token", token)
                        .build();
                return chain.proceed(build);
            }
        };
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .sslSocketFactory(SSLSocketFactoryUtils.createSSLSocketFactory(), SSLSocketFactoryUtils.createTrustAllManager())
                .hostnameVerifier(new SSLSocketFactoryUtils.TrustAllHostnameVerifier())
                .addInterceptor(new EncryptInterceptor())
                .addInterceptor(headerInterceptor)
                .build();

        mBaseUrl = BaseConfig.BASE_URL;
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(mBaseUrl)
                .build();
        return mRetrofit;
    }

    /**
     * 自定义BaseUrl请求
     *
     * @param url
     * @return
     */
    public static Retrofit retrofit(String url) {
        //增加头部信息
        headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String token = BaseSpUtil.getString(BaseCoreApp.getAppContext(), BaseSpUtil.KEY_TOKEN, "");
                LogUtil.e(TAG, "token=" + token);
                Request build = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("token", token)
                        .build();
                return chain.proceed(build);
            }
        };
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .sslSocketFactory(SSLSocketFactoryUtils.createSSLSocketFactory(), SSLSocketFactoryUtils.createTrustAllManager())
                .hostnameVerifier(new SSLSocketFactoryUtils.TrustAllHostnameVerifier())
                .addInterceptor(new EncryptInterceptor())
                .addInterceptor(headerInterceptor)
                .build();

        // baseUrl must end in /
        if (!url.endsWith("/")) {
            url = url + "/";
        }
        mBaseUrl = url;
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(mBaseUrl)
                .build();
        return mRetrofit;

    }

    /**
     * 自定义BaseUrl请求 不加密
     *
     * @param url
     * @return
     */
    public static Retrofit retrofitNoEncrypt(String url) {
        //增加头部信息
        headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String token = BaseSpUtil.getString(BaseCoreApp.getAppContext(), BaseSpUtil.KEY_TOKEN, "");
                LogUtil.e(TAG, "token=" + token);
                Request build = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("token", token)
                        .build();
                return chain.proceed(build);
            }
        };
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .sslSocketFactory(SSLSocketFactoryUtils.createSSLSocketFactory(), SSLSocketFactoryUtils.createTrustAllManager())
                .hostnameVerifier(new SSLSocketFactoryUtils.TrustAllHostnameVerifier())
                .addInterceptor(headerInterceptor)
                .build();

        // baseUrl must end in /
        if (!url.endsWith("/")) {
            url = url + "/";
        }
        mBaseUrl = url;
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(mBaseUrl)
                .build();
        return mRetrofit;

    }

    /**
     * 下载文件专用，支持进度条
     *
     * @return
     */
    public static <T> Retrofit retrofitByProgress(String url, final ProgressCallback<T> callback) {
        //增加头部信息
        headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String token = BaseSpUtil.getString(BaseCoreApp.getAppContext(), BaseSpUtil.KEY_TOKEN, "");
                LogUtil.e(TAG, "token=" + token);
                Request build = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("token", token)
                        .build();
                return chain.proceed(build);
            }
        };
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .sslSocketFactory(SSLSocketFactoryUtils.createSSLSocketFactory(), SSLSocketFactoryUtils.createTrustAllManager())
                .hostnameVerifier(new SSLSocketFactoryUtils.TrustAllHostnameVerifier())
//                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .addInterceptor(headerInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Response response = chain.proceed(chain.request());
                        //将ResponseBody转换成我们需要的FileResponseBody
                        return response.newBuilder().body(new FileResponseBody<T>(response.body(), callback)).build();
                    }
                })
                .build();

        // baseUrl must end in /
        if (!url.endsWith("/")) {
            url = url + "/";
        }
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();
        return mRetrofit;

    }

    public static class RequestLoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            LogUtil.d(TAG, String.format("发送请求 %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));
            return chain.proceed(request);
        }
    }

    /**
     * 根据网络状况获取缓存的策略
     */
    @NonNull
    public static String getCacheControl() {
        return NetWorkUtils.isNetConnected(BaseCoreApp.getAppContext()) ? CACHE_CONTROL_AGE : CACHE_CONTROL_CACHE;
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private final static Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String cacheControl = request.cacheControl().toString();
            if (!NetWorkUtils.isNetConnected(BaseCoreApp.getAppContext())) {
                request = request.newBuilder()
                        .cacheControl(TextUtils.isEmpty(cacheControl) ? CacheControl.FORCE_NETWORK : CacheControl.FORCE_CACHE)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetWorkUtils.isNetConnected(BaseCoreApp.getAppContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置

                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };
}

