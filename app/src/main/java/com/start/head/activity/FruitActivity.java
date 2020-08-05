package com.start.head.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.start.head.R;

public class FruitActivity extends AppCompatActivity {
    public static final String FRUIT_NAME="fruit_name";
    public static final String FRUIT_IMAGE_ID="fruit_image_id";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fruit_layout);
        inView();
    }
    private void inView(){
        Intent intent=getIntent();
        String fruitName=intent.getStringExtra(FRUIT_NAME);
        int fruitImageId=intent.getIntExtra(FRUIT_IMAGE_ID,0);
        Toolbar toolbar=findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsing_toolbar=findViewById(R.id.collapsing_toolbar);
        ImageView fruit_image_view=findViewById(R.id.fruit_image_view);
        TextView fruit_content_text=findViewById(R.id.fruit_content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsing_toolbar.setTitle(fruitName);
        Glide.with(this).load(fruitImageId).into(fruit_image_view);
        fruit_content_text.setText(generateFruitContent(fruitName));
    }
    private String generateFruitContent(String fruitName){
        StringBuilder builder=new StringBuilder();
        for (int i = 0; i < 500; i++) {
            builder.append(fruitName);
        }
        return builder.toString();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
