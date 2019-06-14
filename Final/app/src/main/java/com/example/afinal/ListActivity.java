package com.example.afinal;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListActivity extends android.app.ListActivity {
    Handler handler;
    private List<HashMap<String, String>> listItems;//存放文字图片信息
    private SimpleAdapter listItemApdater;//适配器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list);
        initListView();
        this.setListAdapter(listItemApdater);
    }
    protected void initListView() {
        listItems = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("songName", "s:" + i);//标题文字
            map.put("artistName", "a:" + i);//详情描述
            listItems.add(map);
        }
        //生成适配器的Item和动态数组对应元素
        listItemApdater = new SimpleAdapter(this, listItems//listitems数据源
                , R.layout.activity_list//listitems的xml布局实现
                , new String[]{"songName", "artistName"},
                new int[]{R.id.songName, R.id.artistName}
        );
        setListAdapter(listItemApdater);
    }
}
