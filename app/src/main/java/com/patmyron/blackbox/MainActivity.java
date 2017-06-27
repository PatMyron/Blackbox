package com.patmyron.blackbox;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.BatteryManager;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SoundMeter sm;
    private float last_x, last_y, last_z;
    long lastUpdate;
    SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        for (int sensor : new int[]{Sensor.TYPE_GRAVITY, Sensor.TYPE_PROXIMITY, Sensor.TYPE_LIGHT, Sensor.TYPE_GYROSCOPE, Sensor.TYPE_LINEAR_ACCELERATION}) {
            mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(sensor), SensorManager.SENSOR_DELAY_UI);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        } else if (sm == null) {
            sm = new SoundMeter();
            sm.start();
        }
        batteryBroadcastReceiver();
        screenshotBroadcastReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_GRAVITY:
                ((TextView) findViewById(R.id.tv1)).setText("X: " + event.values[0]);
                ((TextView) findViewById(R.id.tv2)).setText("Y: " + event.values[1]);
                ((TextView) findViewById(R.id.tv3)).setText("Z: " + event.values[2]);
                break;
            case Sensor.TYPE_PROXIMITY:
                ((TextView) findViewById(R.id.tv7)).setText("PROXIMITY: " + event.values[0]);
                break;
            case Sensor.TYPE_LIGHT:
                ((TextView) findViewById(R.id.tv11)).setText("LIGHT: " + event.values[0]);
                break;
            case Sensor.TYPE_GYROSCOPE:
                ((TextView) findViewById(R.id.tv14)).setText("X: " + event.values[0]);
                ((TextView) findViewById(R.id.tv15)).setText("Y: " + event.values[1]);
                ((TextView) findViewById(R.id.tv16)).setText("Z: " + event.values[2]);
                break;
            case Sensor.TYPE_LINEAR_ACCELERATION:
                long curTime = System.currentTimeMillis();
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / (curTime - lastUpdate) * 10000;
                if (speed > 8000) {
                    ((TextView) findViewById(R.id.tv17)).setText("SHOOK");
                }
                last_x = x;
                last_y = y;
                last_z = z;
                lastUpdate = curTime;
                break;
        }
        try {
            ((TextView) findViewById(R.id.tv4)).setText("BRIGHTNESS: " + android.provider.Settings.System.getInt(getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS));
        } catch (SettingNotFoundException e) {
            e.printStackTrace();
        }
        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        ((TextView) findViewById(R.id.tv5)).setText("HEADSET: " + am.isWiredHeadsetOn());
        ((TextView) findViewById(R.id.tv6)).setText("VOLUME: " + am.getStreamVolume(AudioManager.STREAM_MUSIC));
        ((TextView) findViewById(R.id.tv8)).setText("AIRPLANE: " + Settings.Global.getInt(getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED && sm != null) {
            ((TextView) findViewById(R.id.tv10)).setText("NOISE: " + sm.getAmplitude());
        }
        ((TextView) findViewById(R.id.tv12)).setText("CLIPBOARD: " + ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).getText());
    }

    private void batteryBroadcastReceiver() {
        BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);
                int rawLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int level = -1;
                if (rawLevel >= 0 && scale > 0) {
                    level = (rawLevel * 100) / scale;
                }
                ((TextView) findViewById(R.id.tv9)).setText("BATTERY: " + level);
            }
        };
        IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevelReceiver, batteryLevelFilter);
    }

    private void screenshotBroadcastReceiver() {
        BroadcastReceiver receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);
                ((TextView) findViewById(R.id.tv13)).setText("SCREENSHOT");
            }
        };
        IntentFilter filter = new IntentFilter(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        filter.addDataScheme("file");
        registerReceiver(receiver, filter);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        sm = new SoundMeter();
        sm.start();
    }

}