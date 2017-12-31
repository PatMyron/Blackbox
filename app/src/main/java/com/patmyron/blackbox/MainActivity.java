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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

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
        SharedPreferences pref = getSharedPreferences(getString(R.string.pref), MODE_PRIVATE);
        String solved = pref.getString(getString(R.string.prefSolved), "[]");
        try {
            HashSet<Integer> set = new ObjectMapper().readValue(solved, HashSet.class);
            for (Integer i : set) {
                // TODO box specific instead of whole puzzle
                ArrayList<ImageView> ivs = getViewsByTag((ViewGroup) findViewById(R.id.ll), ".Puzzle" + i.toString() + "Activity");
                for (ImageView iv : ivs) {
                    iv.setImageResource(R.drawable.filled);
                }
            }
        } catch (IOException ignored) {
        }
    }

    static void puzzleCompleted(Context context, int puzzleCompleted) {
        SharedPreferences pref = context.getSharedPreferences(context.getString(R.string.pref), MODE_PRIVATE);
        try {
            HashSet<Integer> set = new ObjectMapper().readValue(pref.getString(context.getString(R.string.prefSolved), "[]"), HashSet.class);
            set.add(puzzleCompleted);
            pref.edit().putString(context.getString(R.string.prefSolved), new ObjectMapper().writeValueAsString(set)).apply();
        } catch (Exception ignored) {
        }
    }

    public void puzzleLaunch(View view) {
        Intent intent = new Intent();
        intent.setClassName(this, getPackageName() + view.getTag());
        startActivity(intent);
    }
}