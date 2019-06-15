package com.example.afinal;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //定义各类控件
    private ImageView up;
    private ImageView down;
    private int position;
    private ImageView pause;
    private MediaPlayer mediaPlayer;
    private TextView beginTime;
    private TextView endTime;
    private int totalTime;
    private SeekBar jidutiao;
    private boolean isStop;
    //接受多线程信息，安卓中不允许主线程实现UI更新
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            jidutiao.setProgress(msg.what);
            endTime.setText(formatTime(msg.what));

        }
    };

    private void Link() {

        up = findViewById(R.id.up);
        down = findViewById(R.id.down);
        pause = findViewById(R.id.pause);
        beginTime = findViewById(R.id.beginTime);
        endTime = findViewById(R.id.endTime);
        jidutiao = findViewById(R.id.jidutiao);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//获取传值
        Intent intent = getIntent();
        position = intent.getIntExtra("i", 0);//将传入的值赋给position
//获取mediaplayer
        mediaPlayer = new MediaPlayer();
        Link();//绑定ID方法
        play();//歌曲播放及一系列操作方法

/////////////////////////获取进度条点击位置并使歌曲跳转到该位置////////////////////////////////////
        jidutiao.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    mediaPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
    private void play() {

        mediaPlayer.reset();
        Music music = Common.musicList.get(position);//获取传来的值
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);


        /////////////////////歌曲播放////////////////////////////////////////////////////
        try {
            mediaPlayer.setDataSource(music.path);
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

        up.setOnClickListener(this);
        down.setOnClickListener(this);
        pause.setOnClickListener(this);

//        ///////////////////////////////////唱片打碟/////////////////////////////////////////////
//        animator = ObjectAnimator.ofFloat(pic, "rotation", 0f, 360.0f);
//        animator.setDuration(10000);
//        animator.setInterpolator(new LinearInterpolator());//匀速
//        animator.setRepeatCount(-1);//设置动画重复次数（-1代表一直转）
//        animator.setRepeatMode(ValueAnimator.RESTART);//动画重复模式
//        animator.start();

        ////////////////////////////////进度条/////////////////////////////////////////////////

        endTime.setText(formatTime(music.length));
        totalTime = music.length;
        new Thread(new SeekBarThread()).start();
        jidutiao.setMax(music.length);

//        ////////////////////////////指针拨动////////////////////////////////////////////////////
//        animator1 = ObjectAnimator.ofFloat(zhizhenmap, "rotation", -60f, 0.0f);
//        animator1.setDuration(900);
//        animator1.setRepeatCount(0);//设置动画重复次数（-1代表一直转）
//        animator1.start();




    }

    ///////////////获取歌曲时常///////////////////////
    private String formatTime(int length) {
        Date date = new Date(length);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        String TotalTime = simpleDateFormat.format(date);
        return TotalTime;

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.up:
                position--;
                if (position == -1) {
                    position = Common.musicList.size() - 1;
                }
                play();
                break;
            case R.id.down:
                position++;
                if (position == Common.musicList.size()) {
                    position = 0;
                }
                play();
            case R.id.pause:
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.reset();
        isStop = true;
    }

    class SeekBarThread implements Runnable {

        @Override
        public void run() {
            while (mediaPlayer != null && isStop == false) {
                // 将SeekBar位置设置到当前播放位置
                handler.sendEmptyMessage(mediaPlayer.getCurrentPosition());
                try {
                    // 每100毫秒更新一次位置
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
