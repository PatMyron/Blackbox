package com.patmyron.blackbox;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationUtils;

import static com.patmyron.blackbox.MainActivity.animation;

public class Puzzle20Activity extends AppCompatActivity {

    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle20);
    }

    @Override
    protected void onResume() {
        super.onResume();
        shutdownReceiver();
        findViewById(R.id.imageView1).startAnimation(AnimationUtils.loadAnimation(this, R.anim.slideright));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private void shutdownReceiver() {
        receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                animation((Activity) context, 0);
            }
        };
        IntentFilter filter = new IntentFilter(Intent.ACTION_SHUTDOWN);
        registerReceiver(receiver, filter);
    }
}
