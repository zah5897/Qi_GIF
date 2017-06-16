package com.zhan.qiwen.page.adapter.items;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhan.qiwen.R;
import com.zhan.qiwen.model.item.entity.Item;
import com.zhan.qiwen.page.adapter.element.Header;
import com.zhan.qiwen.page.widget.HtmlTextView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewProvider;

public class HtmlHeaderViewProvider extends ItemViewProvider<Header, HtmlHeaderViewProvider.ViewHolder> {
    ViewHolder viewHolder;

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
                                            @NonNull ViewGroup parent) {
        viewHolder = new ViewHolder(inflater.inflate(R.layout.header_html_detail, parent, false));
        return viewHolder;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Header header) {
        setDetail((Item) header.getModel());
    }

    public void setDetail(Item item) {
        setTitle(item.getTitle());
        setContent(item.getHtml());
        setSub("奇闻", item.getCreate_time());
    }

    public void setTitle(String title) {
        viewHolder.tvTitle.setText(title);
    }

    public void setContent(String html) {
        if (TextUtils.isEmpty(html))
            viewHolder.tvHtmlContent.setVisibility(View.GONE);
        viewHolder.tvHtmlContent.setRichText(html);
    }

    public void setSub(String from, Date time) {

        if (TextUtils.isEmpty(from) && time == null) {
            viewHolder.subLayout.setVisibility(View.GONE);
            return;
        } else {
            viewHolder.subLayout.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(from)) {
            viewHolder.from.setText(from);
        }
        if (time != null) {
            viewHolder.time.setText(time.toLocaleString());
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.from)
        TextView from;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.sub_layout)
        LinearLayout subLayout;
        @BindView(R.id.tvHtmlContent)
        HtmlTextView tvHtmlContent;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}