package com.start.head.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.start.head.R;
import com.start.head.fragment.NewsContentFragment;

public class NewsContentActivity extends AppCompatActivity {
    private final static String title="news_title";
    private final static String content="news_content";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content_layout);
        String newsTitle=getIntent().getStringExtra(title);
        String newsContent=getIntent().getStringExtra(content);
        NewsContentFragment newsContentFragment= (NewsContentFragment) getSupportFragmentManager().findFragmentById(R.id.news_content_fragment);
        newsContentFragment.refresh(newsTitle,newsContent);
    }
    public  static  void actionStart(Context context,String newsTitle,String newsContent){
        Intent intent=new Intent(context,NewsContentActivity.class);
        intent.putExtra(content,newsContent);
        intent.putExtra(title,newsTitle);
        context.startActivity(intent);
    }
}