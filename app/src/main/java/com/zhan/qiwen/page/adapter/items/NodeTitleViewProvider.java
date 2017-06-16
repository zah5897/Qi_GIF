package com.zhan.qiwen.page.adapter.items;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhan.qiwen.R;
import com.zhan.qiwen.model.item.entity.Node;
import com.zhan.qiwen.model.item.entity.TitleNode;
import com.zhan.qiwen.page.ItemDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

public class NodeTitleViewProvider extends ItemViewBinder<Node, NodeTitleViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
                                            @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_node_title, parent, false);
        return new ViewHolder(root);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final Node node) {
        holder.title.setText(node.getTitle());
        if (node instanceof TitleNode) {
            TitleNode titleNode = (TitleNode) node;
            String subTitle = "";
            if (!TextUtils.isEmpty(titleNode.from)) {
                subTitle = titleNode.from + "  ";
            }
            if (!TextUtils.isEmpty(titleNode.time)) {
                subTitle += titleNode.time;
            }
            if (!TextUtils.isEmpty(subTitle)) {
                holder.sub_title.setText(subTitle);
                holder.sub_title.setVisibility(View.VISIBLE);
            }
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.sub_title)
        TextView sub_title;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}