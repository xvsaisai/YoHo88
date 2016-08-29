package com.example.saisai.yoho.util;

import com.example.saisai.yoho.MyApplication;

/**
 * Created by saisai on 2016/8/22.
 */
public class DimensUtils {

    public static int dp2px(int value){
        return (int) (MyApplication.app.getResources().getDisplayMetrics().density*value+0.5f);
    }
}
