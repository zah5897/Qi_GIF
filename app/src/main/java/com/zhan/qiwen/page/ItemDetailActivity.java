package com.zhan.qiwen.page;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.zhan.qiwen.R;
import com.zhan.qiwen.base.BaseSwipBackMvpActivity;
import com.zhan.qiwen.model.base.BasePresenter;
import com.zhan.qiwen.model.item.entity.Item;
import com.zhan.qiwen.model.item.presenter.ItemDetailPresenter;
import com.zhan.qiwen.model.item.view.ItemDetailView;
import com.zhan.qiwen.page.adapter.element.Header;
import com.zhan.qiwen.page.adapter.items.HtmlHeaderViewProvider;
import com.zhan.qiwen.page.widget.EmptyRecyclerView;
import com.zhan.qiwen.page.widget.EmptyView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by zah on 2017/6/14.
 */

public class ItemDetailActivity extends BaseSwipBackMvpActivity implements ItemDetailView {
    @BindView(R.id.rv)
    EmptyRecyclerView rv;
    @BindView(R.id.empty_view)
    EmptyView emptyView;
    private Item model;
    private MultiTypeAdapter adapter;
    private Items items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simpe_item_detail);
        ButterKnife.bind(this);
        items = new Items();
        adapter = new MultiTypeAdapter(items);
        adapter.register(Header.class, new HtmlHeaderViewProvider());
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
//        rv.addItemDecoration(new DividerListItemDecoration(this));
        rv.setEmptyView(emptyView);
        model = getIntent().getParcelableExtra("model");
        ((ItemDetailPresenter) mvpPresenter).getDetail(model.getChannelType(), model.getId());
    }

    @Override
    public void showDetail(Item detail) {
        if (detail != null) {
            this.model = detail;
            items.add(new Header(detail));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return new ItemDetailPresenter(this);
    }

    @OnClick(R.id.title_bar_left_btn)
    public void onViewClicked() {
        finish();
    }
}
