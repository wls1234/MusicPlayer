package com.example.afinal;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Listen extends AppCompatActivity {
    String TAG = "Listen";
    MediaPlayer mediaPlayer;
    private boolean ispause = false;
    int p;

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
