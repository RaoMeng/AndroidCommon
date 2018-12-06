package com.raomeng.common.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * @author RaoMeng
 * @describe 应用的activity生命周期统一管理类
 * @date 2018/1/10 10:47
 */

public class AppActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        //将activity添加到管理类中管理
        AppManager.getInstance().addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        //页面退出时将activity对象移除
        AppManager.getInstance().removeActivity(activity);
    }
}
