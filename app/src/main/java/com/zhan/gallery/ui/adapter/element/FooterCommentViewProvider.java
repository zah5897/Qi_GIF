package com.zhan.gallery.ui.adapter.element;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhan.gallery.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.ItemViewProvider;

public class FooterCommentViewProvider extends ItemViewBinder<Footer, FooterCommentViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
                                            @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_load_more, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final Footer footer) {
        refreshStatus(holder, footer.status);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (footer.status == Footer.STATUS_ERR) {
                    toRefresh();
                }
            }
        });
        if (footer.status == Footer.STATUS_NORMAL) {
            toRefresh();
        }
    }

    private void toRefresh() {
        needLoadMore();
    }

    private void refreshStatus(ViewHolder viewHolder, int status) {
        switch (status) {
            case Footer.STATUS_NO_MORE:
                viewHolder.tips.setText("没有更多评论了");
                viewHolder.progressBar.setVisibility(View.GONE);
                break;
            case Footer.STATUS_ERR:
                viewHolder.tips.setText("加载数据错误，点击重新获取");
                viewHolder.progressBar.setVisibility(View.GONE);
                break;
            case Footer.STATUS_NODATA:
                viewHolder.tips.setText("暂无评论");
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