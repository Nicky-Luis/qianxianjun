package com.luis.nicky.qianxianjun.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Nicky on 2016/7/24.
 * 照片信息
 */
public class Photo extends BmobObject{

    ////////////////////////////////////
    //照片的类型
    private int mPhotoType;
    //照片的所属的用户ID
    private String mUserID;

    public int getmPhotoType() {
        return mPhotoType;
    }

    public String getmUserID() {
        return mUserID;
    }

    public String getmPhotoUrl() {
        return mPhotoUrl;
    }

    public String getmPhotoDescripte() {
        return mPhotoDescripte;
    }

    //照片的Url
    private String mPhotoUrl;
    //照片的描述
    private String mPhotoDescripte;

    public void setmPhotoType(int mPhotoType) {
        this.mPhotoType = mPhotoType;
    }

    public void setmUserID(String mUserID) {
        this.mUserID = mUserID;
    }

    public void setmPhotoUrl(String mPhotoUrl) {
        this.mPhotoUrl = mPhotoUrl;
    }

    public void setmPhotoDescripte(String mPhotoDescripte) {
        this.mPhotoDescripte = mPhotoDescripte;
    }
}
