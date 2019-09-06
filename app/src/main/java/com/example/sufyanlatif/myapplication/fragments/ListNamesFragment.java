package com.example.sufyanlatif.myapplication.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.sufyanlatif.myapplication.activities.ListNamesActivity;
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

public class ListNamesFragment extends Fragment {

    View myView;
    TextView tvList;
    ListView namesListView;

    public ListNamesFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_list_names, container, false);

        tvList = myView.findViewById(R.id.tvPerformance);
        namesListView = myView.findViewById(R.id.names);

        String callFrom = getActivity().getIntent().getStringExtra("callfrom");

        Gson gson = new Gson();
        String json = getActivity().getIntent().getStringExtra("map");
        Type type = new TypeToken<LinkedHashMap<String, ArrayList<String>>>() {
        }.getType();
        LinkedHashMap<String, ArrayList<String>> recordMap = gson.fromJson(json, type);

        final ArrayList<String> ids = recordMap.get("id");
        ArrayList<String> firstName = recordMap.get("first_name");
        ArrayList<String> lastName = recordMap.get("last_name");

        ArrayList<String> completeName = new ArrayList<String>();

        if (callFrom.equalsIgnoreCase("TeacherHomeActivity")) {
            tvList.setText("Assign Task to !!!");

            for (int i = 0; i < ids.size(); i++) {
                completeName.add(firstName.get(i) + " " + lastName.get(i));
            }


            namesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    if (Utility.isInternetConnected(myView.getContext())) {
                        StringRequest request = new StringRequest(Request.Method.POST,
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
                        RequestQueue queue = Volley.newRequestQueue(myView.getContext());
                        queue.add(request);
                    } else
                        Toast.makeText(myView.getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();

                }
            });

        } else if (callFrom.equals("Communication.java teacher")) {
            tvList.setText("Select a parent to communicate from the list !!!");
            for (int i = 0; i < ids.size(); i++) {
                completeName.add(firstName.get(i) + " " + lastName.get(i) + "'s Parents");
            }

            if (Utility.isInternetConnected(myView.getContext())) {
                namesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Communication communication = new Communication(myView.getContext());
                        communication.execute("listnames", ids.get(position), "parents");
                    }
                });
            } else
                Toast.makeText(myView.getContext(), "No internet connection", Toast.LENGTH_SHORT).show();
        } else if (callFrom.equals("Communication.java parent")) {
            tvList.setText("Select a teacher to communicate from the list !!!");
            for (int i = 0; i < ids.size(); i++) {
                completeName.add(firstName.get(i) + " " + lastName.get(i) + "'s Teachers");
            }

            if (Utility.isInternetConnected(myView.getContext())) {
                namesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Communication communication = new Communication(myView.getContext());
                        communication.execute("listnames", ids.get(position), "teachers");
                    }
                });
            } else
                Toast.makeText(myView.getContext(), "No internet connection", Toast.LENGTH_SHORT).show();

        } else {
            tvList.setText("Select a child from the list !!!");
            for (int i = 0; i < ids.size(); i++) {
                completeName.add(firstName.get(i) + " " + lastName.get(i));
            }

            if (Utility.isInternetConnected(myView.getContext())) {
                namesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Performance performance = new Performance(myView.getContext());
                        performance.execute("children", ids.get(position));
                    }
                });
            } else
                Toast.makeText(myView.getContext(), "No internet connection", Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(myView.getContext(), R.layout.listview_text, completeName);
        namesListView.setAdapter(adapter);

        return myView;
    }
}
