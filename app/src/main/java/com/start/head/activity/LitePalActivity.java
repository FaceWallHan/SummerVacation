package com.start.head.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.start.head.R;
import com.start.head.adapter.ListViewAdapter;
import com.start.head.bean.Fruit;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class LitePalActivity extends AppCompatActivity {
    private static final String TAG = "LitePalActivity";
    private Button LitePal_bt;
    private List<Fruit>list;
    private ListView LitePal_list;
    private ListViewAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.litepal_layout);
        inView();
        setListener();
    }
    private void setListener(){
        LitePal_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random=new Random();
                insertSql(getTime(),random.nextInt());
                selectAll();

            }
        });
    }
    private void selectAll(){
        list.clear();
        list.addAll(LitePal.findAll(Fruit.class));
        Collections.sort(list, new Comparator<Fruit>() {
            @Override
            public int compare(Fruit fruit, Fruit t1) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
                try {
                    Date dt1=format.parse(fruit.getName());
                    Date dt2=format.parse(t1.getName());
                    if (dt1.getTime()>dt2.getTime()){
                        return -1;//大的放前面--->降序
                    }else {
                        return 1;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        /**
         *Iteration can be replaced with bulk 'Collection.addAll' call
         * 可以用批量“ Collection.addAll”调用代替迭代
         * */
        adapter.notifyDataSetChanged();
        Log.d(TAG, "selectAll: "+LitePal.findAll(Fruit.class).size());
    }
    private boolean insertSql(String time,int id){
        Fruit fruit=new Fruit();
        fruit.setName(time);
        fruit.setTypeId(id);
        return fruit.save();
    }
    private String getTime(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date=new Date(System.currentTimeMillis());
        return format.format(date);
    }
    private void inView(){
        LitePal_bt=findViewById(R.id.LitePal_bt);
        LitePal_list=findViewById(R.id.LitePal_list);
        list=LitePal.findAll(Fruit.class);
        adapter=new ListViewAdapter(this,list);
        LitePal_list.setAdapter(adapter);
    }
}
