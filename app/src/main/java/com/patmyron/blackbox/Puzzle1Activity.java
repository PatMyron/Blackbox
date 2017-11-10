package com.patmyron.blackbox;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;

import static com.patmyron.blackbox.MainActivity.animation;
import static com.patmyron.blackbox.MainActivity.getDeviceHeightAndWidth;
import static java.lang.Math.acos;


public class Puzzle1Activity extends AppCompatActivity implements SensorEventListener {

    private static final double THRESHOLD = 9.7;
    private SensorManager mSensorManager;

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
        ImageView fluid = findViewById(R.id.fluid);
        float x = event.values[0], y = event.values[1], z = event.values[2];
        int deviceHeight = getDeviceHeightAndWidth(this).first;
        int deviceWidth = getDeviceHeightAndWidth(this).second;
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) fluid.getLayoutParams();
        marginParams.topMargin = deviceHeight / 2;
        fluid.setPivotX(deviceWidth / 2 + 5000);
        fluid.setPivotY(0);
        fluid.setRotation((float) Math.toDegrees(acos(y / 9.8)));
        if (x < 0) {
            fluid.setRotation(fluid.getRotation() * -1);
        }
        if (Double.isNaN(fluid.getRotation())) {
            if (y > 0) {
                fluid.setRotation(0);
            } else {
                fluid.setRotation(180);
            }
        }

        if (x < -THRESHOLD) animation(this, 0);
        if (x > THRESHOLD) animation(this, 1);
        if (y < -THRESHOLD) animation(this, 2);
        if (y > THRESHOLD) animation(this, 3);
        if (z < -THRESHOLD) animation(this, 4);
        if (z > THRESHOLD) animation(this, 5);

        findViewById(R.id.ll).invalidate();
    }
}