package com.luis.nicky.qianxianjun.module.add.interfaces;

import com.luis.nicky.qianxianjun.collections.PersonBean;

/**
 * Created by Nicky on 2016/7/26.
 * 接口
 */
public interface IAddPersonPresenter {

    //添加新的用户
    void  addNewPerson(PersonBean personBean, IAddResultCallBack callBack);
}
