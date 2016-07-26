package com.luis.nicky.qianxianjun.presenter;

import com.luis.nicky.qianxianjun.common.basic.BasePresenter;
import com.luis.nicky.qianxianjun.model.Person;
import com.luis.nicky.qianxianjun.model.Photo;
import com.luis.nicky.qianxianjun.model.TargetPerson;
import com.luis.nicky.qianxianjun.presenter.bean.PersonBean;
import com.luis.nicky.qianxianjun.presenter.interfaces.IAddPersonPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicky on 2016/7/26.
 */
public class AddPersonPersenter extends BasePresenter implements IAddPersonPresenter {

    /**
     * 添加新的用户
     */
    @Override
    public void addNewPerson(PersonBean personBean, PersonBean targetBean) {
        Person person = new Person();
        TargetPerson targetPerson = new TargetPerson();
        List<Photo> photoList = new ArrayList<>();
    }
}
