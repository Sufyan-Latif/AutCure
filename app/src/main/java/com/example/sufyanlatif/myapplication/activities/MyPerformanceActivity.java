package com.example.sufyanlatif.myapplication.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.adapters.ScoreAdapter;
import com.example.sufyanlatif.myapplication.models.Score;
import com.example.sufyanlatif.myapplication.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyPerformanceActivity extends AppCompatActivity {

    String gameId, childId;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    ArrayList<Score> scoreArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_performance);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        childId = getIntent().getStringExtra("child_id");
        scoreArrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.rvScore);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        scoreArrayList.clear();
//        recyclerView.setAdapter(new ScoreAdapter(scoreArrayList));

        String[] games = {"Put the Balls in Basket", "Put the Shapes", "Choose the Animal",
                "Choose the Fruit", "Choose the Vegetable"};

        Spinner spinner = findViewById(R.id.spinnerGames);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, games);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gameId = ""+(i+1);

//                recyclerView.setAdapter(new ScoreAdapter(new ArrayList<Score>()));

                retrievePerformance();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(MyPerformanceActivity.this, "Nothing selected", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void retrievePerformance() {
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Constants.BASE_URL + "retrieve_my_performance.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("code");
                    if (code.equalsIgnoreCase("success")){
                        JSONArray scoreArray = jsonObject.getJSONArray("score");
                        scoreArrayList.clear();
                        for (int i=0; i<scoreArray.length(); i++){
                            JSONObject scoreObj = scoreArray.getJSONObject(i);
                            String correct = scoreObj.getString("correct");
                            String incorrect = scoreObj.getString("incorrect");

                            Score score = new Score();
                            score.setCorrect(correct);
                            score.setIncorrect(incorrect);

                            scoreArrayList.add(score);
                        }
//                        recyclerView.removeAllViewsInLayout();
                        recyclerView.setAdapter(new ScoreAdapter(scoreArrayList));
                    }
                    else {
                        scoreArrayList.clear();
                        recyclerView.setAdapter(new ScoreAdapter(scoreArrayList));
                        Toast.makeText(MyPerformanceActivity.this, "Game not played yet", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("child_id", childId);
                map.put("game_id", gameId);
                return map;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
}
