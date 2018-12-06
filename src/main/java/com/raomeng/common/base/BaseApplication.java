package com.raomeng.common.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.multidex.MultiDex;

import com.raomeng.common.app.AppActivityLifecycleCallbacks;
import com.raomeng.common.service.InitializeService;

/**
 * @author RaoMeng
 * @describe
 * @date 2018/1/2 9:21
 */

public class BaseApplication extends Application {

    private static BaseApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplication = this;

        InitializeService.initApplication(this);

        registerActivityLifecycleCallbacks(new AppActivityLifecycleCallbacks());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getAppContext() {
        return mApplication;
    }

    public static Resources getAppResources() {
        return mApplication.getResources();
    }
}
