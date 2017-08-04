package com.zhan.gallery.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhan.gallery.R;
import com.zhan.gallery.model.Gallery;
import com.zhan.gallery.model.ImageModel;
import com.zhan.gallery.model.service.AppService;
import com.zhan.gallery.ui.ShowGalleryActivity;
import com.zhan.gallery.ui.fragments.WaterFallFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryAdapter extends RecyclerView.Adapter {
    private List<Gallery> galleries;

    private int itemWidth;
    public static final int TYPE_DATA = 0;
    public static final int TYPE_FOOTER = 1;
    public static final int FOOTER_STATUS_NORMAL = 0;
    public static final int FOOTER_STATUS_LOADING = 1;
    public static final int FOOTER_STATUS_NO_MORE = 2;

    public int currentFooterStatus = FOOTER_STATUS_NORMAL;

    private int channel;

    public GalleryAdapter(List<Gallery> list, int channel, int itemWidth) {
        galleries = list;
        this.itemWidth = itemWidth;
        this.channel = channel;
    }

    public void loadMore() {

    }


    public List<Gallery> getGalleries() {
        return galleries;
    }

    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        handleLayoutIfStaggeredGridLayout(holder, holder.getLayoutPosition());
    }

    protected void handleLayoutIfStaggeredGridLayout(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            p.setFullSpan(true);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_DATA) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false));
        } else {
            return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_loading_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Gallery gallery = galleries.get(position);
        if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            if (currentFooterStatus == FOOTER_STATUS_NO_MORE) {
                footerViewHolder.progressBar.setVisibility(View.GONE);
                footerViewHolder.msg.setText("已经到底了");
            } else {
                footerViewHolder.progressBar.setVisibility(View.VISIBLE);
                footerViewHolder.msg.setText("正在加載中...");
                if (currentFooterStatus == FOOTER_STATUS_NORMAL) {
                    loadMore();
                }
            }
        } else {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.title.setText(gallery.title);
            ImageModel imageModel = gallery.images.get(0);

            float scale = itemWidth / (imageModel.thumb_width * 1.0f);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewHolder.img.getLayoutParams();

            params.width = itemWidth;
            params.height = (int) (imageModel.thumb_height * scale);
            viewHolder.img.setLayoutParams(params);

            Glide.with(holder.itemView.getContext()).
                    load(imageModel.img_thumb_url).into(viewHolder.img);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppService.get().tempGalleries = getGalleries();
                    Intent intent = new Intent(v.getContext(), ShowGalleryActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra(WaterFallFragment.CHANNEL, channel);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        Gallery gallery = getGalleries().get(position);
        if (gallery.images != null) {
            return TYPE_DATA;
        } else {
            return TYPE_FOOTER;
        }
    }

    @Override
    public int getItemCount() {
        return galleries.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.title)
        TextView title;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progressbar)
        ProgressBar progressBar;
        @BindView(R.id.msg)
        TextView msg;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}