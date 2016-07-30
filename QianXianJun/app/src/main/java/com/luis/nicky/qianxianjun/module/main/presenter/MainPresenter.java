package com.luis.nicky.qianxianjun.module.main.presenter;

import android.content.Context;

import com.luis.nicky.qianxianjun.base.basic.BaseResponse;
import com.luis.nicky.qianxianjun.base.utils.ToastUtil;
import com.luis.nicky.qianxianjun.helper.PersonItemBean;
import com.luis.nicky.qianxianjun.model.Person;
import com.luis.nicky.qianxianjun.model.Photo;
import com.luis.nicky.qianxianjun.model.TargetPerson;
import com.luis.nicky.qianxianjun.module.main.interfaces.IMainPresenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Nicky on 2016/7/30.
 * p
 */
public class MainPresenter implements IMainPresenter {

    //上下文
    private Context mContext;
    // 下拉刷新
    private static final int STATE_REFRESH = 0;
    // 加载更多
    private static final int STATE_MORE = 1;
    // 每页的数据是10条
    private int limit = 10;
    // 当前页的编号，从0开始
    private int curPage = 0;
    //用户的数据
    private List<PersonItemBean> personDatas;
    //是否是第一次查询
    private boolean isFirstReq = false;

    public MainPresenter(Context mContext) {
        this.mContext = mContext;
        this.personDatas = new ArrayList<>();
    }

    @Override
    public void refreshData(boolean isFirst, ResultCallback callback) {
        isFirstReq = isFirst;
        queryPersonData(0, STATE_REFRESH, callback);
    }

    @Override
    public void addMoreData(ResultCallback callback) {
        isFirstReq = false;
        queryPersonData(curPage, STATE_MORE, callback);
    }


    ////////////////////////////private method///////////////////////////////

    /**
     * 分页获取数据
     *
     * @param page       页码
     * @param actionType ListView的操作类型（下拉刷新、上拉加载更多）
     */
    private void queryPersonData(final int page, final int actionType,
                                 final ResultCallback callback) {
        BmobQuery<Person> query = new BmobQuery<>();

        //缓存策略
        if (isFirstReq) {
            query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        } else {
            query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }
        // 按时间降序查询
        query.order("-createdAt");
        // 如果是加载更多
        if (actionType == STATE_MORE) {
            // 查询小于当前时间的
            query.addWhereLessThanOrEqualTo("createdAt", new BmobDate(new Date()));
            query.setSkip(curPage * limit);
        } else {
            curPage = 0;
            query.setSkip(page);
        }
        // 设置每页数据个数
        query.setLimit(limit);
        // 查找数据
        query.findObjects(mContext, new FindListener<Person>() {
            @Override
            public void onSuccess(List<Person> list) {
                if (list.size() > 0) {
                    if (actionType == STATE_REFRESH) {
                        curPage = 0;
                    }
                    personDatas.clear();
                    // 将本次查询的数据添加到数据中
                    for (Person person : list) {
                        PersonItemBean itemBean = new PersonItemBean(mContext);
                        itemBean.personId = person.getUUID();
                        itemBean.personSex = person.getmUserSex();
                        itemBean.personAge = 11;
                        itemBean.personArea = person.getArea();
                        personDatas.add(itemBean);
                    }
                    curPage++;
                    //开始查询用户的标准
                    queryTargetPerson(list, callback);
                } else if (actionType == STATE_MORE) {
                    ToastUtil.show("没有更多数据了");
                    callback.onFailed(BaseResponse.RESULT_FAILED);
                } else if (actionType == STATE_REFRESH) {
                    ToastUtil.show("没有数据");
                    callback.onFailed(BaseResponse.RESULT_FAILED);
                }
            }

            @Override
            public void onError(int arg0, String arg1) {
                ToastUtil.show("查询失败");
                callback.onFailed(BaseResponse.RESULT_FAILED);
            }
        });
    }

    //查询用户的标准
    private void queryTargetPerson(final List<Person> persons, final ResultCallback callback) {
        //用户的id List
        List<String> personIdList = new ArrayList<>();
        for (Person person : persons) {
            personIdList.add(person.getUUID());
        }

        BmobQuery<TargetPerson> query = new BmobQuery<>();
        //缓存策略
        if (isFirstReq) {
            query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        } else {
            query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }
        query.addWhereContainedIn("mOwnerId", personIdList);

        //执行查询方法
        query.findObjects(mContext, new FindListener<TargetPerson>() {
            @Override
            public void onSuccess(List<TargetPerson> list) {
                if (list.size() > 0) {
                    int existCount = personDatas.size();
                    //读取数据
                    for (TargetPerson person : list) {
                        for (int index = existCount - 1; index >= 0; index--) {
                            PersonItemBean itemBean = personDatas.get(index);
                            if (itemBean.personId.equals(person.getOwnerId())) {
                                itemBean.personTarget = person.getDescription();
                            }
                        }
                    }
                }
                //不管有没有查询到都会进行查询照片信息
                queryPerson(persons, callback);
            }

            @Override
            public void onError(int i, String s) {
                ToastUtil.show("查询用户的标准失败");
                callback.onFailed(BaseResponse.RESULT_FAILED);
            }
        });
    }

    //查询用户的照片
    private void queryPerson(List<Person> persons, final ResultCallback callback) {
        //用户的id List
        List<String> personIdList = new ArrayList<>();
        for (Person person : persons) {
            personIdList.add(person.getUUID());
        }

        BmobQuery<Photo> query = new BmobQuery<>();
        //缓存策略
        if (isFirstReq) {
            query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        } else {
            query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }
        query.addWhereContainedIn("mUserID", personIdList);

        //执行查询方法
        query.findObjects(mContext, new FindListener<Photo>() {
            @Override
            public void onSuccess(List<Photo> list) {
                if (list.size() > 0) {
                    //读取数据
                    for (Photo photo : list) {
                        for (PersonItemBean itemBean : personDatas) {
                            if (itemBean.personId.equals(photo.getmUserID())) {
                                itemBean.personHeadUrl = photo.getmPhotoUrl();
                                break;
                            }
                        }
                    }
                }

                //回调
                callback.onSucceed(personDatas);
            }

            @Override
            public void onError(int i, String s) {
                ToastUtil.show("获取照片信息失败");
                callback.onFailed(BaseResponse.RESULT_FAILED);
            }
        });
    }

    /////////////////////////////结果回调////////////////////////////
    public interface ResultCallback extends BaseResponse {
        //成功
        void onSucceed(List<PersonItemBean> result);
    }
}
