package com.example.sufyanlatif.myapplication.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sufyanlatif.myapplication.R;

public class SplashActivity extends AppCompatActivity {
    private int SPLASH_DELAY= 3000;
    private Handler mDelayHandler= null;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sp = getSharedPreferences("myLoginData", 0);

        mDelayHandler= new Handler();
        Runnable mRunnable= new Runnable() {
            @Override
            public void run() {

//                String type = sp.getString("type", "abc");
                if (sp.getString("type", "") != "") {

                    if (sp.getString("type", "null").equals("parent")) {
                        Intent intent = new Intent(SplashActivity.this, ParentHomeActivity.class);
                        finish();
                        startActivity(intent);
                    } else if (sp.getString("type", "null").equals("child")) {
                        Intent intent = new Intent(SplashActivity.this, ChildHomeActivity.class);
                        finish();
                        startActivity(intent);
                    }
                    if (sp.getString("type", "null").equals("teacher")) {
                        Intent intent = new Intent(SplashActivity.this, TeacherWelcomeActivity.class);
                        finish();
                        startActivity(intent);
                    }
                }
                else {
//                    Intent intent= new Intent(SplashActivity.this, AssignTaskActivity.class);
                    Intent intent= new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        mDelayHandler.postDelayed(mRunnable, SPLASH_DELAY);
    }
}
