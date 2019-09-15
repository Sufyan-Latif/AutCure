package com.example.sufyanlatif.myapplication.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.fragments.GameAnimalFragment;

public class AnimalGameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_game);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.animals_layout, new GameAnimalFragment());
        transaction.commit();
    }
}
