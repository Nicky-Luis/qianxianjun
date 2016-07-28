package com.luis.nicky.qianxianjun.module.add.presenter;

import android.content.Context;

import com.luis.nicky.qianxianjun.collections.BmobPhotoType;
import com.luis.nicky.qianxianjun.common.basic.BasePresenter;
import com.luis.nicky.qianxianjun.common.utils.Logger;
import com.luis.nicky.qianxianjun.model.Photo;
import com.luis.nicky.qianxianjun.module.add.interfaces.IAddResultCallBack;
import com.luis.nicky.qianxianjun.module.add.interfaces.IAddPhotoPresenter;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by Nicky on 2016/7/26.
 * 添加信息
 */
public class AddPhotoPersenter extends BasePresenter implements IAddPhotoPresenter {

    //上下文
    private Context mContext;
    //结果回调
    private IAddResultCallBack resultCallBack;
    //用户id
    private String currentPersonId;

    public AddPhotoPersenter(Context context) {
        this.mContext = context;

    }

    @Override
    public void addNewPotos(List<String> photoList, String personId, IAddResultCallBack callBack) {
        if (photoList == null || photoList.size() <= 0) {
            return;
        }
        this.currentPersonId = personId;
        this.resultCallBack = callBack;

        //将list转换为array
        final int fileCount = photoList.size();
        String[] filePaths = new String[fileCount];
        for (int index = 0; index < fileCount; index++) {
            filePaths[index] = photoList.get(index);
        }

        BmobFile.uploadBatch(mContext, filePaths, new UploadBatchListener() {

            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {
                //1、files-上传完成后的BmobFile集合，是为了方便大家对其上
                // 传后的数据进行操作，例如你可以将该文件保存到表中
                //2、urls-上传文件的完整url地址

                //如果数量相等，则代表文件全部上传完成
                if (urls.size() == fileCount) {
                    savePhotoInfo(urls);
                }
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                Logger.e("错误码:" + statuscode + ",错误描述：" + errormsg);
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                //1、curIndex--表示当前第几个文件正在上传
                //2、curPercent--表示当前上传文件的进度值（百分比）
                //3、total--表示总的上传文件数
                //4、totalPercent--表示总的上传进度（百分比）
                Logger.v("curIndex = " + curIndex);
            }
        });
    }

    ////////////////////////////private method///////////////////////////////////

    /**
     * 保存照片的信息到表里面
     */
    private void savePhotoInfo(List<String> urls) {
        if (urls == null || urls.size() <= 0) {
            return;
        }

        //保存图片的信息
        for (String url : urls) {
            Photo photo = new Photo();
            photo.setmUserID(currentPersonId);
            photo.setmPhotoType(BmobPhotoType.PhotoLife.ordinal());
            photo.setmPhotoUrl(url);
            photo.setmPhotoDescripte("暂无描述");
            //保存
            photo.save(mContext, new SaveListener() {
                @Override
                public void onSuccess() {
                    resultCallBack.onSuccess("");
                }

                @Override
                public void onFailure(int i, String s) {
                    resultCallBack.onFailure(i, s);
                }
            });
        }
    }
}
