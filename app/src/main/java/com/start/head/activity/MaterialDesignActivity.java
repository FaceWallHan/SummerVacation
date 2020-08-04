package com.start.head.activity;

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
    private void inView(){
        Toolbar toolbar=findViewById(R.id.toolbar);
        NavigationView navView=findViewById(R.id.nav_view);
        FloatingActionButton fab=findViewById(R.id.fab);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.draw_layout);
        ActionBar actionBar=getSupportActionBar();
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
}
