package com.example.afinal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
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
import android.widget.Button;
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

public class Final extends AppCompatActivity  {
    private boolean ispause = false;
    private String TAG = "Final";
    Handler handler;
    Handler handler1;
    private SeekBar seekBar;
    SeekBar volumnBar;
    ImageButton playBtn;
    TextView bTime;
    TextView eTime;
    int totalTime;
    private String[] songName2;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

//        mediaPlayer = MediaPlayer.create(this, R.raw.whalien52);
//        mediaPlayer.setLooping(true);
//        mediaPlayer.seekTo(0);
//        mediaPlayer.setVolume(0.5f,0.5f);
//        totalTime=mediaPlayer.getDuration();
//        seekBar =  findViewById(R.id.seekbar);
//        seekBar.setMax(totalTime);
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                if (fromUser){
//                    mediaPlayer.seekTo(progress);
//                    seekBar.setProgress(progress);
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//        volumnBar=findViewById(R.id.volumnBar);
//        volumnBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                float volumnNum=progress/100f;
//                mediaPlayer.setVolume(volumnNum,volumnNum);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//            }
//        });
//        //更新进度条
//        //更新进度条
//        new Thread(new Runnable() {
//            public void run() {
//                while (mediaPlayer!=null){
//                    Message message=new Message();
//                    message.what=mediaPlayer.getCurrentPosition();
//                    handler1.sendMessage(message);
//                }
//            }
//        }).start();
//
//        handler1=new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                int currentPosition=msg.what;
//                seekBar.setProgress(currentPosition);
//                String beTime=createTimeLable(currentPosition);
//                bTime.setText(beTime);
//                String remaintime=createTimeLable(totalTime-currentPosition);
//                eTime.setText(remaintime);
//            }
//        };
//
//        Thread t = new Thread(this);
//        t.start();
//        handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                if (msg.what == 7) {
//                    Bundle bdl=(Bundle)msg.obj;
//                    songName2=bdl.getStringArray("song_name");
//                    for(int i=0;i<songName2.length;i++){
//                        Log.i(TAG, "handleMessage: songName"+songName2[i]);
//                    }
////                    ListAdapter adapter = new ArrayAdapter<String>(Final.this, android.R.layout.simple_list_item_1, list2);
////                    setListAdapter(adapter);
//                }
//                super.handleMessage(msg);
//            }
//        };
//
//    }
//    private String createTimeLable(int time){
//        String timelable="";
//        int min=time/1000/60;
//        int sec=time/1000%60;
//        timelable=min+":";
//        if (sec<10) timelable+=0;
//        timelable+=sec;
//        return timelable;
//
//    }
//    public  void  playBtnClick(View view){
//        if (!mediaPlayer.isPlaying()){
//            mediaPlayer.start();
//            playBtn.setImageDrawable(getResources().getDrawable(R.drawable.play, null));
//        }else{
//            mediaPlayer.pause();
//            playBtn.setImageDrawable(getResources().getDrawable(R.drawable.pause, null));
//        }
//
//    }
//    public void run() {
//        Log.i(TAG, "run: ........");
//            Bundle bundle=new Bundle();
//        try {
//            Document doc = null;
//            doc = Jsoup.connect("https://www.phb123.com/yule/music/32090.html").get();
//            Elements hs = doc.getElementsByTag("h2");
//            String[] songName=new String[hs.size()];
//            for (int i = 0; i < hs.size(); i ++) {
//                Element h1 = hs.get(i);
//                Log.d(TAG, "run: " + h1.text());
//                String str1 = h1.text();
//                songName[i]=str1;
//            }
//            bundle.putStringArray("song_name",songName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Message msg = handler.obtainMessage(7);
//        msg.obj =bundle ;
//        handler.sendMessage(msg);
//    }
//
//
//@Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.list, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.list_item) {
//            Intent intent = new Intent(this, ListActivity.class);
//            startActivity(intent);
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private String inputstream2string(InputStream inputStream) throws IOException {
//        final int bufferSize = 1024;
//        final char[] buffer = new char[bufferSize];
//        final StringBuilder out = new StringBuilder();
//        Reader in = new InputStreamReader(inputStream, "utf-8");
//        for (; ; ) {
//            int rsz = 0;
//            try {
//                rsz = in.read(buffer, 0, buffer.length);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if (rsz < 0)
//                break;
//            out.append(buffer, 0, rsz);
//        }
//        return out.toString();
//    }
    }
    public void FirstOnClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}