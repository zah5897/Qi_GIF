package com.zhan.gallery.utils;

import android.content.Context;
import android.widget.Toast;

import com.zhan.gallery.app.Application;

public class ToastUtil {

    public static void showText(String message) {
        Toast.makeText(Application.getApp(), message, Toast.LENGTH_SHORT).show();
    }
}
