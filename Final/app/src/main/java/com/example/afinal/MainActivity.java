package com.example.afinal;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.afinal.MyPageAdapter;
import com.example.afinal.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager=findViewById(R.id.viewpager);
        MyPageAdapter pageAdapter=new MyPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);
        //在onCreate()方法中添加控件访问，并绑定TabLayout对象和ViewPager对象
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }
}
