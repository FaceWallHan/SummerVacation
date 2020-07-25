package com.start.head.service;

import android.os.Binder;
import android.util.Log;

public class DownloadBinder extends Binder {
    public void startDownload(){
        Log.d(MyService.TAG, "startDownload: executed");
    }
    public int getProgress(){
        Log.d(MyService.TAG, "getProgress: executed");
        return 0;
    }
}
