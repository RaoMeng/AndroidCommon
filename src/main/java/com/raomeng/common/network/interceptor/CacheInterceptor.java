package com.raomeng.common.network.interceptor;

import com.raomeng.common.utils.NetWorkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 缓存拦截器
 *
 * @author Arison
 */
public class CacheInterceptor implements Interceptor {
    private final long CACHE_MAX_TIME = 60 * 60 * 24 * 1;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //要是有网络的话就直接获取网络上面的数据，要是没有网络的话就去缓存里面取数据
        if (!NetWorkUtils.isConnected()) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        }
        Response response = chain.proceed(request);

        if (NetWorkUtils.isConnected()) {
            return response.newBuilder()
                    .removeHeader("Pragma")
                    //这里设置的为0就是说不进行缓存
                    .header("Cache-Control", "public, max-age=" + 0)
                    .build();
        } else {
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    //这里的设置的是没有网络的缓存时间。
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_MAX_TIME)
                    .build();
        }

    }

}
