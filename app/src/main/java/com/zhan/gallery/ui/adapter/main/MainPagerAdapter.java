package com.zhan.gallery.ui.adapter.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zhan.gallery.model.Channel;
import com.zhan.gallery.model.service.ChannelManager;
import com.zhan.gallery.ui.fragments.ListFragment;
import com.zhan.gallery.ui.fragments.WaterFallFragment;


/**
 * Created by plusend on 2016/11/22.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        Channel channel = ChannelManager.get().getMyChannels().get(position);
        if (channel.type == 3) {
            return WaterFallFragment.newInstance(channel);
        } else {
            return ListFragment.newInstance(channel);
        }

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
        } else {
            return ChannelManager.get().getMyChannels().get(position).title;
        }
    }
}
