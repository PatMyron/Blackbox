package com.patmyron.blackbox;

import android.content.Context;
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

import static com.patmyron.blackbox.MainActivity.animation;

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

        mAnimation = new AlphaAnimation(1, 0);
        mAnimation.setDuration(2000);
        mAnimation.setInterpolator(new AccelerateInterpolator());
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.REVERSE);
        findViewById(R.id.imageView).startAnimation(mAnimation);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(SensorEvent event) {
        ImageView iv = findViewById(R.id.imageView);
        if (event.values[0] < 4) {
            iv.clearAnimation();
            iv.setVisibility(View.INVISIBLE);
            animation(this, 0);
        } else {
            iv.startAnimation(mAnimation);
            iv.setVisibility(View.VISIBLE);
        }
    }
}
