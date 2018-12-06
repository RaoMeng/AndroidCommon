package com.raomeng.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;


/**
 * @author RaoMeng
 * @describe MVP模式Activity基类
 * @date 2017/11/9 14:20
 */

public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseSimpleActivity implements BaseView {
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = initPresenter();

        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    protected abstract T initPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

}
