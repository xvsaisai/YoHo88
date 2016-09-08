package com.example.saisai.yoho.receiver;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.example.saisai.yoho.R;
import com.example.saisai.yoho.bean.PushBean;
import com.example.saisai.yoho.util.MyLog;
import com.google.gson.Gson;

import cn.bmob.push.PushConstants;

/**
 * Created by saisai on 2016/9/8.
 */
public class PushMessageReceiver extends BroadcastReceiver {
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
//            Log.d("bmob", "客户端收到推送内容："+intent.getStringExtra("msg"));
            String msg = intent.getStringExtra("msg");
            if (msg != null && !TextUtils.isEmpty(msg)) {

                PushBean pushBean = new Gson().fromJson(msg, PushBean.class);

                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.message);
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_item);
                remoteViews.setImageViewResource(R.id.iv_header, R.drawable.push);
                remoteViews.setTextViewText(R.id.tv_title, pushBean.getTitle());
                remoteViews.setTextViewText(R.id.tv_desc, pushBean.getDesc());

//                PendingIntent pendingIntent=new PendingIntent();
                Notification notification = new Notification.Builder(context).setWhen(System.currentTimeMillis())
                        .setTicker("你有新的消息")
                        .setLargeIcon(bitmap)
                        .setAutoCancel(true)
//                        .setContentIntent()
                        .setContent(remoteViews).build();
                manager.notify(1, notification);
                MyLog.m(pushBean.getTitle() + "=======" + pushBean.getDesc());
            }


        }
    }
}
