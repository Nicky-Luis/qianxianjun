package com.luis.nicky.qianxianjun.module.add;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.luis.nicky.qianxianjun.R;
import com.luis.nicky.qianxianjun.collections.EducationType;
import com.luis.nicky.qianxianjun.collections.PersonBean;
import com.luis.nicky.qianxianjun.collections.SexType;
import com.luis.nicky.qianxianjun.common.basic.BaseActivity;
import com.luis.nicky.qianxianjun.common.utils.ToastUtil;
import com.luis.nicky.qianxianjun.module.add.interfaces.IAddResultCallBack;
import com.luis.nicky.qianxianjun.module.add.interfaces.IAddPersonPresenter;
import com.luis.nicky.qianxianjun.module.add.presenter.AddPersonPersenter;

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

    //回调接口
    private IAddPersonPresenter addPersonPresenter;

    @OnClick(value = {R.id.btn_back, R.id.btn_next})
    public void onclick(View v) {
        switch (v.getId()) {
            //搜索
            case R.id.btn_back:
                finish();
                break;

            //添加并下一步
            case R.id.btn_next:
                startToAdd();
                break;

            default:
                break;
        }
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_add_person;
    }

    @Override
    protected void loadLayout(View v) {

    }

    @Override
    protected void onInitialize() {

    }

    @Override
    public void initPresenter() {
        //添加的结果返回
        addPersonPresenter = new AddPersonPersenter(AddPersonActivity.this
        );
    }


    /**
     * 跳转到填写信息页面
     */
    private void startToTarget(String personId) {
        Intent intent = new Intent(AddPersonActivity.this, AddTargetActivity.class);
        intent.putExtra(AddTargetActivity.Intent_Key, personId);
        startActivity(intent);
    }

    /**
     * 开始添加
     */
    private void startToAdd() {
        PersonBean bean = new PersonBean();
        bean.mUserName = username.getText().toString();
        bean.mUserWechatId = userWechat.getText().toString();
        bean.mUserSex = userSex.getCheckedRadioButtonId() == R.id.radioButton_man
                ? SexType.Man : SexType.Female;
        bean.mUserPhone = "";
        bean.mUserQQ = "";
        bean.mUserWeibo = "";
        bean.mUserArea = userArea.getText().toString();
        bean.mUserBirthday = userBirthday.getText().toString();
        bean.mUserHeight = Integer.valueOf(userHeight.getText().toString());
        bean.mUserBodyWeight = Integer.valueOf(userWeight.getText().toString());
        bean.mUserJob = userJob.getText().toString();

        //学历
        switch (userEducated.getCheckedRadioButtonId()) {
            case R.id.radioButton_master:
                bean.mUserEducationLevel = EducationType.Master;
                break;

            case R.id.radioButton_college:
                bean.mUserEducationLevel = EducationType.College;
                break;

            case R.id.radioButton_specialty:
                bean.mUserEducationLevel = EducationType.Specialty;
                break;

            case R.id.radioButton_highery:
                bean.mUserEducationLevel = EducationType.Highery;
                break;

            default:
                bean.mUserEducationLevel = EducationType.Others;
                break;
        }

        bean.mUserUniversity = "";
        bean.mUserProfessional = "";
        bean.mUserCharacter = userHobby.getText().toString();
        bean.mUserHobby = userInterest.getText().toString();
        bean.mUserDescription = userOthers.getText().toString();

        //开始添加
        addPersonPresenter.addNewPerson(bean, new IAddResultCallBack() {
            @Override
            public void onSuccess(String personId) {
                startToTarget(personId);
            }

            @Override
            public void onFailure(int code, String arg0) {
                ToastUtil.show(AddPersonActivity.this, "添加失败");
            }
        });
    }
}
