package com.start.head.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.start.head.R;
import com.start.head.adapter.ListViewAdapter;
import com.start.head.bean.Fruit;

import java.util.ArrayList;
import java.util.List;

public class ListViewTestActivity extends AppCompatActivity {
    private ListView list_view;
    private String []data={"苹果","香蕉","橘子","西瓜","梨","芒果"};
    private List<Fruit>list;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_test_layout);
        list_view=findViewById(R.id.list_view);
        list=new ArrayList<>();
        for (int j = 0; j < data.length; j++) {
            for (int i = 0; i < data.length; i++) {
                list.add(new Fruit(i,data[i]));
            }
        }
        adapter=new ListViewAdapter(this,list);
        list_view.setAdapter(adapter);
    }
}