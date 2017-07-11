package com.patmyron.blackbox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Puzzle8Activity extends AppCompatActivity {

    private BroadcastReceiver receiver;
    private double ballSize = 100.0;
    private int deviceHeight;
    private int deviceWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle8);
    }

    @Override
    protected void onResume() {
        super.onResume();
        batteryBroadcastReceiver();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        deviceHeight = displayMetrics.heightPixels;
        deviceWidth = displayMetrics.widthPixels;
    }

    private void recurse(ImageView imageView, int rowNumber, int columnNumber, int batteryLevel) {
        if (columnNumber < (deviceWidth / ballSize) - 1) {
            ImageView imageView2 = new ImageView(this);
            imageView2.setId(View.generateViewId());
            imageView2.setImageResource(R.drawable.circle);
            int color = (batteryLevel > 25) ? R.color.puzzle8 : R.color.puzzle8b;
            imageView2.setColorFilter(ContextCompat.getColor(this, color));
            RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams((int) ballSize, (int) ballSize);
            params2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            params2.bottomMargin = (int) (rowNumber * ballSize);
            if ((columnNumber % (int) ((deviceWidth / ballSize))) == 0) {
                params2.leftMargin = (int) ballSize / 2 * (rowNumber % 2);
            }
            if (columnNumber != 0) {
                params2.addRule(RelativeLayout.END_OF, imageView.getId());
            }
            imageView2.setLayoutParams(params2);
            ((RelativeLayout) findViewById(R.id.merge)).addView(imageView2, 0);
            recurse(imageView2, rowNumber, columnNumber + 1, batteryLevel);
        }

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
                } else if (level > 45 && level < 55) {
                    animation(1);
                } else if (level < 5) {
                    animation(2);
                }
                ImageView imageView = new ImageView(context);
                ((ViewGroup) findViewById(R.id.merge)).removeAllViews();
                for (int i = 0; i < ((deviceHeight / ballSize) - 1) * level / 100.0; i++) {
                    recurse(imageView, i, 0, level);
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
