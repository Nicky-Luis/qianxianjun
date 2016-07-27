package com.luis.nicky.qianxianjun.module.add.interfaces;

import java.util.List;

/**
 * Created by Nicky on 2016/7/26.
 * 接口
 */
public interface IAddPhotoPresenter {

    //添加用户的照片
    void addNewPotos(List<String> photoList, String personId, IAddResultCallBack callBack);
}
