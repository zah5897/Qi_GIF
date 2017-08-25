package com.zhan.gallery.ui.adapter.image;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.wx.goodview.GoodView;
import com.zhan.gallery.R;
import com.zhan.gallery.model.ImageModel;
import com.zhan.gallery.model.service.AppService;
import com.zhan.gallery.model.service.UserManager;
import com.zhan.gallery.ui.user.LoginActivity;
import com.zhan.gallery.utils.AppUtil;
import com.zhan.gallery.utils.FileUtils;
import com.zhan.gallery.utils.ToastUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

public class ImgDetailViewProvider extends ItemViewBinder<ImageModel, ImgDetailViewProvider.ViewHolder> {
    private RequestOptions requestOptions;

    public ImgDetailViewProvider() {
        requestOptions = new RequestOptions().placeholder(R.mipmap.image_load_bg);
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
                                            @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_image_detail, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final ImageModel imageModel) {
        holder.title.setText(imageModel.title);
        Glide.with(holder.itemView.getContext()).load(imageModel.thumb_img_url).apply(requestOptions).into(holder.img);

        holder.praise.setText(String.valueOf(imageModel.praise_count));
        holder.praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodView goodView = new GoodView(v.getContext());
                int color = v.getResources().getColor(R.color.collect_red);
                goodView.setTextInfo("+1", color, 18);
                goodView.show(v);
                imageModel.praise_count++;
                AppService.get().praiseAction(imageModel);
                holder.praise.setText(String.valueOf(imageModel.praise_count));
                holder.praise.setTextColor(color);
            }
        });
        updateCollectStatus(holder, imageModel);
        holder.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UserManager.get().isLogin()) {
                    ToastUtil.showText("您还没登录，请先登录！");
                    Intent intent = new Intent(v.getContext(), LoginActivity.class);
                    v.getContext().startActivity(intent);
                    return;
                }
                boolean cancel = false;
                if (imageModel.collected) {
                    cancel = true;
                    imageModel.collected = false;
                    int count = imageModel.collect_count - 1;
                    if (count >= 0) {
                        imageModel.collect_count = count;
                    }
                } else {
                    imageModel.collected = true;
                    int count = imageModel.collect_count + 1;
                    imageModel.collect_count = count;
                }
                updateCollectStatus(holder, imageModel);
                AppService.get().collectAction(imageModel, cancel);
            }
        });
        holder.itemView.findViewById(R.id.download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBuilder requestBuilder = Glide.with(v.getContext()).asFile().load(imageModel.thumb_img_url);
                requestBuilder.apply(new RequestOptions().priority(Priority.HIGH));
                requestBuilder.into(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(File resource, Transition<? super File> transition) {
                        String fileName = imageModel.thumb_img_url.substring(imageModel.thumb_img_url.lastIndexOf("/"));
                        FileUtils.copyfile(resource, new File(Environment.getExternalStorageDirectory() + "/" + FileUtils.ROOT_DIR + fileName), true);
                        ToastUtil.showText("图片已经保存在" + FileUtils.ROOT_DIR + "目录下！");
                    }
                });
            }
        });
        holder.itemView.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appame = v.getResources().getString(R.string.app_name);
                String title = "来自" + appame;
                String content = "分享一个@" + appame + ":" + imageModel.title + " 点击查看:" + imageModel.thumb_img_url;
                AppUtil.share(v.getContext(), title, content);
            }
        });
    }


    private void updateCollectStatus(ViewHolder holder, ImageModel imageModel) {
        holder.collect.setText(imageModel.collect_count < 0 ? "0" : String.valueOf(imageModel.collect_count));
        if (imageModel.collected) {
            Drawable drawable = holder.collect.getResources().getDrawable(R.mipmap.ic_collect_count_collected);
/// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.collect.setCompoundDrawables(drawable, null, null, null);
            holder.collect.setTextColor(holder.collect.getResources().getColor(R.color.collect_red));
        } else {
            Drawable drawable = holder.collect.getResources().getDrawable(R.mipmap.ic_collect_count_press);
/// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.collect.setCompoundDrawables(drawable, null, null, null);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.praise)
        TextView praise;
        @BindView(R.id.collect)
        TextView collect;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}