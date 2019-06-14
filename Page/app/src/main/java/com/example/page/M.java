package com.example.page;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class M extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private boolean ispause=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_m);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    // 打开菜单项
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.menu){
            Intent list = new Intent(this, MusicList.class);
            startActivity(list);
            //测试数据库
//            RateItem item1=new RateItem("aaaaa","1111");
//            RateManager manager=new RateManager(this);
//            manager.add(item1);
//            manager.add(new RateItem("BBBB","1233"));
//            Log.i(TAG, "onOptionsItemSelected: 写入数据完毕");
//            //查询所有数据
//            List<RateItem> testList=manager.listAll();
//            for (RateItem i:testList){
//                Log.i(TAG, "onOptionsItemSelected: 取出数据[id="+i.getId()+"]Name[="+i.getCurName()+"]Rate[="+i.getCurRate()+"]");
//            }
        }
        return super.onOptionsItemSelected(item);
    }
    public void onClick(View v){

        int id=v.getId();                              //单击按钮的id

        switch(id){

            case R.id.play: {

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
                }else {

                    mediaPlayer.reset();

                    play();mediaPlayer.start();

                    handler.post(run);

                    play.setImageResource(R.drawable.play);       //音乐播放

                }

                break;

            }

            case R.id.right:{

                if (mediaPlayer!=null&&mediaPlayer.isPlaying()){

                    mediaPlayer.stop();

                    seekBar.setProgress(0);}

                if (i == n)

                { i=1;

                    play();

                    mediaPlayer.start();

                    handler.post(run);

                    songname.setText(musicName[i-1]);}

                else if (i =< n){

                    i++;

                    play();

                    mediaPlayer.start();

                    handler.post(run);

                    songname.setText(musicName[i-1]);

                }

                break;

            }                                              //下一首音乐

            case R.id.left:{

                if (mediaPlayer!=null&&mediaPlayer.isPlaying()){

                    mediaPlayer.stop();

                    seekBar.setProgress(0);}

                if (i == 1) {

                    Toast.makeText(MainActivity.this, "没有上一首了", Toast.LENGTH_LONG).show();

                    play();mediaPlayer.start();handler.post(run);

                }

                else if (i > 1){

                    i--;

                    play();

                    mediaPlayer.start();

                    handler.post(run);

                    songname.setText(musicName[i-1]);

                }

                break;

            }                                                 //上一首音乐

            case R.id.stop:{

                int j=i;

                mediaPlayer.stop();

                seekBar.setProgress(0);

                i=j;

                play.setImageResource(R.drawable.pause);

                break;

            }                                                  //音乐停止

        }

    }

//    实现音乐播放结束后自动播放下一首音乐

    private Runnable thread = new Runnable()

    {

        @Override

        public void run()

        {

            playNext(flag);

            handler.postDelayed(thread, 1000);

        }

    }

    private void playNext(boolean flag){

        if(flag){ if (startTime.getText().equals(countTime.getText()))

        {

            if(i<n){

                i++;

                play();

                player.start();

                handler.post(run);

                songname.setText(musicName[i-1]);

            }

            else if(i==n){

                i=1;

                play();

                player.start();

                handler.post(run);

                songname.setText(musicName[i-1]);

            }

        }}

    }

//    实线音乐进度条：

    @Override

    public void onStopTrackingTouch(SeekBar seekBar) {}

    @Override

    public void onStartTrackingTouch(SeekBar seekBar){}

    @Override

    public void onProgressChanged(SeekBar seekBar,int progress,boolean fromUser){

        if(fromUser){

            player.seekTo(progress);

        }

        Handler handler=new Handler();

        Runnable run=new Runnable(){

            @Override

            public void run() {

                seekBar.setProgress(player.getCurrentPosition());

                setTime();

                handler.postDelayed(run, 200);

            }

        }

        public void setTime(){

            int now=player.getCurrentPosition();

            int count=player.getDuration();

            int second=now/1000;

            int csecond=count/1000;

            startTime.setText(second/60+":"+second%60);

            countTime.setText(csecond/60+":"+csecond%60);

        }

        @Override

        public boolean onKeyDown(int keyCode, KeyEvent event){

            if(keyCode==KeyEvent.KEYCODE_BACK){

                player.stop();

                player.release();

                handler.removeCallbacks(run);

                finish();}

            return super.onKeyDown(keyCode,event);

        }

//        实现音量调节功能如下：

        vbar = (SeekBar) findViewById(R.id.vbar);

        final AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        vbar.setMax(am.getStreamMaxVolume(AudioManager.STREAM_MUSIC));

        vbar.setProgress(am.getStreamVolume(AudioManager.STREAM_MUSIC));

        vbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()

        {

            @Override

            public void onStopTrackingTouch(SeekBar seekBar)  {}

            @Override

            public void onStartTrackingTouch(SeekBar seekBar){}

            @Override

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){

                am.setStreamVolume(AudioManager.STREAM_MUSIC, vbar.getProgress(), 0);

            }

        });

//        实现接收到音乐列表传值并播放音乐功能如下：

        protected void onActivityResult(int requestCode,int resultCode,Intent data){

            if(resultCode==100){

                Bundle bundle = data.getExtras();

                String name= bundle.getString("name");

                i=0;

                for(int j=0;j<n;j++){

                    if(name.equals(musicName[i])==false)

                    {i++;}

                }

                i++;

                flag=true;

                songname.setText(musicName[i-1]);

                player.stop();

                seekBar.setProgress(0);

                play();

                player.start();

                handler.post(run);

            }

        }

}
