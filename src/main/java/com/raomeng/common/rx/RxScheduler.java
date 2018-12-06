package com.raomeng.common.rx;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author RaoMeng
 * @describe RxJava2线程调度管理
 * @date 2018/1/4 11:21
 */

public class RxScheduler {

    public static <T> ObservableTransformer<T, T> io2main() {
        return schedulerTransformer(Schedulers.io());
    }

    private static <T> ObservableTransformer<T, T> schedulerTransformer(final Scheduler scheduler) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(scheduler)
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> FlowableTransformer<T, T> io2mainF() {
        return schedulerTransformerF(Schedulers.io());
    }

    private static <T> FlowableTransformer<T, T> schedulerTransformerF(final Scheduler scheduler) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(scheduler)
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
