package com.zhan.qiwen.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zhan.qiwen.utils.Constant;
import com.zhan.qiwen.utils.PrefUtil;

/**
 * Created by zah on 2017/6/6.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (PrefUtil.getInstance(getApplicationContext()).getInt(Constant.Theme.SP_THEME, Constant.Theme.THEME_LIGHT) == Constant.Theme.THEME_LIGHT) {
            //setTheme(R.style.Theme_Light);
        } else {
            //setTheme(R.style.Theme_Night);
        }
    }

    public Context getContext() {
        return this;
    }

    protected void intent2Activity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }

    protected void showToast(String msg) {
//        ToastUtils.showToast(msg);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
