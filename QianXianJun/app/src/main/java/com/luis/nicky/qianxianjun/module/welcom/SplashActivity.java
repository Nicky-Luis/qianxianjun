package com.luis.nicky.qianxianjun.module.welcom;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import com.luis.nicky.qianxianjun.R;
import com.luis.nicky.qianxianjun.base.basic.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    public int setLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void loadLayout(View v) {

    }

    @Override
    protected void onInitialize() {
        // Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init();
    }

    @Override
    public void initPresenter() {

    }

    //初始化
    private void init() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
