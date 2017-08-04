package com.zhan.gallery.ui.adapter.richitem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhan.gallery.R;
import com.zhan.gallery.model.RichTextItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

public class SimpleItemViewProvider extends ItemViewBinder<RichTextItem, SimpleItemViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
                                            @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_simple_item, parent, false);
        return new ViewHolder(root);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final RichTextItem simpleItem) {
        holder.abstract_txt.setText(simpleItem.getQw_abstract());
        holder.title.setText(simpleItem.getTitle());
        Glide.with(holder.left_img.getContext())
                .load(simpleItem.getSmall_img())
                .into(holder.left_img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.left_img)
        ImageView left_img;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.abstract_txt)
        TextView abstract_txt;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}