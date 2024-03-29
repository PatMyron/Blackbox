package com.patmyron.blackbox;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import static com.patmyron.blackbox.MainActivity.animation;

public class Puzzle6Activity extends AppCompatActivity {

    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle6);
    }

    @Override
    protected void onResume() {
        super.onResume();
        screenshotBroadcastReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private void screenshotBroadcastReceiver() {
        receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                animation((Activity) context, 0);
            }
        };
        IntentFilter filter = new IntentFilter(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        filter.addDataScheme("file");
        registerReceiver(receiver, filter);
    }
}
