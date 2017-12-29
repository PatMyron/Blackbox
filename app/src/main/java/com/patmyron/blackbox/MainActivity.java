package com.patmyron.blackbox;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<ImageView> getViewsByTag(ViewGroup root, String tag) {
        ArrayList<ImageView> views = new ArrayList<>();
        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = root.getChildAt(i);
            if (tag.equals(child.getTag())) {
                views.add((ImageView) child);
            }
        }
        return views;
    }

    static Pair<Integer, Integer> getDeviceHeightAndWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return new Pair<>(displayMetrics.heightPixels, displayMetrics.widthPixels);
    }

    static void animation(Activity activity, int index) {
        ImageView iv = (ImageView) ((ViewGroup) activity.findViewById(R.id.ll)).getChildAt(index);
        iv.setBackgroundResource(R.drawable.animation);
        ((AnimationDrawable) iv.getBackground()).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.RECORD_AUDIO}, 1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = getSharedPreferences("com.patmyron.blackbox", MODE_PRIVATE);
        String solved = pref.getString("solved", "");
        // pref.edit().putString("solved", "").apply();
    }

    public void puzzleLaunch(View view) {
        Intent intent = new Intent();
        intent.setClassName(this, getPackageName() + view.getTag());
        startActivity(intent);
    }
}