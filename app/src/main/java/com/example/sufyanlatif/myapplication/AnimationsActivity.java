package com.example.sufyanlatif.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import pl.droidsonroids.gif.GifImageView;

public class AnimationsActivity extends AppCompatActivity {

    GifImageView gifImageView;
    Button next, prev;
    ViewFlipper animations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animations);

        next= findViewById(R.id.nextAnimation);
        prev= findViewById(R.id.prevAnimation);
        animations= findViewById(R.id.animations);
//        gifImageView= findViewById(R.id.animations);


        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animations.setInAnimation(AnimationsActivity.this, android.R.anim.slide_in_left);
                animations.setOutAnimation(AnimationsActivity.this, R.anim.slide_out_left);
                animations.showPrevious();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animations.setInAnimation(AnimationsActivity.this, R.anim.slide_out_left);
                animations.setOutAnimation(AnimationsActivity.this, R.anim.slide_in_right);
                animations.showNext();
            }
        });
    }
}
