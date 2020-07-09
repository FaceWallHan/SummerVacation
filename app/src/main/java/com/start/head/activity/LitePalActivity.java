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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            //list= LitePal.findAll(Fruit.class);
            adapter.notifyDataSetChanged();
            Log.d(TAG, "handleMessage: ");
            return false;
        }
    });
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
        new Thread(){
            @Override
            public void run() {
                list= LitePal.findAll(Fruit.class);

                super.run();
            }
        }.start();

        Log.d(TAG, "selectAll: "+LitePal.findAll(Fruit.class).size());
        handler.sendEmptyMessageAtTime(0,50);

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
