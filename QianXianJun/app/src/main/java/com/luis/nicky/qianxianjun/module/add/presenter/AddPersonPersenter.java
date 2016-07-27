package com.luis.nicky.qianxianjun.module.add.presenter;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.luis.nicky.qianxianjun.common.basic.BasePresenter;
import com.luis.nicky.qianxianjun.model.Person;
import com.luis.nicky.qianxianjun.collections.BmobDataType;
import com.luis.nicky.qianxianjun.collections.PersonBean;
import com.luis.nicky.qianxianjun.module.add.interfaces.IAddResultCallBack;
import com.luis.nicky.qianxianjun.module.add.interfaces.IAddPersonPresenter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Nicky on 2016/7/26.
 * 添加信息
 */
public class AddPersonPersenter extends BasePresenter implements IAddPersonPresenter {

    //上下文
    private Context mContext;
    //结果回调
    private IAddResultCallBack resultCallBack;
    //当前用户的唯一标示
    private String currentPersonId;

    public AddPersonPersenter(Context context) {
        this.mContext = context;
    }

    @Override
    public void addNewPerson(PersonBean personBean, IAddResultCallBack callBack) {
        this.resultCallBack = callBack;
        //生成用户id
        this.currentPersonId = getUUID(BmobDataType.PERSON);
        //当前用户信息
        final Person person = new Person(personBean);
        person.setUUID(currentPersonId);
        person.addAll("mUserHobby", stringToList(personBean.mUserHobby));
        person.addAll("mUserCharacter", stringToList(personBean.mUserCharacter));

        //首先保存用户信息
        person.save(mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                //如果操作成功了，则保存拥有者Id
                resultCallBack.onSuccess(currentPersonId);
            }

            @Override
            public void onFailure(int i, String s) {
                resultCallBack.onFailure(i, s);
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

    /**
     * 安卓生成唯一标示
     *
     * @return String:
     * 微信号前三个字母+IMEI+时间戳（毫秒）
     */
    private String getUUID(BmobDataType type) {
        String Prefix = "";
        switch (type) {
            case PERSON:
                Prefix = "PERSON";
                break;

            case TARGET_PERSON:
                Prefix = "TARGET_PERSON";
                break;

            case PHOTO:
                Prefix = "TARGET_PERSON";
                break;

            default:
                Prefix = "OTHER";
                break;
        }

        //获取imei
        TelephonyManager TelephonyMgr = (TelephonyManager) mContext.getSystemService(mContext.TELEPHONY_SERVICE);
        String IMEI = TelephonyMgr.getDeviceId();
        //获取时间戳
        String Timestamp = String.valueOf(System.currentTimeMillis());
        String UUID = Prefix + "-" + IMEI + "-" + Timestamp;
        return UUID;
    }

}
