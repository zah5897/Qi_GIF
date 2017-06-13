package com.zhan.qiwen.page.adapter.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zhan.qiwen.model.channel.ChannelManager;
import com.zhan.qiwen.page.fragment.SimpleListFragment;


/**
 * Created by plusend on 2016/11/22.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return SimpleListFragment.newInstance(ChannelManager.get().getMyChannels().get(position));
    }

    @Override
    public int getCount() {
        return ChannelManager.get().getMyChannels().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int size = getCount();
        if (position < 0) {
            return ChannelManager.get().getMyChannels().get(0).title;
        } else if (position >= size) {
            return ChannelManager.get().getMyChannels().get(size - 1).title;
        }else{
            return ChannelManager.get().getMyChannels().get(position).title;
        }
    }
}
