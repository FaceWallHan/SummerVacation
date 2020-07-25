package com.start.head.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.start.head.R;
import com.start.head.bean.App;
import com.start.head.net.HttpUtil;
import com.start.head.tools.ContentHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HTTPActivity extends AppCompatActivity {
    private String urlLocalXml="http://192.168.10.106:8080/test/get_data.";
    private TextView response_text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.http_layout);
        Button send_request=findViewById(R.id.send_request);
        response_text=findViewById(R.id.response_text);
        send_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequestWithOkHttp();
            }
        });
    }
    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client=new OkHttpClient();
                    RequestBody requestBody=new FormBody.Builder()
                            .add("username","admin")
                            .add("password","123456")
                            .build();
                    Request request=new Request.Builder()
                            //.url(urlLocalXml+"xml")
                            .url(urlLocalXml+"json")
                           // .post(requestBody)//POST请求
                            .build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();//为什么toString()不可以
                    //showResponse(responseData);
                    //showResponse(parseXMLWithPull(responseData));
                    //parseXMLWithSAX(responseData);
                    /***/
                    //showResponse(parseJSONWithJSONObject(responseData));
                    showResponse(parseJSONWithGSON(responseData));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private String parseJSONWithGSON(String jsonData){
        StringBuilder builder=new StringBuilder();
        Gson gson=new Gson();
        List<App> list=gson.fromJson(jsonData,new TypeToken<List<App>>(){}.getType());
        for (App app : list) {
            String id=app.getId();
            String name=app.getName();
            String version=app.getVersion();
            builder.append("id:").append(id).append("name:").append(name).append("version:").append(version).append("\n");
        }
        return builder.toString();
    }
    private String parseJSONWithJSONObject(String jsonData){
        StringBuilder builder=new StringBuilder();
        try {
            JSONArray jsonArray=new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String id=jsonObject.getString("id");
                String name=jsonObject.getString("name");
                String version=jsonObject.getString("version");
                builder.append("id:").append(id).append("name:").append(name).append("version:").append(version).append("\n");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
    private void parseXMLWithSAX(String xmlData){
        try {
            SAXParserFactory factory=SAXParserFactory.newInstance();
            XMLReader xmlReader=factory.newSAXParser().getXMLReader();
            ContentHandler handler=new ContentHandler();
            //将ContentHandler的实例设置到中
            xmlReader.setContentHandler(handler);
            //开始执行解析
            xmlReader.parse(new InputSource(new StringReader(xmlData)));
            showResponse(handler.getStrSum());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String parseXMLWithPull(String xmlData){
        StringBuilder builder=new StringBuilder();
        try {
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser=factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType=xmlPullParser.getEventType();
            String id="";
            String name="";
            String version="";
            while (eventType!=XmlPullParser.END_DOCUMENT){
                String nodeName=xmlPullParser.getName();
                switch (eventType){
                    //开始解析某个节点
                    case XmlPullParser.START_TAG:
                        if ("id".equals(nodeName)){
                            id=xmlPullParser.nextText();
                        }else if ("name".equals(nodeName)){
                            name=xmlPullParser.nextText();
                        }else if ("version".equals(nodeName)){
                            version=xmlPullParser.nextText();
                        }
                        break;
                    //完成解析某个节点
                    case XmlPullParser.END_TAG:
                        if ("app".equals(nodeName)){
                            builder.append("id:").append(id).append("name:").append(name).append("version:").append(version).append("\n");
                        }
                        break;
                }
                eventType=xmlPullParser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
    private void sendRequestWithHttpURLConnection(){
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try {
                    URL url=new URL("http://www.baidu.com");
                    connection= (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
//                    connection.setRequestMethod("POST");
////                    DataOutputStream out=new DataOutputStream(connection.getOutputStream());
////                    out.writeBytes("username=admin&password=123456");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in=connection.getInputStream();
                    //下面对获取到的输入流进行读取
                    reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    showResponse(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if (reader!=null){
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作，将结果显示到界面上
                response_text.setText(response);
            }
        });
    }
}
