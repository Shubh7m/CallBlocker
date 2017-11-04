package com.example.shubhamchandrakar.callblocker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class FlashAct extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        final Activity ctx = this;
        ImageView iv1 = (ImageView) findViewById(R.id.imageView );
        Animation a1 = AnimationUtils.loadAnimation(ctx, R.anim.fadeout);
        iv1.startAnimation(a1);
        Timer t1 = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                Intent i1 = new Intent(ctx, MainActivity.class);
                startActivity(i1);
                ctx.finish();
            }
        };
        t1.schedule(tt, 1800);
    }
}
