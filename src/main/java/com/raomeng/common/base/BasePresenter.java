package com.raomeng.common.base;


/**
 * @author RaoMeng
 * @describe Presenter层基础接口
 * @date 2017/11/9 14:10
 */

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);

    void detachView();
}
