package com.luis.nicky.qianxianjun.module.add.presenter;

import android.content.Context;

import com.luis.nicky.qianxianjun.collections.PersonBean;
import com.luis.nicky.qianxianjun.common.basic.BasePresenter;
import com.luis.nicky.qianxianjun.model.TargetPerson;
import com.luis.nicky.qianxianjun.module.add.interfaces.IAddResultCallBack;
import com.luis.nicky.qianxianjun.module.add.interfaces.IAddTargetPresenter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Nicky on 2016/7/26.
 * 添加信息
 */
public class AddTargetPersenter extends BasePresenter implements IAddTargetPresenter {

    //上下文
    private Context mContext;

    public AddTargetPersenter(Context context) {
        this.mContext = context;

    }


    @Override
    public void addNewTarget(PersonBean targetBean, final String personId,
                             final IAddResultCallBack callBack) {
        //目标用户信息
        final TargetPerson targetPerson = new TargetPerson(targetBean);
        targetPerson.setOwnerID(personId);
        targetPerson.addAll("mUserHobby", stringToList(targetBean.mUserHobby));
        targetPerson.addAll("mUserCharacter", stringToList(targetBean.mUserCharacter));

        //保存目标用户的信息
        targetPerson.save(mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                callBack.onSuccess(personId);
            }

            @Override
            public void onFailure(int i, String s) {
                callBack.onFailure(i, s);
            }
        });
    }

    ////////////////////////////private method///////////////////////////////////

    /**
     * 性格、爱好转换为list
     */
    private List<String> stringToList(String info) {
        List<String> infoList = new ArrayList<>();

        String[] infoArray = info.split("、");
        for (String msg : infoArray) {
            infoList.add(msg);
        }
        return infoList;
    }
}
