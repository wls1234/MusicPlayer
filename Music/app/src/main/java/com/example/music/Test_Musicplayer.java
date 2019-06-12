package com.example.music;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import java.io.IOException;

public class Test_Musicplayer extends AppCompatActivity {
        private  MediaPlayer mediaPlayer;
        private boolean ispause=false;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mediaplayer);
            mediaPlayer = MediaPlayer.create(this, R.raw.alltoowell);//创建MediaPlayer独享
            final Button btn_play = findViewById(R.id.btnPlayUrl);
            // final Button btn_pause=findViewById(R.id.btnPause);
            final Button btn_stop = findViewById(R.id.btnStop);
            btn_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mediaPlayer.isPlaying() && !ispause) {
                        mediaPlayer.pause();
                        ispause = true;
                    } else {
                        mediaPlayer.start();//开始播放音频
                        ispause = false;
                    }
                }
            });
            btn_stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaPlayer.stop();
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
                    mediaPlayer=MediaPlayer.create(this,R.raw.alltoowell);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
            this.progress = progress * player.mediaPlayer.getDuration()
                    / seekBar.getMax();
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
            player.mediaPlayer.seekTo(progress);
        }
    }

}
