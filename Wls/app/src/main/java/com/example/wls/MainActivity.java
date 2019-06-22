package com.example.wls;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    //定义activity_main.xml的控件对象
    private TextView frag1;
    private TextView frag2;
    private ViewPager viewPager;
    //将Fragment放入List集合中，存放fragment对象
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        frag1 = findViewById(R.id.frag1);
        frag2 = findViewById(R.id.frag2);
        viewPager = findViewById(R.id.main_vp);
        //设置监听
        frag1.setOnClickListener(this);
        frag2.setOnClickListener(this);
        //创建fragment对象
        MyMusicFragment myMusicFragment= new MyMusicFragment();
        RecFragment recFragment = new RecFragment();
        //将fragment对象添加到fragmentList中
        fragmentList.add(myMusicFragment);
        fragmentList.add(recFragment);
        //通过MusicPagerAdapter类创建musicPagerAdapter的适配器，MusicPagerAdapter类的创建方法
        MusicPagerAdapter musicPagerAdapter = new MusicPagerAdapter(getSupportFragmentManager(), fragmentList);
        //viewPager绑定适配器
        viewPager.setAdapter(musicPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("11", "onPageSelected: "+position);
                switch (position) {
                    case 0:
                        frag1.setTextColor(getResources().getColor(R.color.white));
                        frag2.setTextColor(getResources().getColor(R.color.white_60P));
                        break;
                    case 1:
                        frag1.setTextColor(getResources().getColor(R.color.white_60P));
                        frag2.setTextColor(getResources().getColor(R.color.white));
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag1:
                //实现点击TextView切换fragment
                viewPager.setCurrentItem(0);
                break;
            case R.id.frag2:
                viewPager.setCurrentItem(1);
                break;
            default:
                break;
        }

    }
//    退出提示
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            final View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("退出提示");
            builder.setView(view);
            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }

            });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
