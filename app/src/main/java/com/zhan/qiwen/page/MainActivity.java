package com.zhan.qiwen.page;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;
import com.zhan.qiwen.R;
import com.zhan.qiwen.base.BaseActivity;
import com.zhan.qiwen.model.channel.ChannelManager;
import com.zhan.qiwen.model.channel.entity.Channel;
import com.zhan.qiwen.model.user.entity.UserInfo;
import com.zhan.qiwen.model.user.presenter.UserPresenter;
import com.zhan.qiwen.model.user.view.FastRegistView;
import com.zhan.qiwen.page.adapter.main.MainPagerAdapter;
import com.zhan.qiwen.page.fragment.ChannelDialogFragment;
import com.zhan.qiwen.page.widget.ColorTrackTabLayout;
import com.zhan.qiwen.utils.PrefUtil;
import com.zhan.qiwen.utils.ZLog;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, FastRegistView {

    PagerAdapter pagerAdapter;
    ViewPager viewPager;
    ColorTrackTabLayout tabLayout;
    DrawerLayout drawer;
    private UserPresenter userPresenter;
    private UserInfo me;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initPage();
    }

    protected void initPage() {
        pagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);
        tabLayout = ((ColorTrackTabLayout) findViewById(R.id.tab_layout));
        tabLayout.setupWithViewPager(viewPager);
        findViewById(R.id.title_bar_left_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        loadToken();
    }

    private void loadToken() {
        userPresenter = new UserPresenter(this);
        String token = PrefUtil.getToken(this);
        if (!TextUtils.isEmpty(token)) {
            me = PrefUtil.getMe(this);
        }

        if (me == null || me.getId() <= 0) {
            userPresenter.fastRegist();
        } else {
            showMe(me);
        }
    }

    private void showMe(UserInfo userInfo) {
//        Glide.with(this)
//                .load(userDetailInfo.getAvatarUrl())
//                .crossFade()
//                .bitmapTransform(new CropCircleTransformation(this))
//                .error(R.mipmap.ic_launcher)
//                .into(avatar);
    }
    long lastBcck;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(System.currentTimeMillis()-lastBcck<2000){
                super.onBackPressed();
            }else{
                showToast(getString(R.string.exit_tip));
                lastBcck=System.currentTimeMillis();
            }

        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void channelManager(final View v) {
        ChannelDialogFragment dialogFragment = ChannelDialogFragment.newInstance();
        dialogFragment.show(getSupportFragmentManager(), "CHANNEL");
        dialogFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                pagerAdapter.notifyDataSetChanged();
                viewPager.setOffscreenPageLimit(ChannelManager.get().getMyChannels().size());
                tabLayout.setCurrentItem(tabLayout.getSelectedTabPosition());
//                ViewGroup slidingTabStrip = (ViewGroup) tabLayout.getChildAt(0);
//                //注意：因为最开始设置了最小宽度，所以重新测量宽度的时候一定要先将最小宽度设置为0
//                slidingTabStrip.setMinimumWidth(0);
//                slidingTabStrip.measure(0, 0);
//                slidingTabStrip.setMinimumWidth(slidingTabStrip.getMeasuredWidth() + v.getMeasuredWidth());
            }
        });
    }

    @Override
    public void fastRegist(UserInfo info) {
        showMe(info);
    }
}
