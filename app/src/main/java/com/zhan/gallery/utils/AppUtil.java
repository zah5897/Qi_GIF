package com.zhan.gallery.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by zah on 2017/6/23.
 */

public class AppUtil {
    public static void copy(String content, Context context) {
// 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(ClipData.newPlainText(null, content));
    }

    public static void hiddenSoftInput(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static void share(Context context, String title, String content) {
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
        share_intent.setType("text/plain");//设置分享内容的类型
        share_intent.putExtra(Intent.EXTRA_SUBJECT, title);//添加分享内容标题
        share_intent.putExtra(Intent.EXTRA_TEXT, content);//添加分享内容
        //创建分享的Dialog
        share_intent = Intent.createChooser(share_intent, "分享");
        context.startActivity(share_intent);
    }
}
