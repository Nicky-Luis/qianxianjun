package com.luis.nicky.qianxianjun.module.detail;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.luis.nicky.qianxianjun.R;
import com.luis.nicky.qianxianjun.BR;
import com.luis.nicky.qianxianjun.base.basic.BaseActivity;
import com.luis.nicky.qianxianjun.base.view.DialogUtil;
import com.luis.nicky.qianxianjun.model.Person;
import com.luis.nicky.qianxianjun.model.TargetPerson;
import com.luis.nicky.qianxianjun.module.detail.interfaces.IDetailPresenter;
import com.luis.nicky.qianxianjun.module.detail.presenter.DetailPresenter;

public class PersonDetailActivity extends BaseActivity {

    public static final String Intent_Key = "person_id";
    //presenter
    private IDetailPresenter detailPresenter;

    @Override
    public int setLayoutId() {
        return R.layout.activity_person_detail;
    }

    @Override
    protected void loadLayout(View v) {
        getPersonInfo();
    }

    @Override
    protected void onInitialize() {
    }

    @Override
    public void initPresenter() {
        detailPresenter = new DetailPresenter(this);
    }

    //获取用户信息
    private void getPersonInfo() {
        Intent intent = getIntent();
        String personId = intent.getStringExtra(Intent_Key);


        DialogUtil.instance().showLoadingDialog("加载数据中");
        detailPresenter.getPersonInfo(personId, new DetailPresenter.ResultCallback() {
            @Override
            public void onSuccessed(Person person, TargetPerson targetPerson) {
                DialogUtil.dismissDialog();


                ViewDataBinding bing = DataBindingUtil.setContentView(PersonDetailActivity.this, R.layout.activity_person_detail);
                bing.setVariable(BR.person, person);
                bing.setVariable(BR.target, person);
            }

            @Override
            public void onFailed() {
                DialogUtil.dismissDialog();
            }
        });
    }
}
