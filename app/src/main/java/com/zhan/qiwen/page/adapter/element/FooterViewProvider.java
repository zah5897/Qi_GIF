package com.zhan.qiwen.page.adapter.element;

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

public class FooterViewProvider extends ItemViewProvider<Footer, FooterViewProvider.ViewHolder> {
    ViewHolder viewHolder;
    private int status = Footer.STATUS_NORMAL;

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
                                            @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_load_more, parent, false);
        viewHolder = new ViewHolder(root);
        return viewHolder;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Footer footer) {
        refreshStatus(viewHolder, status);
        if (status == Footer.STATUS_NORMAL) {
            needLoadMore();
        }
    }

    public boolean isLoadMore() {
        return status == Footer.STATUS_LOAD_MORE;
    }

    public void refreshFooterStatus(int status) {
        this.status = status;
        if (viewHolder != null) {
            refreshStatus(viewHolder, status);
        }
    }

    public boolean canRefresh() {
        return status == Footer.STATUS_NORMAL || status == Footer.STATUS_NO_MORE;
    }

    public boolean canLoadMore() {
        return status == Footer.STATUS_NORMAL;
    }

    private void refreshStatus(ViewHolder viewHolder, int status) {
        switch (status) {
            case Footer.STATUS_NO_MORE:
                viewHolder.tips.setText("没有更多了");
                viewHolder.progressBar.setVisibility(View.GONE);
                break;
            default:
                viewHolder.tips.setText("加载中...");
                viewHolder.progressBar.setVisibility(View.VISIBLE);
                break;

        }
    }

    public void needLoadMore() {
    }

    ;

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tips)
        TextView tips;
        @BindView(R.id.progress_bar)
        ProgressBar progressBar;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}