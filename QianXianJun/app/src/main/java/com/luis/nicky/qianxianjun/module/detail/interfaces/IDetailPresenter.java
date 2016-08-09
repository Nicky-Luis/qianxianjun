package com.luis.nicky.qianxianjun.module.detail.interfaces;

import com.luis.nicky.qianxianjun.module.detail.presenter.DetailPresenter;

/**
 * Created by Nicky on 2016/7/31.
 */
public interface IDetailPresenter {

    //获取用户的信息
    void getPersonInfo(String personId, DetailPresenter.ResultCallback callback);
}
