package com.start.head.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.start.head.R;
import com.start.head.service.DownloadBinder;
import com.start.head.service.MyIntentService;
import com.start.head.service.MyService;

public class ServiceTestActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
    private DownloadBinder downloadBinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_test_layout);
        inView();
    }
    private void inView(){
        Button start_service=findViewById(R.id.start_service);
        Button stop_service=findViewById(R.id.stop_service);
        Button bind_service=findViewById(R.id.bind_service);
        Button unbind_service=findViewById(R.id.unbind_service);
        Button start_intent_service=findViewById(R.id.start_intent_service);
        start_intent_service.setOnClickListener(this);
        start_service.setOnClickListener(this);
        stop_service.setOnClickListener(this);
        bind_service.setOnClickListener(this);
        unbind_service.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_service:
                Intent startIntent=new Intent(this, MyService.class);
                startService(startIntent);
                break;
            case R.id.stop_service:
                Intent stopIntent=new Intent(this, MyService.class);
                stopService(stopIntent);
                break;
                /**一个服务既调用了startService方法，又调用了bindService方法。根据Android系统的机制，一个服务
                 * 只要被启动或者被绑定了之后，就会一直处于运行状态，必须要让以上两种条件同时不满足，服务才能被销毁，
                 * 所以要同时调用stopService和unbindService，onDestroy才会执行
                 * */
            case R.id.bind_service:
                Intent bindIntent=new Intent(this, MyService.class);
                bindService(bindIntent,this,BIND_AUTO_CREATE);//绑定服务
                //activity和service进行绑定后自动创建服务，会使得onCreate得到执行，onStartCommand不会执行
                break;
            case R.id.unbind_service:
                unbindService(this);//解绑服务
                break;
            case R.id.start_intent_service:
                Log.d("ServiceTestActivity", "Thread id is "+Thread.currentThread().getId());
                Intent intentService=new Intent(this, MyIntentService.class);
                startService(intentService);
                break;
            default:
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        downloadBinder= (DownloadBinder) iBinder;
        downloadBinder.startDownload();
        downloadBinder.getProgress();
        //成功绑定
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        //连接断开
    }
}
