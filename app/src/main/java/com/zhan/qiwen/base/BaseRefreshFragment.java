package com.zhan.qiwen.base;/*
 * Copyright 2017 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2017-04-09 21:16:47
 *
 * GitHub:  https://github.com/GcsSloop
 * Website: http://www.gcssloop.com
 * Weibo:   http://weibo.com/GcsSloop
 */


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhan.qiwen.R;
import com.zhan.qiwen.model.base.BaseModel;
import com.zhan.qiwen.model.item.entity.SimpleItem;
import com.zhan.qiwen.page.adapter.element.Footer;
import com.zhan.qiwen.page.adapter.element.FooterViewProvider;
import com.zhan.qiwen.page.adapter.simpleItem.SimpleItemViewProvider;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * 具有下拉刷新和上拉加载的 Fragment
 */
public abstract class BaseRefreshFragment extends BaseMvpFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    private MultiTypeAdapter adapter;
    private Items items;
    private LinearLayoutManager linearLayoutManager;
    public static final  int STATE_NORMAL=0;
    public static final  int STATE_NO_MORE=-1;
    public static final  int STATE_REFRESH=1;
    public static final  int STATE_MORE=2;
    private int loadingState=STATE_NORMAL; //0 为不加在，1为刷新，2为加载更多
    @Override
    protected View loadLayout(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_refresh_recycler, container, false);
        ButterKnife.bind(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // refreshLayout
        mRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        mRefreshLayout.setProgressViewOffset(false, -20, 80);
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        mRefreshLayout.setEnabled(true);
        // RecyclerView
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        // 监听 RefreshLayout 下拉刷新
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((Footer) items.get(items.size() - 1)).setStatus(Footer.STATUS_LOADING);
                if(loadingState!=STATE_NORMAL){
                    mRefreshLayout.setRefreshing(false);
                    return;
                }
                loadingState=STATE_REFRESH;
                offset=0;
                loadData();
            }
        });
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        loadingState=STATE_REFRESH;
        loadData();
    }

    @Override
    protected void firstInit() {
        linearLayoutManager = new LinearLayoutManager(getContext());
        items = new Items();
        adapter = new MultiTypeAdapter(items);
        registProvider(adapter);
        adapter.register(Footer.class, new FooterViewProvider(){
            @Override
            public void needLoadMore() {
                super.needLoadMore();
                if(loadingState!=STATE_NORMAL){
                    return;
                }
                loadingState=STATE_MORE;
                loadData();
            }
        });
    }
   protected abstract void loadData();
   protected abstract void registProvider(MultiTypeAdapter adapter);
   protected void onLoadData(List<?> datas){
        if(loadingState==STATE_REFRESH){
            onRefresh((List<BaseModel>) datas);
        }else{
            onLoadMore((List<BaseModel>) datas);
        }
   }
    protected void onRefresh(List<BaseModel> datas) {
        mRefreshLayout.setRefreshing(false);
        if (datas!=null) {
            items.clear();
            if (items.size() == 0) {
                items.add(new Footer(Footer.STATUS_NORMAL));
            }
            for (BaseModel model : datas) {
                // 插入 FooterView 前面
                items.add(items.size() - 1, model);
                adapter.notifyItemInserted(adapter.getItemCount() - 1);
            }
            offset = items.size() - 1;
            if (datas.size() < limit) {
                ((Footer) items.get(items.size() - 1)).setStatus(Footer.STATUS_NO_MORE);
                loadingState=STATE_NO_MORE;
            } else {
                ((Footer) items.get(items.size() - 1)).setStatus(Footer.STATUS_NORMAL);
                loadingState=STATE_NORMAL;
            }
            adapter.notifyDataSetChanged();
            offset=items.size()-1;
        }else{
            //状态恢复
            loadingState=STATE_NORMAL;
        }
    }

    protected void onLoadMore(List<BaseModel> datas) {
        if (datas==null||datas.size() < limit) {
            ((Footer) items.get(items.size() - 1)).setStatus(Footer.STATUS_NO_MORE);
            loadingState=STATE_NO_MORE;
        } else {
            ((Footer) items.get(items.size() - 1)).setStatus(Footer.STATUS_NORMAL);
            loadingState=STATE_NORMAL;
        }
        for (BaseModel model : datas) {
            // 插入 FooterView 前面
            items.add(items.size() - 1, model);
            adapter.notifyItemInserted(adapter.getItemCount() - 1);
        }
        offset=items.size()-1;
    }
    public void setRefreshEnable(boolean refreshEnable) {
        mRefreshLayout.setEnabled(refreshEnable);
    }

    public void quickToTop() {
        mRecyclerView.smoothScrollToPosition(0);
    }

}