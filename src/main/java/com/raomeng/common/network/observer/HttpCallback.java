package com.raomeng.common.network.observer;

/**
 * @author RaoMeng
 * @describe 网络回调接口
 * @date 2018/1/4 11:12
 */

public interface HttpCallback<T> {
    void onSuccess(T t) throws Exception;

    void onFailure(String failMsg, boolean isNetWorkError) throws Exception;
}
