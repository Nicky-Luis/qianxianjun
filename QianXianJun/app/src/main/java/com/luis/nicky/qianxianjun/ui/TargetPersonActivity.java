package com.luis.nicky.qianxianjun.ui;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.luis.nicky.qianxianjun.R;
import com.luis.nicky.qianxianjun.common.basic.BaseActivity;

import butterknife.InjectView;
import butterknife.OnClick;

public class TargetPersonActivity extends BaseActivity {

    //性别
    @InjectView(R.id.rdg_sex_target)
    RadioGroup userSex;
    //身高
    @InjectView(R.id.edt_height_target)
    EditText userHeight;
    //体重
    @InjectView(R.id.edt_weight_target)
    EditText userWeight;
    //地区
    @InjectView(R.id.edt_area_target)
    EditText userArea;
    //生日
    @InjectView(R.id.edt_birthday_target)
    EditText userBirthday;
    //职业
    @InjectView(R.id.edt_job_target)
    EditText userJob;
    //学历
    @InjectView(R.id.rdg_educated_target)
    RadioGroup userEducated;
    //兴趣
    @InjectView(R.id.edt_interest_target)
    EditText userInterest;
    //性格
    @InjectView(R.id.edt_hobby_target)
    EditText userHobby;
    //其他
    @InjectView(R.id.edt_others_target)
    EditText userOthers;

    @OnClick(value = {R.id.btn_back, R.id.btn_next})
    public void onclick(View v) {
        switch (v.getId()) {
            //搜索
            case R.id.btn_back:
                finish();
                break;

            //添加
            case R.id.btn_save:
                startSaveInfo();
                break;

            default:
                break;
        }
    }

    /**
     * 保存信息
     */
    private void startSaveInfo() {

    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_target_person;
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
