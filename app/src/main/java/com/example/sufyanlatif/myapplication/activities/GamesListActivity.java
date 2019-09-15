package com.example.sufyanlatif.myapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sufyanlatif.myapplication.R;

public class GamesListActivity extends AppCompatActivity {

    CardView cvPutBalls, cvPutShapes, cvChooseAnimal, cvChooseFruit, cvVegetable;
    ImageView imgBack;
    public static String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);

        if (getIntent() != null)
            status = getIntent().getStringExtra("status");

        bindViews();
        cvPutBalls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GamesListActivity.this, GameDragActivity.class);
                intent.putExtra("status", status);
                startActivity(intent);
            }
        });

        cvPutShapes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GamesListActivity.this, GameMatchShapeActivity.class);
                intent.putExtra("status", status);
                startActivity(intent);
            }
        });

        cvChooseAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.equalsIgnoreCase("registered")){
                    Intent intent = new Intent(GamesListActivity.this, AnimalGameActivity.class);
                    intent.putExtra("game", "Choose the Animal");
                    startActivity(intent);
                }
                else {
                    Toast.makeText(GamesListActivity.this, "Login first to play all games", Toast.LENGTH_SHORT).show();
                }

            }
        });

        cvChooseFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.equalsIgnoreCase("registered")) {
                    Intent intent = new Intent(GamesListActivity.this, AnimalGameActivity.class);
                    intent.putExtra("game", "Choose the Fruit");
                    startActivity(intent);
                }
                else {
                    Toast.makeText(GamesListActivity.this, "Login first to play all games", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cvVegetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.equalsIgnoreCase("registered")) {
                    Intent intent = new Intent(GamesListActivity.this, AnimalGameActivity.class);
                    intent.putExtra("game", "Choose the Vegetable");
                    startActivity(intent);
                }
                else {
                    Toast.makeText(GamesListActivity.this, "Login first to play all games", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void bindViews() {
        cvPutBalls = findViewById(R.id.cvPutTheBalls);
        cvPutShapes = findViewById(R.id.cvPutTheShapes);
        cvChooseAnimal = findViewById(R.id.cvChooseAnimal);
        cvChooseFruit = findViewById(R.id.cvChooseFruit);
        cvVegetable = findViewById(R.id.cvChooseVegetable);
        imgBack = findViewById(R.id.imgBack);
    }
}
