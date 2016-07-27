package com.luis.nicky.qianxianjun.module.add.interfaces;

/**
 * Created by Nicky on 2016/7/26.
 *
 */
public interface IAddResultCallBack {

    //添加成功
    void onSuccess(String personId);

    // 添加失败
    void onFailure(int code, String arg0);
}
