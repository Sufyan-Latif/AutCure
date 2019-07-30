package com.example.sufyanlatif.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sufyanlatif.myapplication.models.Score;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class TempActivity extends AppCompatActivity {

    ListView listView;
//    TextView tvgame, tvcorrect, tvincorrect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        listView= findViewById(R.id.listView);

        ArrayList<String> games= new ArrayList<>();
        ArrayList<String> correct= new ArrayList<>();
        ArrayList<String> incorrect= new ArrayList<>();


        Intent intent= getIntent();
        Gson gson= new Gson();
        String json= intent.getStringExtra("score");
        Type type = new TypeToken<LinkedHashMap<Integer, Score>>() {}.getType();
        LinkedHashMap<Integer, Score> scoreLinked = gson.fromJson(json, type);

        for(Map.Entry<Integer, Score> entry:scoreLinked.entrySet()){
            int key=entry.getKey();
            Score current=entry.getValue();

            games.add(current.game_id);
            correct.add(current.correct);
            incorrect.add(current.incorrect);
//            Log.d("RetrievePerformanceFina", b+ " "+b.id+" "+b.correct+" "+b.incorrect);
        }

        MyAdapter adapter= new MyAdapter(TempActivity.this, games, correct, incorrect);
        listView.setAdapter(adapter);

/*
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
*/
    }
}
class MyAdapter extends ArrayAdapter{
    ArrayList<String> gamesList, correctList, incorrectList;
    public MyAdapter(Context context, ArrayList<String> games, ArrayList<String> correct, ArrayList<String> incorrect){
        super(context, R.layout.score_listview_row, R.id.tvgameId, games);
        this.gamesList= games;
        this.correctList= correct;
        this.incorrectList= incorrect;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        LayoutInflater.from(parent.getContext()).inflate(R.layout.score_listview_row, parent, false);
        View row= inflater.inflate(R.layout.score_listview_row, parent, false);

        TextView tvGameId= row.findViewById(R.id.tvgameId);
        TextView correct= row.findViewById(R.id.tvcorrect);
        TextView incorrect= row.findViewById(R.id.tvincorrect);

        tvGameId.setText(gamesList.get(position));
        correct.setText(correctList.get(position));
        incorrect.setText(incorrectList.get(position));

        return row;
    }
}