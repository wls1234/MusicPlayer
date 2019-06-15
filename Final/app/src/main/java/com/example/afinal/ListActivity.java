package com.example.afinal;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ListActivity extends android.app.ListActivity implements  Runnable, AdapterView.OnItemClickListener {
    Handler handler;
    private List<HashMap<String, String>> listItems;//存放文字图片信息
    private SimpleAdapter listItemApdater;//适配器
    String TAG = "ListActivity";
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list);
        initListView();
        this.setListAdapter(listItemApdater);
        Thread t = new Thread(this);
        t.start();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 7) {
                    List<HashMap<String,String>> list2 = (List<HashMap<String,String>>) msg.obj;
                    listItemApdater = new SimpleAdapter(ListActivity.this,list2//listitems数据源
                            , R.layout.activity_list//listitems的xml布局实现
                            , new String[]{"songName", "artistName"},
                            new int[]{R.id.songName, R.id.artistName}
                    );
                    setListAdapter(listItemApdater);
                }
                super.handleMessage(msg);
            }
        };
        getListView().setOnItemClickListener(this);
    }

    protected void initListView() {
        listItems = new ArrayList<HashMap<String, String>>();
        List<String> list1 = new ArrayList<String>();
        for (int i = 1; i < 10; i++) {
            list1.add("item" + i);
        }
        //第一步，构造adapter 对象
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list1);
        setListAdapter(adapter);
        //生成适配器的Item和动态数组对应元素
        listItemApdater = new SimpleAdapter(this, listItems//listitems数据源
                , R.layout.activity_list//listitems的xml布局实现
                , new String[]{"songName", "artistName"},
                new int[]{R.id.songName, R.id.artistName}
        );
        setListAdapter(listItemApdater);
    }
    public void run() {
        Log.i(TAG, "run: ........");
        List<HashMap<String,String>> retlist = new ArrayList<HashMap<String, String>>();
        try {
            Document doc = null;
            doc = Jsoup.connect("https://www.phb123.com/yule/music/32090.html").get();
            Elements hs = doc.getElementsByTag("h2");
            String[] songName=new String[hs.size()];
            String[] artistName=new String[hs.size()];
            for (int i = 0; i < hs.size(); i ++) {
                Element h1 = hs.get(i);
                Log.d(TAG, "run: " + h1.text());
                String str1 = h1.text();
                String str2="artist["+i+"]";
//                songName[i]=str1;
//                artistName[i]=str2;
                HashMap<String,String> map=new HashMap<String, String>();
                map.put("songName",str1);
                map.put("artistName",str2);
                retlist.add(map);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(7);
        msg.obj =retlist ;
        handler.sendMessage(msg);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       HashMap<String,String> map= (HashMap<String, String>) getListView().getItemAtPosition(position);
        Log.i(TAG, "onItemClick: position"+position);
       String song=map.get("songName");
       String art=map.get("artistName");
        Log.i(TAG, "onItemClick: song"+song);
        Log.i(TAG, "onItemClick: art"+art);
//        TextView song_n=findViewById(R.id.song);
//        TextView art_n=findViewById(R.id.artist);
//        song_n.setText(song);
//        art_n.setText(art);
//打开新的页面，传入参数
        Intent intent =new Intent(this,Listen.class);
        intent.putExtra("sn",song);
        intent.putExtra("an",art);
        intent.putExtra("position",position);
        startActivity(intent);
    }
}
