package com.luis.nicky.qianxianjun.model;

import com.luis.nicky.qianxianjun.helper.PersonNetBean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Nicky on 2016/7/24.
 * 目标用户实例
 */
public class TargetPerson extends BmobObject {

    public int getmSex() {
        return mSex;
    }

    public void setmSex(int mSex) {
        this.mSex = mSex;
    }

    ///////////////////////////用户的基本信息///////////////////////////////
    //用户id
    private int mSex;
    //用户id
    private String mOwnerId;
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

    public TargetPerson() {
    }

    public TargetPerson(PersonNetBean personBean) {
        this.setmSex(personBean.mUserSex);
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

    //////////////////////////////////////////////////
    public String getOwnerId() {
        return mOwnerId;
    }

    public String getArea() {
        return mUserArea;
    }

    public String getBirthday() {
        return mUserBirthday;
    }

    public String getHeight() {
        return mUserHeight+"";
    }

    public String getBodyWeight() {
        return mUserBodyWeight+"";
    }

    public String getJob() {
        return mUserJob;
    }

    public String getEducationLevel() {
        return mUserEducationLevel+"";
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

    public void setOwnerID(String id) {
        this.mOwnerId = id;
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

}
