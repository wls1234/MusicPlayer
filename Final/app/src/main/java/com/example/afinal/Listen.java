package com.example.afinal;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class Listen extends AppCompatActivity {
    String TAG = "Listen";
    MediaPlayer mediaPlayer;
    private boolean ispause = false;
    int p;
    private SeekBar seekBar;
    SeekBar volumnBar;
    TextView bTime;
    TextView eTime;
    int totalTime;
    Handler handler1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen);
        String s = getIntent().getStringExtra("sn");
        String a = getIntent().getStringExtra("an");
        p = getIntent().getIntExtra("position", 0);
        Log.i(TAG, "onCreate: sn=" + s);
        ((TextView) findViewById(R.id.song1)).setText(s);
        ((TextView) findViewById(R.id.artist1)).setText(a);
        if (p == 0) {
            mediaPlayer = MediaPlayer.create(this, R.raw.song1);
        } else if (p == 1) {
            mediaPlayer = MediaPlayer.create(this, R.raw.song2);
        } else if (p == 2) {
            mediaPlayer = MediaPlayer.create(this, R.raw.song3);
        }
//        mediaPlayer.setLooping(true);
////        mediaPlayer.seekTo(0);
////        mediaPlayer.setVolume(0.5f,0.5f);
////        totalTime=mediaPlayer.getDuration();
////        seekBar =  findViewById(R.id.seekbar);
////        seekBar.setMax(totalTime);
////        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
////            @Override
////            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
////                if (fromUser){
////                    mediaPlayer.seekTo(progress);
////                    seekBar.setProgress(progress);
////                }
////            }
////
////            @Override
////            public void onStartTrackingTouch(SeekBar seekBar) {
////
////            }
////
////            @Override
////            public void onStopTrackingTouch(SeekBar seekBar) {
////
////            }
////        });
////        volumnBar=findViewById(R.id.volumnBar);
////        volumnBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
////            @Override
////            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
////                float volumnNum=progress/100f;
////                mediaPlayer.setVolume(volumnNum,volumnNum);
////            }
////
////            @Override
////            public void onStartTrackingTouch(SeekBar seekBar) {
////
////            }
////
////            @Override
////            public void onStopTrackingTouch(SeekBar seekBar) {
////            }
////        });
////        //更新进度条
////        new Thread(new Runnable() {
////            public void run() {
////                while (mediaPlayer!=null){
////                    Message message=new Message();
////                    message.what=mediaPlayer.getCurrentPosition();
////                    handler1.sendMessage(message);
////                }
////            }
////        }).start();
////
////      handler1=new Handler(){
////            @Override
////            public void handleMessage(Message message) {
////                super.handleMessage(message);
////                int currentPosition=message.what;
////                seekBar.setProgress(currentPosition);
////                String beTime=createTimeLable(currentPosition);
////                bTime.setText(beTime);
////                String remaintime=createTimeLable(totalTime-currentPosition);
////                eTime.setText(remaintime);
////            }
////        };
////    }
////    private String createTimeLable(int time){
////        String timelable="";
////        int min=time/1000/60;
////        int sec=time/1000%60;
////        timelable=min+":";
////        if (sec<10) timelable+=0;
////        timelable+=sec;
////        return timelable;
////
////    }
    }
    public void onClick(View v) {
        if (mediaPlayer.isPlaying() && !ispause) {
            mediaPlayer.pause();
            ispause = true;
            ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.pause, null));
        } else {
            mediaPlayer.start();//开始播放音频
            ispause = false;
            ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.play, null));
        }
    }
    }
