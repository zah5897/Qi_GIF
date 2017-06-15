package com.zhan.qiwen.model.item.event;


import com.zhan.qiwen.model.item.entity.Item;

import java.util.List;

/**
 * Created by plusend on 2016/11/24.
 */

public class ItemDetailEvent {
    Item detailItem;

    public ItemDetailEvent(Item detailItem) {
        this.detailItem = detailItem;
    }

    public Item getDetailItem() {
        return detailItem;
    }
}
