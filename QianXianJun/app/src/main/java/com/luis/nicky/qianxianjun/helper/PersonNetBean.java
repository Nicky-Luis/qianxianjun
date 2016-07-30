package com.luis.nicky.qianxianjun.helper;


import android.content.Context;

import com.luis.nicky.qianxianjun.base.basic.BaseModel;

/**
 * Created by Nicky on 2016/7/26.
 * 用户网络操作相关的bean
 */
public class PersonNetBean  extends BaseModel {
    public String mUserName;
    //微信号
    public String mUserWechatId;
    //性别
    public int mUserSex;
    //电话
    public String mUserPhone;
    //qq号码
    public String mUserQQ;
    //微博
    public String mUserWeibo;
    //地区
    public String mUserArea;
    //生日
    public String mUserBirthday;
    //身高cm
    public int mUserHeight;
    //体重kg
    public int mUserBodyWeight;
    //工作职业
    public String mUserJob;
    //学历
    public int mUserEducationLevel;
    //大学
    public String mUserUniversity;
    //专业
    public String mUserProfessional;
    //性格
    public String mUserCharacter;
    //爱好
    public String mUserHobby;
    //其他描述
    public String mUserDescription;

    public PersonNetBean(Context context) {
        super(context);
    }

}
