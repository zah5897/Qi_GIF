package com.zhan.qiwen.utils;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by zah on 2017/6/6.
 */

public class ToolBarUtil {
    public static  void setToolBar(AppCompatActivity activity, Toolbar mToolbar){
//设置toolbar高度和内边距
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            mToolbar.getLayoutParams().height = getAppBarHeight(activity);
            mToolbar.setPadding(mToolbar.getPaddingLeft(),
                    getStatusBarHeight(activity),
                    mToolbar.getPaddingRight(),
                    mToolbar.getPaddingBottom());
        }
        activity.setSupportActionBar(mToolbar);
    }

    private static int getAppBarHeight(Context context)
    {
        return dip2px(context,56)+getStatusBarHeight(context);
    }

    private static int getStatusBarHeight(Context context)
    {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0)
        {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }

    private static   int dip2px(Context context,float dipValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
