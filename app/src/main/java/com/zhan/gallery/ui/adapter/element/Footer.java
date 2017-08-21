package com.zhan.gallery.ui.adapter.element;

public class Footer {

    public int status;

    public Footer(int status) {
        this.status = status;
    }

    public static final int STATUS_NORMAL = 1;//正常状态
    public static final int STATUS_LOADING = 2;//正在加载中
    public static final int STATUS_NO_MORE = 3;//没有更多了
    public static final int STATUS_ERR = 4;//获取数据错误
    public static final int STATUS_NODATA = 5;//获取数据错误
}