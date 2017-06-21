package com.zhan.qiwen.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;

import java.util.Random;

/**
 * Created by zah on 2017/6/20.
 */

public class PhoneInfoUtils {
    private static String imei;

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

    public static String getImei(Context context) {
        String imei = null;
        if (context != null) {
            try {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (tm != null)
                    imei = tm.getDeviceId();
            } catch (Exception e) {
            }
        }
        if (TextUtils.isEmpty(imei)) {
            imei = getUniqueID();
        }
        return imei;
    }

    public static String loadImei(Context context) {

        if (!TextUtils.isEmpty(imei)) {
            return imei;
        }
        imei = PrefUtil.getInstance(context).getString(Constant.IMEI, "");
        String fileName = String.valueOf(Constant.IMEI.hashCode());
        if (!TextUtils.isEmpty(imei)) {
            FileUtils.saveContent(fileName, imei);
            return imei;
        }

        imei = FileUtils.readContent(fileName);
        if (!TextUtils.isEmpty(imei)) {
            PrefUtil.getInstance(context).putString(Constant.IMEI, imei);
            return imei;
        }
        imei = getImei(context);

        PrefUtil.getInstance(context).putString(Constant.IMEI, imei);
        FileUtils.saveContent(fileName, imei);
        return imei;
    }
}
