package com.raomeng.common.network.http;

/**
 * @author RaoMeng
 * @describe 回调接口
 * @date 2017/11/9 14:47
 */

public interface HttpCallback<T> {

    void onSuccess(int flag, T t);

    void onFail(int flag, String failStr);
}
