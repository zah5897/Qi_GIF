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
import com.zhan.gallery.model.Gallery;
import com.zhan.gallery.model.event.GalleriesEvent;
import com.zhan.gallery.model.event.LoopEvent;
import com.zhan.gallery.model.event.RefreshGalleryEvent;
import com.zhan.gallery.model.service.AppService;
import com.zhan.gallery.ui.adapter.GalleryAdapter;
import com.zhan.gallery.ui.base.LazyFragment;
import com.zhan.gallery.ui.widget.EmptyRecyclerView;
import com.zhan.gallery.ui.widget.EmptyView;
import com.zhan.gallery.ui.widget.SpacesItemDecoration;
import com.zhan.gallery.utils.DeviceUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    GalleryAdapter adapter;
    private int channel;


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
        List<Gallery> galleries = new ArrayList<>();
        AppService.get().tempGalleries = galleries;
        adapter = new GalleryAdapter(galleries, channel, (DeviceUtils.getScreenWidth(getActivity()) - 64 - DeviceUtils.dip2px(getContext(), 5)) / 2) {
            @Override
            public void loadMore() {
                super.loadMore();
                if (swiperefreshLayout.isRefreshing()) {
                    adapter.currentFooterStatus = FOOTER_STATUS_NORMAL;
                    return;
                }
                requestGalleries();
            }
        };
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
                requestGalleries();
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
        if (adapter.currentFooterStatus == adapter.FOOTER_STATUS_LOADING) {
            swiperefreshLayout.setRefreshing(false);
            return;
        }
        cursor = 0;
        requestGalleries();
    }

    @Deprecated
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGalleries(GalleriesEvent event) {
        swiperefreshLayout.setRefreshing(false);
        adapter.currentFooterStatus = adapter.FOOTER_STATUS_NORMAL;
        if (event.galleries != null) {
            if (cursor == 0) {
                adapter.getGalleries().clear();
                adapter.getGalleries().addAll(event.galleries);
                adapter.notifyDataSetChanged();
            } else {
                adapter.getGalleries().remove(adapter.getItemCount() - 1);
                for (Gallery gallery : event.galleries) {
                    adapter.getGalleries().add(gallery);
                    adapter.notifyItemInserted(adapter.getItemCount() - 1);
                }
            }
            adapter.getGalleries().add(new Gallery());
            if (event.galleries.size() < limit) {
                adapter.currentFooterStatus = adapter.FOOTER_STATUS_NO_MORE;
            }
            cursor++;
        }

        //确定是否需要通知gallery页面
        if (needNotifyGallery) {
            EventBus.getDefault().post(new RefreshGalleryEvent(event.galleries));
            needNotifyGallery = false;
        }
        if (adapter.getItemCount() == 0) {
            empty_view.showEmpty();
        }
    }

    //是否需要通知gallery页面标记
    boolean needNotifyGallery = false;

    @Deprecated
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoopNotify(LoopEvent event) {
        if (this.channel == event.channel) {
            if (!swiperefreshLayout.isRefreshing()) {
                needNotifyGallery = true;
                requestGalleries();
            }
        }
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        List<Gallery> galleries =
                AppService.get().getDBHelper(getContext()).loadCacheGallerys(channel);

        if (galleries.size() > 0) {
            adapter.getGalleries().addAll(galleries);
            adapter.notifyDataSetChanged();
            swiperefreshLayout.setRefreshing(true);
        }
        requestGalleries();
    }


    private void requestGalleries() {
        AppService.get().requestGallery(channel, cursor, limit);
    }

}
