package com.zhan.gallery.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by zah on 2017/6/27.
 */

public class BaseActivity extends AppCompatActivity {

    protected int cursor = 0;
    protected int limit = 20;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void to(Class clazz) {
        startActivity(new Intent(this, clazz));
    }

    public void to(Intent intent) {
        startActivity(intent);
    }
}
