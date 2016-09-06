package com.example.saisai.yoho.activity;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.saisai.yoho.util.HttpUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by saisai on 2016/9/5.
 */
public class MyIntentService extends IntentService {

    public MyIntentService() {
        //必须实现父类的构造方法
        super("IntentServiceDemo");
    }

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("onBind");
        return super.onBind(intent);
    }


    @Override
    public void onCreate() {
        System.out.println("onCreate");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        System.out.println("onStart");
        super.onStart(intent, startId);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void setIntentRedelivery(boolean enabled) {
        super.setIntentRedelivery(enabled);
        System.out.println("setIntentRedelivery");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //Intent是从Activity发过来的，携带识别参数，根据参数不同执行不同的任务
        String action = intent.getExtras().getString("url");

        new HttpUtils().loadData(action, "").setOnLoadDataListener(new HttpUtils.OnLoadDataListener() {
            @Override
            public void loadSuccess(String content) {

                File filesDir = getFilesDir();
                File file = new File(filesDir, "a");
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(content));
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));

                    byte[] bytes = new byte[1024];
                    int i = -1;
                    while ((i = bis.read(bytes)) != -1) {
                        bos.write(bytes);
                        bos.flush();
                    }
                    Log.e("file", "下载万恒");
                    bos.close();
                    bis.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void loadFailed(String msg) {

            }
        });
//        if (action.equals("oper1")) {
//            System.out.println("Operation1");
//        }else if (action.equals("oper2")) {
//            System.out.println("Operation2");
//        }
//
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onDestroy() {
        System.out.println("onDestroy");
        super.onDestroy();
    }
}
