package com.start.head.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.start.head.R;

import java.io.File;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);
        Button send_notice=findViewById(R.id.send_notice);
        send_notice.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send_notice:
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:10086"));
                PendingIntent pi=PendingIntent.getActivity(this,0,intent,0);
                NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification=new NotificationCompat.Builder(this)
                        .setContentTitle("This is content title")
                        .setContentText("This is content text")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)//显示在系统状态栏上的小图标
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))//下拉系统状态栏时的大图标
                        .setContentIntent(pi)
                        .setAutoCancel(true)
                        .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg")))
                        .setVibrate(new long[]{0,1000,1000,1000})
                            //下标为0的值表示手机静止的时长，下标为1的值手机振动的时长，下标为2的值又表示手机静止的时长，以此类推
                        .setLights(Color.GREEN,1000,1000)
                                               //亮起的时长，暗去的时长
                        .setDefaults(NotificationCompat.DEFAULT_ALL)//默认效果，根据当前手机环境来觉得
                        .build();
                manager.notify(1,notification);
                break;
            default:
                break;
        }
    }
}
