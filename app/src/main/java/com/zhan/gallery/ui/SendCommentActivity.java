package com.zhan.gallery.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhan.gallery.R;
import com.zhan.gallery.model.Comment;
import com.zhan.gallery.model.User;
import com.zhan.gallery.model.event.CommentEvent;
import com.zhan.gallery.model.service.AppService;
import com.zhan.gallery.model.service.Callback;
import com.zhan.gallery.model.service.UserManager;
import com.zhan.gallery.ui.base.BaseActivity;
import com.zhan.gallery.ui.base.BaseSwipeBackActivity;
import com.zhan.gallery.ui.widget.HQMaterialProgressTip;
import com.zhan.gallery.utils.GsonUtil;
import com.zhan.gallery.utils.http.RequestParam;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zah on 2017/8/1.
 */

public class SendCommentActivity extends BaseSwipeBackActivity {
    @BindView(R.id.title)
    TextView barTitle;
    @BindView(R.id.input)
    EditText input;

    private int channel;
    private String gallery_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_comment);
        ButterKnife.bind(this);
//        channel = getIntent().getIntExtra(WaterFallFragment.CHANNEL, 0);
        gallery_id = getIntent().getStringExtra("gallery_id");
        barTitle.setText("评论");
    }

    private HQMaterialProgressTip progressTip;

    @OnClick({R.id.title_bar_left_btn, R.id.send})

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_left_btn:
                finish();
                break;
            case R.id.send:

                if (progressTip != null && progressTip.isShow()) {
                    showToast("正在发送中，请稍后..");
                    return;
                }

                String text = input.getText().toString().trim();
                if (text.isEmpty()) {
                    showToast("你什么也没写...");
                    return;
                }
                RequestParam requestParam = new RequestParam();
                requestParam.put("channel", channel);
                requestParam.put("gallery_id", gallery_id);
                requestParam.put("content", text);

                progressTip = new HQMaterialProgressTip(this, "正在发送中...");
                progressTip.show();
                AppService.get().requestData("/comment/send", requestParam, new Callback() {
                    @Override
                    public void onResult(final Object object) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast("发送成功！");
                                String json = ((JSONObject) object).optString("comment");
                                Comment comment = GsonUtil.toComment(json);
                                comment.user = UserManager.get().getLoginUser();
                                progressTip.dismiss();
                                progressTip = null;
                                EventBus.getDefault().post(new CommentEvent(gallery_id, comment));
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressTip.dismiss();
                                progressTip = null;
                                showToast("发送失败");
                            }
                        });
                    }
                });

                break;
        }
    }
}
