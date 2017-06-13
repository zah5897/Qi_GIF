package com.zhan.qiwen.model.item.data;


import com.zhan.qiwen.model.base.BaseData;

interface SimpleItemData extends BaseData {

    /**
     * 获取列表
     *
     * @param type
     * @param offset
     * @param limit
     */
    void getSimpleItems(int type, Integer offset, Integer limit);
}
