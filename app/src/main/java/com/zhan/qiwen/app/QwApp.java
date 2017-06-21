package com.zhan.qiwen.app;

import android.app.Application;

import com.zhan.qiwen.model.channel.ChannelManager;
import com.zhan.qiwen.utils.EncryUtils;

/**
 * Created by zah on 2017/6/6.
 */

public class QwApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        EncryUtils.getInstance(getApplicationContext());
        ChannelManager.get(getApplicationContext()).load();
        CrashHandler.getInstance().init(getApplicationContext());
    }
}
