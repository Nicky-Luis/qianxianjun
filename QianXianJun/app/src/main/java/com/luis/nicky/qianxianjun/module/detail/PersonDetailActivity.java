package com.luis.nicky.qianxianjun.module.detail;


import android.content.Intent;
import android.view.View;

import com.luis.nicky.qianxianjun.R;
import com.luis.nicky.qianxianjun.base.basic.BaseActivityWithTitleBar;
import com.luis.nicky.qianxianjun.base.view.DialogUtil;
import com.luis.nicky.qianxianjun.base.view.TitleBar;
import com.luis.nicky.qianxianjun.databinding.ActivityPersonDetailBinding;
import com.luis.nicky.qianxianjun.model.Person;
import com.luis.nicky.qianxianjun.model.TargetPerson;
import com.luis.nicky.qianxianjun.module.detail.interfaces.IDetailPresenter;
import com.luis.nicky.qianxianjun.module.detail.presenter.DetailPresenter;

public class PersonDetailActivity extends BaseActivityWithTitleBar {

    public static final String Intent_Key = "person_id";
    //presenter
    private IDetailPresenter detailPresenter;

    @Override
    public int setLayoutId() {
        setDataBindingFlag(true);
        return R.layout.activity_person_detail;
    }

    @Override
    protected void loadLayout(View rootView) {
        super.loadLayout(rootView);
        TitleBar titleBar = (TitleBar) rootView.findViewById(R.id.title_bar);
        titleBar.setTitle("用户的详情");
    }

    @Override
    public void initPresenter() {
        detailPresenter = new DetailPresenter(this);
    }

    @Override
    protected void onInitialize() {
        getPersonInfo();
    }


    //获取用户信息
    private void getPersonInfo() {
        Intent intent = getIntent();
        String personId = intent.getStringExtra(Intent_Key);

        DialogUtil.instance().showLoadingDialog("加载数据中");
        detailPresenter.getPersonInfo(personId, new DetailPresenter
                .ResultCallback() {
            @Override
            public void onSuccessed(Person person, TargetPerson targetPerson) {
                DialogUtil.dismissDialog();

                //databing绑定数据
                ActivityPersonDetailBinding binding = (ActivityPersonDetailBinding)
                        viewDataBinding;
                binding.setPerson(person);
                binding.setTarget(targetPerson);
            }

            @Override
            public void onFailed() {
                DialogUtil.dismissDialog();
            }
        });
    }
}
