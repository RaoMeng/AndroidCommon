package com.raomeng.common.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author RaoMeng
 * @describe 添加公共参数
 * @date 2018/1/3 15:17
 */

public class ParamsInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request mRequest = chain.request();
        return chain.proceed(mRequest);
    }
}
