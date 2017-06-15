package com.zhan.qiwen.model.item.presenter;

import com.zhan.qiwen.model.base.BaseData;
import com.zhan.qiwen.model.base.BasePresenter;
import com.zhan.qiwen.model.item.data.ItemDataNetwork;
import com.zhan.qiwen.model.item.event.ItemDetailEvent;
import com.zhan.qiwen.model.item.event.ItemsEvent;
import com.zhan.qiwen.model.item.view.ItemDetailView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ItemDetailPresenter extends BasePresenter {
    private BaseData data;
    private ItemDetailView detailView;

    public ItemDetailPresenter(ItemDetailView detailView) {
        this.data = ItemDataNetwork.getInstance();
        this.detailView = detailView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showItemDetail(ItemDetailEvent event) {
        detailView.showDetail(event.getDetailItem());
    }

    public void getDetail(int type, String id) {
        ((ItemDataNetwork) data).getDetail(type, id);
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
