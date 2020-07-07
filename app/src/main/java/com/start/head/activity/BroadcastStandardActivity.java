package com.start.head.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.start.head.R;
import com.start.head.broadcast.LocalReceiver;
import com.start.head.broadcast.MyBroadcastReceiver;

public class BroadcastStandardActivity extends AppCompatActivity {
    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager manager;//大清亡了？？？LocalBroadcastManager变得要导依赖了！！！

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.broadcast_standard_layout);
        final String localStr=getString(R.string.LocalBroadcastReceiver);
        Button send_broadcast_standard=findViewById(R.id.send_broadcast_standard);
        manager=LocalBroadcastManager.getInstance(this);//获取实例
        send_broadcast_standard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                Intent intent=new Intent(getString(R.string.MyBroadcastReceiver));
                //sendBroadcast(intent);//标准广播
                sendOrderedBroadcast(intent,null);//有序广播
                 */
                Intent intent=new Intent(localStr);
                manager.sendBroadcast(intent);
            }
        });
        intentFilter=new IntentFilter();
        intentFilter.addAction(localStr);
        localReceiver=new LocalReceiver();
        manager.registerReceiver(localReceiver,intentFilter);//注册本地广播监听器
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.unregisterReceiver(localReceiver);
        /**
         * 本地广播无法通过静态注册的方式来接收
         * 本地广播仅在应用程序内部传递
         * 本地广播比发送系统全局广播更高效？？？
         * */
    }
}