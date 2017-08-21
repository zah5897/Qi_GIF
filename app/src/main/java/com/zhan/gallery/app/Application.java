package com.zhan.gallery.app;

import com.zhan.gallery.model.service.ChannelManager;

/**
 * Created by zah on 2017/6/6.
 */

public class Application extends android.app.Application {
    private static Application app;

    public static Application getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        ChannelManager.get().load();
        CrashHandler.get().init(getApplicationContext());
    }
}
