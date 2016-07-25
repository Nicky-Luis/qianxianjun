package com.luis.nicky.qianxianjun.ui;

import android.content.Intent;
import android.os.Handler;
import android.view.View;

import com.luis.nicky.qianxianjun.R;
import com.luis.nicky.qianxianjun.common.basic.BaseActivity;

public class LoadingActivity extends BaseActivity {

    @Override
    public int setLayoutId() {
        return R.layout.activity_loading;
    }

    @Override
    protected void loadLayout(View v) {

    }

    @Override
    protected void setUpView() {
        init();
    }

    @Override
    public void initPresenter() {

    }

    //初始化
    private void init() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(LoadingActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
