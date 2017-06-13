package com.zhan.qiwen.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.zhan.qiwen.R;
import com.zhan.qiwen.page.widget.SwipeBackLayout;
import com.zhan.qiwen.utils.Constant;
import com.zhan.qiwen.utils.PrefUtil;

/**
 * Created by zah on 2017/6/6.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private SwipeBackLayout mSwipeBackLayout;
    private boolean enableSwipe=true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= 19) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            SystemBarTintManager tintManager = new SystemBarTintManager(this);
//            // 激活状态栏设置
//            tintManager.setStatusBarTintEnabled(true);
//            // 使用颜色资源
//            tintManager.setStatusBarTintResource(R.color.transparent);
//        }

        if (PrefUtil.getInstance(getApplicationContext()).getInt(Constant.Theme.SP_THEME, Constant.Theme.THEME_LIGHT) == Constant.Theme.THEME_LIGHT) {
            setTheme(R.style.Theme_Light);
        } else {
            setTheme(R.style.Theme_Night);
        }
//        mSubscription = toObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Notice>() {
//            @Override
//            public void call(Notice message) {
//                if (message.type == ConstanceValue.MSG_TYPE_CHANGE_THEME)
//                    ColorUiUtil.changeTheme(getWindow().getDecorView(), getTheme());
//            }
//
//        });

        initView(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        if(enableSwipe){
            super.setContentView(getContainer());
            View view = LayoutInflater.from(this).inflate(layoutResID, null);
            mSwipeBackLayout.addView(view);
        }else{
            super.setContentView(layoutResID);
        }
    }

    private View getContainer() {
        RelativeLayout container = new RelativeLayout(this);
        mSwipeBackLayout = new SwipeBackLayout(this);
        mSwipeBackLayout.setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        mSwipeBackLayout.setEnablePullToBack(enableSwipe);
//        mIvShadow = new ImageView(this);
//        mIvShadow.setBackgroundColor(getResources().getColor(R.color.black));
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        container.addView(mIvShadow, params);
        container.addView(mSwipeBackLayout);
        return container;
    }

    public void setEnableSwipe(boolean enableSwipe) {
        this.enableSwipe=enableSwipe;
    }
    /**
     * 初始化界面
     */
    protected void initView(Bundle savedInstanceState) {
        loadViewLayout();
        processLogic(savedInstanceState);
    }
    protected abstract void loadViewLayout();
    protected abstract void processLogic(Bundle savedInstanceState);

    protected void intent2Activity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }
    protected void showToast(String msg) {
//        ToastUtils.showToast(msg);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
