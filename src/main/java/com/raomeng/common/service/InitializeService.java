package com.raomeng.common.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * @author RaoMeng
 * @describe 初始化应用配置
 * @date 2018/1/5 16:55
 */

public class InitializeService extends IntentService {
    private static final String ACTION_INIT_APPLICATION = "initApplication";

    public InitializeService() {
        super("InitializeService");
    }

    public static void initApplication(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT_APPLICATION);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_INIT_APPLICATION.equals(action)) {
                // TODO: 2018/1/5 初始化应用配置
            }
        }
    }
}
