package com.start.head.activity;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.start.head.R;

public class ProviderActivity extends AppCompatActivity {
    private Button add_data,query_data,update_data,delete_data;
    private ListView provider_list;
    private String newId,uriString="content://example.databasetest.provider/book";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_layout);
        inView();
    }
    private void setListener(){
        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri=Uri.parse(uriString);
                ContentValues values=new ContentValues();
                values.put("name","A Clash of Kings");
                values.put("author","George Martin");
                values.put("pages",1040);
                values.put("price",22.85);
                Uri newUri=getContentResolver().insert(uri,values);
                newId=newUri.getPathSegments().get(1);
            }
        });
        update_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri=Uri.parse(uriString+"/"+newId);
                ContentValues values=new ContentValues();
                values.put("name","A Storm of Swords");
                values.put("pages",1216);
                values.put("price",24.05);
                getContentResolver().update(uri,values,null,null);
            }
        });
        delete_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri=Uri.parse(uriString+"/"+newId);
                getContentResolver().delete(uri,null,null);
            }
        });
    }
    private void inView(){
        add_data=findViewById(R.id.add_data);
        query_data=findViewById(R.id.query_data);
        update_data=findViewById(R.id.update_data);
        delete_data=findViewById(R.id.delete_data);
        provider_list=findViewById(R.id.provider_list);
    }
}
