package com.xiaoyou.greendaotest1.application;

import android.app.Application;
import android.content.Context;

import com.xiaoyou.greendaotest1.greendao.manager.GreenDaoManager;

/**
 * Created by joybar on 8/18/16.
 */
public class MyApplication extends Application {

    private static String TAG = "MyApplication";
    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        GreenDaoManager.initialize(this);//初始化数据库
    }

    public static Context getContext() {
        return context;
    }



}
