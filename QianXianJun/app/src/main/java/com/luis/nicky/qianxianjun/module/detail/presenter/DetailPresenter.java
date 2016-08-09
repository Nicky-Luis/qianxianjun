package com.luis.nicky.qianxianjun.module.detail.presenter;

import android.content.Context;

import com.luis.nicky.qianxianjun.model.Person;
import com.luis.nicky.qianxianjun.model.TargetPerson;
import com.luis.nicky.qianxianjun.module.detail.interfaces.IDetailPresenter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by Nicky on 2016/7/31.
 *
 */
public class DetailPresenter implements IDetailPresenter {

    //context
    private Context mContext;
    //用户id
    private String personId;
    //用户信息
    private Person person;
    //目标用户
    private TargetPerson targetPerson;

    public DetailPresenter(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public void getPersonInfo(String uId, ResultCallback callback) {
        this.personId = uId;
        searchPersonInfo(callback);
    }

    //////////////////////////person method//////////////////////////////
    //个人信息
    private void searchPersonInfo(final ResultCallback callback) {
        BmobQuery<Person> query = new BmobQuery<>();
        query.addWhereEqualTo("UUID", personId);
        query.findObjects(mContext, new FindListener<Person>() {

            @Override
            public void onSuccess(List<Person> list) {
                //查询成功：共" + object.size() + "条数据i
                if (list.size() > 0) {
                    person = list.get(0);
                    searchTargetInfo(callback);
                }
            }

            @Override
            public void onError(int i, String s) {
                callback.onFailed();
            }
        });
    }

    //目标人物信息
    private void searchTargetInfo(final ResultCallback callback) {
        BmobQuery<TargetPerson> query = new BmobQuery<>();
        query.addWhereEqualTo("mOwnerId", personId);
        query.findObjects(mContext, new FindListener<TargetPerson>() {

            @Override
            public void onSuccess(List<TargetPerson> list) {
                if (list.size() > 0) {
                    targetPerson = list.get(0);
                    callback.onSuccessed(person, targetPerson);
                }
            }

            @Override
            public void onError(int i, String s) {
                callback.onFailed();
            }
        });
    }

    ///////////////////////////////////////////////////
    public interface ResultCallback {

        //成功后
        void onSuccessed(Person person, TargetPerson targetPerson);

        //失败后
        void onFailed();
    }
}
