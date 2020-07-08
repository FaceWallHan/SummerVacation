package com.start.head.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.start.head.R;

public class MainActivity extends BroadcastBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button force_offline=findViewById(R.id.force_offline);
        force_offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getString(R.string.login));
                sendBroadcast(intent);
            }
        });
    }
}