package com.example.sufyanlatif.myapplication.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Telephony;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.example.sufyanlatif.myapplication.webservices.Communication;
import com.example.sufyanlatif.myapplication.webservices.Performance;
import com.example.sufyanlatif.myapplication.webservices.UserRecord;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TeacherHomeActivity extends AppCompatActivity {

    int REQUEST_PHONE_CALL=1998;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    Button communication, viewPerformance,assignTasks, updateInfo;
    Teacher teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);

        sp = getSharedPreferences("myLoginData", 0);
        editor = sp.edit();
        editor.putString("type", "teacher");
        editor.apply();

        String username = sp.getString("username", "abc");
        String password = sp.getString("password", "xyz");

        Log.d("TeacherHomeActivity", "username = "+username + " Password = "+password);

        teacher = Teacher.getInstance();
        if (teacher.getUsername()==null)
        {
            if (isInternetConnected()){
                UserRecord userRecord = new UserRecord(TeacherHomeActivity.this);
                userRecord.execute("getRecord","teacher", username, password);
            }
            else
                Toast.makeText(TeacherHomeActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
        }

        viewPerformance= findViewById(R.id.btnTeacherViewPerformance);
        viewPerformance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetConnected()){
                    if (teacher.getAssociated().equals("yes")){
                        Performance performance= new Performance(TeacherHomeActivity.this);
                        Log.d("xyz", teacher.getId());
                        performance.execute("teachers", teacher.getId());
                    }
                    else
                        Toast.makeText(TeacherHomeActivity.this, "No child associated", Toast.LENGTH_SHORT).show();
//                    RetrievePerformance retrievePerformance= new RetrievePerformance(ParentHomeActivity.this);
//                    retrievePerformance.execute("retrieve_performance", parent.getUsername());

                }
                else
                    Toast.makeText(TeacherHomeActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
//                Intent intent= new Intent(TeacherHomeActivity.this, PerformanceFinalActivity.class);
//                startActivity(intent);
            }
        });
        communication = findViewById(R.id.btnTeacherCommunication);
        communication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Communication communication= new Communication(TeacherHomeActivity.this);
                communication.execute("TeacherHomeActivity", teacher.getId(), "children");
            }
        });

        assignTasks = findViewById(R.id.btn_assign_tasks);
        assignTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest request = new StringRequest(Request.Method.POST, Constants.BASE_URL + "assign_tasks.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("VolleyError", ""+response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equalsIgnoreCase("success")){
                                JSONArray jsonArray = jsonObject.getJSONArray("children");
//                                for (int i=0; i<jsonArray.length(); i++){
//                                    JSONObject childObject = jsonArray.getJSONObject(i);




                                    HashMap<String, ArrayList<String>> taskMap= new LinkedHashMap<>(jsonArray.length());
                                    Intent intent = new Intent(TeacherHomeActivity.this, ListNamesActivity.class);

                                    ArrayList<String> ids= new ArrayList<>();
                                    ArrayList<String> firstNames= new ArrayList<>();
                                    ArrayList<String> lastNames= new ArrayList<>();

                                    for (int i=0; i<jsonArray.length(); i++){
                                        JSONObject jo= jsonArray.getJSONObject(i);
                                        ids.add(jo.getString("id"));
                                        firstNames.add(jo.getString("first_name"));
                                        lastNames.add(jo.getString("last_name"));
//                                        if(i==jsonArray.length()-1){
//                                            recordMap.put("id", ids);
//                                            recordMap.put("first_name", firstNames);
//                                            recordMap.put("last_name", lastNames);
//                                        }
                                    }
                                taskMap.put("id", ids);
                                taskMap.put("first_name", firstNames);
                                taskMap.put("last_name", lastNames);

                                    Gson gson = new Gson();
                                    String json= gson.toJson(taskMap);
                                    intent.putExtra("callfrom", "TeacherHomeActivity");
                                    intent.putExtra("map", json);
                                    startActivity(intent);

//                                }
                            }

                        } catch (JSONException e) {
                            Log.e("VolleyError", ""+e);
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", ""+error);

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("teacher_id", teacher.getId());
                        map.put("activity", "TeacherHomeActivity");
                        return map;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(TeacherHomeActivity.this);
                queue.add(request);
            }
        });
        /*
        communication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Communication communication= new Communication(TeacherHomeActivity.this);
                communication.execute("TeacherHomeActivity", teacher.getId(), "children");
//                Intent intent= new Intent(TeacherHomeActivity.this, TeacherCommunicationActivity.class);
//                startActivity(intent);
            }
        });

        assignTasks = findViewById(R.id.btn_assign_tasks);
        assignTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest request = new StringRequest(Request.Method.POST, Constants.BASE_URL + "assign_tasks.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String code = jsonObject.getString("code");

                                    if (code.equals("success")){
                                        ArrayList<String> ids = new ArrayList<>();
                                        ArrayList<String> names = new ArrayList<>();
                                        JSONArray jsonArray = jsonObject.getJSONArray("children");

                                        for(int j = 0; j<jsonArray.length(); j++) {
                                            JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                                            ids.add(jsonObject1.getString("id"));
                                            names.add(jsonObject1.getString("first_name")
                                                    + jsonObject1.getString("last_name"));
                                        }
                                    }
                                    else {
                                        Toast.makeText(TeacherHomeActivity.this,
                                                ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("assignTasksError", ""+error);
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap= new HashMap<String, String>();
                        hashMap.put("teacher_id", teacher.getId());
                        hashMap.put("activity", "TeacherHomeActivity");
                        return hashMap;
                    }
                };
                Volley.newRequestQueue(TeacherHomeActivity.this).add(request);

            }
        });

        */

        updateInfo = findViewById(R.id.btn_update_info);
        updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeacherHomeActivity.this, UpdateInfoActivity.class);
                startActivity(intent);
            }
        });
//        communication.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("MissingPermission")
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_CALL);
//                intent.setData(Uri.parse("tel:03364087008"));

//                sendSMS();
//                if (ActivityCompat.checkSelfPermission(TeacherHomeActivity.this,
//                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//                startActivity(intent);

//                if (ContextCompat.checkSelfPermission(TeacherHomeActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(TeacherHomeActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
//                }
//                else
//                {
//                    startActivity(intent);
//                }

//                try {
//                    startActivity(intent);
//                } catch (android.content.ActivityNotFoundException ex) {
//                    Toast.makeText(TeacherHomeActivity.this, "Could not find an activity to place the call.", Toast.LENGTH_SHORT).show();
//                }
//                if (ActivityCompat.checkSelfPermission(TeacherHomeActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menu1= getMenuInflater();
        menu1.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.logout)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(TeacherHomeActivity.this);
            builder.setTitle("Alert!!!")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sp= getSharedPreferences("myLoginData", 0);
                            editor = sp.edit();
                            editor.remove("type");
                            editor.apply();
                            teacher.remove();
                            Intent intent = new Intent(TeacherHomeActivity.this, MyAuthenticationActivity.class);
                            finish();
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        return true;
    }

    private void sendSMS() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) // At least KitKat
        {
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this); // Need to change the build to API 19

//            Intent sendIntent = new Intent(Intent.ACTION_SEND);
//            sendIntent.setData(Uri.parse("03364087008"));
//            sendIntent.setType("text/plain");
//            sendIntent.putExtra(Intent.EXTRA_TEXT, "text");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setType("vnd.android-dir/mms-sms");
            intent.putExtra("address","03364087008");
            intent.putExtra("sms_body","message");

            if (defaultSmsPackageName != null)// Can be null in case that there is no default, then the user would be able to choose
            // any app that support this intent.
            {
                intent.setPackage(defaultSmsPackageName);
            }
            startActivity(intent);

        }
        else // For early versions, do what worked for you before.
        {
            Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address","phoneNumber");
            smsIntent.putExtra("sms_body","message");
            startActivity(smsIntent);
        }
    }

    public boolean isInternetConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else
            return false;
    }
}
