package com.luis.nicky.qianxianjun.presenter.interfaces;

/**
 * Created by Nicky on 2016/7/26.
 *
 */
public interface IAddPersonCallBack {

    //添加成功
    void onSuccess();

    // 添加失败
    void onFailure(int code, String arg0);
}
