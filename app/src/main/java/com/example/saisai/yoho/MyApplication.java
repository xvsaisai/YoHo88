package com.example.saisai.yoho;

import android.app.Application;

import com.example.saisai.yoho.user.User;

/**
 * Created by saisai on 2016/8/22.
 */
public class MyApplication extends Application {

    public static MyApplication app;
    public static User user;
    public static int count;

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
    }

    public static boolean checkLogin() {
        return user != null;
    }
}
