package com.example.afinal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Final extends AppCompatActivity {
    private ImageButton btn_up;
    private ImageButton btn_pause;
    private ImageButton btn_down;
    private MediaPlayer mediaPlayer;
    private boolean ispause=false;
    TextView song;
    TextView artist;
    private String songName = "oo";
    private String artistName ="11" ;
    private String TAG="Final";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        btn_up = findViewById(R.id.up);
        btn_pause = findViewById(R.id.pause);
        btn_down = findViewById(R.id.down);
        song=findViewById(R.id.song);
        artist=findViewById(R.id.artist);

        songName = getIntent().getStringExtra("songTitle");
        Log.i(TAG, "onCreate: songName="+songName);
//        artistName = getIntent().getSringExtra("artistTitle");
//        songName=getIntent().getStringExtra("song_name","0");
//        artistName=sharedPreferences.getString("artist_name","1");
//        Thread t = new Thread((Runnable) this);
//        t.start();
//        mediaPlayer=MediaPlayer.create(this,R.raw.whalien52);
////        btn_up.setOnClickListener((View.OnClickListener) this);
////        btn_down.setOnClickListener((View.OnClickListener) this);
//        btn_pause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mediaPlayer.isPlaying() && !ispause) {
//                    mediaPlayer.pause();
//                    ispause = true;
//                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.pause, null));
//                } else {
//                    mediaPlayer.start();//开始播放音频
//                    ispause = false;
//                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.play, null));
//                }
//            }
//        });
//        Handler handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                if (msg.what == 5) {
//                    Bundle bdl = (Bundle) msg.obj;
//                    songName = bdl.getString("song-name");
//                    artistName = bdl.getString("artistName");
//                    SharedPreferences sharedPreferences = getSharedPreferences("music", Activity.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("song_name", songName);
//                    editor.putString("artist_name",artistName);
//                    editor.apply();
//                }
//                super.handleMessage(msg);
//            }
//        };
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.list_item){
            Intent list = new Intent(this, MusicList.class);
            startActivity(list);
        }
        return super.onOptionsItemSelected(item);
    }
}