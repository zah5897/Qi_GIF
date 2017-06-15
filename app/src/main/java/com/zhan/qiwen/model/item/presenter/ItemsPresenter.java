package com.zhan.qiwen.model.item.presenter;

import com.zhan.qiwen.model.base.BaseData;
import com.zhan.qiwen.model.base.BasePresenter;
import com.zhan.qiwen.model.item.data.ItemDataNetwork;
import com.zhan.qiwen.model.item.event.ItemsEvent;
import com.zhan.qiwen.model.item.view.ItemsView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ItemsPresenter extends BasePresenter {
    private BaseData data;
    private ItemsView simpleItemView;

    public ItemsPresenter(ItemsView simpleItemView) {
        this.data = ItemDataNetwork.getInstance();
        this.simpleItemView = simpleItemView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showItems(ItemsEvent event) {
        simpleItemView.showItems(event.getType(),event.getList());
    }

    public void getItems(int type, Integer offset, Integer limit) {
        ((ItemDataNetwork) data).getItems(type, offset, limit);
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
