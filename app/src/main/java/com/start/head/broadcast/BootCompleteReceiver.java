package com.start.head.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Toast.makeText(context, "Boot Complete", Toast.LENGTH_SHORT).show();
        /**
         * 不允许开启线程
         * 不要再onReceive中添加过多的逻辑或者进行任何的耗时操作
         * */
    }
}
