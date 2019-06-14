package com.example.page;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Pre extends ListActivity {
    Handler handler=new Handler(){

        public void handleMessage(Message msg){

            if(msg.what==250){

                Intent intent=new Intent(Pre.this,MainActivity.class);

                startActivity(intent);

                finish();

            }

        };

    };

    @Override

    protected void onCreate(Bundle savedInstanceState){

        //TODO Auto-generated method stub

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pre);     //加载布局

        WaitThread thread=new WaitThread();        //开启线程

        thread.start();

    }

    public class WaitThread extends Thread{            //创建线程

        @Override

        public void run(){

            //TODO Auto-generated method stub

            try{

                Thread.sleep(1000);

            }catch (InterruptedException e){

                //TODO Auto-generated catch block

                e.printStackTrace();

            }

            Message message=new Message();

            message.what=250;

            message.arg1=1;

            handler.sendMessage(message);

        }

    }

}