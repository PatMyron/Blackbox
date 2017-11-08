package com.patmyron.blackbox;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.seismic.ShakeDetector;

import static com.patmyron.blackbox.MainActivity.animation;

public class Puzzle17Activity extends AppCompatActivity implements ShakeDetector.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle17);

        new ShakeDetector(this).start((SensorManager) getSystemService(SENSOR_SERVICE));
    }

    @Override
    public void hearShake() {
        animation(this, 0);
    }
}
