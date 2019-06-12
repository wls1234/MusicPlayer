package com.example.yaoyiyao;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.aware.DiscoverySession;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Vibrator vibrator;
    private static String strs[]={"石头","剪刀","布"};
    private static int pics[]={R.mipmap.shitou,R.mipmap.jian,R.mipmap.bu};
    private TextView text;
    private ImageView img;
    private static final String TAG="MainActivity ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text=findViewById(R.id.text);
        img=findViewById(R.id.img);

        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator= (Vibrator) getSystemService(VIBRATOR_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager!=null){
//            注册监听器
            sensorManager.registerListener(sensorEventListener,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    //    重力感应监听
    private SensorEventListener sensorEventListener=new SensorEventListener() {
    @Override
    public void onSensorChanged(SensorEvent event) {
//        传感器信息变化时执行该方法
        float[] values=event.values;
        //获取x轴，y，z的重力加速度
        float x=values[0];
        float y=values[1];
        float z=values[2];
        Log.i(TAG, "onSensorChanged: x="+x);
        Log.i(TAG, "onSensorChanged: y="+y);
        Log.i(TAG, "onSensorChanged: z="+z);

        //重力加速度达到某值就达到了摇晃手机的状态
        int medumValue=10;
        if (Math.abs(x)>medumValue || Math.abs(y)>medumValue || Math.abs(z)>medumValue){
            vibrator.vibrate(200);
            Message msg=new Message();
            msg.what=10;
            handler.sendMessage(msg);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
};
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 10:
                    Log.i(TAG, "检测到摇晃执行操作");
                    Random r=new Random();
                    int num=Math.abs(r.nextInt())%3;
                    text.setText(strs[num]);
                    img.setImageResource(pics[num]);
                    break;
            }
        }
    };
}
