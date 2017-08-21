package com.zhan.gallery.ui.adapter.image;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhan.gallery.R;
import com.zhan.gallery.model.ImageModel;
import com.zhan.gallery.ui.ImageDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

public class ImgListViewProvider extends ItemViewBinder<ImageModel, ImgListViewProvider.ViewHolder> {
    private RequestOptions requestOptions;

    public ImgListViewProvider() {
        requestOptions = new RequestOptions().placeholder(R.mipmap.image_load_bg);
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
                                            @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_image_list, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final ImageModel imageModel) {
        holder.title.setText(imageModel.txt);
        if (imageModel.img_url.endsWith(".gif")) {
            holder.gif_tip.setVisibility(View.VISIBLE);
        } else {
            holder.gif_tip.setVisibility(View.GONE);
        }
        Glide.with(holder.itemView.getContext()).asBitmap().load(imageModel.img_url).apply(requestOptions).into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ImageDetailActivity.class);
                intent.putExtra("model", imageModel);
                v.getContext().startActivity(intent);
            }
        });
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.gif_tip)
        ImageView gif_tip;
        @BindView(R.id.title)
        TextView title;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}