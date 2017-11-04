package com.example.shubhamchandrakar.callblocker;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import database.BaseData;

public class MainActivity extends Activity {
    boolean isStarted = false;
    Button b1, b2;
    ImageView iv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isStarted = checkRunningService();
        BaseData.open_or_create_db(MainActivity.this);
       // iv1 = (ImageView) findViewById(R.id.imgView);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        if(isStarted){
            b1.setText("Stop");
        }
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStarted) {
                    Intent i1 = new Intent(MainActivity.this, MyService.class);
                    stopService(i1);
                    isStarted = false;
                    b1.setText("Start");
                }
                else {
                    Intent i1 = new Intent(MainActivity.this, MyService.class);
                    startService(i1);
                    isStarted = true;
                    b1.setText("Stop");

                }


            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(MainActivity.this, BlockList.class);
                startActivity(i2);
            }
        });
    }
    public boolean checkRunningService() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> rsList = activityManager.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo r1 : rsList) {
            if ("com.example.shubhamchandrakar.callblocker.MyService".equals(r1.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder ab1 = new AlertDialog.Builder(MainActivity.this);
        ab1.setMessage("Are you sure want to exit!!");
        ab1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.finish();
            }
        });
        ab1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        ab1.setCancelable(false);
        ab1.show();
    }
}
