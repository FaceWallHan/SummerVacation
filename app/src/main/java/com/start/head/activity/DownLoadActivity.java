package com.start.head.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.start.head.R;

public class DownLoadActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_layout);
        inView();
    }
    private void inView(){
        Button cancel_download=findViewById(R.id.cancel_download);
        Button pause_download=findViewById(R.id.pause_download);
        Button start_download=findViewById(R.id.start_download);
        start_download.setOnClickListener(this);
        cancel_download.setOnClickListener(this);
        pause_download.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
