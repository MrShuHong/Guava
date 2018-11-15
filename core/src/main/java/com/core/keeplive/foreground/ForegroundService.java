package com.core.keeplive.foreground;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/**
 * File description.
 * 通过通知进程保活方案
 * @author dsh
 * @date 2018/11/3
 */

public class ForegroundService extends Service {

    private static final int SERVICE_ID = 1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (Build.VERSION.SDK_INT < 18){
            startForeground(SERVICE_ID,new Notification());
        } else if (Build.VERSION.SDK_INT < 26){
            startForeground(SERVICE_ID,new Notification());
            startService(new Intent(this,InnerService.class));
        }else{

            //以后的版本 通知需要设置通道channel 和 重要等级IMPORTANCE_NONE（不重要））

            // 8.0 app 退出前台，通知栏消息显示出来
            // 9.0   开启服务后就立马显示出来
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (manager != null){
                NotificationChannel channel = new NotificationChannel("channel", "xxx",
                        NotificationManager.IMPORTANCE_NONE);
                manager.createNotificationChannel(channel);
                Notification notification = new NotificationCompat
                        .Builder(this, "channel").build();
                startForeground(SERVICE_ID,notification);

            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private static class InnerService extends Service{
        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {

            startForeground(SERVICE_ID,new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }
    }
}
