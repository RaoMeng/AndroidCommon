package com.raomeng.common.network.observer;

import android.accounts.NetworkErrorException;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

/**
 * @author RaoMeng
 * @describe 网络结果观察者
 * @date 2018/1/4 10:42
 */

public class HttpObserver<T> extends ResourceSubscriber<T> {
    private HttpCallback<T> mHttpCallback;

    public HttpObserver(HttpCallback<T> httpCallback) {
        if (httpCallback != null) {
            this.mHttpCallback = httpCallback;
        }
    }

    @Override
    public void onNext(T t) {
        try {
            if (mHttpCallback != null) {
                mHttpCallback.onSuccess(t);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onError(Throwable e) {
        try {
            if (mHttpCallback != null) {
                if (e instanceof ConnectException
                        || e instanceof TimeoutException
                        || e instanceof NetworkErrorException
                        || e instanceof UnknownHostException) {
                    mHttpCallback.onFailure("", true);
                } else if (e instanceof HttpException) {
                    HttpException httpException = (HttpException) e;
                    mHttpCallback.onFailure(httpException.response().errorBody().string(), false);
                } else {
                    mHttpCallback.onFailure(e.getMessage(), false);
                }
            }
        } catch (Exception exception) {

        }

    }

    @Override
    public void onComplete() {

    }

}
