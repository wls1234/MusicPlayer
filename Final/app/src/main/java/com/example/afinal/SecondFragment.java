package com.example.afinal;


import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {
    String TAG = "Listen";
    MediaPlayer mediaPlayer;
    private boolean ispause = false;
    int p;
    private SeekBar seekBar;
    SeekBar volumnBar;
    TextView bTime;
    TextView eTime;
    int totalTime;
    Handler mhandler;
    ImageButton btn_play;
    boolean isplayed=false;
    boolean isrelease=false;
    private int total=-1;
    public SecondFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        String s = getActivity().getIntent().getStringExtra("sn");
        String a = getActivity().getIntent().getStringExtra("an");
        p = getActivity().getIntent().getIntExtra("position", 0);
        Log.i(TAG, "onCreate: sn=" + s);
        ((TextView) getView().findViewById(R.id.song1)).setText(s);
        ((TextView) getView().findViewById(R.id.artist1)).setText(a);
//        if (p == 0) {
//            mediaPlayer = MediaPlayer.create(getActivity(), R.raw.song1);
//        } else if (p == 1) {
//            mediaPlayer = MediaPlayer.create(getActivity(), R.raw.song2);
//        } else if (p == 2) {
//            mediaPlayer = MediaPlayer.create(getActivity(), R.raw.song3);
//        }else {
//            mediaPlayer = MediaPlayer.create(getActivity(), R.raw.whalien52);
//        }
//        mediaPlayer.setLooping(true);
//        mediaPlayer.seekTo(0);
//        mediaPlayer.setVolume(0.5f, 0.5f);
        bTime = getView().findViewById(R.id.begin_Time);
        eTime = getView().findViewById(R.id.end_Time);
        totalTime = mediaPlayer.getDuration();
        seekBar = getView().findViewById(R.id.seekbar);
        seekBar.setMax(totalTime);
        volumnBar = getView().findViewById(R.id.volumnBar);
        volumnBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volumnNum = progress / 100f;
                mediaPlayer.setVolume(volumnNum, volumnNum);
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar bar) {

            }
        });
         mhandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (!isrelease) {
                    if (mediaPlayer.isPlaying()) {
                        int curr = mediaPlayer.getCurrentPosition();
                        int progresss = (int) (float) (curr * 100 * 1.0) / total;
                        seekBar.setProgress(progresss);
                        sendEmptyMessageDelayed(1, 1000);
                    }
                }
            }
        };
//        Thread t=  new Thread();
//          t.start();
//
//          handler=new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                int currentPosition=msg.what;
//                seekBar.setProgress(currentPosition);
//                String beTime=createTimeLable(currentPosition);
//                bTime.setText(beTime);
//                String remaintime=createTimeLable(totalTime-currentPosition);
//                eTime.setText(remaintime);
//                super.handleMessage(msg);
//            }
//        };
//        btn_play= getActivity().findViewById(R.id.playBtn);
//        btn_play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
////                    startActivity(new Intent(getActivity(), Listen.class));
//
//                if (mediaPlayer.isPlaying() && !ispause) {
//                    mediaPlayer.pause();
//                    ispause = true;
//                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.pause, null));
//                } else {
//                    mediaPlayer.start();//开始播放音频
//                    ispause = false;
//                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.play, null));
//                }
//
//            }
//        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar bar) {
                int progress = bar.getProgress();
                int curr = (int) ((progress * total * 1.0) / 100.0);
                mediaPlayer.seekTo(curr);
            }
        });
    }
        public void playOrPause (View view){
            if (isplayed) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    ((ImageButton) view).setImageDrawable(getResources().getDrawable(R.drawable.pause, null));
                } else {
                    mediaPlayer.start();
                    mhandler.sendEmptyMessage(1);
                    ((ImageButton) view).setImageDrawable(getResources().getDrawable(R.drawable.play, null));
                }
            } else {
                mediaPlayer.reset();
                try {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(), R.raw.whalien52);
                    mediaPlayer.prepare();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            Log.i(TAG, "onPrepared: 准备完成");
                            mp.start();
                            total = mp.getDuration();

                            isplayed = true;
                            mhandler.sendEmptyMessage(1);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        try {
                            mediaPlayer.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        mediaPlayer.release();
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.reset();
                        return false;
                    }
                });
                isplayed = true;
            }
//        //实例化MediaPlayer
//        if(mediaPlayer==null){
//            //mediaPlayer = MediaPlayer.create(this, R.raw.s8);
//            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//                String sdPath=Environment.getExternalStorageDirectory().getAbsolutePath();
//                mediaPlayer=new MediaPlayer();
//
//                //设置音频流的类型
//                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//
//                //设置音源
//                Log.i("hhhhh", "playOrPause: 111111");
//                mediaPlayer = MediaPlayer.create(getActivity(), R.raw.alltoowell);
//                Log.i("jjj", "playOrPause: test");
////                    mediaPlayer.setDataSource(this, Uri.parse("http://192.168.43.77:7788/s11.mp3"));
//
////                mediaPlayer.prepareAsync();
//                //设置准备监听
//                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                    @Override
//                    public void onPrepared(MediaPlayer mp) {
//                        mediaPlayer.start();
//                        //暂停图标
//                        imageButton.setImageResource(android.R.drawable.ic_media_pause);
//
//                        //获取音乐的播放时间
//                        int time=mediaPlayer.getDuration();
//
//                        //设置进度条的最大值 为  音乐的播放时间
//                        seekBar.setMax(time);
//
//                        new MyThread().start();
//                    }
//                });
//            }
//
//        }else if(mediaPlayer.isPlaying()){
//            mediaPlayer.pause();
//            //播放图标
//            imageButton.setImageResource(android.R.drawable.ic_media_play);
//        }else{
//            mediaPlayer.start();
//            //暂停图标
//            imageButton.setImageResource(android.R.drawable.ic_media_pause);
//        }
//    }
        }
        public void onDestroy () {
            super.onDestroy();
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }

//    private String createTimeLable ( int time){
//        String timelable = "";
//        int min = time / 1000 / 60;
//        int sec = time / 1000 % 60;
//        timelable = min + ":";
//        if (sec < 10) timelable += 0;
//        timelable += sec;
//        return timelable;
//    }


//    public void onClick(View v) {
//        if (mediaPlayer.isPlaying() && !ispause) {
//            mediaPlayer.pause();
//            ispause = true;
//            ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.pause, null));
//        } else {
//            mediaPlayer.start();//开始播放音频
//            ispause = false;
//            ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.play, null));
//        }
//    }
        //    public  void  run(){
//        while (mediaPlayer!=null){
//                    Message msg=new Message();
//                    msg.what=mediaPlayer.getCurrentPosition();
//                    handler.sendMessage(msg);
//                }
//            }


//    private class MyThread extends  Thread{
//        @Override
//        public void run() {
//            super.run();
//            while(seekBar.getProgress()<seekBar.getMax()){
//                //获取音乐当前的播放位置
//                int currentPosition=mediaPlayer.getCurrentPosition();
//                seekBar.setProgress(currentPosition);
//            }
//        }
//    }

}
