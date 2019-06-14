package com.example.afinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapter extends ArrayAdapter {

    public MyAdapter(Context context, int resource, ArrayList<HashMap<String,String>> list){
        super(context, resource,list);

    }
    public View getView(int position, View convertView, ViewGroup parent){
        View itemview=convertView;
        if (itemview==null){
            itemview= LayoutInflater.from(getContext()).inflate(R.layout.activity_list,parent,false);
        }
    }
}
