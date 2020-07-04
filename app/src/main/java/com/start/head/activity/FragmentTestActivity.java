package com.start.head.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.start.head.R;
import com.start.head.fragment.AnotherRightFragment;
import com.start.head.fragment.RightFragment;

public class FragmentTestActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_test_layout);
        Button button=findViewById(R.id.button);
        button.setOnClickListener(this);
        replaceFragment(new RightFragment());
        /**
         * 使用不同限定符来加载不同布局
         * res文件夹下新建layout-large（大屏幕设备）,layout-sw600dp（>=600dp时会加载本文件夹下布局，<600dp加载默认的layout）
         * */
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:
                replaceFragment(new AnotherRightFragment());
                break;
            default:
                break;
        }
    }
    private  void  replaceFragment(Fragment fragment){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
//        RightFragment rightFragment= (RightFragment) getSupportFragmentManager().findFragmentById(R.id.right_layout);
        //fragment与activity进行通信
        transaction.replace(R.id.right_layout,fragment);
        transaction.addToBackStack(null);//将事务添加到返回栈中
        transaction.commit();
    }
}