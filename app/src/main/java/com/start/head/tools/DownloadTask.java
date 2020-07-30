package com.start.head.tools;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/**
 * Params:   执行AsyncTask时需要传入的参数
 * Progress: 后台任务执行时，需要在界面上显示的当前进度
 * Result:   任务执行完毕后需要返回的结果
 * */
public class DownloadTask extends AsyncTask<String,Integer,Integer> {
    public static final int TYPE_SUCCESS=0;
    public static final int TYPE_FAILED=1;
    public static final int TYPE_PAUSED=2;
    public static final int TYPE_CANCELED=3;
    private DownloadListener listener;
    private boolean isCanceled=false,isPaused=false;
    private int lastProgress;

    public DownloadTask(DownloadListener listener) {
        this.listener = listener;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        /**
         * 在后台执行具体的下载逻辑
         * */
        InputStream is=null;
        RandomAccessFile savedFile=null;
        File file=null;
        try {
            long downloadedLength=0;//记录已下载的文件长度
            String downloadUrl=strings[0];
            String fileName=downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            String directory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            file=new File(directory+fileName);
            if (file.exists()){
                downloadedLength=file.length();
            }
            long contentLength=getContentLength(downloadUrl);
            if (contentLength==0){
                return TYPE_FAILED;
            }else if (contentLength==downloadedLength){
                //已下载字节和文件总字节相等，说明已经下载完成了
                return TYPE_SUCCESS;
            }
            OkHttpClient client=new OkHttpClient();
            Request request=new Request.Builder()
                    //断点下载，指定从哪个字节开始下载
                    .addHeader("RANGE","bytes="+downloadedLength+"-")
                    .url(downloadUrl)
                    .build();
            Response response=client.newCall(request).execute();
            if (response!=null){
                is=response.body().byteStream();
                savedFile=new RandomAccessFile(file,"rw");
                savedFile.seek(downloadedLength);//跳过已下载的字节
                byte[] b=new byte[1024];
                int total=0;
                int len;
                while ((len=is.read(b))!=-1){
                    if (isCanceled){
                        return TYPE_CANCELED;
                    }else if (isPaused){
                        return TYPE_PAUSED;
                    }else {
                        total+=len;
                    }
                    savedFile.write(b,0,len);
                    //计算已下载的百分比
                    int progress= (int) ((total+downloadedLength)*100/contentLength);
                    publishProgress(progress);
                }
            }
            response.body().close();
            return TYPE_SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (is!=null){
                    is.close();
                }
                if (savedFile!=null){
                    savedFile.close();
                }
                if (isCanceled&&file!=null){
                    file.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        /**
         * 在界面上更新当前的下载进度
         * */
        int progress=values[0];
        if (progress>lastProgress){
            listener.onProgress(progress);
            lastProgress=progress;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        /**
         * 通知最终的下载结果
         * */
        switch (integer){
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            case TYPE_PAUSED:
                listener.onPaused();
                break;
            case TYPE_CANCELED:
                listener.onCanceled();
                break;
            default:
                break;
        }
    }
    public void pauseDownload(){
        isPaused=true;
    }
    public void cancelDownload(){
        isCanceled=true;
    }
    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(downloadUrl)
                .build();
        Response response=client.newCall(request).execute();
        if (response!=null&&response.isSuccessful()){
            long contentLength=response.body().contentLength();
            response.body().close();
            return contentLength;
        }
        return 0;
    }
}
