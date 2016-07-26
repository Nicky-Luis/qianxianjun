package com.luis.nicky.qianxianjun.ui;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;

import com.luis.nicky.qianxianjun.R;
import com.luis.nicky.qianxianjun.common.basic.BaseActivity;
import com.luis.nicky.qianxianjun.common.utils.ToastUtil;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @OnClick(value = {R.id.btn_search, R.id.btn_add_person})
    public void onclick(View v) {
        switch (v.getId()) {
            //搜索
            case R.id.btn_search:
                startSearch();
                break;

            //添加
            case R.id.btn_add_person:
                startAdd();
                break;

            default:
                break;
        }
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void loadLayout(View v) {

    }

    @Override
    protected void setUpView() {

    }

    @Override
    public void initPresenter() {

    }

    /***
     * 搜索
     */
    private void startSearch() {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    /***
     * 添加
     */
    private void startAdd() {
        Intent intent = new Intent(MainActivity.this, AddPersonActivity.class);
        startActivity(intent);
    }

    //再按一次退出程序
    private long mExitTime;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtil.show(this, "再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
