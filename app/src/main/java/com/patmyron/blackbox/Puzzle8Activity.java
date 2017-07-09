package com.patmyron.blackbox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Puzzle8Activity extends AppCompatActivity {

    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle8);
    }

    @Override
    protected void onResume() {
        super.onResume();
        batteryBroadcastReceiver();

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.circle);
        imageView.setColorFilter(ContextCompat.getColor(this, R.color.puzzle8), PorterDuff.Mode.ADD);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
        ((FrameLayout) findViewById(R.id.merge)).addView(imageView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private void batteryBroadcastReceiver() {
        receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                int rawLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int level = -1;
                if (rawLevel >= 0 && scale > 0) {
                    level = (rawLevel * 100) / scale;
                }
                if (level > 95) {
                    animation(0);
                } else if (level == 50) {
                    animation(1);
                } else if (level < 5) {
                    animation(2);
                }
            }
        };
        IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver, batteryLevelFilter);
    }

    private void animation(int index) {
        ImageView iv = (ImageView) ((LinearLayout) ((LinearLayout) findViewById(R.id.ll)).getChildAt(index)).getChildAt(0);
        iv.setBackgroundResource(R.drawable.animation);
        ((AnimationDrawable) iv.getBackground()).start();
    }
}
