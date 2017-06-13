package com.zhan.qiwen.app;

import android.app.Application;

import com.zhan.qiwen.model.channel.ChannelManager;

/**
 * Created by zah on 2017/6/6.
 */

public class QwApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ChannelManager.get(getApplicationContext()).load();
    }
}
