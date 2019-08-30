package com.example.sufyanlatif.myapplication.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sufyanlatif.myapplication.R;

public class TempActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        TextView one, two, three;
        one = findViewById(R.id.tvAli);
        two = findViewById(R.id.tvHammad);
        three = findViewById(R.id.tvSufyan);

        String games[] = {"Put the balls in Basket", "Match the shapes", "Fill Colors", "Animals"};
        AlertDialog.Builder builder = new AlertDialog.Builder(TempActivity.this)
                .setTitle("Select the game !")
                .setItems(games, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Toast.makeText(TempActivity.this, "Task Assigned", Toast.LENGTH_SHORT).show();
                        } else if (which == 1) {
                            Toast.makeText(TempActivity.this, "Task Assigned", Toast.LENGTH_SHORT).show();
                        } else if (which == 3) {
                            Toast.makeText(TempActivity.this, "Task Assigned", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null);
        final AlertDialog dialog = builder.create();


        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
    }
}
