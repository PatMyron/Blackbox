package com.patmyron.blackbox;

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
                        anim(0);
                    }
                }, THRESHOLD);
                break;
            }
            case MotionEvent.ACTION_UP: {
                timer.cancel();
                break;
            }
            default:
        }
        return true;
    }

    void anim(int index) {
        animation(this, index);
    }
}
