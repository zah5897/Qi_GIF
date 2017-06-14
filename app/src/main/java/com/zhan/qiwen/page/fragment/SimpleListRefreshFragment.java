package com.zhan.qiwen.page.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhan.qiwen.R;
import com.zhan.qiwen.base.BaseMvpFragment;
import com.zhan.qiwen.base.BaseRefreshFragment;
import com.zhan.qiwen.model.base.BaseModel;
import com.zhan.qiwen.model.base.BasePresenter;
import com.zhan.qiwen.model.channel.entity.Channel;
import com.zhan.qiwen.model.item.entity.SimpleItem;
import com.zhan.qiwen.model.item.presenter.SimpleItemPresenter;
import com.zhan.qiwen.model.item.view.SimpleItemView;
import com.zhan.qiwen.page.adapter.element.Footer;
import com.zhan.qiwen.page.adapter.element.FooterViewProvider;
import com.zhan.qiwen.page.adapter.simpleItem.SimpleItemViewProvider;
import com.zhan.qiwen.page.widget.DividerListItemDecoration;
import com.zhan.qiwen.page.widget.EmptyRecyclerView;
import com.zhan.qiwen.page.widget.EmptyView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class SimpleListRefreshFragment extends BaseRefreshFragment implements SimpleItemView {
    public static final String TYPE = "type";
    private int type;
    public static SimpleListRefreshFragment newInstance(Channel channel) {

        SimpleListRefreshFragment topicFragment = new SimpleListRefreshFragment();
        Bundle b = new Bundle();
        topicFragment.type=channel.type;
        b.putInt(TYPE, topicFragment.type);
        topicFragment.setArguments(b);
        return topicFragment;
    }
    protected void loadData() {
        ((SimpleItemPresenter) mvpPresenter).getSimpleItems(type, offset, limit);
    }

    @Override
    protected void registProvider(MultiTypeAdapter adapter) {
        adapter.register(SimpleItem.class, new SimpleItemViewProvider());
    }

    @Override
    public void showItems(List<SimpleItem> simpleItems) {
        onLoadData(simpleItems);
    }

    @Override
    protected BasePresenter createPresenter() {
        return new SimpleItemPresenter(this);
    }
}
