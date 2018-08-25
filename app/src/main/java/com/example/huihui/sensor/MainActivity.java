package com.example.huihui.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView accelerometerView;
    private TextView orientationView;
    private TextView magneticView;
    private TextView proView;
    private TextView lightView;
    private SensorManager sensorManager;
    private MySensorEventListener sensorEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorEventListener = new MySensorEventListener();
        accelerometerView = (TextView) this.findViewById(R.id.accelerometerView);
        orientationView = (TextView) this.findViewById(R.id.orientationView);
        magneticView = (TextView) this.findViewById(R.id.magneticView);
        proView = (TextView) this.findViewById(R.id.proView);
        lightView = (TextView) this.findViewById(R.id.lightView);
        //获取感应器管理器
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        List<Sensor> list = sensorManager.getSensorList(Sensor.TYPE_ALL);  //获取传感器的集合
        for (Sensor sensor:list){
            //allView.append(sensor.getName() + "\n");  //把传感器种类显示在TextView中
            Log.d("sensor ", sensor.getName());
        }

    }
    @Override
    protected void onResume()
    {
        //获取方向传感器
        Sensor orientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener(sensorEventListener, orientationSensor, SensorManager.SENSOR_DELAY_NORMAL);

        //获取加速度传感器
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);

        //获取距离传感器
        Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(sensorEventListener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);

        //获取磁场传感器
        Sensor magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(sensorEventListener, magneticSensor, SensorManager.SENSOR_DELAY_NORMAL);

        //获取光线传感器
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(sensorEventListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);

        super.onResume();
    }

    private final class MySensorEventListener implements SensorEventListener
    {
        //可以得到传感器实时测量出来的变化值
        @Override
        public void onSensorChanged(SensorEvent event)
        {
            //得到方向的值
            if(event.sensor.getType()==Sensor.TYPE_ORIENTATION)
            {
                float x = event.values[SensorManager.DATA_X];
                float y = event.values[SensorManager.DATA_Y];
                float z = event.values[SensorManager.DATA_Z];
                orientationView.setText("方位传感器: \n" + "x:" + x + ", " +"\n" + "y:" +  y + ","+ "\n" + "y:" +  z);
            }
            //得到加速度的值
            else if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
            {
                float x = event.values[SensorManager.DATA_X];
                float y = event.values[SensorManager.DATA_Y];
                float z = event.values[SensorManager.DATA_Z];
                accelerometerView.setText("三轴加速度计: \n" + "x:" + x + ", " +"\n" + "y:" +  y + ","+ "\n" + "y:" +  z);
            }
            //得到磁场的值
            else if(event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD)
            {
                float x = event.values[SensorManager.DATA_X];
                float y = event.values[SensorManager.DATA_Y];
                float z = event.values[SensorManager.DATA_Z];
                magneticView.setText("三轴磁场: \n" + "x:" + x + ", " +"\n" + "y:" +  y + ","+ "\n" + "y:" +  z);
            }

            else if(event.sensor.getType()==Sensor.TYPE_LIGHT) {
                // 获取光线强度
                float lux = event.values[0];// 光线强度
                lightView.setText("光照强度 : " + lux);
            }
            else if(event.sensor.getType()==Sensor.TYPE_PROXIMITY) {
                // 获取距离
                float pro = event.values[0];
                proView.setText("距离 : " + pro);
            }

        }
        //重写变化
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy)
        {
        }
    }

    //暂停传感器的捕获
    @Override
    protected void onPause()
    {
        sensorManager.unregisterListener(sensorEventListener);
        super.onPause();
    }


}
