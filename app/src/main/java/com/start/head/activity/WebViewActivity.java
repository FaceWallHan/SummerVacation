package com.start.head.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.start.head.R;

public class WebViewActivity extends AppCompatActivity {
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);
        WebView web_view=findViewById(R.id.web_view);
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.setWebViewClient(new WebViewClient());
        web_view.loadUrl("http://www.baidu.com");
//        web_view.loadUrl("file:///android_asset/user_self/user.html");
//        web_view.addJavascriptInterface(this,"FaceWall");
    }
    @JavascriptInterface
    public void callAndroid(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(WebViewActivity.this, "js调用android成功", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
