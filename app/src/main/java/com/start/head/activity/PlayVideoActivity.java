package com.start.head.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.start.head.R;

public class PlayVideoActivity extends AppCompatActivity implements View.OnClickListener {
    private MediaPlayer mediaPlayer;
    private Button play_video,pause_video,replay_video,stop_video;
    private final int AUDIO_PERMISSION=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_video_layout);
        inView();
    }
    private void inView(){
        mediaPlayer=new MediaPlayer();
        play_video=findViewById(R.id.play_video);
        pause_video=findViewById(R.id.pause_video);
        replay_video=findViewById(R.id.replay_video);
        stop_video=findViewById(R.id.stop_video);
        stop_video.setOnClickListener(this);
        replay_video.setOnClickListener(this);
        pause_video.setOnClickListener(this);
        play_video.setOnClickListener(this);
        if (ContextCompat.checkSelfPermission(PlayVideoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(PlayVideoActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},AUDIO_PERMISSION);
        }else {
            initVideoPath();
        }
    }
    private void initVideoPath(){
        mediaPlayer=MediaPlayer.create(PlayVideoActivity.this,R.raw.jack);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.play_audio:
                if (!mediaPlayer.isPlaying()){
                    mediaPlayer.start();//开始播放
                }
                break;
            case R.id.pause_audio:
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();//暂停播放
                }
                break;
            case R.id.stop_audio:
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.reset();//停止播放
                    initVideoPath();
                }
                break;
        }
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
        if (mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}