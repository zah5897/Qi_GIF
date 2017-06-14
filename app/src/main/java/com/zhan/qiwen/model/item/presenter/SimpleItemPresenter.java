package com.zhan.qiwen.model.item.presenter;

import com.zhan.qiwen.model.base.BaseData;
import com.zhan.qiwen.model.base.BasePresenter;
import com.zhan.qiwen.model.item.data.SimpleItemDataNetwork;
import com.zhan.qiwen.model.item.entity.SimpleItem;
import com.zhan.qiwen.model.item.event.SimpleItemListEvent;
import com.zhan.qiwen.model.item.view.SimpleItemView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class SimpleItemPresenter extends BasePresenter {
    private BaseData data;
    private SimpleItemView simpleItemView;

    public SimpleItemPresenter(SimpleItemView simpleItemView) {
        this.data = SimpleItemDataNetwork.getInstance();
        this.simpleItemView = simpleItemView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showItems(SimpleItemListEvent event) {
        simpleItemView.showItems(event.getList());
    }

    public void getSimpleItems(int type, Integer offset, Integer limit) {
        ((SimpleItemDataNetwork) data).getSimpleItems(type, offset, limit);
    }

    @Override
    public void start() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void stop() {
        EventBus.getDefault().unregister(this);
    }
}
