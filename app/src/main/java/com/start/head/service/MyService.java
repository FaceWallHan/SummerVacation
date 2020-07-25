package com.start.head.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.start.head.R;

import java.io.File;

public class MyService extends Service {
    private DownloadBinder binder=new DownloadBinder();
    public static final String TAG = "MyService";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        //服务创建时调用
        super.onCreate();
        Log.d(TAG, "onCreate: executed");
        Intent intent=new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:10086"));
        PendingIntent pi=PendingIntent.getActivity(this,0,intent,0);
        Notification notification=new NotificationCompat.Builder(this)
                .setContentTitle("This is content title")
                .setContentText("This is content text")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)//显示在系统状态栏上的小图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))//下拉系统状态栏时的大图标
                .setContentIntent(pi)
                .build();
        startForeground(1,notification);//让MyService变成一个前台服务，并在系统状态栏显示出来
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: executed");
        return super.onStartCommand(intent, flags, startId);
        //每次服务启动时调用

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //服务销毁时调用
        Log.d(TAG, "onDestroy: executed");
    }
}
