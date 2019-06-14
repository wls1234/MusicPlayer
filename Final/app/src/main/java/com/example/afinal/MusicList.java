package com.example.afinal;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public  class MusicList extends AppCompatActivity {
//    Handler handler;
//    private List<HashMap<String, String>> listItems;//存放文字图片信息
//    private SimpleAdapter listItemApdater;//适配器
//    String TAG="MusicList";
//    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        ListView listView=findViewById(R.id.mylist);
        String data[]={"11","22"};
        ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);
    }

//    protected void initListView() {
//        listItems = new ArrayList<HashMap<String, String>>();
//        for (int i = 0; i < 10; i++) {
//            HashMap<String, String> map = new HashMap<String, String>();
//            map.put("songTitle", "=" + i);//标题文字
//            map.put("artistTitle", "="+ i);//详情描述
//            listItems.add(map);
//        }
//        //生成适配器的Item和动态数组对应元素
//        listItemApdater = new SimpleAdapter(this, listItems//listitems数据源
//                , R.layout.activity_music_list//listitems的xml布局实现
//                , new String[]{"songTitle", "artistTitle"},
//                new int[]{R.id.songTitle, R.id.artistTitle}
//        );
//        setListAdapter(listItemApdater);
//    }
//    public void run() {
//        //获取网络数据，放入list带回到主线程中
//        List<HashMap<String, String>> retlist = new ArrayList<HashMap<String, String>>();
//        Document doc = null;
//        try {
//            doc = Jsoup.connect("http://www.usd-cny.com/").get();
//            Log.d(TAG, "run: " + doc.title());
//            Elements tables = doc.getElementsByTag("table");
//            /*for(Element table:tables){
//                Log.d(TAG, "run: table["+i+"]="+table);
//                i++;
//            }*/
//            Element table1 = tables.get(0);
//            //Log.d(TAG, "run: table6="+table6);
//            //获取td中的数据
//            Elements tds = table1.getElementsByTag("td");
//            for (int i = 0; i < tds.size(); i += 5) {
//                //获取第一列数据
//                Element td1 = tds.get(i);
//                //获取第1+5列数据
//                Element td2 = tds.get(i + 3);
//                Log.d(TAG, "run: " + td1.text() + "==>" + td2.text());
//                String str1 = td1.text();
//                String val = td2.text();
//                HashMap<String, String> map = new HashMap<String, String>();
//                map.put("songTitle", str1);
//                map.put("artistTitle", val);
//                retlist.add(map);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Message msg = handler.obtainMessage(7);
//        msg.obj = retlist;
//        handler.sendMessage(msg);
//    }

//    @Override
//    protected void onListItemClick(ListView l, View v, int position, long id) {
//        Log.d(TAG, "onItemClick: position"+position);
//        super.onListItemClick(l, v, position, id);
//        HashMap<String,String> map=(HashMap<String,String>) getListView().getItemAtPosition(position);
//        String songStr=map.get("songTitle");
//        String artistStr=map.get("artistTitle");
//        Log.d(TAG, "onItemClick: songStr:"+songStr);
//        TextView song=findViewById(R.id.song);
//        TextView artist=findViewById(R.id.artist);
//        //打开新的页面，传入参数
//        Intent intent =new Intent(this,Final.class);
//        intent.putExtra("songTitle",songStr);
//        intent.putExtra("artistTitle",artistStr);
//        startActivity(intent);
//    }
}
