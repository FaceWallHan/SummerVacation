package com.start.head.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.start.head.R;
import com.start.head.adapter.NewsAdapter;
import com.start.head.bean.News;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewsTitleFragment extends Fragment {
    public static   boolean isTwoPane;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.news_title_frag_layout,container,false);
        RecyclerView newsTitleRecyclerView=view.findViewById(R.id.news_title_recycler_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        newsTitleRecyclerView.setLayoutManager(linearLayoutManager);
        NewsAdapter adapter=new NewsAdapter(getNews(),this);
        newsTitleRecyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (view.findViewById(R.id.news_content_fragment)!=null){
            isTwoPane=true;
            //可以找到news_content_layout布局时，为双页模式
        }else {
            isTwoPane=false;
            //找不到news_content_layout布局时，为单页模式
        }
    }
    private List<News> getNews(){
        List<News>list=new ArrayList<>();
        for (int i = 0; i <=50; i++) {
            News news=new News();
            news.setTitle("this is news title"+i);
            news.setContent(getRandomLengthContent("this is news content"+i+"."));
            list.add(news);
        }
        return list;
    }
    private String getRandomLengthContent(String content){
        Random random=new Random();
        int length=random.nextInt(20)+1;
        StringBuilder builder=new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(content);
        }
        return builder.toString();
    }
}
