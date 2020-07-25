package com.start.head.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyIntentService extends IntentService {//'android.app.IntentService' is deprecated？？？
    /**
     * @param name
     * @deprecated
     * MyIntentService集开启线程和自动停止于一身
     */
    private static final String TAG = "MyIntentService";
    public MyIntentService(String name) {
        super(name);
    }
    public MyIntentService() {
        super("MyIntentService");
        //为什么必须要有无参构造函数？？？
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //打印当前线程的id
        Log.d(TAG, "Thread id is "+Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: executed");
    }
}
