package com.raomeng.common.network;

import com.google.gson.Gson;
import com.raomeng.common.BuildConfig;
import com.raomeng.common.base.BaseApplication;
import com.raomeng.common.network.converter.StringConverterFactory;
import com.raomeng.common.network.http.HttpService;
import com.raomeng.common.network.http.HttpUrls;
import com.raomeng.common.network.interceptor.CacheInterceptor;
import com.raomeng.common.network.interceptor.HttpLoggerInterceptor;
import com.raomeng.common.network.interceptor.ParamsInterceptor;
import com.raomeng.common.network.logger.HttpLogger;
import com.raomeng.common.network.ssl.TrustAllCerts;
import com.raomeng.common.network.ssl.TrustAllHostnameVerifier;

import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author RaoMeng
 * @describe
 * @date 2018/1/3 17:45
 */

public class RetrofitFactory {
    private static final long HTTP_CONNECT_TIME_OUT = 30;
    private static final long HTTP_READ_TIME_OUT = 30;
    private static final long HTTP_WRITE_TIME_OUT = 30;
    private static final long CACHE_MAX_SIZE = 100 * 1024 * 1024;

    private HttpService mHttpService;
    public Retrofit retrofit;
    private static Map<HostType, RetrofitFactory> sRetrofitManager = new HashMap<>(HostType.values().length);

    private RetrofitFactory(HostType hostType) {
        OkHttpClient.Builder mOkBuilder = new OkHttpClient.Builder()
                .connectTimeout(HTTP_CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(HTTP_READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(HTTP_WRITE_TIME_OUT, TimeUnit.SECONDS)
                .sslSocketFactory(createSSLSocketFactory(), new TrustAllCerts())// 信任所有证书
                .hostnameVerifier(new TrustAllHostnameVerifier())
                .cookieJar(new CookieJar() {
                    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                        cookieStore.put(httpUrl.host(), list);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                        List<Cookie> cookies = cookieStore.get(httpUrl.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                });

        HttpLoggerInterceptor loggerInterceptor = new HttpLoggerInterceptor(new HttpLogger());
        if (BuildConfig.DEBUG) {
            loggerInterceptor.setLevel(HttpLoggerInterceptor.Level.BODY);
        } else {
            loggerInterceptor.setLevel(HttpLoggerInterceptor.Level.NONE);
        }

        ParamsInterceptor netInterceptor = new ParamsInterceptor();
        File cacheFile = new File(BaseApplication.getAppContext().getCacheDir(), "cache");

        OkHttpClient mOkHttpClient = mOkBuilder
                .addNetworkInterceptor(netInterceptor)
                .addInterceptor(loggerInterceptor)
                .addInterceptor(new CacheInterceptor())
                .cache(new Cache(cacheFile, CACHE_MAX_SIZE)).build();
        retrofit = new Retrofit.Builder().client(mOkHttpClient)
                .baseUrl(HttpUrls.getHost(hostType))
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mHttpService = retrofit.create(HttpService.class);
    }

    public static HttpService API(HostType hostType) {
        if (sRetrofitManager == null) {
            sRetrofitManager = new HashMap<>(HostType.values().length);
        }
        RetrofitFactory retrofitManager = sRetrofitManager.get(hostType);
        if (retrofitManager == null) {
            retrofitManager = new RetrofitFactory(hostType);
            sRetrofitManager.put(hostType, retrofitManager);
        }
        return retrofitManager.mHttpService;
    }


    public SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }
        return ssfFactory;
    }
}
