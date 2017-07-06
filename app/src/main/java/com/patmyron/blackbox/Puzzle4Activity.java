package com.patmyron.blackbox;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Puzzle4Activity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Animation mAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle4);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_UI);
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        mAnimation = new AlphaAnimation(1, 0);
        mAnimation.setDuration(2000);
        mAnimation.setInterpolator(new AccelerateInterpolator());
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.REVERSE);
        iv.startAnimation(mAnimation);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(SensorEvent event) {
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        if (event.values[0] < 4) {
            iv.clearAnimation();
            iv.setVisibility(View.INVISIBLE);
            animation(0);
        } else {
            iv.startAnimation(mAnimation);
            iv.setVisibility(View.VISIBLE);
        }
    }

    private void animation(int index) {
        ImageView iv = (ImageView) ((RelativeLayout) findViewById(R.id.ll)).getChildAt(index);
        iv.setBackgroundResource(R.drawable.animation);
        ((AnimationDrawable) iv.getBackground()).start();
    }
}
