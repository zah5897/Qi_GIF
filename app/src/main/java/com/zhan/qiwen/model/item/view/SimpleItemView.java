package com.zhan.qiwen.model.item.view;


import com.zhan.qiwen.model.base.BaseView;
import com.zhan.qiwen.model.item.entity.SimpleItem;

import java.util.List;

public interface SimpleItemView extends BaseView {
    void showItems(List<SimpleItem> items);
}
