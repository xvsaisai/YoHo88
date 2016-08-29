package com.example.saisai.yoho.util;

import android.util.Log;

/**
 * Created by saisai on 2016/8/23.
 */
public class MyLog {

    public static void log(String tag,String value){
        if(true)
        print(tag,value);
    }

    private static void print(String tag, String value) {
        Log.e(tag,value);
    }
}
