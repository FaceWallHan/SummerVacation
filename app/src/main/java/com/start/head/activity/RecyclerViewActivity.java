package com.start.head.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.start.head.R;
import com.start.head.adapter.RecyclerViewAdapter;
import com.start.head.bean.Fruit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecyclerViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String []data={"苹果","香蕉","橘子","西瓜","梨","芒果"};
    private List<Fruit> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_layout);
        recyclerView=findViewById(R.id.recycler_view);
//        LinearLayoutManager manager=new LinearLayoutManager(this);
//        manager.setOrientation(LinearLayoutManager.HORIZONTAL);//方向
        /**横向布局*/
//        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);//3列，排列方向
        /**瀑布流布局*/
        GridLayoutManager manager=new GridLayoutManager(this,3);
        /**卡片式布局*/
        recyclerView.setLayoutManager(manager);
        list=new ArrayList<>();
        for (int j = 0; j < data.length; j++) {
            for (int i = 0; i < data.length; i++) {
                list.add(new Fruit(i,getRandomLengthName(data[i])));
            }
        }

        RecyclerViewAdapter adapter=new RecyclerViewAdapter(list);
        recyclerView.setAdapter(adapter);
    }
    private String getRandomLengthName(String name){
        Random random=new Random();
        int length=random.nextInt(20)+1;
        StringBuilder builder=new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(name);
        }
        return builder.toString();
    }
}