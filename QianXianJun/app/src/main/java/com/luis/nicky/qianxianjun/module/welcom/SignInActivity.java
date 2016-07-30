package com.luis.nicky.qianxianjun.module.welcom;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.luis.nicky.qianxianjun.MainActivity;
import com.luis.nicky.qianxianjun.R;
import com.luis.nicky.qianxianjun.base.basic.BaseActivity;
import com.luis.nicky.qianxianjun.base.utils.ToastUtil;

import butterknife.InjectView;
import butterknife.OnClick;

public class SignInActivity extends BaseActivity {

    //用户名输入框
    @InjectView(R.id.edt_username)
    EditText usernameEt;
    //密码输入框
    @InjectView(R.id.edt_password)
    EditText passwordEt;
    //登录按钮
    @InjectView(R.id.btn_login)
    Button loginBtn;


    @OnClick(value = {R.id.btn_login})
    public void onclick(View v) {
        switch (v.getId()) {
            //登录
            case R.id.btn_login:
                login();
                break;

            default:
                break;
        }
    }


    @Override
    public int setLayoutId() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected void loadLayout(View view) {

    }

    @Override
    protected void onInitialize() {

    }

    @Override
    public void initPresenter() {

    }

    /////////////////////////登录操作//////////////////
    //
    private void login() {
        String username = usernameEt.getText().toString();
        String password = passwordEt.getText().toString();

        if (username.equals("13802536915") && password.equals("43517725274")) {
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            ToastUtil.show("用户名或密码错误");
        }
    }
}
