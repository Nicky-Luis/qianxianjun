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
import com.luis.nicky.qianxianjun.module.add.interfaces.IAddTargetPresenter;
import com.luis.nicky.qianxianjun.module.add.presenter.AddTargetPersenter;

import butterknife.InjectView;
import butterknife.OnClick;

public class AddTargetActivity extends BaseActivity {

    public static final String Intent_Key = "PERSON_ID";
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
    //用户ID
    private String personId;


    //回调接口
    private IAddTargetPresenter addTargetPresenter;

    @OnClick(value = {R.id.btn_back, R.id.btn_next})
    public void onclick(View v) {
        switch (v.getId()) {
            //搜索
            case R.id.btn_back:
                finish();
                break;

            //添加
            case R.id.btn_next:
                startToAdd();
                break;

            default:
                break;
        }
    }


    @Override
    public int setLayoutId() {
        return R.layout.activity_target_person;
    }

    @Override
    protected void loadLayout(View v) {

    }

    @Override
    protected void onInitialize() {
        Intent intent = getIntent();
        personId = intent.getStringExtra(Intent_Key);
    }

    @Override
    public void initPresenter() {
        addTargetPresenter = new AddTargetPersenter(AddTargetActivity.this);
    }

    /**
     * 开始添加
     */
    private void startToAdd() {
        PersonBean bean = new PersonBean();
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
        addTargetPresenter.addNewTarget(bean, personId, new IAddResultCallBack() {
            @Override
            public void onSuccess(String personId) {
                ToastUtil.show(AddTargetActivity.this, "添加成功");
                startToPhoto();
            }

            @Override
            public void onFailure(int code, String arg0) {
                ToastUtil.show(AddTargetActivity.this, "添加失败");
            }
        });
    }

    /**
     * 保存信息
     */
    private void startToPhoto() {
        Intent intent = new Intent(AddTargetActivity.this, AddPhotoActivity.class);
        intent.putExtra(AddPhotoActivity.Intent_Key, personId);
        startActivity(intent);
    }
}
