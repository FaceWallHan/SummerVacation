package com.start.head.activity;

import android.app.AppComponentFactory;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.start.head.R;
import com.start.head.broadcast.ForeOfflineReceiver;
import com.start.head.tools.ActivityCollector;

public class BroadcastBaseActivity extends AppCompatActivity {
    private ForeOfflineReceiver receiver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(getString(R.string.login));
        receiver=new ForeOfflineReceiver();
        registerReceiver(receiver,intentFilter);
    }
    /**
     * 保证只有处于栈顶的activity才能接受到这条强制下线广播
     * */
    @Override
    protected void onPause() {
        super.onPause();
        if (receiver!=null){
            unregisterReceiver(receiver);
            receiver=null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
