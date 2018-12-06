package com.raomeng.common.network.logger;

import android.util.Log;

import com.raomeng.common.network.interceptor.HttpLoggerInterceptor;


/**
 * @author RaoMeng
 * @describe 请求信息打印类
 * @date 2017/12/13 14:53
 */

public class HttpLogger implements HttpLoggerInterceptor.Logger {
    @Override
    public void log(String message) {
        if (message != null) {
            Log.d("HttpLogger", message);
        }
    }
}
