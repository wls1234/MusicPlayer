package com.example.afinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapter extends ArrayAdapter {

    public MyAdapter( Context context, int resource) {
        super(context, resource);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View itemview=convertView;
        if (itemview==null){
            itemview= LayoutInflater.from(getContext()).inflate(R.layout.item,parent,false);
        }
        Map<String,String> map= (Map<String, String>) getItem(position);
        TextView songName=itemview.findViewById(R.id.songName);
        TextView artistName=itemview.findViewById(R.id.artistName);
        songName.setText("songName；"+map.get("songName"));
        artistName.setText("artistName；"+map.get("artistName"));
        return itemview;
    }
}
