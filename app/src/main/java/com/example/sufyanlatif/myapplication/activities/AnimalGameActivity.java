package com.example.sufyanlatif.myapplication.activities;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.sufyanlatif.myapplication.R;

import java.util.ArrayList;
import java.util.Random;

public class AnimalGameActivity extends AppCompatActivity {
    ImageView imageView;
    int level;
    ArrayList<Integer> animalImages;
    ArrayList<String> animalTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_game);

        Random r = new Random();
        int i1 = r.nextInt(3);
        Log.d("RandomNum", "Random Number = "+i1);
//        animalImages.add(R.drawable.button_style);
//        animalTexts.add("Cat");
//        animalTexts.add("Dog");
//        animalTexts.add("Lion");

        int[] animalImages= {R.drawable.basket, R.drawable.blue_ball, R.drawable.button};
        String[] animalTexts= {"Basket", "Blue Ball", "Button"};
        if (level == 0){
//            Easy level
            ImageView imageView= new ImageView(AnimalGameActivity.this);
            RelativeLayout.LayoutParams layoutParams= new RelativeLayout.LayoutParams(160, 160);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            layoutParams.setMargins(0, 0, 0, 20);
            imageView.setLayoutParams(layoutParams);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageView.setImageDrawable(getDrawable(R.drawable.boy));
            }
            imageView.setId(0);

            RelativeLayout animalsLayout = findViewById(R.id.animals_layout);
            animalsLayout.addView(imageView);

        }
        else if (level == 1){
//            Medium level
        }
    }
}
