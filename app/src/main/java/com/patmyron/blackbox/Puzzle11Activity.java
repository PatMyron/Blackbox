package com.patmyron.blackbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.shredzone.commons.suncalc.MoonIllumination;

import static com.patmyron.blackbox.MainActivity.animation;

public class Puzzle11Activity extends AppCompatActivity {

    private static final double THRESHOLD = 4.0 / 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle11);
        MoonIllumination moonIllumination = MoonIllumination.compute()
                .now()
                .execute();
        // positive phase is waning
        if (moonIllumination.getFraction() < THRESHOLD) animation(this, 0);
        if (moonIllumination.getFraction() > (1 - THRESHOLD)) animation(this, 1);
    }
}
