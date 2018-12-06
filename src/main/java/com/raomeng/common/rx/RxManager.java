package com.raomeng.common.rx;

import com.raomeng.common.app.AppManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author RaoMeng
 * @describe
 * @date 2018/1/9 16:45
 */

public class RxManager {

    /**
     * 通过CompositeSubscription收集订阅者
     */
    protected CompositeDisposable mCompositeDisposable;

    private ConcurrentHashMap<Object, List<Disposable>> mDisposables = new ConcurrentHashMap<Object, List<Disposable>>();

    /**
     * 统一取消订阅
     */
    public void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    /**
     * 取消相应订阅
     */
    public void unSubscribe(Object disposeKey) {
        if (mCompositeDisposable != null) {
            List<Disposable> disposables = mDisposables.get(disposeKey);
            for (Disposable disposable : disposables) {
                mCompositeDisposable.remove(disposable);
            }
        }
    }

    /**
     * 添加自定义订阅
     *
     * @param subscription
     */
    public void addSubscribe(Disposable subscription) {
        addSubscribe(AppManager.getInstance().currentActivity().getClass().getSimpleName(), subscription);
    }

    /**
     * 添加自定义订阅
     *
     * @param subscription
     */
    public void addSubscribe(Object disposeKey, Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);

        List<Disposable> disposables = mDisposables.get(disposeKey);
        if (disposables == null) {
            disposables = new ArrayList<>();
        }
        disposables.add(subscription);
        mDisposables.put(disposeKey, disposables);
    }

    /**
     * 添加默认订阅
     *
     * @param eventType
     * @param act
     * @param <U>
     */
    public <U> void addSubscribe(Object disposeKey, Class<U> eventType, Consumer<U> act) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        Disposable disposable = RxBus.getInstance().toDefaultFlowable(eventType, act);
        mCompositeDisposable.add(disposable);

        List<Disposable> disposables = mDisposables.get(disposeKey);
        if (disposables == null) {
            disposables = new ArrayList<>();
        }
        disposables.add(disposable);
        mDisposables.put(disposeKey, disposables);
    }

}
