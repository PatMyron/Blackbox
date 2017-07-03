package com.patmyron.blackbox;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import static java.lang.Math.acos;


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
        ImageView fluid = (ImageView) findViewById(R.id.fluid);
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int deviceWidth = displayMetrics.widthPixels;
        int deviceHeight = displayMetrics.heightPixels;
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) fluid.getLayoutParams();
        marginParams.topMargin = deviceHeight / 2 - (int) (z / 9.8 * deviceHeight) / 2;
        Log.e("pm", String.valueOf(deviceHeight / 2 - (int) (z / 9.8 * deviceHeight) / 2));
        fluid.setPivotX(deviceWidth / 2 + 5000);
        fluid.setPivotY(0);
        fluid.setRotation((float) Math.toDegrees(acos(y / 9.8)));
        if (x < 0) {
            fluid.setRotation(fluid.getRotation() * -1);
        }

        if (x < -9.7) {
            animation(0);
        } else if (x > 9.7) {
            animation(1);
        }
        if (y < -9.7) {
            animation(2);
        } else if (y > 9.7) {
            animation(3);
        }
        if (z < -9.7) {
            animation(4);
        } else if (z > 9.7) {
            animation(5);
        }
        ViewGroup vg = (ViewGroup) findViewById(R.id.ll);
        vg.invalidate();
    }

    private void animation(int index) {
        ImageView iv = (ImageView) ((RelativeLayout) findViewById(R.id.ll)).getChildAt(index);
        iv.setBackgroundResource(R.drawable.animation);
        ((AnimationDrawable) iv.getBackground()).start();
    }

}