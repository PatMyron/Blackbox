package com.patmyron.blackbox;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;

import static com.patmyron.blackbox.MainActivity.animation;
import static com.patmyron.blackbox.MainActivity.getViewsByTag;

public class Puzzle2Activity extends AppCompatActivity implements SensorEventListener {

    private static final double THRESHOLD = 10.1;
    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle2);
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
        try {
            int brightness = android.provider.Settings.System.getInt(getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS);
            if (brightness < THRESHOLD) animation(this, 0);
            if (brightness > (255 - THRESHOLD)) animation(this, 1);

            for (ImageView ray : getViewsByTag((ViewGroup) findViewById(R.id.ll), "rays")) {
                ViewGroup.LayoutParams layoutParams = ray.getLayoutParams();
                layoutParams.height = (int) (100 * (brightness / 255.0));
                ray.setLayoutParams(layoutParams);
            }
        } catch (Settings.SettingNotFoundException ignored) {
        }
    }
}