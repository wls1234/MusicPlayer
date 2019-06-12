package com.example.music;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private  MediaPlayer mediaPlayer;
    private boolean ispause=false;
    private File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        file = new File("/sdcard/whalien52.mp3");
        if(file.exists()){
            mediaPlayer = MediaPlayer.create(this, Uri.parse(file.getAbsolutePath()));//创建MediaPlayer独享
        }else{
           Toast.makeText(MainActivity.this,"要播放的音频文件不存在！",Toast.LENGTH_SHORT).show();
            return;
        }
        final ImageButton btn_play=findViewById(R.id.play);
        ImageButton btn_stop=findViewById(R.id.stop);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying() && !ispause) {
                    mediaPlayer.pause();
                    ispause = true;
                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.play, null));
                } else {
                    mediaPlayer.start();//开始播放音频
                    ispause = false;
                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_background, null));
                }
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mediaPlayer.stop();
            btn_play.setImageDrawable(getResources().getDrawable(R.drawable.play, null));
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play();
            }
        });
    }
    private  void play(){
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(file.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
