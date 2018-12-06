package com.raomeng.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;


/**
 * @author RaoMeng
 * @describe MVP模式fragment基类
 * @date 2017/11/12 10:54
 */

public abstract class BaseMvpFragment<T extends BasePresenter> extends BaseSimpleFragment implements BaseView {

    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = initPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    /**
     * 创建presenter
     *
     * @return
     */
    protected abstract T initPresenter();

}
