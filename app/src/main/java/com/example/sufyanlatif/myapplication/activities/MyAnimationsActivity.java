package com.example.sufyanlatif.myapplication.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sufyanlatif.myapplication.R;

import pl.droidsonroids.gif.GifImageView;

public class MyAnimationsActivity extends AppCompatActivity {

    GifImageView gifImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_animations);

        gifImageView = findViewById(R.id.gifImgView);
        String gif = getIntent().getStringExtra("gif");
        switch (gif){
            case "tie":
                gifImageView.setImageResource(R.drawable.ti);
//                gifImageView.setImageDrawable(getResources().getDrawable(R.drawable.ti));
                break;
            case "shoes":
                gifImageView.setImageResource(R.drawable.shoes);
//                gifImageView.setImageDrawable(getResources().getDrawable(R.drawable.shoes));
                break;
            case "shirt":
                gifImageView.setImageResource(R.drawable.shrt);
//                gifImageView.setImageDrawable(getResources().getDrawable(R.drawable.shrt));
                break;
            case "socks":
                gifImageView.setImageResource(R.drawable.socks);
//                gifImageView.setImageDrawable(getResources().getDrawable(R.drawable.socks));
                break;
            case "turn":
                gifImageView.setImageResource(R.drawable.round);
//                gifImageView.setImageDrawable(getResources().getDrawable(R.drawable.round));
                break;
        }
    }
}
