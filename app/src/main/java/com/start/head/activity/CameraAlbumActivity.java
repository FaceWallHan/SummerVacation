package com.start.head.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.start.head.R;

public class CameraAlbumActivity extends AppCompatActivity {
    private static final int TAKE_PHOTO=1;
    private ImageView picture;
    private Uri imageUri;
    private Button take_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_album_layout);
        inView();
        setListener();
    }
    private void setListener(){
        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void inView(){
        picture=findViewById(R.id.picture);
        take_photo=findViewById(R.id.take_photo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode==RESULT_OK){

                }
                break;
            default:
                break;
        }
    }
}