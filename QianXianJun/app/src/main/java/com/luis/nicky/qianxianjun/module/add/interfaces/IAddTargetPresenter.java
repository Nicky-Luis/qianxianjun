package com.luis.nicky.qianxianjun.module.add.interfaces;

import com.luis.nicky.qianxianjun.helper.PersonNetBean;

/**
 * Created by Nicky on 2016/7/26.
 * 接口
 */
public interface IAddTargetPresenter {

    //添加新的用户标准
    void addNewTarget(PersonNetBean targetBean, final String personId,
                      final IAddResultCallBack callBack);
}
