package com.zhan.qiwen.page;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;
import com.zhan.qiwen.R;
import com.zhan.qiwen.base.BaseActivity;
import com.zhan.qiwen.model.channel.ChannelManager;
import com.zhan.qiwen.model.channel.entity.Channel;
import com.zhan.qiwen.page.adapter.main.MainPagerAdapter;
import com.zhan.qiwen.page.fragment.ChannelDialogFragment;
import com.zhan.qiwen.page.widget.ColorTrackTabLayout;
import com.zhan.qiwen.utils.ToolBarUtil;

import java.util.ArrayList;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    PagerAdapter pagerAdapter;
    ViewPager viewPager;
    ColorTrackTabLayout tabLayout;
    DrawerLayout drawer;

    @Override
    protected void loadViewLayout() {
        setEnableSwipe(false);
        setContentView(R.layout.activity_main);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        pagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager= (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);
        tabLayout=((ColorTrackTabLayout) findViewById(R.id.tab_layout));
        tabLayout.setupWithViewPager(viewPager);
        findViewById(R.id.title_bar_left_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void channelManager(final View v){
        ChannelDialogFragment dialogFragment = ChannelDialogFragment.newInstance(new ArrayList<Channel>(), new ArrayList<Channel>());
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
