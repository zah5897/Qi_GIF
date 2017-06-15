package com.zhan.qiwen.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.zhan.qiwen.R;
import com.zhan.qiwen.base.BaseActivity;
import com.zhan.qiwen.model.base.BasePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageActivity extends BaseActivity {
    public static final String URL = "url";
    private static final String TAG = "ImageActivity";
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.image)
    PhotoView image;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        initPage();
    }


    protected void initPage() {
        String url = getIntent().getStringExtra(URL);
        Log.d(TAG, "url: " + url);
        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target,
                                               boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model,
                                                   Target<GlideDrawable> target, boolean isFromMemoryCache,
                                                   boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(image);
    }
}
