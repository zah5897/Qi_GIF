package com.zhan.qiwen.model.item.view;


import com.zhan.qiwen.model.item.entity.Item;

import java.util.List;

public interface ItemsView {
    void showItems(int type,List<Item> items);
}
