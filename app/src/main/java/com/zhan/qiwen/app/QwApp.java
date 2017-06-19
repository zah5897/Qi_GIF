package com.zhan.qiwen.app;

import android.app.Application;

import com.zhan.qiwen.model.channel.ChannelManager;
import com.zhan.qiwen.utils.Constant;
import com.zhan.qiwen.utils.KeyStoreHelper;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * Created by zah on 2017/6/6.
 */

public class QwApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            KeyStoreHelper.createKeys(getApplicationContext(), Constant.KEYSTORE_KEY_ALIAS);
        } catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        ChannelManager.get(getApplicationContext()).load();
        CrashHandler.getInstance().init(getApplicationContext());
    }
}
