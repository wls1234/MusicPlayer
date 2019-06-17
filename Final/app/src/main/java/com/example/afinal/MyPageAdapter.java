package com.example.afinal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPageAdapter extends FragmentPagerAdapter {
    private  String[] title=new String[]{"歌曲列表","正在播放","专辑列表"};
    public MyPageAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new FirstFragment();
        }else if(position==1){
            return new SecondFragment();
        }else{
            return new ThirdFragment();
        }
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
    @Override
    public int getCount() {
        return 3;
    }
}
