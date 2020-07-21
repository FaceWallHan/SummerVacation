package com.start.head.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.start.head.R;

public class PlayVideoActivity extends AppCompatActivity {
    private final int AUDIO_PERMISSION=0;
    private VideoView video_view;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_video_layout);
        inView();
    }
    private void inView(){
        video_view=findViewById(R.id.video_view);
        mediaController=new MediaController(this);
        if (ContextCompat.checkSelfPermission(PlayVideoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(PlayVideoActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},AUDIO_PERMISSION);
        }else {
            initVideoPath();
        }
    }
    private void initVideoPath(){
        video_view.setMediaController(mediaController);
        mediaController.setMediaPlayer(video_view);
        video_view.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.life));
        video_view.start();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case AUDIO_PERMISSION:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    initVideoPath();
                }else {
                    Toast.makeText(this, "you denied the permission", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}