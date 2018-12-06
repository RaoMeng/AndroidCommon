package com.raomeng.common.network.http;

import com.raomeng.common.network.RetrofitFactory;
import com.raomeng.common.network.observer.HttpObserver;
import com.raomeng.common.rx.RxScheduler;

import io.reactivex.disposables.Disposable;


/**
 * @author RaoMeng
 * @describe 发起请求
 * @date 2018/1/4 11:41
 */

public class HttpRequest {

    private static HttpRequest instance;

    private HttpRequest() {

    }

    public static final HttpRequest getInstance() {
        if (instance == null) {
            synchronized (HttpRequest.class) {
                if (instance == null) {
                    instance = new HttpRequest();
                }
            }
        }
        return instance;
    }

    public Disposable sendRequest(HttpParams httpParams, HttpCallback<Object> callback) {
        if (httpParams == null) {
            throw new IllegalArgumentException("httpParams can not be NULL");
        }
        if (HttpMethod.POST.equals(httpParams.getMethod())) {
            return postRequest(httpParams, callback);
        } else {
            return getRequest(httpParams, callback);
        }
    }

    private Disposable getRequest(final HttpParams httpParams, final HttpCallback<Object> callback) {

        if (httpParams.getHostType() == null) {
            throw new IllegalArgumentException("hostType can not be NULL");
        }

        if (httpParams.getUrl() == null) {
            throw new IllegalArgumentException("url can not be NULL");
        }

        if (callback == null) {
            throw new IllegalArgumentException("callback can not be NULL");
        }

        return RetrofitFactory.API(httpParams.getHostType())
                .getRequest(httpParams.getUrl(), httpParams.getParams(), httpParams.getHeaders())
                .compose(RxScheduler.io2mainF())
                .subscribeWith(new HttpObserver<Object>(new com.raomeng.common.network.observer.HttpCallback() {

                    @Override
                    public void onSuccess(Object o) throws Exception {
                        callback.onSuccess(httpParams.getFlag(), o);
                    }

                    @Override
                    public void onFailure(String failMsg, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError) {
                            failMsg = "网络连接出错，请检查网络连接";
                        }
                        callback.onFail(httpParams.getFlag(), failMsg);
                    }
                }));
    }

    private Disposable postRequest(final HttpParams httpParams, final HttpCallback<Object> callback) {
        if (httpParams.getHostType() == null) {
            throw new IllegalArgumentException("hostType can not be NULL");
        }

        if (httpParams.getUrl() == null) {
            throw new IllegalArgumentException("url can not be NULL");
        }

        if (callback == null) {
            throw new IllegalArgumentException("callback can not be NULL");
        }

        return RetrofitFactory.API(httpParams.getHostType())
                .postRequest(httpParams.getUrl(), httpParams.getParams(), httpParams.getHeaders())
                .compose(RxScheduler.io2mainF())
                .subscribeWith(new HttpObserver<Object>(new com.raomeng.common.network.observer.HttpCallback() {

                    @Override
                    public void onSuccess(Object o) throws Exception {
                        callback.onSuccess(httpParams.getFlag(), o);
                    }

                    @Override
                    public void onFailure(String failMsg, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError) {
                            failMsg = "网络连接出错，请检查网络连接";
                        }
                        callback.onFail(httpParams.getFlag(), failMsg);
                    }
                }));
    }
}
