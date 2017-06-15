package com.zhan.qiwen.base;

import android.os.Bundle;

import com.zhan.qiwen.model.base.BasePresenter;

/**
 * Created by RayYeung on 2016/8/8.
 */
public abstract class BaseSwipBackMvpActivity<P extends BasePresenter> extends BaseSwipeBackActivity {
    protected P mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        mvpPresenter.start();
        super.onCreate(savedInstanceState);
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null) {
            mvpPresenter.stop();
        }
    }

}
