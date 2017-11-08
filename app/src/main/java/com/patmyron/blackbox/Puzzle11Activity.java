package com.patmyron.blackbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.shredzone.commons.suncalc.MoonIllumination;

public class Puzzle11Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle11);
        MoonIllumination.compute()
                .now()
                .execute();
    }
}
