package com.start.head.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import androidx.annotation.Nullable;

public class LongRunningService  extends Service {
    /**Android中的定时任务一般有两种实现方式，
     * 一种是使用Java API里提供的Timer类，并不太适用于那些在后台运行的定时任务
     * 一种是使用Android的Alarm机制
     * */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //在这里执行具体的逻辑操作
            }
        }).start();
        AlarmManager manager= (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour=60*60*1000;      //这是一个小时的毫秒数
        long triggerAtTime= SystemClock.elapsedRealtime()+anHour;
        Intent i=new Intent(this,LongRunningService.class);
        PendingIntent pi=PendingIntent.getService(this,0,i,0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        /**
         * ELAPSED_REALTIME_WAKEUP：表示让定时任务的触发事件从系统开机开始算起，但会唤醒CPU
         * ELAPSED_REALTIME：表示让定时任务的触发事件从系统开机开始算起，但不会唤醒CPU
         * RTC：表示让定时任务的触发事件从1970年1月1日0点开始算起，但不会唤醒CPU
         * RTC_WAKEUP：表示让定时任务的触发事件从1970年1月1日0点开始算起，但会唤醒CPU
         */
        /**
         * SystemClock.elapsedRealtime()可以获取系统开机至今所经历时间的毫秒数
         * System.currentTimeMillis()可以获取1970年1月1日0点至今所经历时间的毫秒数
         * */
        //setExact代替set，基本上可以保证任务能够准时执行
        /**
         * 在Doze模式（用户长时间不使用手机）下，Alarm任务将会变得不准时
         * */
        return super.onStartCommand(intent, flags, startId);
    }
}
