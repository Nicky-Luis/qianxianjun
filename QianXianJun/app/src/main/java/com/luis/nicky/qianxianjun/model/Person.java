package com.luis.nicky.qianxianjun.model;

import com.luis.nicky.qianxianjun.collections.PersonBean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Nicky on 2016/7/24.
 * 用户实例
 */
public class Person extends BmobObject {


    ///////////////////////////用户的基本信息///////////////////////////////
    //用户id
    private String UUID;

    public int getmUserSex() {
        return mUserSex;
    }

    public void setmUserSex(int mUserSex) {
        this.mUserSex = mUserSex;
    }

    //性别
    private int mUserSex;
    //名字
    private String mUserName;
    //微信号
    private String mUserWechatId;
    //电话
    private String mUserPhone;
    //qq号码
    private String mUserQQ;
    //微博
    private String mUserWeibo;
    //地区
    private String mUserArea;
    //生日
    private String mUserBirthday;
    //身高cm
    private int mUserHeight;
    //体重kg
    private int mUserBodyWeight;
    //工作职业
    private String mUserJob;
    //学历
    private int mUserEducationLevel;
    //大学
    private String mUserUniversity;
    //专业
    private String mUserProfessional;
    //性格
    private List<String> mUserCharacter;
    //爱好
    private List<String> mUserHobby;
    //其他描述
    private String mUserDescription;


    /////////////////////////////////////////////

    public Person() {
    }

    public Person(PersonBean personBean) {
        this.setName(personBean.mUserName);
        this.setmUserSex(personBean.mUserSex);
        this.setWechatId(personBean.mUserWechatId);
        this.setPhone(personBean.mUserPhone);
        this.setQQ(personBean.mUserQQ);
        this.setWeibo(personBean.mUserWeibo);
        this.setArea(personBean.mUserArea);
        this.setBirthday(personBean.mUserBirthday);
        this.setHeight(personBean.mUserHeight);
        this.setBodyWeight(personBean.mUserBodyWeight);
        this.setJob(personBean.mUserJob);
        this.setEducationLevel(personBean.mUserEducationLevel);
        this.setUniversity(personBean.mUserUniversity);
        this.setProfessional(personBean.mUserProfessional);
        this.setDescription(personBean.mUserDescription);
    }

    //////////////////////setter/////////////////////////////////
    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public void setName(String userName) {
        this.mUserName = userName;
    }

    public void setWechatId(String wchatId) {
        this.mUserWechatId = wchatId;
    }

    public void setPhone(String mUserPhone) {
        this.mUserPhone = mUserPhone;
    }

    public void setQQ(String userQQ) {
        this.mUserQQ = userQQ;
    }

    public void setWeibo(String userWeibo) {
        this.mUserWeibo = userWeibo;
    }

    public void setArea(String userArea) {
        this.mUserArea = userArea;
    }

    public void setBirthday(String userBirthday) {
        this.mUserBirthday = userBirthday;
    }

    public void setHeight(int serHeight) {
        this.mUserHeight = serHeight;
    }

    public void setBodyWeight(int uerBodyWeight) {
        this.mUserBodyWeight = uerBodyWeight;
    }

    public void setJob(String userJob) {
        this.mUserJob = userJob;
    }

    public void setEducationLevel(int userEducationLevel) {
        this.mUserEducationLevel = userEducationLevel;
    }

    public void setUniversity(String userUniversity) {
        this.mUserUniversity = userUniversity;
    }

    public void setProfessional(String userProfessional) {
        this.mUserProfessional = userProfessional;
    }

    public void setCharacter(List<String> userCharacter) {
        this.mUserCharacter = userCharacter;
    }

    public void setHobby(List<String> userHobby) {
        this.mUserHobby = userHobby;
    }

    public void setDescription(String userDescription) {
        this.mUserDescription = userDescription;
    }


    ////////////////////////getter//////////////////////////////////

    public String getUUID() {
        return UUID;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getWechatId() {
        return mUserWechatId;
    }

    public String getPhone() {
        return mUserPhone;
    }

    public String getQQ() {
        return mUserQQ;
    }

    public String getWeibo() {
        return mUserWeibo;
    }

    public String getArea() {
        return mUserArea;
    }

    public String getBirthday() {
        return mUserBirthday;
    }

    public int getHeight() {
        return mUserHeight;
    }

    public int getBodyWeight() {
        return mUserBodyWeight;
    }

    public String getJob() {
        return mUserJob;
    }

    public int getEducationLevel() {
        return mUserEducationLevel;
    }

    public String getUniversity() {
        return mUserUniversity;
    }

    public String getProfessional() {
        return mUserProfessional;
    }

    public List<String> getCharacter() {
        return mUserCharacter;
    }

    public List<String> getHobby() {
        return mUserHobby;
    }

    public String getDescription() {
        return mUserDescription;
    }

}
