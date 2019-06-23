package com.example.wls;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wls.inner.BlurUtil;
import com.example.wls.inner.Common;
import com.example.wls.inner.MergeImage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListenActivity extends AppCompatActivity implements View.OnClickListener {
    private int i = 0;
    private int playMode = 0;
    private int buttonWitch = 0;
    private ImageView bgImgv;
    private TextView titleTv;
    private TextView artistTv;
    private TextView currrentTv;
    private TextView totalTv;
    private ImageView prevImgv;
    private ImageView nextImgv;
    private int position;
    private ImageView discImagv;
    private ImageView needleImagv;
    private MediaPlayer mediaPlayer;
    private ImageView pauseImgv;
    private ImageView backImg;
    private ImageView styleImg;
    private SeekBar seekBar;
    private int totaltime;
    private boolean isStop;
    private ObjectAnimator objectAnimator = null;
    private RotateAnimation rotateAnimation = null;
    private RotateAnimation rotateAnimation2 = null;
    private String TAG = "ListenActivity";

    //Handler实现向主线程进行传值
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 将SeekBar位置设置到当前播放位置
            seekBar.setProgress((msg.what));
            //获得音乐的当前播放时间
            currrentTv.setText(formatTime(msg.what));
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen);
        //绑定ID
        bingID();
        Intent intent = getIntent();                                                    //通过getIntent()方法实现intent信息的获取
        position = intent.getIntExtra("position", 0);            //获取position

        mediaPlayer = new MediaPlayer();
        changeMusic(Common.musicList.get(position).path);
        //监听进度条
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //seekto方法的参数是毫秒，而不是秒
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    //绑定id，设置监听
    private void bingID() {
        titleTv = findViewById(R.id.music_title_tv);
        artistTv = findViewById(R.id.music_artist_tv);
        bgImgv = findViewById(R.id.music_bg_imgv);
        currrentTv =  findViewById(R.id.music_current_tv);
        totalTv = findViewById(R.id.music_total_tv);
        prevImgv = findViewById(R.id.music_prev_imgv);
        nextImgv =  findViewById(R.id.music_next_imgv);
        discImagv =  findViewById(R.id.music_disc_imagv);
        needleImagv = findViewById(R.id.music_needle_imag);
        pauseImgv =  findViewById(R.id.music_pause_imgv);
        backImg =  findViewById(R.id.listen_back);
        seekBar =  findViewById(R.id.music_seekbar);
        styleImg =  findViewById(R.id.music_play_btn_loop_img);
        pauseImgv.setOnClickListener(this);
        prevImgv.setOnClickListener(this);
        nextImgv.setOnClickListener(this);
        backImg.setOnClickListener(this);
        styleImg.setOnClickListener(this);

    }

    //changeMusic() 实现页面的展现
    private void changeMusic(String path) {
        isStop = false;
        mediaPlayer.reset();
//        Common获取本地音乐信息
        titleTv.setText(Common.musicList.get(position).title);
        artistTv.setText(Common.musicList.get(position).artist + "--" + Common.musicList.get(position).album);
        pauseImgv.setImageResource(R.mipmap.listen_btn_pause);

        if (Common.musicList.get(position).albumBip != null) {
            Bitmap bgbm = BlurUtil.doBlur(Common.musicList.get(position).albumBip, 10, 5);//将专辑封面虚化
            bgImgv.setImageBitmap(bgbm);                                    //设置虚化后的专辑图片为背景
            // BitmapFactory.decodeResource用于根据给定的资源ID从指定的资源文件中解析、创建Bitmap对象。
            Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.liste_disc);
            Bitmap bm = MergeImage.mergeThumbnailBitmap(bitmap1, Common.musicList.get(position).albumBip);//将专辑图片放到圆盘中
            discImagv.setImageBitmap(bm);
        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.listen_bg);
            bgImgv.setImageBitmap(bitmap);
            Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.liste_disc);
            Bitmap bm = MergeImage.mergeThumbnailBitmap(bitmap1, bitmap);
            discImagv.setImageBitmap(bm);
        }
        //重置，当切换音乐时不会放前一首歌的歌曲
        mediaPlayer.reset();
        //播放
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();                   // 准备
            mediaPlayer.start();                        // 启动
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if(!mediaPlayer.isPlaying()){
                        setPlayMode();
                    }

                }
            });
        } catch (IllegalArgumentException | SecurityException | IllegalStateException
                | IOException e) {
            e.printStackTrace();
        }

        totalTv.setText(formatTime(Common.musicList.get(position).length));
        // 设置seekbar的最大值
        seekBar.setMax(mediaPlayer.getDuration());
        MusicThread musicThread = new MusicThread();                                         //启动线程
        new Thread(musicThread).start();

//使唱片盘转动动画操作
        //实例化，设置旋转对象
        objectAnimator = ObjectAnimator.ofFloat(discImagv, "rotation", 0f, 360f);
        //设置转一圈要多长时间
        objectAnimator.setDuration(8000);
        //设置旋转速率
        objectAnimator.setInterpolator(new LinearInterpolator());
        //设置循环次数 -1为一直循环
        objectAnimator.setRepeatCount(-1);
        //设置转一圈后怎么转
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.start();

        rotateAnimation = new RotateAnimation(-25f, 0f, Animation.RELATIVE_TO_SELF, 0.3f, Animation.RELATIVE_TO_SELF, 0.1f);
        rotateAnimation.setDuration(500);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(0);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setStartOffset(500);
        needleImagv.setAnimation(rotateAnimation);
        rotateAnimation.cancel();


        rotateAnimation2 = new RotateAnimation(0f, -25f, Animation.RELATIVE_TO_SELF, 0.3f, Animation.RELATIVE_TO_SELF, 0.1f);
        rotateAnimation2.setDuration(500);
        rotateAnimation2.setInterpolator(new LinearInterpolator());
        rotateAnimation2.setRepeatCount(0);
        rotateAnimation2.setFillAfter(true);
        needleImagv.setAnimation(rotateAnimation2);
        rotateAnimation2.cancel();
    }
    //格式化歌曲总时长，将得到的音乐时间毫秒转换为时分秒格式
    private String formatTime(int length) {
        Date date = new Date(length);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");    //规定固定的格式
        String totaltime = simpleDateFormat.format(date);
        return totaltime;
    }
//播放器模式切换功能
    private void setPlayMode() {
        if (playMode == 0)//顺序播放
        {
            if (position == Common.musicList.size() - 1)//默认循环播放
            {
                position = 0;// 第一首
                mediaPlayer.reset();
                objectAnimator.pause();
                needleImagv.startAnimation(rotateAnimation2);
                changeMusic(Common.musicList.get(position).path);

            } else {
                position++;
                mediaPlayer.reset();
                objectAnimator.pause();
                needleImagv.startAnimation(rotateAnimation2);
                changeMusic(Common.musicList.get(position).path);
            }
        } else if (playMode == 1)//单曲循环
        {
            //position不需要更改
            mediaPlayer.reset();
            objectAnimator.pause();
            needleImagv.startAnimation(rotateAnimation2);
            changeMusic(Common.musicList.get(position).path);
        } else if (playMode == 2)//随机
        {
            position = (int) (Math.random() * Common.musicList.size());//随机播放
            mediaPlayer.reset();
            objectAnimator.pause();
            needleImagv.startAnimation(rotateAnimation2);
            changeMusic(Common.musicList.get(position).path);
        }
    }


    private void setBtnMode() {
        if (playMode == 0)//全部循环
        {
            if (position == Common.musicList.size() - 1)//默认循环播放
            {
                if (buttonWitch == 1) {
                    position--;
                    mediaPlayer.reset();
                    objectAnimator.pause();
                    needleImagv.startAnimation(rotateAnimation2);
                    changeMusic(Common.musicList.get(position).path);
                } else if (buttonWitch == 2) {
                    position = 0;// 第一首
                    mediaPlayer.reset();
                    objectAnimator.pause();
                    needleImagv.startAnimation(rotateAnimation2);
                    changeMusic(Common.musicList.get(position).path);
                }
            } else if (position == 0) {
                if (buttonWitch == 1) {
                    position = Common.musicList.size() - 1;
                    mediaPlayer.reset();
                    objectAnimator.pause();
                    needleImagv.startAnimation(rotateAnimation2);
                    changeMusic(Common.musicList.get(position).path);
                } else if (buttonWitch == 2) {
                    position++;
                    mediaPlayer.reset();
                    objectAnimator.pause();
                    needleImagv.startAnimation(rotateAnimation2);
                    changeMusic(Common.musicList.get(position).path);
                }
            }else {
                if(buttonWitch ==1){
                    position--;
                    mediaPlayer.reset();
                    objectAnimator.pause();
                    needleImagv.startAnimation(rotateAnimation2);
                    changeMusic(Common.musicList.get(position).path);

                }else if(buttonWitch ==2){
                    position++;
                    mediaPlayer.reset();
                    objectAnimator.pause();
                    needleImagv.startAnimation(rotateAnimation2);
                    changeMusic(Common.musicList.get(position).path);
                }
            }
        } else if (playMode == 1)//单曲循环
        {
            //position不需要更改
            mediaPlayer.reset();
            objectAnimator.pause();
            needleImagv.startAnimation(rotateAnimation2);
            changeMusic(Common.musicList.get(position).path);
        } else if (playMode == 2)//随机
        {
            position = (int) (Math.random() * Common.musicList.size());//随机播放
            mediaPlayer.reset();
            objectAnimator.pause();
            needleImagv.startAnimation(rotateAnimation2);
            changeMusic(Common.musicList.get(position).path);
        }
    }



    //onClick（）点击监听
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.music_prev_imgv:
                buttonWitch = 1;
                setBtnMode();
                break;
            case R.id.music_next_imgv:
                buttonWitch = 2;
                setBtnMode();
                break;
            case R.id.music_pause_imgv:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    objectAnimator.pause();
                    needleImagv.startAnimation(rotateAnimation2);
                    pauseImgv.setImageResource(R.mipmap.listen_btn_play);
                } else {
                    mediaPlayer.start();
                    objectAnimator.resume();
                    needleImagv.startAnimation(rotateAnimation);
                    pauseImgv.setImageResource(R.mipmap.listen_btn_pause);
                }
                break;
            case R.id.music_play_btn_loop_img:
                i++;
                if (i % 3 == 1) {
                    Toast.makeText(ListenActivity.this, "单曲循环", Toast.LENGTH_SHORT).show();
                    playMode = 1;
                    styleImg.setImageResource(R.mipmap.ic_play_btn_one);
                }
                if (i % 3 == 2) {
                    Toast.makeText(ListenActivity.this, "随机播放", Toast.LENGTH_SHORT).show();
                    playMode = 2;
                    styleImg.setImageResource(R.mipmap.ic_play_btn_shuffle);
                }
                if (i % 3 == 0) {
                    Toast.makeText(ListenActivity.this, "顺序播放", Toast.LENGTH_SHORT).show();
                    playMode = 0;
                    styleImg.setImageResource(R.mipmap.ic_play_btn_loop);
                }
                break;
            case R.id.listen_back:
                this.finish();
                break;
            default:
                break;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Common.musicList.get(position).isPlaying = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        i = 0;
        isStop = false;
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    //创建一个类MusicThread实现Runnable接口，实现多线程
    class MusicThread implements Runnable {
        @Override
        public void run() {
            //判断音乐的状态，在不停止与不暂停的情况下向主线程发出信息
            while (!isStop && Common.musicList.get(position) != null) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(mediaPlayer.getCurrentPosition());
            }
        }
    }


}

