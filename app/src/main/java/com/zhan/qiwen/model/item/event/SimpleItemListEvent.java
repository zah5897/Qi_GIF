package com.zhan.qiwen.model.item.event;


import com.zhan.qiwen.model.item.entity.SimpleItem;

import java.util.List;

/**
 * Created by plusend on 2016/11/24.
 */

public class SimpleItemListEvent{
    List<SimpleItem> list;
    public SimpleItemListEvent(List<SimpleItem> list){
       this.list=list;
    }

    public List<SimpleItem> getList() {
        return list;
    }
}
