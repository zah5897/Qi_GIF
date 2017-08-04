package com.zhan.gallery.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;

import com.zhan.gallery.app.Application;

import java.util.Random;

/**
 * 设备通用类
 * Created by wuxubaiyang on 16/3/11.
 */
public class DeviceUtils {
    /**
     * 获取屏幕宽度
     *
     * @param activity activity
     * @return 屏幕宽度
     */
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param activity activity
     * @return 屏幕高度
     */
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    private static String imei;
    private static final String IMEI = "imei";
    private static int versionCode;

    public static final String getUniqueID() {
        int t1 = (int) (System.currentTimeMillis() / 1000L);
        int t2 = (int) System.nanoTime();
        int t3 = new Random().nextInt();
        int t4 = new Random().nextInt();
        byte[] b1 = getBytes(t1);
        byte[] b2 = getBytes(t2);
        byte[] b3 = getBytes(t3);
        byte[] b4 = getBytes(t4);
        byte[] bUniqueID = new byte[16];
        System.arraycopy(b1, 0, bUniqueID, 0, 4);
        System.arraycopy(b2, 0, bUniqueID, 4, 4);
        System.arraycopy(b3, 0, bUniqueID, 8, 4);
        System.arraycopy(b4, 0, bUniqueID, 12, 4);
        return Base64.encodeToString(bUniqueID, 2);
    }

    private static byte[] getBytes(int i) {
        byte[] bInt = new byte[4];
        int value = i;
        bInt[3] = ((byte) (value % 256));
        value >>= 8;
        bInt[2] = ((byte) (value % 256));
        value >>= 8;
        bInt[1] = ((byte) (value % 256));
        value >>= 8;
        bInt[0] = ((byte) (value % 256));
        return bInt;
    }

    public static String getImei() {
        String imei = null;
        try {
            TelephonyManager tm = (TelephonyManager) Application.getApp().getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null)
                imei = tm.getDeviceId();
        } catch (Exception e) {
        }
        if (TextUtils.isEmpty(imei)) {
            imei = getUniqueID();
        }
        return imei.trim();
    }

    public static String loadImei() {

        if (!TextUtils.isEmpty(imei)) {
            return imei;
        }
        imei = PrefUtil.get().getString(IMEI, "");
        String fileName = String.valueOf(IMEI.hashCode());
        if (!TextUtils.isEmpty(imei)) {
            FileUtils.saveContent(fileName, imei);
            return imei;
        }

        imei = FileUtils.readContent(fileName);
        if (!TextUtils.isEmpty(imei)) {
            PrefUtil.get().putString(IMEI, imei);
            return imei;
        }
        imei = getImei();

        PrefUtil.get().putString(IMEI, imei);
        FileUtils.saveContent(fileName, imei);
        return imei;
    }

    public static int getVersionCode() {

        if (versionCode > 0) {
            return versionCode;
        }
        PackageManager pm = Application.getApp().getPackageManager();//context为当前Activity上下文
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(Application.getApp().getPackageName(), 0);
            return versionCode = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getVersionName() {
        PackageManager pm = Application.getApp().getPackageManager();//context为当前Activity上下文
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(Application.getApp().getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}