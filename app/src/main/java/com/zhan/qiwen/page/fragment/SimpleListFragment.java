package com.zhan.qiwen.page.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhan.qiwen.R;
import com.zhan.qiwen.base.BaseMvpFragment;
import com.zhan.qiwen.model.base.BasePresenter;
import com.zhan.qiwen.model.channel.entity.Channel;
import com.zhan.qiwen.model.item.entity.SimpleItem;
import com.zhan.qiwen.model.item.presenter.SimpleItemPresenter;
import com.zhan.qiwen.model.item.view.SimpleItemView;
import com.zhan.qiwen.page.adapter.base.BaseFooter;
import com.zhan.qiwen.page.adapter.base.BaseFooterViewProvider;
import com.zhan.qiwen.page.adapter.simpleItem.SimpleItemViewProvider;
import com.zhan.qiwen.page.widget.DividerListItemDecoration;
import com.zhan.qiwen.page.widget.EmptyRecyclerView;
import com.zhan.qiwen.page.widget.EmptyView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class SimpleListFragment extends BaseMvpFragment implements SimpleItemView {
    public static final String TYPE = "type";
    @BindView(R.id.rv)
    EmptyRecyclerView rv;
    @BindView(R.id.empty_view)
    EmptyView emptyView;
    private MultiTypeAdapter adapter;
    private Items items;
    private LinearLayoutManager linearLayoutManager;
    private int offset = 0;
    private int type;

    public static SimpleListFragment newInstance(Channel channel) {
        SimpleListFragment topicFragment = new SimpleListFragment();
        Bundle b = new Bundle();
        b.putInt(TYPE, channel.type);
        topicFragment.setArguments(b);
        return topicFragment;
    }

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_simple_item, container, false);
    }

    @Override
    protected void bindViews(View view) {
        ButterKnife.bind(this, rootView);
        linearLayoutManager = new LinearLayoutManager(getContext());
        items = new Items();
        adapter = new MultiTypeAdapter(items);
        adapter.register(SimpleItem.class, new SimpleItemViewProvider());
        adapter.register(BaseFooter.class, new BaseFooterViewProvider());
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerListItemDecoration(getActivity()));
        rv.setEmptyView(emptyView);
    }

    @Override
    protected void processLogic() {
        type = getArguments().getInt(TYPE);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()) {
                    ((BaseFooter) items.get(items.size() - 1)).setStatus(BaseFooter.STATUS_LOADING);
                    adapter.notifyItemChanged(adapter.getItemCount());
                    lazyLoad();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
        emptyView.setBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lazyLoad();
            }
        });
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        ((SimpleItemPresenter) mvpPresenter).getSimpleItems(type, offset, null);
    }

    @Override
    public void showItems(List<SimpleItem> simpleItems) {
        if (simpleItems!=null) {
            if (items.size() == 0) {
                items.add(new BaseFooter(BaseFooter.STATUS_NORMAL));
            }
            for (SimpleItem simpleItem : simpleItems) {
                // 插入 FooterView 前面
                items.add(items.size() - 1, simpleItem);
                adapter.notifyItemInserted(adapter.getItemCount() - 1);
            }
            offset = items.size() - 1;
            if (simpleItems.size() < 20) {
                ((BaseFooter) items.get(items.size() - 1)).setStatus(BaseFooter.STATUS_NO_MORE);
            } else {
                ((BaseFooter) items.get(items.size() - 1)).setStatus(BaseFooter.STATUS_NORMAL);
            }
            adapter.notifyItemChanged(adapter.getItemCount());
        }
        if(adapter.getItemCount()>0){
            emptyView.showEmpty();
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return new SimpleItemPresenter(this);
    }


}
