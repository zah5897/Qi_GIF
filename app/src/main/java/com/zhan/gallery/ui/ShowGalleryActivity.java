/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.zhan.gallery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zhan.gallery.R;
import com.zhan.gallery.model.Gallery;
import com.zhan.gallery.model.event.LoopEvent;
import com.zhan.gallery.model.event.RefreshGalleryEvent;
import com.zhan.gallery.model.service.AppService;
import com.zhan.gallery.model.service.UserManager;
import com.zhan.gallery.ui.base.BaseActivity;
import com.zhan.gallery.ui.fragments.PhotoViewLoopFragment;
import com.zhan.gallery.ui.fragments.WaterFallFragment;
import com.zhan.gallery.ui.user.LoginActivity;
import com.zhan.gallery.utils.DBHelper;
import com.zhan.gallery.utils.FileUtils;
import com.zhan.gallery.utils.PrefUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowGalleryActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView barTitle;
    @BindView(R.id.pager)
    ViewPager viewPager;
    int initPosition;
    private List<PhotoViewLoopFragment> fragmentList;

    private List<Gallery> galleries;
    private int channel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_gallery);
        ButterKnife.bind(this);
        channel = getIntent().getIntExtra(WaterFallFragment.CHANNEL, 0);
        AppService.get().dbHelper = new DBHelper(getApplicationContext());
        EventBus.getDefault().register(this);
        initPosition = getIntent().getIntExtra("position", 0);
        initData();
        initViewPager();

        if (!PrefUtil.get().getBoolean("slide_hint", false)) {
            findViewById(R.id.viewstub).setVisibility(View.VISIBLE);
            findViewById(R.id.slied_hint).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PrefUtil.get().putBoolean("slide_hint", true);
                    v.setVisibility(View.GONE);
                }
            });
        }
    }

    private void initData() {
        fragmentList = new ArrayList<PhotoViewLoopFragment>();
        PhotoViewLoopFragment index_1 = PhotoViewLoopFragment.get(initPosition);
        PhotoViewLoopFragment index_2 = PhotoViewLoopFragment.get(initPosition + 1);
        PhotoViewLoopFragment index_3 = PhotoViewLoopFragment.get(initPosition + 2);
        PhotoViewLoopFragment index_4 = PhotoViewLoopFragment.get(initPosition + 3);
        fragmentList.add(index_1);
        fragmentList.add(index_2);
        fragmentList.add(index_3);
        fragmentList.add(index_4);
    }

    private void initViewPager() {
        galleries = new ArrayList<>();
        galleries.addAll(AppService.get().tempGalleries);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                PhotoViewLoopFragment fragment = fragmentList.get(position % fragmentList.size());
                fragment.refreshState(position);
                return fragment;
            }

            @Override
            public int getCount() {
                return galleries.size();
            }
        });
        viewPager.setCurrentItem(initPosition);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {//position表示在扩展Fragments中即将要显示的Fragment的索引
                if (position == viewPager.getAdapter().getCount() - 5) {
                    EventBus.getDefault().post(new LoopEvent(channel, position));
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Deprecated
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewDataNotify(RefreshGalleryEvent event) {
        if (event.galleries != null) {
            galleries.addAll(event.galleries);
            viewPager.getAdapter().notifyDataSetChanged();
        }

    }

    @OnClick({R.id.title_bar_left_btn, R.id.action_comment, R.id.action_download, R.id.action_favor, R.id.action_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_left_btn:
                finish();
                break;
            case R.id.action_comment:
                if (UserManager.get().isLogin()) {
                    Gallery gallery = (Gallery) AppService.get().tempGalleries.get(viewPager.getCurrentItem());
                    Intent intent = new Intent(this, SendCommentActivity.class);
                    intent.putExtra(WaterFallFragment.CHANNEL, channel);
                    intent.putExtra("gallery_id", gallery._id);
                    to(intent);
                } else {
                    to(LoginActivity.class);
                }
                break;
            case R.id.action_download:
                int position = viewPager.getCurrentItem();
                final Gallery gallery = (Gallery) AppService.get().tempGalleries.get(position);
                RequestBuilder requestBuilder = Glide.with(this).asFile().load(gallery.images.get(0).img_url);
                requestBuilder.apply(new RequestOptions().priority(Priority.HIGH));
                requestBuilder.into(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(File resource, Transition<? super File> transition) {
                        String img_url = gallery.images.get(0).img_url;
                        String fileName = img_url.substring(img_url.lastIndexOf("/"));
                        FileUtils.copyfile(resource, new File(Environment.getExternalStorageDirectory() + "/" + FileUtils.ROOT_DIR + fileName), true);
                        showToast("图片已经保存在" + FileUtils.ROOT_DIR + "目录下！");
                    }
                });
                break;
            case R.id.action_favor:

                break;
            case R.id.action_share:

                break;
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        AppService.get().getDBHelper(this).clearGalleryComments();
        AppService.get().dbHelper.close();
        AppService.get().tempGalleries = null;
        super.onDestroy();

    }
}
