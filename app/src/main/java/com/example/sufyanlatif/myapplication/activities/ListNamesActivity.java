package com.example.sufyanlatif.myapplication.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.models.Teacher;
import com.example.sufyanlatif.myapplication.utils.Constants;
import com.example.sufyanlatif.myapplication.utils.Utility;
import com.example.sufyanlatif.myapplication.webservices.Communication;
import com.example.sufyanlatif.myapplication.webservices.Performance;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ListNamesActivity extends AppCompatActivity {

    TextView tvList;
    ListView namesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_names);

        tvList = findViewById(R.id.tvPerformance);
        namesListView = findViewById(R.id.names);

//        Intent communicationIntent = getIntent();
//        String communication = communicationIntent.getStringExtra("communication");
//        if (communication.equals("communication")) {
//
//        }

        Intent intent = getIntent();
        String callFrom = intent.getStringExtra("callfrom");
//        String message = intent.getStringExtra("message");

        Gson gson = new Gson();
        String json = intent.getStringExtra("map");
        Log.d("PerformanceAct.java", json);
        Type type = new TypeToken<LinkedHashMap<String, ArrayList<String>>>() {
        }.getType();
        LinkedHashMap<String, ArrayList<String>> recordMap = gson.fromJson(json, type);

        final ArrayList<String> ids = recordMap.get("id");
        ArrayList<String> firstName = recordMap.get("first_name");
        ArrayList<String> lastName = recordMap.get("last_name");

        ArrayList<String> completeName = new ArrayList<String>();
//        for (int i = 0; i < ids.size(); i++) {
//            completeName.add(firstName.get(i) + " " + lastName.get(i) + "'s Parents");
//        }
/*
        switch (callFrom) {
            case "ParentHomeActivity":
                tvList.setText("Assign Task to !!!");

            case "Communication.java teacher":
                if (isInternetConnected()) {
                    namesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Communication communication = new Communication(ListNamesActivity.this);
                            communication.execute("listnames", ids.get(position), "parents");
                        }
                    });
                } else
                    Toast.makeText(ListNamesActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
        */

        if (callFrom.equalsIgnoreCase("TeacherHomeActivity")) {
            tvList.setText("Assign Task to !!!");

            for (int i = 0; i < ids.size(); i++) {
                completeName.add(firstName.get(i) + " " + lastName.get(i));
            }


            namesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    if (Utility.isInternetConnected(ListNamesActivity.this)) {

                        String games[] = {"Put the balls in Basket", "Match the shapes", "Choose the Animal", "Choose the fruit", "Choose the vegetable"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(ListNamesActivity.this)
                                .setTitle("Select the game !")
                                .setItems(games, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(ListNamesActivity.this, "Task Assigned Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("Cancel", null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        /*StringRequest request = new StringRequest(Request.Method.POST,
                                Constants.BASE_URL + "assign_tasks.php", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("newResponse", "" + response);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<>();
                                map.put("teacher_id", Teacher.getInstance().getId());
                                map.put("activity", "ListNamesActivity");
                                map.put("child_id", ids.get(position));
                                map.put("description", "Play more games");
                                return map;
                            }
                        };
                        RequestQueue queue = Volley.newRequestQueue(ListNamesActivity.this);
                        queue.add(request);*/
                    } else
                        Toast.makeText(ListNamesActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

                }
            });

        } else if (callFrom.equals("Communication.java teacher")) {
            tvList.setText("Select a parent to communicate from the list !!!");
            for (int i = 0; i < ids.size(); i++) {
                completeName.add(firstName.get(i) + " " + lastName.get(i) + "'s Parents");
            }

            if (isInternetConnected()) {
                namesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Communication communication = new Communication(ListNamesActivity.this);
                        communication.execute("listnames", ids.get(position), "parents");
                    }
                });
            } else
                Toast.makeText(ListNamesActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
        } else if (callFrom.equals("Communication.java parent")) {
            tvList.setText("Select a teacher to communicate from the list !!!");
            for (int i = 0; i < ids.size(); i++) {
                completeName.add(firstName.get(i) + " " + lastName.get(i) + "'s Teachers");
            }

            if (isInternetConnected()) {
                namesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Communication communication = new Communication(ListNamesActivity.this);
                        communication.execute("listnames", ids.get(position), "teachers");
                    }
                });
            } else
                Toast.makeText(ListNamesActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();

        } else {
            tvList.setText("Select a child from the list !!!");
            for (int i = 0; i < ids.size(); i++) {
                completeName.add(firstName.get(i) + " " + lastName.get(i));
            }

            if (isInternetConnected()) {
                namesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Performance performance = new Performance(ListNamesActivity.this);
                        performance.execute("children", ids.get(position));
                    }
                });
            } else
                Toast.makeText(ListNamesActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(ListNamesActivity.this, R.layout.listview_text, completeName);
        namesListView.setAdapter(adapter);


//        HashMap<String, String> hashMap = (HashMap<String, String>) intent.getSerializableExtra("map");
//        if (message.equals("MultipleChildren")) {
//            HashMap<String, ArrayList<String>> hashMapMultiple = (HashMap<String, ArrayList<String>>) intent.getSerializableExtra("map");
//            final ArrayList<String> ids = hashMapMultiple.get("id");
//            ArrayList<String> firstName = hashMapMultiple.get("first_name");
//            ArrayList<String> lastName = hashMapMultiple.get("last_name");
//
//            ArrayList<String> completeName = new ArrayList<String>();
//            for (int i = 0; i < firstName.size(); i++) {
//                completeName.add(firstName.get(i) + " " + lastName.get(i));
//            }
//
//            ArrayAdapter<String> adapter = new ArrayAdapter<>(ListNamesActivity.this, R.layout.listview_text, completeName);
//            names.setAdapter(adapter);
//
//            names.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    if (isInternetConnected()) {
//                        RetrievePerformanceFinal retrievePerformance = new RetrievePerformanceFinal(ListNamesActivity.this);
//                        retrievePerformance.execute("retrieve_performance_final", ids.get(position));
//                    } else
//                        Toast.makeText(ListNamesActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
//
//                }
//            });
//            tvPerformance.setText("Select a child from the list !!!");
//
//
////            tvPerformance.setText("ArrayList : "+hashMapMultiple);
////            tvPerformance.setText("Name : "+ firstName+" "+ lastName);
//        } else if (message.equals("SingleData")) {
//            String correct = hashMap.get("correct");
//            String incorrect = hashMap.get("incorrect");
//
//            tvPerformance.setText("Correct : " + correct + "Incorrect: " + incorrect);
//
//        }
    }

    public boolean isInternetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else
            return false;
    }
}
