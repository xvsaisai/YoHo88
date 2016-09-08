package com.example.saisai.yoho;

import android.app.Application;

import com.example.saisai.yoho.user.User;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;

/**
 * Created by saisai on 2016/8/22.
 */
public class MyApplication extends Application {

    public static MyApplication app;
    public static User user = null;//new User();
    public static int count;

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "18c0f050eafa68809c7beac909d20a23");
        BmobInstallation.getCurrentInstallation().save();
        BmobPush.startWork(this);
        app=this;
    }

    public static boolean checkLogin() {
        return user != null;
    }
}
