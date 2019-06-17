package com.example.afinal;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment implements  Runnable{

    String TAG = "Listen";
    MediaPlayer mediaPlayer;
    private boolean ispause = false;
    int p;
    private SeekBar seekBar;
    SeekBar volumnBar;
    TextView bTime;
    TextView eTime;
    int totalTime;
    Handler handler;

    ImageButton btn_play;
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
            if (p == 0) {
                mediaPlayer = MediaPlayer.create(getActivity(), R.raw.song1);
            } else if (p == 1) {
                mediaPlayer = MediaPlayer.create(getActivity(), R.raw.song2);
            } else if (p == 2) {
                mediaPlayer = MediaPlayer.create(getActivity(), R.raw.song3);
            }else {
                mediaPlayer = MediaPlayer.create(getActivity(), R.raw.whalien52);
            }
            mediaPlayer.setLooping(true);
            mediaPlayer.seekTo(0);
            mediaPlayer.setVolume(0.5f, 0.5f);
            bTime=getView().findViewById(R.id.begin_Time);
            eTime=getView().findViewById(R.id.end_Time);
            totalTime = mediaPlayer.getDuration();
            seekBar = getView().findViewById(R.id.seekbar);
            seekBar.setMax(totalTime);

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        mediaPlayer.seekTo(progress);
                        seekBar.setProgress(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
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
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });

            new Thread(new Runnable() {
            public void run() {
                while (mediaPlayer!=null){
                    Message message=new Message();
                    message.what=mediaPlayer.getCurrentPosition();
                    handler.sendMessage(message);
                }
            }
        }).start();

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int currentPosition=msg.what;
                seekBar.setProgress(currentPosition);
                String beTime=createTimeLable(currentPosition);
                bTime.setText(beTime);
                String remaintime=createTimeLable(totalTime-currentPosition);
                eTime.setText(remaintime);
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
            btn_play= getActivity().findViewById(R.id.playBtn);
            btn_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
//                    startActivity(new Intent(getActivity(), Listen.class));

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
            });
        }

    private String createTimeLable ( int time){
        String timelable = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;
        timelable = min + ":";
        if (sec < 10) timelable += 0;
        timelable += sec;
        return timelable;
    }

    public void onClick(View v) {
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
//    public  void  run(){
//        while (mediaPlayer!=null){
//                    Message msg=new Message();
//                    msg.what=mediaPlayer.getCurrentPosition();
//                    handler.sendMessage(msg);
//                }
//            }
public void run() {
    while (mediaPlayer!=null){
        Message message=new Message();
        message.what=mediaPlayer.getCurrentPosition();
        handler.sendMessage(message);
    }
}
}



