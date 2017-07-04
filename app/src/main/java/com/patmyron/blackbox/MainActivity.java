package com.patmyron.blackbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void puzzleLaunch(View view) {
        Intent intent = new Intent();
        intent.setClassName(this, this.getPackageName() + view.getTag());
        this.startActivity(intent);
    }
}