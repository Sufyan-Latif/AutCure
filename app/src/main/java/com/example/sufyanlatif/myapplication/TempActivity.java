package com.example.sufyanlatif.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public class TempActivity extends AppCompatActivity {

    TextView tvgame, tvcorrect, tvincorrect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        tvgame= findViewById(R.id.tvGame);
        tvcorrect= findViewById(R.id.tvCorrect);
        tvincorrect= findViewById(R.id.tvIncorrect);

        StringBuffer games= new StringBuffer("Game");
        StringBuffer correct= new StringBuffer("Correct");
        StringBuffer incorrect= new StringBuffer("Incorrect");

        Intent intent= getIntent();
        Gson gson= new Gson();
        String json= intent.getStringExtra("score");
        Type type = new TypeToken<LinkedHashMap<Integer, Score>>() {}.getType();
        LinkedHashMap<Integer, Score> scoreLinked = gson.fromJson(json, type);
//        LinkedHashMap<Integer, Score> score= (LinkedHashMap<Integer, Score>) intent.getSerializableExtra("score");

        for(Map.Entry<Integer, Score> entry:scoreLinked.entrySet()){
            int key=entry.getKey();
            Score current=entry.getValue();

            games.append("\n"+current.game_id);
            correct.append("\n"+current.correct);
            incorrect.append("\n"+current.incorrect);

//            Log.d("RetrievePerformanceFina", b+ " "+b.id+" "+b.correct+" "+b.incorrect);
        }

        tvgame.setText(games);
        tvcorrect.setText(correct);
        tvincorrect.setText(incorrect);

//        temp.setText("Score = "+score);
    }
}
