package com.patmyron.blackbox;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class Puzzle1Activity extends AppCompatActivity implements SensorEventListener {

    SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] < -9.7) {
            animation(0);
        } else if (event.values[0] > 9.7) {
            animation(1);
        }
        if (event.values[1] < -9.7) {
            animation(2);
        } else if (event.values[1] > 9.7) {
            animation(3);
        }
        if (event.values[2] < -9.7) {
            animation(4);
        } else if (event.values[2] > 9.7) {
            animation(5);
        }
    }

    private void animation(int index) {
        ImageView iv = (ImageView) ((RelativeLayout) findViewById(R.id.ll)).getChildAt(index);
        iv.setBackgroundResource(R.drawable.animation);
        ((AnimationDrawable) iv.getBackground()).start();
    }

}