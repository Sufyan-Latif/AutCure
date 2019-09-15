package com.example.sufyanlatif.myapplication.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.adapters.ChildrenAdapter;
import com.example.sufyanlatif.myapplication.interfaces.OnChildClick;
import com.example.sufyanlatif.myapplication.models.Child;
import com.example.sufyanlatif.myapplication.models.Parent;
import com.example.sufyanlatif.myapplication.models.Teacher;
import com.example.sufyanlatif.myapplication.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChildrenListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String callFrom, action;
    ArrayList<Child> childArrayList;
    SharedPreferences sp;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children_list);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        sp = getSharedPreferences("myLoginData", 0);

        callFrom = getIntent().getStringExtra("callFrom");
        action = getIntent().getStringExtra("action");

        recyclerView = findViewById(R.id.childrenList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        childArrayList = new ArrayList<>();

        if (action.equalsIgnoreCase("AssociateChildren"))
            retreiveAllChildren();
        else
            retreiveAssocChildren();
    }

    private void retreiveAssocChildren() {

        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Constants.BASE_URL + "retreive_associations.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("code");
                    if (code.equals("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("children");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject objChild = jsonArray.getJSONObject(i);
                            Child child = new Child();
                            child.setId(objChild.getString("id"));
                            child.setFirstName(objChild.getString("first_name"));
                            child.setLastName(objChild.getString("last_name"));
                            childArrayList.add(child);
                        }
                        recyclerView.setAdapter(new ChildrenAdapter(ChildrenListActivity.this, childArrayList, new OnChildClick() {
                            @Override
                            public void onClick(String id) {
                                if (action.equalsIgnoreCase("Communication")) {
                                    communication(id);
                                } else if (action.equalsIgnoreCase("AssignTask")) {
                                    assignTask(sp.getString("id", ""), id);
                                } else {
                                    Intent intent = new Intent(ChildrenListActivity.this, MyPerformanceActivity.class);
                                    intent.putExtra("child_id", id);
                                    startActivity(intent);
                                }
                            }
                        }));
                    } else
                        Toast.makeText(ChildrenListActivity.this, "No Child Found", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(ChildrenListActivity.this, "JSONException: " + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ChildrenListActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
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

    private void assignTask(final String teacherId, final String childId) {
        progressDialog.show();
        String URL = Constants.BASE_URL + "assign_taskdetails.php";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("code");
                    if (code.equalsIgnoreCase("success")) {
                        JSONArray childDetail = jsonObject.getJSONArray("child_detail");
                        JSONObject childObj = childDetail.getJSONObject(0);
                        String firstName = childObj.getString("first_name");
                        String lastName = childObj.getString("last_name");
                        String address = childObj.getString("address");
                        String gender = childObj.getString("gender");
                        String dob = childObj.getString("dob");

                        JSONArray taskDetail = jsonObject.getJSONArray("task_detail");
                        JSONObject taskObj = taskDetail.getJSONObject(0);
                        String description = taskObj.getString("description");
                        String assignedDate = taskObj.getString("assigned_date");

                        Intent intent = new Intent(ChildrenListActivity.this, AssignTaskActivity.class);
                        intent.putExtra("child_id", childId);
                        intent.putExtra("first_name", firstName);
                        intent.putExtra("last_name", lastName);
                        intent.putExtra("address", address);
                        intent.putExtra("gender", gender);
                        intent.putExtra("dob", dob);
                        intent.putExtra("task_assigned", description);
                        intent.putExtra("assigned_date", assignedDate);
                        startActivity(intent);
                    } else
                        Toast.makeText(ChildrenListActivity.this, "Task not assigned", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(ChildrenListActivity.this, "Exception: " + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ChildrenListActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();

                map.put("child_id", childId);
                map.put("teacher_id", teacherId);

                return map;
            }
        };
        Volley.newRequestQueue(this).add(request);

    }

    private void retreiveAllChildren() {

        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Constants.BASE_URL + "associate_children.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("code");
                    if (code.equals("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("children");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject objChild = jsonArray.getJSONObject(i);
                            Child child = new Child();
                            child.setId(objChild.getString("id"));
                            child.setFirstName(objChild.getString("first_name"));
                            child.setLastName(objChild.getString("last_name"));
                            childArrayList.add(child);
                        }
                        recyclerView.setAdapter(new ChildrenAdapter(ChildrenListActivity.this, childArrayList, new OnChildClick() {
                            @Override
                            public void onClick(String id) {
                                Toast.makeText(ChildrenListActivity.this, "Child id = " + id, Toast.LENGTH_SHORT).show();
                                callFrom = getIntent().getStringExtra("callFrom");
                                action = getIntent().getStringExtra("action");
                                if (callFrom.equalsIgnoreCase("ParentHomeActivity.java") &&
                                        action.equalsIgnoreCase("AssociateChildren")) {
                                    associateChild(id);
                                } else {
                                    associateChild(id);
                                }
                            }
                        }));
                    } else
                        Toast.makeText(ChildrenListActivity.this, "No Child Found", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(ChildrenListActivity.this, "JSONException: " + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ChildrenListActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(request);

    }

    private void associateChild(final String id) {
        progressDialog.show();
        String URL = null;
        if (callFrom.equalsIgnoreCase("ParentHomeActivity.java"))
            URL = Constants.BASE_URL + "assoc_child_parent.php";
        else if (callFrom.equalsIgnoreCase("TeacherWelcomeActivity.java"))
            URL = Constants.BASE_URL + "assoc_child_teacher.php";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("code");
                    String message = jsonObject.getString("message");
                    Toast.makeText(ChildrenListActivity.this, "Child Added Successfully", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(ChildrenListActivity.this, "Exception: " + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();

                map.put("child_id", id);
                if (callFrom.equalsIgnoreCase("ParentHomeActivity.java"))
                    map.put("parent_id", sp.getString("id", ""));
                else if (callFrom.equalsIgnoreCase("TeacherWelcomeActivity.java"))
                    map.put("teacher_id", sp.getString("id", ""));

                return map;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void communication(final String id) {
        progressDialog.show();
        String URL = Constants.BASE_URL + "my_communication.php";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("code");
                    if (code.equalsIgnoreCase("success")) {
                        if (callFrom.equalsIgnoreCase("ParentHomeActivity.java")) {
                            JSONArray contactDetails = jsonObject.getJSONArray("contact_details");
                            if (contactDetails.length() == 0)
                                Toast.makeText(ChildrenListActivity.this, "Not associated", Toast.LENGTH_SHORT).show();
                            else if (contactDetails.length() == 1) {
                                Intent intent = new Intent(ChildrenListActivity.this, CommunicationDetailedActivity.class);
                                JSONObject teacherObj = contactDetails.getJSONObject(0);
                                Teacher teacher = new Teacher();
                                teacher.setFirstName(teacherObj.getString("first_name"));
                                teacher.setLastName(teacherObj.getString("last_name"));
                                teacher.setAddress(teacherObj.getString("address"));
                                teacher.setEmailAddress(teacherObj.getString("email"));
                                teacher.setPhoneNumber(teacherObj.getString("phone"));

                                intent.putExtra("model", "Parent");
                                intent.putExtra("teacher", teacher);
                                startActivity(intent);
                            } else if (contactDetails.length() > 1) {

                                ArrayList<Teacher> teacherArrayList = new ArrayList<>();
                                for (int i = 0; i < contactDetails.length(); i++) {
                                    JSONObject teacherObj = contactDetails.getJSONObject(i);

                                    Teacher teacher = new Teacher();
                                    teacher.setFirstName(teacherObj.getString("first_name"));
                                    teacher.setLastName(teacherObj.getString("last_name"));
                                    teacher.setAddress(teacherObj.getString("address"));
                                    teacher.setEmailAddress(teacherObj.getString("email"));
                                    teacher.setPhoneNumber(teacherObj.getString("phone"));

                                    teacherArrayList.add(teacher);
                                }
                            }
                        } else if (callFrom.equalsIgnoreCase("TeacherWelcomeActivity.java")) {
                            Intent intent = new Intent(ChildrenListActivity.this, CommunicationDetailedActivity.class);
                            JSONArray contactDetails = jsonObject.getJSONArray("contact_details");
                            JSONObject parentObj = contactDetails.getJSONObject(0);
                            Parent parent = new Parent();
                            parent.setFirstName(parentObj.getString("first_name"));
                            parent.setLastName(parentObj.getString("last_name"));
                            parent.setAddress(parentObj.getString("address"));
                            parent.setEmailAddress(parentObj.getString("email"));
                            parent.setPhoneNumber(parentObj.getString("phone"));

                            intent.putExtra("model", "Teacher");
                            intent.putExtra("parent", parent);
                            startActivity(intent);
                        }
                    } else {
//                        String message = jsonObject.getString("message");
                        Toast.makeText(ChildrenListActivity.this, "Not associated yet", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(ChildrenListActivity.this, "Exception: " + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();

                map.put("child_id", id);
                if (callFrom.equalsIgnoreCase("ParentHomeActivity.java"))
                    map.put("model", "Parent");
                else if (callFrom.equalsIgnoreCase("TeacherWelcomeActivity.java"))
                    map.put("model", "Teacher");

                return map;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
}
