package com.zhan.gallery.ui.user.regist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhan.gallery.R;
import com.zhan.gallery.model.service.Callback;
import com.zhan.gallery.model.service.UserManager;
import com.zhan.gallery.ui.base.BaseActivity;
import com.zhan.gallery.ui.widget.HQMaterialProgressTip;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zah on 2017/7/6.
 */

public class ForgetPwdActivity extends BaseActivity {

    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.new_pwd)
    EditText newPwd;

    int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        type = getIntent().getIntExtra("type", 0);
        ButterKnife.bind(this);
        if (type != 0) {
            username.setVisibility(View.GONE);
        } else {
            username.setVisibility(View.VISIBLE);
        }
        barTitle.setText("修改密码");
    }

    @Override
    public void onBackPressed() {
        if (resetTip != null) {
            showToast("正在修改密码中，请稍后...");
            return;
        }
        super.onBackPressed();
    }

    private HQMaterialProgressTip resetTip;

    @OnClick({R.id.back, R.id.save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.save:
                String usernameStr;
                if (type == 0) {
                    usernameStr = username.getText().toString().trim();
                    if (TextUtils.isEmpty(usernameStr)) {
                        showToast("请输入帐号");
                        return;
                    }
                } else {
                    usernameStr = UserManager.get().getLoginUser().nickname;
                }
                String newPwdStr = newPwd.getText().toString().trim();

                if (newPwdStr.length() < 6 || newPwdStr.length() > 12) {
                    showToast("请输入6-12密码");
                    return;
                }
                resetTip = new HQMaterialProgressTip(this, "正在修改密码");
                UserManager.get().resetPwd(usernameStr, newPwdStr,new Callback() {
                @Override
                public void onResult (Object object){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (resetTip != null) {
                                resetTip.dismiss();
                                resetTip = null;
                            }
                            showToast("密码修改成功");
                            finish();
                        }
                    });
                }

                @Override
                public void onFailed ( int code, final String msg){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (resetTip != null) {
                                resetTip.dismiss();
                                resetTip = null;
                            }
                            showToast(msg);
                            finish();
                        }
                    });
                }
            });
            break;
        }
    }
}
