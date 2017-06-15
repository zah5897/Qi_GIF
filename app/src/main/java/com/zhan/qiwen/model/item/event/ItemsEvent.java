package com.zhan.qiwen.model.item.event;


import com.zhan.qiwen.model.item.entity.Item;

import java.util.List;

/**
 * Created by plusend on 2016/11/24.
 */

public class ItemsEvent {
    List<Item> list;
    int type;

    public ItemsEvent(int type, List<Item> list) {
        this.list = list;
        this.type = type;
    }

    public List<Item> getList() {
        return list;
    }

    public int getType() {
        return type;
    }
}
