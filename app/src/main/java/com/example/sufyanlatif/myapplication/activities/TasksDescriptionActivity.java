package com.example.sufyanlatif.myapplication.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.sufyanlatif.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TasksDescriptionActivity extends AppCompatActivity {

    TextView taskDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_description);

        taskDescription = findViewById(R.id.taskDescription);
        String response = getIntent().getStringExtra("response");
        StringBuilder stringBuilder = new StringBuilder();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray teachersArray = jsonObject.getJSONArray("teachers");
            JSONArray tasksArray = jsonObject.getJSONArray("tasks");
            for (int i=0; i<tasksArray.length(); i++){
                JSONObject taskObj = tasksArray.getJSONObject(i);
                JSONObject teacherObj = teachersArray.getJSONObject(i);
                String description = taskObj.getString("description");
                if (description != null){
                    String firstName = teacherObj.getString("first_name");
                    String lastName = teacherObj.getString("last_name");
                    stringBuilder.append("\n\nTask "+(i+1)+" :"+description);
                    stringBuilder.append("\nAssigned By: "+firstName+ " "+lastName);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        taskDescription.setText(stringBuilder);
    }
}
