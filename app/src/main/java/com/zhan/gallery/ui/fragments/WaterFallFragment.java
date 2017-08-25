package com.zhan.gallery.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.zhan.gallery.R;
import com.zhan.gallery.model.Channel;
import com.zhan.gallery.model.ImageModel;
import com.zhan.gallery.model.event.ImagesEvent;
import com.zhan.gallery.model.service.AppService;
import com.zhan.gallery.ui.adapter.element.Footer;
import com.zhan.gallery.ui.adapter.element.FooterViewProvider;
import com.zhan.gallery.ui.adapter.image.ImgFullViewProvider;
import com.zhan.gallery.ui.base.LazyFragment;
import com.zhan.gallery.ui.widget.EmptyRecyclerView;
import com.zhan.gallery.ui.widget.EmptyView;
import com.zhan.gallery.ui.widget.SpacesItemDecoration;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by zah on 2017/6/27.
 */

public class WaterFallFragment extends LazyFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recycler_view)
    EmptyRecyclerView recyclerView;
    @BindView(R.id.empty_view)
    EmptyView empty_view;
    @BindView(R.id.swiperefresh_layout)
    SwipeRefreshLayout swiperefreshLayout;
    private int channel;

    Items items;
    MultiTypeAdapter adapter;
    public static final String CHANNEL = "channel";

    public static WaterFallFragment newInstance(Channel channel) {
        WaterFallFragment topicFragment = new WaterFallFragment();
        Bundle b = new Bundle();
        b.putInt(CHANNEL, channel.type);
        topicFragment.setArguments(b);
        return topicFragment;
    }

    @Override
    protected void initComponent() {
        super.initComponent();
        channel = getArguments().getInt(CHANNEL);
        items = new Items();
        adapter = new MultiTypeAdapter(items);
        adapter.register(ImageModel.class, new ImgFullViewProvider());
        adapter.register(Footer.class, new FooterViewProvider() {
            @Override
            public void needLoadMore() {
                super.needLoadMore();
                loadData();
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        //设置layoutManager
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //设置adapter
        recyclerView.setAdapter(adapter);
        //设置item之间的间隔
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setEmptyView(empty_view);
        empty_view.setBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swiperefreshLayout.isRefreshing()) {
                    return;
                }
                cursor = 0;
                loadData();
            }
        });
        swiperefreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        switch (newState) {
                            case RecyclerView.SCROLL_STATE_DRAGGING:
                                Glide.with(recyclerView.getContext()).resumeRequests();
                                break;
                            case RecyclerView.SCROLL_STATE_SETTLING:
                                Glide.with(recyclerView.getContext()).pauseRequests();
                                break;
                            case RecyclerView.SCROLL_STATE_IDLE:
                                Glide.with(recyclerView.getContext()).resumeRequests();
                                break;
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onRefresh() {
        cursor = 0;
        loadData();
    }

    @Deprecated
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGalleries(ImagesEvent event) {
        if (event.channel != channel) {
            return;
        }
        swiperefreshLayout.setRefreshing(false);
        setData(event.imageModels);
    }

    private void setData(List<ImageModel> imageModels) {
        if (imageModels == null) {
            return;
        }
        if (items.size() == 0 && imageModels.size() == 0) {
            empty_view.showEmpty();
            return;
        }
        if (cursor == 0) {
            items.clear();
            adapter.notifyDataSetChanged();
        }
        if (items.size() == 0) {
            items.add(new Footer(Footer.STATUS_NORMAL));
        }
        for (ImageModel imageModel : imageModels) {
            // 插入 FooterView 前面
            items.add(items.size() - 1, imageModel);
            adapter.notifyItemInserted(adapter.getItemCount() - 1);
        }
        if (imageModels.size() < limit) {
            ((Footer) items.get(items.size() - 1)).status = Footer.STATUS_NO_MORE;
        } else {
            ((Footer) items.get(items.size() - 1)).status = (Footer.STATUS_NORMAL);
        }
        adapter.notifyItemChanged(adapter.getItemCount());
        empty_view.setVisibility(View.GONE);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        List<ImageModel> imageModels =
                AppService.get().getDBHelper(getContext()).loadCacheData(channel);
        setData(imageModels);
        cursor = 0;
        loadData();
    }


    private void loadData() {
        AppService.get().requestImgs(channel, cursor, limit);
    }

}
