package com.luis.nicky.qianxianjun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.luis.nicky.qianxianjun.model.Person;
import com.luis.nicky.qianxianjun.common.utils.ToastUtil;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bombInit();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(MainActivity.this,"开始添加");

                Person people = new Person();
                people.setName("刘飞辉");
                people.setArea("广州");
                people.setBirthday("19910824");
                people.save(MainActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {

                        ToastUtil.show(MainActivity.this,"添加数据成功");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        ToastUtil.show(MainActivity.this,"添加数失败");
                    }
                });
            }
        });
    }

    /***
     * 初始化比目SDK
     */
    private void bombInit() {
        //提供以下两种方式进行初始化操作：

        //第一：默认初始化
        Bmob.initialize(this, "a47b827fafafaf9fe3884e12bfc4cec2");

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);
    }
}
