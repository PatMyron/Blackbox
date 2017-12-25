package com.patmyron.blackbox;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

public class Puzzle10Activity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private SoundMeter sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle10);
//        recognizer = defaultSetup()
//                .setAcousticModel(new File(assetsDir, "en-us-ptm"))
//                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))
//                .getRecognizer();
//        recognizer.addListener(this);
//        recognizer.addKeyphraseSearch(KWS_SEARCH, KEYPHRASE);
//        recognizer.startListening(searchName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_NORMAL);
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
        sm.stop();
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(SensorEvent event) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED && sm != null) {
            double amp = sm.getAmplitude();
            WavyLineView view = findViewById(R.id.wavyLineView);
            view.setAmplitude((int) (90 * amp / 32767));
            view.setPeriod((float) (0.1));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        sm = new SoundMeter();
        sm.start();
    }
}
