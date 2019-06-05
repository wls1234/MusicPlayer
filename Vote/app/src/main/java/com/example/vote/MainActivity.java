package com.example.vote;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    public  static final String TAG="VOTE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View btn) {
        if (btn.getId() == R.id.btn1) {
            //异步调用
            new VoteTask().execute("赞成");
        } else if (btn.getId() == R.id.btn2) {
            new VoteTask().execute("反对");
        } else {
            new VoteTask().execute("弃权");
        }
    }
    private  class VoteTask extends AsyncTask<String,Void,String>{
        protected String doInBackground(String...params){
            for (String p:params){
                Log.i(TAG, "doInBackground: "+p);
            }
            String ret=doVote(params[0]);
            return  ret;
        }
        protected void onPostExecute(String s){
            Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
        }
    }
    private String doVote(String voteStr){
        String retStr="";
        Log.i("Vote: ","doVote() voteStr:"+voteStr);
        try{
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append("r=").append(URLEncoder.encode(voteStr,"utf-8"));

            byte[] data=stringBuffer.toString().getBytes();
            String urlPath ="http://10.64.224.18:8080/vote/Getvote";
            URL url=new URL(urlPath);


            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            //设置连接超时时间
            httpURLConnection.setConnectTimeout(3000);
            //打开输入流，向服务器获取数据
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            //以post方法提交数据
            httpURLConnection.setRequestMethod("POST");
            //设置请求体的类型是文本类型
            httpURLConnection.setRequestProperty("Content-type","application/x-www-form-urlencoded");
            //设置请求体长度
            httpURLConnection.setRequestProperty("Content-length",String.valueOf(data.length));
            //获得输出流，像服务器写入数据
            OutputStream outputStream=httpURLConnection.getOutputStream();
            outputStream.write(data);

            //获得服务器的响应码
            int response=httpURLConnection.getResponseCode();
            if (response==HttpURLConnection.HTTP_OK){
                InputStream inputStream=httpURLConnection.getInputStream();
                //处理服务器响应结果
                retStr=inputStreamToSting(inputStream);
                Log.i("doVote: ","retStr"+retStr);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retStr;
    }

    private String inputStreamToSting(InputStream inputStream) {
        //存储处理结果
        String resultData=null;
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        byte[] data=new byte[1024];
        int len=0;
        try{
            while ((len = inputStream.read(data))!=-1){
                byteArrayOutputStream.write(data,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData =new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }
}