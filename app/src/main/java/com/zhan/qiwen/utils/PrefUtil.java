package com.zhan.qiwen.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.zhan.qiwen.model.user.entity.UserInfo;

/**
 * 偏好参数存储工具类
 */
public class PrefUtil {
    private static volatile PrefUtil prefUtil;
    private SharedPreferences mSharedPreferences;
    private Editor mEditor;

    private PrefUtil(Context context) {
        mSharedPreferences =
                context.getApplicationContext().getSharedPreferences(Constant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static PrefUtil getInstance(Context context) {
        if (prefUtil == null) {
            synchronized (PrefUtil.class) {
                if (prefUtil == null) {
                    prefUtil = new PrefUtil(context);
                }
            }
        }
        return prefUtil;
    }

    /**
     * 存储登录 Token
     */
    public static void saveToken(Context context, String token) {
        Constant.VALUE_TOKEN = token;
        try {
            EncryUtils.get().encryptString(Constant.Token.ACCESS_TOKEN, token);
        } catch (Exception e) {
            e.printStackTrace();
            Constant.VALUE_TOKEN = "";
            return;
        }
        PrefUtil prefUtil = PrefUtil.getInstance(context);
        prefUtil.putString(Constant.Token.ACCESS_TOKEN, token);
    }

    /**
     * 获取登录 Token
     */
    public static String getToken(Context context) {
        PrefUtil prefUtil = PrefUtil.getInstance(context);
        String token = prefUtil.getString(Constant.Token.ACCESS_TOKEN, "");

        try {
            token = EncryUtils.get().decryptString(Constant.Token.ACCESS_TOKEN, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Constant.VALUE_TOKEN = token;
        return token;
    }

    /**
     * 存储登录信息
     */
    public static void saveMe(Context context, UserInfo userInfo) {
        PrefUtil prefUtil = PrefUtil.getInstance(context);
        prefUtil.putLong(Constant.User.ID, userInfo.getId());
        prefUtil.putString(Constant.User.AVATAR_URL, userInfo.getAvatar());
        prefUtil.putString(Constant.User.NICKNAME, userInfo.getNickname());
    }

    /**
     * 获取登录信息
     */
    public static UserInfo getMe(Context context) {
        PrefUtil prefUtil = PrefUtil.getInstance(context);
        UserInfo info = new UserInfo();
        info.setId(prefUtil.getLong(Constant.User.ID, 0));
        info.setAvatar(prefUtil.getString(Constant.User.AVATAR_URL, ""));
        info.setNickname(prefUtil.getString(Constant.User.NICKNAME, ""));
        return info;
    }

    /**
     * 清理登录信息
     */
    public static void clearMe(Context context) {
        PrefUtil prefUtil = PrefUtil.getInstance(context);
        // User
        prefUtil.putLong(Constant.User.ID, 0);
        prefUtil.putString(Constant.User.AVATAR_URL, "");
        prefUtil.putString(Constant.User.NICKNAME, "");

        // Token
        prefUtil.putString(Constant.Token.ACCESS_TOKEN, "");
        Constant.VALUE_TOKEN = "";
    }

    public SharedPreferences getSP() {
        return mSharedPreferences;
    }

    public Editor getEditor() {
        return mEditor;
    }

    /**
     * 存储数据(Long)
     */
    public void putLong(String key, long value) {
        mEditor.putLong(key, value).apply();
    }

    /**
     * 存储数据(Int)
     */
    public void putInt(String key, int value) {
        mEditor.putInt(key, value).apply();
    }

    /**
     * 存储数据(String)
     */
    public void putString(String key, String value) {
        mEditor.putString(key, value).apply();
    }

    /**
     * 存储数据(boolean)
     */
    public void putBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value).apply();
    }

    /**
     * 取出数据(Long)
     */
    public long getLong(String key, long defValue) {
        return mSharedPreferences.getLong(key, defValue);
    }

    /**
     * 取出数据(int)
     */
    public int getInt(String key, int defValue) {
        return mSharedPreferences.getInt(key, defValue);
    }

    /**
     * 取出数据(boolean)
     */
    public boolean getBoolean(String key, boolean defValue) {
        return mSharedPreferences.getBoolean(key, defValue);
    }

    /**
     * 取出数据(String)
     */
    public String getString(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }

    /**
     * 清空所有数据
     */
    public void clear() {
        mEditor.clear().apply();
    }

    /**
     * 移除指定数据
     */
    public void remove(String key) {
        mEditor.remove(key).apply();
    }
}