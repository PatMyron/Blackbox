package com.patmyron.blackbox;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import java.util.Timer;
import java.util.TimerTask;

import static com.patmyron.blackbox.MainActivity.animation;

public class Puzzle29Activity extends AppCompatActivity {

    private Timer timer;
    private static final int THRESHOLD = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle29);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        anim(1);
                    }
                }, THRESHOLD);
                final int reps = 50;
                for (int i = 0; i < reps; i++) {
                    final String r = String.format("%02x", Integer.parseInt("94", 16) * i / reps);
                    final String g = String.format("%02x", Integer.parseInt("46", 16) * i / reps);
                    final String b = String.format("%02x", Integer.parseInt("7C", 16) * i / reps);
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            findViewById(R.id.bg).setBackgroundColor(Color.parseColor("#99" + r + g + b));
                        }
                    }, i * THRESHOLD / reps);
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                findViewById(R.id.bg).setBackgroundColor(getResources().getColor(R.color.bg));
                timer.cancel();
                break;
            }
            default:
        }
        return true;
    }

    private void anim(int index) {
        animation(this, index);
    }
}
