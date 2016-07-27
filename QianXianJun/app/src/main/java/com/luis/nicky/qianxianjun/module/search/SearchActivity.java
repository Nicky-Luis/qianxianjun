package com.luis.nicky.qianxianjun.module.search;

import android.view.View;

import com.luis.nicky.qianxianjun.R;
import com.luis.nicky.qianxianjun.common.basic.BaseActivity;

import butterknife.OnClick;

public class SearchActivity extends BaseActivity {


    @OnClick(value = {R.id.btn_back})
    public void onclick(View v) {
        switch (v.getId()) {
            //搜索
            case R.id.btn_back:
                finish();
                break;
            default:
                break;
        }
    }
    @Override
    public int setLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void loadLayout(View v) {

    }

    @Override
    protected void onInitialize() {

    }

    @Override
    public void initPresenter() {

    }
}
