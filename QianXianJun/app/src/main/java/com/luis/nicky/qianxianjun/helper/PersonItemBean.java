package com.luis.nicky.qianxianjun.helper;

import android.content.Context;

import com.luis.nicky.qianxianjun.base.basic.BaseModel;
import com.luis.nicky.qianxianjun.model.Person;
import com.luis.nicky.qianxianjun.model.TargetPerson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicky on 2016/7/29.
 * 显示子在主页的item的数据
 */
public class PersonItemBean extends BaseModel {
    //用户
    public Person person;
   //目标用户
    public TargetPerson personTarget;
    //保存用户的相册信息
    public List<String> photos =new ArrayList<>();

    public PersonItemBean(Context context) {
        super(context);
    }
}
