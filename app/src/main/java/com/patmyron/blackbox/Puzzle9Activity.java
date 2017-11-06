package com.patmyron.blackbox;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.IOException;

import static com.patmyron.blackbox.MainActivity.getDeviceHeightAndWidth;

public class Puzzle9Activity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private SoundMeter sm;
    private final int ballSize = 300;
    private static final double THRESHOLD = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle9);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_UI);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.RECORD_AUDIO}, 1);
        } else {
            if (sm == null) {
                sm = new SoundMeter();
                sm.start();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(SensorEvent event) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED && sm != null) {
            double amp = sm.getAmplitude();
            if (amp > (22000 - THRESHOLD)) animation(0);
            if (Math.abs(amp - 10000) < THRESHOLD) animation(1);
            if (amp < THRESHOLD) animation(2);
            int deviceHeight = getDeviceHeightAndWidth(this).first;
            int deviceWidth = getDeviceHeightAndWidth(this).second;
            ((ViewGroup) findViewById(R.id.merge)).removeAllViews();
            for (int i = 0; i < amp / 3000; i++) {
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.circle);
                imageView.setColorFilter(ContextCompat.getColor(this, R.color.puzzle9translucent));
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ballSize, ballSize);
                params.topMargin = (int) (Math.random() * (deviceHeight - ballSize));
                params.leftMargin = (int) (Math.random() * (deviceWidth - ballSize));
                imageView.setLayoutParams(params);
                ((RelativeLayout) findViewById(R.id.merge)).addView(imageView, 0);
            }
        }
    }

    private void animation(int index) {
        ImageView iv = (ImageView) ((LinearLayout) ((LinearLayout) findViewById(R.id.ll)).getChildAt(index)).getChildAt(0);
        iv.setBackgroundResource(R.drawable.animation);
        ((AnimationDrawable) iv.getBackground()).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        sm = new SoundMeter();
        sm.start();
    }

    private class SoundMeter {
        private MediaRecorder mRecorder = null;

        void start() {
            if (mRecorder == null) {
                mRecorder = new MediaRecorder();
                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mRecorder.setOutputFile("/dev/null");
                try {
                    mRecorder.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mRecorder.start();
            }
        }

        public void stop() {
            if (mRecorder != null) {
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
            }
        }

        double getAmplitude() {
            if (mRecorder != null)
                return mRecorder.getMaxAmplitude();
            else
                return 0;
        }
    }
}