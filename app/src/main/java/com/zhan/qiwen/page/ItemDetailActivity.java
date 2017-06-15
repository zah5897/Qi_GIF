package com.zhan.qiwen.page;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhan.qiwen.R;
import com.zhan.qiwen.base.BaseMvpActivity;
import com.zhan.qiwen.base.BaseSwipBackMvpActivity;
import com.zhan.qiwen.model.base.BasePresenter;
import com.zhan.qiwen.model.item.entity.Item;
import com.zhan.qiwen.model.item.presenter.ItemDetailPresenter;
import com.zhan.qiwen.model.item.view.ItemDetailView;
import com.zhan.qiwen.page.adapter.simpleItem.SimpleItemViewProvider;
import com.zhan.qiwen.page.widget.DividerListItemDecoration;
import com.zhan.qiwen.page.widget.EmptyRecyclerView;
import com.zhan.qiwen.page.widget.EmptyView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by zah on 2017/6/14.
 */

public class ItemDetailActivity extends BaseSwipBackMvpActivity implements ItemDetailView {
    @BindView(R.id.title_bar_left_btn)
    ImageView titleBarLeftBtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rv)
    EmptyRecyclerView rv;
    @BindView(R.id.empty_view)
    EmptyView emptyView;
    @BindView(R.id.drawer_layout)
    RelativeLayout drawerLayout;
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
        adapter.register(Item.class, new SimpleItemViewProvider());
//        footerViewProvider = new FooterViewProvider();
//        adapter.register(Footer.class, footerViewProvider);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerListItemDecoration(this));
        rv.setEmptyView(emptyView);
        model = getIntent().getParcelableExtra("model");
    }

    @Override
    public void showDetail(Item detail) {
        ((ItemDetailPresenter) mvpPresenter).getDetail(model.getChannelType(), model.getId());
    }

    @Override
    protected BasePresenter createPresenter() {
        return new ItemDetailPresenter(this);
    }
}
