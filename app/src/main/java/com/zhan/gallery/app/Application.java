package com.zhan.gallery.app;

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
        CrashHandler.get().init(getApplicationContext());
    }
}
