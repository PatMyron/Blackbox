package com.patmyron.blackbox;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;

import static com.patmyron.blackbox.MainActivity.animation;
import static com.patmyron.blackbox.MainActivity.getDeviceHeightAndWidth;

public class Puzzle3Activity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle3);
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
        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (am.isWiredHeadsetOn()) {
            animation(this, 2);
        }
        if (am.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
            animation(this, 3);
        }
        int media = am.getStreamVolume(AudioManager.STREAM_RING);
        if (media < 1) {
            animation(this, 1);
        } else if (media == am.getStreamMaxVolume(AudioManager.STREAM_MUSIC)) {
            animation(this, 0);
        }

        ImageView fluid = findViewById(R.id.fluid);
        int deviceHeight = getDeviceHeightAndWidth(getApplicationContext()).first;
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) fluid.getLayoutParams();
        params.height = (int) (deviceHeight * ((double) media / am.getStreamMaxVolume(AudioManager.STREAM_MUSIC)));
        fluid.setLayoutParams(params);
    }
}