package com.zhan.qiwen.page.adapter.items;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhan.qiwen.R;
import com.zhan.qiwen.model.item.entity.Node;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

public class NodeTxtViewProvider extends ItemViewBinder<Node, NodeTxtViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
                                            @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_node_txt, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final Node node) {
        holder.txt.setText(node.getTitle());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt)
        TextView txt;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}