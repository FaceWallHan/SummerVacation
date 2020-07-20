package com.start.head.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.start.head.R;

import java.io.File;
import java.io.IOException;

public class PlayAudioActivity extends AppCompatActivity implements View.OnClickListener {
    private MediaPlayer mediaPlayer;
    private Button play_audio,stop_audio,pause_audio;
    private final int AUDIO_PERMISSION=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_audio_layout);
        inView();
    }
    private void inView(){
        mediaPlayer=new MediaPlayer();
        play_audio=findViewById(R.id.play_audio);
        stop_audio=findViewById(R.id.stop_audio);
        pause_audio=findViewById(R.id.pause_audio);
        pause_audio.setOnClickListener(this);
        stop_audio.setOnClickListener(this);
        play_audio.setOnClickListener(this);
        if (ContextCompat.checkSelfPermission(PlayAudioActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(PlayAudioActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},AUDIO_PERMISSION);
        }else {
            initMediaPlayer();
        }
    }
    private void initMediaPlayer(){
//        File file=new File(Environment.getExternalStorageDirectory(),"seven.mp3");
//        try {
//            mediaPlayer.setDataSource(file.getPath());//指定音频文件的路径
//            mediaPlayer.prepare();//让MediaPlayer进入到准备状态
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        mediaPlayer=MediaPlayer.create(PlayAudioActivity.this,R.raw.jack);
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
                    initMediaPlayer();
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
                    initMediaPlayer();
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