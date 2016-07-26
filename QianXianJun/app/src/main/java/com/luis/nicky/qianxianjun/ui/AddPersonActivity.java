package com.luis.nicky.qianxianjun.ui;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.luis.nicky.qianxianjun.R;
import com.luis.nicky.qianxianjun.common.basic.BaseActivity;

import butterknife.InjectView;
import butterknife.OnClick;

public class AddPersonActivity extends BaseActivity {

    //用户名
    @InjectView(R.id.edt_name)
    EditText username;
    //微信号
    @InjectView(R.id.edt_weichat)
    EditText userWechat;
    //性别
    @InjectView(R.id.rdg_sex)
    RadioGroup userSex;
    //身高
    @InjectView(R.id.edt_height)
    EditText userHeight;
    //体重
    @InjectView(R.id.edt_weight)
    EditText userWeight;
    //地区
    @InjectView(R.id.edt_area)
    EditText userArea;
    //生日
    @InjectView(R.id.edt_birthday)
    EditText userBirthday;
    //职业
    @InjectView(R.id.edt_job)
    EditText userJob;
    //学历
    @InjectView(R.id.rdg_educated)
    RadioGroup userEducated;
    //兴趣
    @InjectView(R.id.edt_interest)
    EditText userInterest;
    //性格
    @InjectView(R.id.edt_hobby)
    EditText userHobby;
    //其他
    @InjectView(R.id.edt_others)
    EditText userOthers;

    @OnClick(value = {R.id.btn_back, R.id.btn_next})
    public void onclick(View v) {
        switch (v.getId()) {
            //搜索
            case R.id.btn_back:
                finish();
                break;

            //添加
            case R.id.btn_next:
                startToTarget();
                break;

            default:
                break;
        }
    }

    /**
     * 跳转到填写信息页面
     * */
    private void startToTarget() {
        Intent intent = new Intent(AddPersonActivity.this, TargetPersonActivity.class);
        startActivity(intent);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_add_person;
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
}
