package com.start.head.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.start.head.R;
import com.start.head.activity.NewsContentActivity;
import com.start.head.bean.News;
import com.start.head.fragment.NewsContentFragment;
import com.start.head.fragment.NewsTitleFragment;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{
    private List<News>list;
    private Fragment fragment;

    public NewsAdapter(List<News> list, Fragment fragment) {
        this.list = list;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_layout,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                News news=list.get(holder.getAdapterPosition());
                if (NewsTitleFragment.isTwoPane){
                    //如果是双页模式，则刷新NewsContentFragment中的内容
                    NewsContentFragment newsContentFragment= (NewsContentFragment) fragment.getFragmentManager().findFragmentById(R.id.news_content_fragment);
                    newsContentFragment.refresh(news.getTitle(),news.getContent());
                }else {
                    //如果是单页模式，则直接启动NewsContentActivity
                    NewsContentActivity.actionStart(parent.getContext(),news.getTitle(),news.getContent());
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news=list.get(position);
        holder.newsTitleText.setText(news.getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView newsTitleText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitleText=itemView.findViewById(R.id.news_title);
        }
    }
}
