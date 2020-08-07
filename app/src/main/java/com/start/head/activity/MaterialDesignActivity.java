package com.start.head.activity;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.start.head.R;
import com.start.head.adapter.FruitAdapter;
import com.start.head.bean.Fruit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MaterialDesignActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navView;
    private FloatingActionButton fab;
    private ActionBar actionBar;
    private SwipeRefreshLayout swipe_refresh;
    private List<Fruit>list;
    private FruitAdapter adapter;
    private Fruit[] fruits={new Fruit(R.drawable.apple,"apple"),new Fruit(R.drawable.banana,"banana")
    ,new Fruit(R.drawable.mango,"mango"),new Fruit(R.drawable.orange,"orange")
    ,new Fruit(R.drawable.pear,"pear"),new Fruit(R.drawable.watermelon,"watermelon")};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_design_layout);
        setRecyclerView();
        initFruits();
        inView();
        setListener();
    }
    private void initFruits(){
        list.clear();
        for (int i = 0; i<50; i++) {
            Random random=new Random();
            int index=random.nextInt(fruits.length);
            list.add(fruits[index]);
        }
        adapter.notifyDataSetChanged();
    }
    private void setRecyclerView(){
        RecyclerView recycler_design=findViewById(R.id.recycler_design);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recycler_design.setLayoutManager(layoutManager);
        list=new ArrayList<>();
        adapter=new FruitAdapter(list);
        recycler_design.setAdapter(adapter);
    }
    @SuppressLint("ResourceAsColor")
    private void setListener(){
        setSupportActionBar(toolbar);
        swipe_refresh.setColorSchemeColors(R.color.colorPrimary);
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);//显示导航按钮
            actionBar.setHomeAsUpIndicator(R.drawable.change);
        }
        navView.setCheckedItem(R.id.nav_call);//nav_call菜单项设置为默认选中
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawers();
                return true;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Data deleted",Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MaterialDesignActivity.this, "FAB clicked", Toast.LENGTH_SHORT).show();
                            }
                        }).show();

            }
        });
    }
    private void inView(){
        toolbar=findViewById(R.id.toolbar);
        navView=findViewById(R.id.nav_view);
        fab=findViewById(R.id.fab);
        drawerLayout=findViewById(R.id.draw_layout);
        actionBar=getSupportActionBar();
        swipe_refresh=findViewById(R.id.swipe_refresh);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toobar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(this, "you clicked backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "you clicked delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this, "you clicked setting", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
    private void refreshFruits(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        swipe_refresh.setRefreshing(false);//刷新事件结束，隐藏刷新进度条
                    }
                });
            }
        }).start();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //在屏幕变化的时候进行相应的逻辑处理
    }
}
