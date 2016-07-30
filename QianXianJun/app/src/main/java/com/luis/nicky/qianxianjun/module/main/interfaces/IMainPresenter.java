package com.luis.nicky.qianxianjun.module.main.interfaces;

import com.luis.nicky.qianxianjun.module.main.presenter.MainPresenter;


/**
 * Created by Nicky on 2016/7/30.
 * p
 */
public interface IMainPresenter {
    //刷新数据
    void refreshData(boolean isFirst, MainPresenter.ResultCallback callback);

    //加载更多数据
    void addMoreData(MainPresenter.ResultCallback callback);
}
