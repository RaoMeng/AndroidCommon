package com.raomeng.common.app;

/**
 * @author RaoMeng
 * @describe 应用缓存数据
 * @date 2018/1/3 13:36
 */

public class AppCache {
    private static AppCache instance;

    private AppCache() {

    }

    public static AppCache getInstance() {
        if (instance == null) {
            synchronized (AppCache.class) {
                if (instance == null) {
                    instance = new AppCache();
                }
            }
        }
        return instance;
    }

}
