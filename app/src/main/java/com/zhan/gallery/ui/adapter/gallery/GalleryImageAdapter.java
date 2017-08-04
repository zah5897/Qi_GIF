package com.zhan.gallery.ui.adapter.gallery;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.zhan.gallery.R;
import com.zhan.gallery.model.Comment;
import com.zhan.gallery.model.Gallery;
import com.zhan.gallery.ui.adapter.GalleryAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by zah on 2017/7/31.
 */

public class GalleryImageAdapter extends ItemViewBinder<Gallery, GalleryImageAdapter.ViewHolder> {

    private RequestOptions requestOptions;

    @NonNull
    @Override
    protected GalleryImageAdapter.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        requestOptions = new RequestOptions().placeholder(R.mipmap.image_load_bg).priority(Priority.HIGH);
        return new GalleryImageAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_detail_item_img, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull GalleryImageAdapter.ViewHolder holder, @NonNull Gallery gallery) {
        final String url = gallery.images.get(0).img_url;
        Glide.with(holder.itemView.getContext()).load(url).apply(requestOptions).into(holder.img);


        holder.title.setText(gallery.title);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(v.getContext(), android.R.style.Theme_NoTitleBar);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                PhotoView photoView = new PhotoView(v.getContext());
                photoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Glide.with(v.getContext()).load(url).into(photoView);
                dialog.setContentView(photoView, new ViewGroup.LayoutParams(-1, -1));
                dialog.show();
            }
        });
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.title)
        TextView title;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
