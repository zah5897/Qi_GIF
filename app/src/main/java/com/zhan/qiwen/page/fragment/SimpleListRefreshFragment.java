package com.zhan.qiwen.page.fragment;

import android.os.Bundle;

import com.zhan.qiwen.base.BaseRefreshFragment;
import com.zhan.qiwen.model.base.BasePresenter;
import com.zhan.qiwen.model.channel.entity.Channel;
import com.zhan.qiwen.model.item.entity.Item;
import com.zhan.qiwen.model.item.presenter.ItemsPresenter;
import com.zhan.qiwen.model.item.view.ItemsView;
import com.zhan.qiwen.page.adapter.items.SimpleItemViewProvider;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;

public class SimpleListRefreshFragment extends BaseRefreshFragment implements ItemsView {
    public static final String TYPE = "type";
    private int type;

    public static SimpleListRefreshFragment newInstance(Channel channel) {
        SimpleListRefreshFragment topicFragment = new SimpleListRefreshFragment();
        Bundle b = new Bundle();
        b.putInt(TYPE, channel.type);
        topicFragment.setArguments(b);
        return topicFragment;
    }

    @Override
    protected void firstInit() {
        super.firstInit();
        type = getArguments().getInt(TYPE);
    }

    protected void loadData() {
        ((ItemsPresenter) mvpPresenter).getItems(type, offset, limit);
    }

    @Override
    protected void registProvider(MultiTypeAdapter adapter) {
        adapter.register(Item.class, new SimpleItemViewProvider());
    }

    @Override
    public void showItems(int type, List<Item> simpleItems) {
        if (this.type != type) {
            return;
        }
        onLoadData(simpleItems);
    }

    @Override
    protected BasePresenter createPresenter() {
        return new ItemsPresenter(this);
    }
}
