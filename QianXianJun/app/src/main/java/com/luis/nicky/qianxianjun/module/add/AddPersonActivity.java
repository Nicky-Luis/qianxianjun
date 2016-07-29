package com.luis.nicky.qianxianjun.module.add;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.luis.nicky.qianxianjun.R;
import com.luis.nicky.qianxianjun.entry.EducationLevel;
import com.luis.nicky.qianxianjun.helper.PersonNetBean;
import com.luis.nicky.qianxianjun.entry.SexType;
import com.luis.nicky.qianxianjun.base.basic.BaseActivityWithTitleBar;
import com.luis.nicky.qianxianjun.base.utils.ToastUtil;
import com.luis.nicky.qianxianjun.base.view.DialogUtil;
import com.luis.nicky.qianxianjun.base.view.TitleBar;
import com.luis.nicky.qianxianjun.module.add.interfaces.IAddResultCallBack;
import com.luis.nicky.qianxianjun.module.add.interfaces.IAddPersonPresenter;
import com.luis.nicky.qianxianjun.module.add.presenter.AddPersonPersenter;

import butterknife.InjectView;
import de.greenrobot.event.EventBus;

public class AddPersonActivity extends BaseActivityWithTitleBar {

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
    //标头栏
    @InjectView(R.id.title_bar)
    TitleBar titleBar;

    //回调接口
    private IAddPersonPresenter addPersonPresenter;

    @Override
    public int setLayoutId() {
        return R.layout.activity_add_person;
    }

    @Override
    protected void loadLayout(View v) {
        titleBar.setTitle("完善用户的信息");
        //添加右侧按钮
        TextView nextBtn = new TextView(this);
        nextBtn.setTextSize(14);
        nextBtn.setText("下一步");
        nextBtn.setTextColor(ContextCompat.getColor(this, R.color.main_light_white));
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startToAdd();
            }
        });

        titleBar.setRightView(nextBtn);
    }

    @Override
    protected void onInitialize() {
        //注册EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    public void initPresenter() {
        //添加的结果返回
        addPersonPresenter = new AddPersonPersenter(AddPersonActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //反注册EventBus
        EventBus.getDefault().unregister(this);
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
        PersonNetBean bean = new PersonNetBean();
        bean.mUserName = username.getText().toString();
        bean.mUserWechatId = userWechat.getText().toString();
        int checkId = userSex.getCheckedRadioButtonId();
        bean.mUserSex = ((checkId == R.id.radioButton_man) ? SexType.Man : SexType.Female);
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

        //数据不完善则返回
        if (!isDataComplete(bean)) {
            return;
        }

        DialogUtil.instance().showLoadingDialog(AddPersonActivity.this, "开始上传");
        //开始添加
        addPersonPresenter.addNewPerson(bean, new IAddResultCallBack() {
            @Override
            public void onSuccess(String personId) {
                DialogUtil.dismissDialog();
                startToTarget(personId);
                ToastUtil.show(AddPersonActivity.this, "添加成功");
            }

            @Override
            public void onFailure(int code, String arg0) {
                DialogUtil.dismissDialog();
                ToastUtil.show(AddPersonActivity.this, "添加失败");
            }
        });
    }


    //获取控件的值
    private int getEdtValue(EditText View) {
        String heightVar = View.getText().toString();
        if (heightVar == null || heightVar.equals("")) {
            return 0;
        }
        return Integer.valueOf(heightVar);
    }

    //判断数据是否合法
    private boolean isDataComplete(PersonNetBean bean) {
        if (null == bean.mUserWechatId || bean.mUserWechatId.equals("")) {
            ToastUtil.show(this, "微信号不能为空");
            return false;
        }
        return true;
    }


    //结束
    public void onEventMainThread(AddPhotoActivity.AddFinish msg) {
        finish();
    }
}
