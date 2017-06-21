package com.zhan.qiwen.page.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
import me.drakeet.multitype.MultiTypePool;

/**
 * Created by zah on 2017/6/21.
 */

public class AppMultiTypeAdapter extends MultiTypeAdapter {
    public AppMultiTypeAdapter(@Nullable List<?> items) {
        super(items);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {   // 布局是GridLayoutManager所管理
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果是Header、Footer的对象则占据spanCount的位置，否则就只占用1个位置
                    return (isHeader(position) || isFooter(position)) ? gridLayoutManager
                            .getSpanCount() : 1;
                }
            });
        }
    }

    public boolean isFooter(int position) {
        return position < getItemCount() && position >= getItemCount() - 1;
    }

    private boolean isHeader(int position) {
        return false;
    }


}
