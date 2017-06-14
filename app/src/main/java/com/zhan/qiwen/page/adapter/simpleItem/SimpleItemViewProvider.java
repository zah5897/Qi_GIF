package com.zhan.qiwen.page.adapter.simpleItem;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhan.qiwen.R;
import com.zhan.qiwen.model.item.entity.SimpleItem;
import com.zhan.qiwen.utils.TimeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewProvider;

public class SimpleItemViewProvider extends ItemViewProvider<SimpleItem,SimpleItemViewProvider.ViewHolder> {

    @NonNull
    @Override protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
                                                      @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_simple_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final SimpleItem simpleItem) {
        holder.abstract_txt.setText(simpleItem.getQw_abstract());
        holder.title.setText(simpleItem.getTitle());
        Glide.with(holder.left_img.getContext())
            .load(simpleItem.getSmall_img())
            .placeholder(R.mipmap.image_loading)
            .error(R.mipmap.ic_avatar_error)
            .crossFade()
            .into(holder.left_img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
             
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.left_img) ImageView left_img;
        @BindView(R.id.title) TextView title;
        @BindView(R.id.abstract_txt) TextView abstract_txt;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}