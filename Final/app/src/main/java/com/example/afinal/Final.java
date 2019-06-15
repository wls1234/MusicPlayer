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
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.net.ssl.HttpsURLConnection;

public class Final extends AppCompatActivity implements Runnable {
    private ImageButton btn_up;
    private ImageButton btn_pause;
    private ImageButton btn_down;
    private MediaPlayer mediaPlayer;
    private boolean ispause = false;
    private String TAG = "Final";
    Handler handler;
    private int currentPosition;//当前音乐播放的进度
    private SeekBar seekBar;
    private Timer timer;
    private boolean isSeekBarChanging;
    private  String[] songName2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        btn_up = findViewById(R.id.up);
        btn_pause = findViewById(R.id.pause);
        btn_down = findViewById(R.id.down);
        //监听滚动条事件
        seekBar =  findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new MySeekBar());
        mediaPlayer = MediaPlayer.create(this, R.raw.whalien52);
//        btn_up.setOnClickListener((View.OnClickListener) this);
//        btn_down.setOnClickListener((View.OnClickListener) this);
        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
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
        });
        Thread t = new Thread(this);
        t.start();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 7) {
                    Bundle bdl=(Bundle)msg.obj;
                    songName2=bdl.getStringArray("song_name");
                    for(int i=0;i<songName2.length;i++){
                        Log.i(TAG, "handleMessage: songName"+songName2[i]);
                    }
//                    ListAdapter adapter = new ArrayAdapter<String>(Final.this, android.R.layout.simple_list_item_1, list2);
//                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
    }
    public class MySeekBar implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
        }

        /*滚动时,应当暂停后台定时器*/
        public void onStartTrackingTouch(SeekBar seekBar) {
            isSeekBarChanging = true;
        }
        /*滑动结束后，重新设置值*/
        public void onStopTrackingTouch(SeekBar seekBar) {
            isSeekBarChanging = false;
            mediaPlayer.seekTo(seekBar.getProgress());
        }
    }
    public void run() {
        Log.i(TAG, "run: ........");
            Bundle bundle=new Bundle();
        try {
            Document doc = null;
            doc = Jsoup.connect("https://www.phb123.com/yule/music/32090.html").get();
            Elements hs = doc.getElementsByTag("h2");
            String[] songName=new String[hs.size()];
            for (int i = 0; i < hs.size(); i ++) {
                Element h1 = hs.get(i);
                Log.d(TAG, "run: " + h1.text());
                String str1 = h1.text();
                songName[i]=str1;
            }
            bundle.putStringArray("song_name",songName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(7);
        msg.obj =bundle ;
        handler.sendMessage(msg);
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.list_item) {
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private String inputstream2string(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "utf-8");
        for (; ; ) {
            int rsz = 0;
            try {
                rsz = in.read(buffer, 0, buffer.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }
}