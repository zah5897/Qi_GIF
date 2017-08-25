package com.zhan.gallery.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.zhan.gallery.R;
import com.zhan.gallery.ui.adapter.element.Footer;
import com.zhan.gallery.ui.adapter.element.FooterViewProvider;
import com.zhan.gallery.ui.adapter.richitem.SimpleItemViewProvider;
import com.zhan.gallery.ui.base.BaseActivity;
import com.zhan.gallery.ui.widget.DividerListItemDecoration;
import com.zhan.gallery.ui.widget.EmptyRecyclerView;
import com.zhan.gallery.ui.widget.EmptyView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by zah on 2017/6/14.
 */

public class MyCollectActivity extends BaseActivity {
    @BindView(R.id.rv)
    EmptyRecyclerView mRecyclerView;
    @BindView(R.id.empty_view)
    EmptyView emptyView;
    @BindView(R.id.title)
    TextView title;
    private MultiTypeAdapter adapter;
    private Items items;
    private FooterViewProvider footerViewProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        title.setText(getString(R.string.my_collect));
        items = new Items();
        adapter = new MultiTypeAdapter(items);
        adapter.register(Footer.class, footerViewProvider = new FooterViewProvider() {
            @Override
            public void needLoadMore() {
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new DividerListItemDecoration(this, 1));
        mRecyclerView.setEmptyView(emptyView);
        emptyView.setBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UserNetwork.get().collectList();
            }
        });
        //UserNetwork.get().collectList();
    }


}
