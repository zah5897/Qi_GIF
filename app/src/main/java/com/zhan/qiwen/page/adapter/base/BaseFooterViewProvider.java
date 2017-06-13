package com.zhan.qiwen.page.adapter.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhan.qiwen.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewProvider;

public class BaseFooterViewProvider extends ItemViewProvider<BaseFooter, BaseFooterViewProvider.ViewHolder> {

    @NonNull
    @Override protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
                                                      @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_list_load_more, parent, false);
        return new ViewHolder(root);
    }

    @Override protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull BaseFooter footer) {
        switch (footer.getStatus()) {
            case BaseFooter.STATUS_NORMAL:
                holder.tips.setText("上拉加载更多");
                holder.progressBar.setVisibility(View.GONE);
                break;
            case BaseFooter.STATUS_LOADING:
                holder.tips.setText("加载中");
                holder.progressBar.setVisibility(View.VISIBLE);
                break;
            case BaseFooter.STATUS_NO_MORE:
                holder.tips.setText("没有更多了");
                holder.progressBar.setVisibility(View.GONE);
                break;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tips) TextView tips;
        @BindView(R.id.progress_bar) ProgressBar progressBar;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}