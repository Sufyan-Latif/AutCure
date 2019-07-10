package com.example.sufyanlatif.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    private int SPLASH_DELAY= 3000;
    private Handler mDelayHandler= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashact);
        mDelayHandler= new Handler();
        Runnable mRunnable= new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(SplashActivity.this,loginact.class);
                startActivity(intent);
                finish();
            }
        };
        mDelayHandler.postDelayed(mRunnable, SPLASH_DELAY);
    }

}
