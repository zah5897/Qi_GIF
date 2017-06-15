package com.zhan.qiwen.model.item.data;


import com.zhan.qiwen.model.base.BaseData;

interface ItemData extends BaseData {

    /**
     * 获取列表
     *
     * @param type
     * @param offset
     * @param limit
     */
    void getItems(int type, Integer offset, Integer limit);

    void getDetail(int type, String id);
}
