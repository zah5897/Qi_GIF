package com.zhan.gallery.ui.user;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhan.gallery.R;
import com.zhan.gallery.ui.base.BaseSwipeBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;


/**
 * Created by zah on 2016/11/24.
 */

public class EditUserActivity extends BaseSwipeBackActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.nick_name)
    EditText nickName;
    @BindView(R.id.birthday)
    TextView birthday;
    @BindView(R.id.right)
    TextView right;
    @BindView(R.id.signature)
    TextView signature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        ButterKnife.bind(this);
        barTitle.setText("修改信息");
    }

    private void setUserInfo() {

    }

    @OnClick({R.id.back, R.id.right, R.id.avatar_item, R.id.nick_name_item, R.id.birthday_item, R.id.signature_item})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.right:
                break;
            case R.id.avatar_item:
                break;
            case R.id.nick_name_item:
                MultiImageSelector.create()
                        .showCamera(true) // show camera or not. true by default
                        .count(1) // max select image size, 9 by default. used width #.multi()
                        .single() // single mode
                        .multi() // multi mode, default mode;
                        .start(this, 0);
                break;
            case R.id.birthday_item:
                break;
            case R.id.signature_item:
                break;
        }
    }
}
