package com.zhan.qiwen.page;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhan.qiwen.R;
import com.zhan.qiwen.base.BaseActivity;
import com.zhan.qiwen.utils.GlideImageGetter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/2/22 0022.
 */

public class SplashActivity extends BaseActivity {
    final int COUT_DOWN_TIME = 2;
    @BindView(R.id.banner_view)
    ImageView mBannerView;
    @BindView(R.id.splash_view)
    ImageView mSplashView;
    @BindView(R.id.skip_real)
    TextView mSkipReal;
    @BindView(R.id.guide_fragment)
    FrameLayout mGuideFragment;
    @BindView(R.id.ad_click_small)
    ImageView mAdClickSmall;
    @BindView(R.id.ad_click)
    LinearLayout mAdClick;
    @BindView(R.id.ad_skip_loading)
    ImageView mAdSkipLoading;
    @BindView(R.id.ad_ignore)
    FrameLayout mAdIgnore;
    @BindView(R.id.splash_video_frame)
    FrameLayout mSplashVideoFrame;
    @BindView(R.id.splash_video_layout)
    RelativeLayout mSplashVideoLayout;
    Handler handler;
    boolean cancel = false;

    @Override
    protected void loadViewLayout() {
        setEnableSwipe(false);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        setTimerCount(COUT_DOWN_TIME);
    }

    private void setTimerCount(int seconds) {
        mSkipReal.setText(TextUtils.concat(seconds + "s", getResources().getString(R.string.splash_ad_ignore)));
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == COUT_DOWN_TIME) {
                    Glide.with(SplashActivity.this)
                            .load("http://www.3vsheji.com/uploads/allimg/151222/1F92594D_0.jpg")
                            .crossFade()
                            .into(mSplashView);
                    mAdClickSmall.setVisibility(View.VISIBLE);
                    mSplashView.setVisibility(View.VISIBLE);
                    mAdIgnore.setVisibility(View.VISIBLE);
                } else if (msg.what >= 0) {
                    setTimerCount(msg.what);
                    if (msg.what == 0) {
                        goMain();
                    }
                }
            }
        };

        new Thread() {
            @Override
            public void run() {
                int time = COUT_DOWN_TIME;
                do {
                    handler.sendEmptyMessage(time);
                    SystemClock.sleep(1000);
                    time--;
                } while (time >= -1 && !cancel);
            }
        }.start();
    }

    @OnClick(R.id.skip_real)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.skip_real:
                goMain();
                break;
        }
    }

    private void goMain() {
        cancel=true;
        intent2Activity(MainActivity.class);
        finish();
    }
}
