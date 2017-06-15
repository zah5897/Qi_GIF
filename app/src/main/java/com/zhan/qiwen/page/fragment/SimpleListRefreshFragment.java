package com.zhan.qiwen.page.fragment;

import android.os.Bundle;

import com.zhan.qiwen.base.BaseRefreshFragment;
import com.zhan.qiwen.model.base.BasePresenter;
import com.zhan.qiwen.model.channel.entity.Channel;
import com.zhan.qiwen.model.item.entity.Item;
import com.zhan.qiwen.model.item.presenter.ItemsPresenter;
import com.zhan.qiwen.model.item.view.ItemsView;
import com.zhan.qiwen.page.adapter.simpleItem.SimpleItemViewProvider;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;

public class SimpleListRefreshFragment extends BaseRefreshFragment implements ItemsView {
    public static final String TYPE = "type";
    private int type;
    public static SimpleListRefreshFragment newInstance(Channel channel) {

        SimpleListRefreshFragment topicFragment = new SimpleListRefreshFragment();
        Bundle b = new Bundle();
        topicFragment.type=channel.type;
        b.putInt(TYPE, topicFragment.type);
        topicFragment.setArguments(b);
        return topicFragment;
    }
    protected void loadData() {
        ((ItemsPresenter) mvpPresenter).getSimpleItems(type, offset, limit);
    }

    @Override
    protected void registProvider(MultiTypeAdapter adapter) {
        adapter.register(Item.class, new SimpleItemViewProvider());
    }

    @Override
    public void showItems(List<Item> simpleItems) {
        onLoadData(simpleItems);
    }

    @Override
    protected BasePresenter createPresenter() {
        return new ItemsPresenter(this);
    }
}
