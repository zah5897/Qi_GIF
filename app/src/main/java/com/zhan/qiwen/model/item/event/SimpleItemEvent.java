package com.zhan.qiwen.model.item.event;


import com.zhan.qiwen.model.item.entity.SimpleItem;

import java.util.List;

/**
 * Created by plusend on 2016/11/24.
 */

public class SimpleItemEvent {
    private List<SimpleItem> itemList;

    public SimpleItemEvent(List<SimpleItem> itemList) {
        this.itemList = itemList;
    }

    public List<SimpleItem> getTopicList() {
        return itemList;
    }
}
