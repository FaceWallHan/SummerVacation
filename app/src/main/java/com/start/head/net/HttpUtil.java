package com.start.head.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.start.head.AppClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {
    private  static String httpUrl="http://192.168.10.106:8080/test/";
    public static void sendOkHttpRequest(String address, Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(httpUrl+address)
                .build();
        client.newCall(request).enqueue(callback);
    }
    public static boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager= (ConnectivityManager) AppClient.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable();
    }
    public static void sendHttpRequest(final String address, final HttpCallbackListener listener){
        //既然都要new，那么加上static又有什么意义呢？
        if (!isNetworkAvailable()){
            Toast.makeText(AppClient.getContext(), "network is unavailable", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                try {
                    URL url = new URL(httpUrl+address);
                    connection= (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    InputStream in=connection.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    if (listener!=null){
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (listener!=null){
                        listener.onError(e);
                    }
                }finally {
                    if (connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();

    }
}
