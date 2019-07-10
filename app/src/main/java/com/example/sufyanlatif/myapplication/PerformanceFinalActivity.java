package com.example.sufyanlatif.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PerformanceFinalActivity extends AppCompatActivity {

    TableLayout scoreTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_final);

        scoreTable = (TableLayout) findViewById(R.id.scoreTable);
/*
        scoreTable.setPadding(5, 5, 5, 5);
        TableRow tr_head = new TableRow(this);
        tr_head.setPadding(30, 30, 30, 30);
        tr_head.setGravity(View.TEXT_ALIGNMENT_CENTER);
//        tr_head.setId(10);
        tr_head.setBackgroundColor(Color.GRAY);        // part1
        tr_head.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        TextView label_correct = new TextView(this);
//        label_correct.setId(20);
        label_correct.setText("CORRECT");
        label_correct.setTextSize(20);
        label_correct.setTextColor(Color.WHITE);          // part2
        label_correct.setPadding(5, 5, 5, 5);
        tr_head.addView(label_correct);// add the column to the table row here

        TextView label_incorrect = new TextView(this);    // part3
//        label_android.setId(21);// define id that must be unique
        label_incorrect.setText("INCORRECT"); // set the text for the header
        label_incorrect.setTextSize(20);
        label_incorrect.setTextColor(Color.WHITE); // set the color
        label_incorrect.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_incorrect); // add the column to the table row here

        TextView label_game = new TextView(this);    // part3
//        label_android.setId(21);// define id that must be unique
        label_game.setText("GAME"); // set the text for the header
        label_game.setTextColor(Color.WHITE); // set the color
        label_game.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_game); // add the column to the table row here

        scoreTable.addView(tr_head, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,                    //part4
                TableLayout.LayoutParams.WRAP_CONTENT));
*/
//        TextView[] textArray = new TextView[productsList.length()];
//        TableRow[] tr_head = new TableRow[productsList.length()];
    }

    public TableLayout getScoreTable() {
        return scoreTable;
    }
}
