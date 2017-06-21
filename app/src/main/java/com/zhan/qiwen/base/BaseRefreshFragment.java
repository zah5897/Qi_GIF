package com.zhan.qiwen.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhan.qiwen.R;
import com.zhan.qiwen.model.base.BaseModel;
import com.zhan.qiwen.page.adapter.element.Footer;
import com.zhan.qiwen.page.adapter.element.FooterViewProvider;
import com.zhan.qiwen.page.widget.DividerListItemDecoration;
import com.zhan.qiwen.page.widget.EmptyRecyclerView;
import com.zhan.qiwen.page.widget.EmptyView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * 具有下拉刷新和上拉加载的 Fragment
 */
public abstract class BaseRefreshFragment extends BaseMvpFragment {

    @BindView(R.id.recycler_view)
    EmptyRecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.empty_view)
    EmptyView emptyView;
    private MultiTypeAdapter adapter;
    protected Items items;
    private RecyclerView.LayoutManager layoutManager;
    private FooterViewProvider footerViewProvider;

    @Override
    protected View loadLayout(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_refresh_recycler, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mRefreshLayout.setProgressViewOffset(false, -20, 80);
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        mRefreshLayout.setEnabled(true);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new DividerListItemDecoration(getActivity()));
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setEmptyView(emptyView);
        // 监听 RefreshLayout 下拉刷新
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (footerViewProvider.canRefresh()) {
                    mRefreshLayout.setRefreshing(true);
                    offset = 0;
                    footerViewProvider.refreshFooterStatus(Footer.STATUS_REFRESH);
                    lazyLoad();
                } else {
                    mRefreshLayout.setRefreshing(false);
                }
            }
        });
        emptyView.setBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lazyLoad();
            }
        });
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    @Override
    protected void lazyLoad() {
        loadData();
    }

    @Override
    protected void firstInit() {
        layoutManager = new LinearLayoutManager(getContext());
        items = new Items();
        adapter = getAdapter();
        registProvider(adapter);
        footerViewProvider = new FooterViewProvider() {
            @Override
            public void needLoadMore() {
                if (footerViewProvider.canLoadMore()) {
                    footerViewProvider.refreshFooterStatus(Footer.STATUS_LOAD_MORE);
                    loadData();
                }
            }
        };
        adapter.register(Footer.class, footerViewProvider);
    }

    public MultiTypeAdapter getAdapter() {
        if (adapter == null) {
            return new MultiTypeAdapter(items);
        } else {
            return adapter;
        }
    }

    protected abstract void loadData();

    protected abstract void registProvider(MultiTypeAdapter adapter);

    protected void onLoadData(List<?> datas) {
        if (!footerViewProvider.isLoadMore()) {
            onRefresh((List<BaseModel>) datas);
        } else {
            onLoadMore((List<BaseModel>) datas);
        }
    }

    protected void onRefresh(List<BaseModel> datas) {
        mRefreshLayout.setRefreshing(false);
        if (datas != null && datas.size() > 0) {
            items.clear();
            if (items.size() == 0) {
                items.add(new Footer());
            }
            for (BaseModel model : datas) {
                // 插入 FooterView 前面
                items.add(items.size() - 1, model);
                adapter.notifyItemInserted(adapter.getItemCount() - 1);
            }
            offset = items.size() - 1;
            if (datas.size() < limit) {
                footerViewProvider.refreshFooterStatus(Footer.STATUS_NO_MORE);
            } else {
                footerViewProvider.refreshFooterStatus(Footer.STATUS_NORMAL);
            }
            adapter.notifyDataSetChanged();
            offset = items.size() - 1;
        } else {
            //状态恢复
            footerViewProvider.refreshFooterStatus(Footer.STATUS_NORMAL);
        }
        if (items.size() == 0) {
            emptyView.showEmpty();
        }
    }

    protected void onLoadMore(List<BaseModel> datas) {

        if (datas == null || datas.size() < limit) {
            footerViewProvider.refreshFooterStatus(Footer.STATUS_NO_MORE);
        } else {
            footerViewProvider.refreshFooterStatus(Footer.STATUS_NORMAL);
        }
        for (BaseModel model : datas) {
            // 插入 FooterView 前面
            items.add(items.size() - 1, model);
            adapter.notifyItemInserted(adapter.getItemCount() - 1);
        }
        offset = items.size() - 1;
    }

    public void setRefreshEnable(boolean refreshEnable) {
        mRefreshLayout.setEnabled(refreshEnable);
    }

    public void quickToTop() {
        mRecyclerView.smoothScrollToPosition(0);
    }

}