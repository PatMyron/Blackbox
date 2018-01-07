package com.patmyron.blackbox;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static com.patmyron.blackbox.MainActivity.getDeviceHeightAndWidth;

public class Puzzle7Activity extends AppCompatActivity {

    private int RADIUS = 1200;
    private static final double PERCENTAGE = .75;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle7);

        arc(new Date().getHours() * 30);
    }

    private void arc(int start) {
        RADIUS = Collections.min(Arrays.asList(RADIUS, getDeviceHeightAndWidth(this).first - 24, getDeviceHeightAndWidth(this).second - 24));
        ShapeDrawable arcShape = new ShapeDrawable(new ArcShape(start - 90, 30));
        arcShape.getPaint().setColor(getResources().getColor(R.color.bg));
        arcShape.setIntrinsicHeight(RADIUS);
        arcShape.setIntrinsicWidth(RADIUS);
        ImageView imageView = new ImageView(this);
        imageView.setX((float) (getDeviceHeightAndWidth(this).second / 2.0 - RADIUS / 2.0));
        imageView.setY((float) (getDeviceHeightAndWidth(this).first / 2.0 - RADIUS / 2.0 - 75 / 2.0));
        imageView.setImageDrawable(arcShape);
        ((ViewGroup) findViewById(R.id.ll)).addView(imageView);

        ShapeDrawable arcShape2 = new ShapeDrawable(new ArcShape(start - 90, 30));
        arcShape2.getPaint().setColor(getResources().getColor(R.color.puzzle7translucent));
        arcShape2.setIntrinsicHeight((int) (RADIUS * PERCENTAGE));
        arcShape2.setIntrinsicWidth((int) (RADIUS * PERCENTAGE));
        ImageView imageView2 = new ImageView(this);
        imageView2.setX((float) (getDeviceHeightAndWidth(this).second / 2.0 - RADIUS * PERCENTAGE / 2.0));
        imageView2.setY((float) (getDeviceHeightAndWidth(this).first / 2.0 - RADIUS * PERCENTAGE / 2.0 - 75 / 2.0));
        imageView2.setImageDrawable(arcShape2);
        ((ViewGroup) findViewById(R.id.ll)).addView(imageView2);

        findViewById(R.id.imageView0).bringToFront();
    }
}
