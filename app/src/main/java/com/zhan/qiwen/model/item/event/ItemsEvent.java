package com.zhan.qiwen.model.item.event;


import com.zhan.qiwen.model.item.entity.Item;

import java.util.List;

/**
 * Created by plusend on 2016/11/24.
 */

public class ItemsEvent {
    List<Item> list;
    public ItemsEvent(List<Item> list){
       this.list=list;
    }

    public List<Item> getList() {
        return list;
    }
}
