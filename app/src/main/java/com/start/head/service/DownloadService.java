package com.start.head.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.start.head.R;
import com.start.head.tools.DownloadListener;
import com.start.head.tools.DownloadTask;

import java.io.File;

public class DownloadService extends Service implements DownloadListener {
    private DownloadTask downloadTask;
    private String downloadUrl;
    private DownloadBinder binder=new DownloadBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onProgress(int progress) {
        getNotificationManager().notify(1,getNotification("Downloading...",progress));
    }

    @Override
    public void onSuccess() {
        downloadTask=null;
        //下载成功时将前台服务通知关闭，并创建一个下载成功的通知
        stopForeground(true);
        getNotificationManager().notify(1,getNotification("Downloading Success",-1));
        Toast.makeText(this, "Downloading Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailed() {
        downloadTask=null;
        //下载失败时将前台服务通知关闭，并创建一个下载失败的通知
        stopForeground(true);
        getNotificationManager().notify(1,getNotification("Downloading Failed",-1));
        Toast.makeText(this, "Downloading Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaused() {
        downloadTask=null;
        Toast.makeText(this, "Paused", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCanceled() {
        downloadTask=null;
        stopForeground(true);
        Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
    }
    private NotificationManager getNotificationManager(){
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }
    private Notification getNotification(String title,int progress){
        Intent intent=new Intent();
        PendingIntent pi=PendingIntent.getActivity(this,0,intent,0);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .setContentTitle(title);
        if (progress>=0){
            //当progress大于或等于0时才需显示下载进度
            builder.setContentText(progress+"%")
            .setProgress(100,progress,false);
        }
        return builder.build();
    }
    class DownloadBinder extends Binder{
        public void startDownload(String url){
            if (downloadTask==null){
                downloadUrl=url;
                downloadTask=new DownloadTask(DownloadService.this);
                downloadTask.execute(downloadUrl);
                startForeground(1,getNotification("Downloading...",0));
                Toast.makeText(DownloadService.this, "Downloading...", Toast.LENGTH_SHORT).show();
            }
        }
        public void pauseDownload(){
            if (downloadTask!=null){
                downloadTask.pauseDownload();
            }
        }
        public void cancelDownload(){
            if (downloadTask!=null){
                downloadTask.cancelDownload();
            }
            if (downloadUrl!=null){
                //取消下载时需将文件删除，并将通知关闭
                String fileName=downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                String directory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                File file=new File(directory+fileName);
                if (file.exists()){
                    file.delete();
                }
                getNotificationManager().cancel(1);
                stopForeground(true);
                Toast.makeText(DownloadService.this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
