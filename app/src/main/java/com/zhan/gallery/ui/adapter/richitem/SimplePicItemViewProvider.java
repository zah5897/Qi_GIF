package com.zhan.gallery.ui.adapter.richitem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhan.gallery.R;
import com.zhan.gallery.model.RichTextItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

public class SimplePicItemViewProvider extends ItemViewBinder<RichTextItem, SimplePicItemViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
                                            @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_pic_item, parent, false);

        return new ViewHolder(root);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final RichTextItem simpleItem) {
        holder.title.setText(simpleItem.getTitle());
        Glide.with(holder.img.getContext())
                .load(simpleItem.getSmall_img())
                .into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.title)
        TextView title;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            int item_height = itemView.getContext().getResources().getDisplayMetrics().widthPixels / 2;
            itemView.setLayoutParams(new RelativeLayout.LayoutParams(-1, item_height));
        }
    }
}