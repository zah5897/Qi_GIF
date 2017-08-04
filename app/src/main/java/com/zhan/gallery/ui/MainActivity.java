package com.zhan.gallery.ui;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;
import com.zhan.gallery.R;
import com.zhan.gallery.model.User;
import com.zhan.gallery.model.service.ChannelManager;
import com.zhan.gallery.ui.adapter.main.MainPagerAdapter;
import com.zhan.gallery.ui.base.BaseActivity;
import com.zhan.gallery.ui.fragments.ChannelDialogFragment;
import com.zhan.gallery.ui.widget.ColorTrackTabLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    PagerAdapter pagerAdapter;
    ViewPager viewPager;
    ColorTrackTabLayout tabLayout;
    DrawerLayout drawer;
    private ImageView avatarView;
    private TextView nick_name;

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
        View header = navigationView.getHeaderView(0);
        avatarView = (ImageView) header.findViewById(R.id.imageView);
        nick_name = (TextView) header.findViewById(R.id.nick_name);
        navigationView.setNavigationItemSelectedListener(this);
        initPage();
    }

    protected void initPage() {
        pagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);
        tabLayout = ((ColorTrackTabLayout) findViewById(R.id.tab_layout));
        tabLayout.setupWithViewPager(viewPager);
        ImageView imageView = (ImageView) findViewById(R.id.title_bar_left_btn);
        imageView.setImageResource(R.mipmap.ic_menu_home);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
    }


    private void showMe(User userInfo) {
        if (userInfo == null) {
            return;
        }
        if (!TextUtils.isEmpty(userInfo.avatar)) {
            Glide.with(this)
                    .load(userInfo.avatar)
                    .into(avatarView);
        }

        if (!TextUtils.isEmpty(userInfo.nickname)) {
            nick_name.setText(userInfo.nickname);
        }
    }

    long lastBcck;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (System.currentTimeMillis() - lastBcck < 2000) {
                super.onBackPressed();
            } else {
                showToast(getString(R.string.exit_tip));
                lastBcck = System.currentTimeMillis();
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

        switch (id) {
            case R.id.my_collect:
                to(MyCollectActivity.class);
                break;
        }
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

}
