package com.luis.nicky.qianxianjun.module.add;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.luis.nicky.qianxianjun.R;
import com.luis.nicky.qianxianjun.entry.EducationLevel;
import com.luis.nicky.qianxianjun.helper.PersonNetBean;
import com.luis.nicky.qianxianjun.entry.SexType;
import com.luis.nicky.qianxianjun.base.basic.BaseActivity;
import com.luis.nicky.qianxianjun.base.utils.ToastUtil;
import com.luis.nicky.qianxianjun.base.view.DialogUtil;
import com.luis.nicky.qianxianjun.base.view.TitleBar;
import com.luis.nicky.qianxianjun.module.add.interfaces.IAddResultCallBack;
import com.luis.nicky.qianxianjun.module.add.interfaces.IAddTargetPresenter;
import com.luis.nicky.qianxianjun.module.add.presenter.AddTargetPersenter;

import butterknife.InjectView;
import de.greenrobot.event.EventBus;

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
    //标头栏
    @InjectView(R.id.title_bar)
    TitleBar titleBar;

    //回调接口
    private IAddTargetPresenter addTargetPresenter;

    @Override
    public int setLayoutId() {
        return R.layout.activity_target_person;
    }

    @Override
    protected void loadLayout(View v) {
        titleBar.setTitle("填写用户的要求");
        //添加右侧按钮
        TextView nextBtn = new TextView(this);
        nextBtn.setTextSize(14);
        nextBtn.setText("下一步");
        nextBtn.setTextColor(ContextCompat.getColor(this, R.color.main_light_white));
        titleBar.setRightView(nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startToAdd();
            }
        });
    }

    @Override
    protected void onInitialize() {
        //注册EventBus
        EventBus.getDefault().register(this);

        Intent intent = getIntent();
        personId = intent.getStringExtra(Intent_Key);
    }

    @Override
    public void initPresenter() {
        addTargetPresenter = new AddTargetPersenter(AddTargetActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //反注册EventBus
        EventBus.getDefault().unregister(this);
    }

    /**
     * 开始添加
     */
    private void startToAdd() {
        PersonNetBean bean = new PersonNetBean(this);
        bean.mUserSex = userSex.getCheckedRadioButtonId() == R.id.radioButton_man
                ? SexType.Man : SexType.Female;
        bean.mUserPhone = "";
        bean.mUserQQ = "";
        bean.mUserWeibo = "";
        bean.mUserArea = userArea.getText().toString();
        bean.mUserBirthday = userBirthday.getText().toString();
        bean.mUserHeight = getEdtValue(userHeight);
        bean.mUserBodyWeight = getEdtValue(userWeight);
        bean.mUserJob = userJob.getText().toString();

        //学历
        switch (userEducated.getCheckedRadioButtonId()) {
            case R.id.radioButton_master:
                bean.mUserEducationLevel = EducationLevel.Master;
                break;

            case R.id.radioButton_college:
                bean.mUserEducationLevel = EducationLevel.College;
                break;

            case R.id.radioButton_specialty:
                bean.mUserEducationLevel = EducationLevel.Specialty;
                break;

            case R.id.radioButton_highery:
                bean.mUserEducationLevel = EducationLevel.Highery;
                break;

            default:
                bean.mUserEducationLevel = EducationLevel.Others;
                break;
        }

        bean.mUserUniversity = "";
        bean.mUserProfessional = "";
        bean.mUserCharacter = userHobby.getText().toString();
        bean.mUserHobby = userInterest.getText().toString();
        bean.mUserDescription = userOthers.getText().toString();

        if (!isDataComplete(bean)) {
            return;
        }

        DialogUtil.instance().showLoadingDialog(AddTargetActivity.this, "开始上传");
        //开始添加
        addTargetPresenter.addNewTarget(bean, personId, new IAddResultCallBack() {
            @Override
            public void onSuccess(String personId) {
                DialogUtil.dismissDialog();
                ToastUtil.show("添加成功");
                startToPhoto();
            }

            @Override
            public void onFailure(int code, String arg0) {
                DialogUtil.dismissDialog();
                ToastUtil.show( "添加失败");
            }
        });
    }

    //获取控件的值
    private int getEdtValue(EditText View) {
        String heightVar = View.getText().toString();
        if (TextUtils.isEmpty(heightVar)) {
            return 0;
        }
        return Integer.valueOf(heightVar);
    }

    //判断数据是否合法
    private boolean isDataComplete(PersonNetBean bean) {
        if (TextUtils.isEmpty(userHeight.getText().toString())) {
            ToastUtil.show( "身高不能为空");
            return false;
        }
        return true;
    }

    /**
     * 保存信息
     */
    private void startToPhoto() {
        Intent intent = new Intent(AddTargetActivity.this, AddPhotoActivity.class);
        intent.putExtra(AddPhotoActivity.Intent_Key, personId);
        startActivity(intent);
    }

    //结束
    public void onEventMainThread(AddPhotoActivity.AddFinish msg) {
        finish();
    }
}
