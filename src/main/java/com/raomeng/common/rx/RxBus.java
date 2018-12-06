package com.raomeng.common.rx;

import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * @author RaoMeng
 * @describe RxJava2实现线程通信
 * @date 2018/1/4 17:31
 */

public class RxBus {
    private static RxBus instance;

    private final FlowableProcessor<Object> mEventBus;

    private ConcurrentHashMap<Integer, RxEvent> mStickyEvents;

    private RxBus() {
        mEventBus = PublishProcessor.create().toSerialized();
        mStickyEvents = new ConcurrentHashMap<>();
    }

    public static RxBus getInstance() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }


    /**
     * 发送消息
     *
     * @param o
     */
    public void post(Object o) {
        mEventBus.onNext(o);
    }


    /**
     * 发送黏性消息
     *
     * @param event
     */
    public <T> void postSticky(RxEvent<T> event) {
        mStickyEvents.put(event.getFlag(), event);
        mEventBus.onNext(event);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     *
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Flowable<T> toFlowable(Class<T> eventType) {
        return mEventBus.ofType(eventType);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 黏性被观察者
     *
     * @param
     * @param
     * @return
     */
    public Flowable<RxEvent> toFlowableSticky(int flag) {
        Flowable flowable = mEventBus.ofType(RxEvent.class);
        final Object event = mStickyEvents.get(flag);
        if (event == null) {
            return flowable;
        } else {
            return flowable.mergeWith(Flowable.create(new FlowableOnSubscribe<RxEvent>() {
                @Override
                public void subscribe(FlowableEmitter<RxEvent> e) throws Exception {
                    e.onNext(RxEvent.class.cast(event));
                }
            }, BackpressureStrategy.LATEST));
        }
    }

    /**
     * 获取默认订阅者
     *
     * @param eventType
     * @param act
     * @param <T>
     * @return
     */
    public <T> Disposable toDefaultFlowable(final Class<T> eventType, final Consumer<T> act) {
        return toFlowable(eventType)
                .compose(RxScheduler.<T>io2mainF())
                .subscribe(act, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        toDefaultFlowable(eventType, act);
                    }
                });
    }

    /**
     * 获取默认黏性订阅者
     *
     * @param
     * @param act
     * @param
     * @return
     */
    public Disposable toDefaultStickyFlowable(final int flag, final Consumer act) {
        return toFlowableSticky(flag)
                .compose(RxScheduler.io2mainF())
                .subscribe(act, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        toDefaultStickyFlowable(flag, act);
                    }
                });
    }

    /**
     * 判断是否有订阅者
     *
     * @return
     */
    public boolean hasSubscribers() {
        return mEventBus.hasSubscribers();
    }

    /**
     * 移除指定eventType的Sticky事件
     */
    public <T> T removeStickyEvent(Class<T> eventType) {
        return eventType.cast(mStickyEvents.remove(eventType));
    }

    /**
     * 移除所有的Sticky事件
     * 在应用退出是
     */
    public void removeAllStickyEvents() {
        mStickyEvents.clear();
    }
}
