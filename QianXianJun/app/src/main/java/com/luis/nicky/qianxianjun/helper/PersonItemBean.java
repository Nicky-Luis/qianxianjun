package com.luis.nicky.qianxianjun.helper;

import android.content.Context;

import com.luis.nicky.qianxianjun.base.basic.BaseModel;

/**
 * Created by Nicky on 2016/7/29.
 * 显示子在主页的item的数据
 */
public class PersonItemBean extends BaseModel {
    //用户id
    public String personId;
    //头像
    public String personHeadUrl;
    //性别
    public int personSex;
    //年龄
    public int personAge;
    //地区
    public String personArea;
    //要求
    public String personTarget;

    public PersonItemBean(Context context) {
        super(context);
    }
}
