package com.example.sufyanlatif.myapplication.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.adapters.ChildrenAdapter;
import com.example.sufyanlatif.myapplication.adapters.TeacherAdapter;
import com.example.sufyanlatif.myapplication.interfaces.OnChildClick;
import com.example.sufyanlatif.myapplication.interfaces.OnTeacherClick;
import com.example.sufyanlatif.myapplication.models.Child;
import com.example.sufyanlatif.myapplication.models.Teacher;
import com.example.sufyanlatif.myapplication.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeachersListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String callFrom, action;
    ArrayList<Teacher> teacherArrayList;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_list);

        sp = getSharedPreferences("myLoginData", 0);

        callFrom = getIntent().getStringExtra("callFrom");
        action = getIntent().getStringExtra("action");

        recyclerView = findViewById(R.id.childrenList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        teacherArrayList = new ArrayList<>();

        if (getIntent()!= null){
            if (getIntent().getStringExtra("response") != null){
                String response = getIntent().getStringExtra("response");

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray teachersArray = jsonObject.getJSONArray("teachers");
                    JSONArray tasksArray = jsonObject.getJSONArray("tasks");

                    for (int i=0; i<teachersArray.length(); i++){
                        JSONObject teacherObj = teachersArray.getJSONObject(i);
                        Teacher teacher = new Teacher();
                        teacher.setId(teacherObj.getString("id"));
                        teacher.setFirstName(teacherObj.getString("first_name"));
                        teacher.setLastName(teacherObj.getString("last_name"));
                        teacherArrayList.add(teacher);
                    }

                    } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        retrieveAssocTeachers();
    }

    private void retrieveAssocTeachers(){

        StringRequest request = new StringRequest(Request.Method.POST, Constants.BASE_URL + "retrieve_teacher_associations.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("code");
                    if (code.equals("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("teachers");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject objTeacher = jsonArray.getJSONObject(i);
                            Teacher teacher = new Teacher();
                            teacher.setId(objTeacher.getString("id"));
                            teacher.setFirstName(objTeacher.getString("first_name"));
                            teacher.setLastName(objTeacher.getString("last_name"));
                            teacherArrayList.add(teacher);
                        }
                        recyclerView.setAdapter(new TeacherAdapter(TeachersListActivity.this, teacherArrayList, new OnTeacherClick() {
                            @Override
                            public void onClick(String id) {
                                Toast.makeText(TeachersListActivity.this, "Teacher id = " + id, Toast.LENGTH_SHORT).show();

                                if (action.equalsIgnoreCase("Communication")) {
//                                    communication(id);
                                } else if (action.equalsIgnoreCase("AssignTask")) {
//                                    assignTask(sp.getString("id", ""), id);
                                } else {
                                    Intent intent = new Intent(TeachersListActivity.this, MyPerformanceActivity.class);
                                    intent.putExtra("child_id", id);
                                    startActivity(intent);
                                }
                            }
                        }));
                    } else
                        Toast.makeText(TeachersListActivity.this, "No Teacher Found", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(TeachersListActivity.this, "JSONException: " + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TeachersListActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                if (callFrom.equalsIgnoreCase("ParentHomeActivity.java")) {
                    map.put("model", "Parent");
//                    map.put("parent_id", Parent.getInstance().getId());
                    map.put("parent_id", sp.getString("id", ""));
                } else {
                    map.put("model", "Teacher");
//                    map.put("teacher_id", Teacher.getInstance().getId());
                    map.put("teacher_id", sp.getString("id", ""));
                }
                return map;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
}
