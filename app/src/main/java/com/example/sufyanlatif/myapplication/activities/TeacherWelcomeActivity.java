package com.example.sufyanlatif.myapplication.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.fragments.NavHomeFragment;
import com.example.sufyanlatif.myapplication.models.Teacher;
import com.example.sufyanlatif.myapplication.utils.Constants;
import com.example.sufyanlatif.myapplication.utils.Utility;
import com.example.sufyanlatif.myapplication.webservices.Communication;
import com.example.sufyanlatif.myapplication.webservices.Performance;
import com.example.sufyanlatif.myapplication.webservices.UserRecord;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TeacherWelcomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

//    private AppBarConfiguration mAppBarConfiguration;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Teacher teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_welcome);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(R.string.app_name);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.teacherHomeContainer, new NavHomeFragment());
        transaction.commit();

        sp = getSharedPreferences("myLoginData", 0);
        editor = sp.edit();
        editor.putString("type", "teacher");
        editor.apply();

        String username = sp.getString("username", "abc");
        String password = sp.getString("password", "xyz");

        Log.d("TeacherHomeActivity", "username = " + username + " Password = " + password);

        teacher = Teacher.getInstance();
        if (teacher.getUsername() == null) {
            if (Utility.isInternetConnected(this)) {
                UserRecord userRecord = new UserRecord(TeacherWelcomeActivity.this);
                userRecord.execute("getRecord", "teacher", username, password);

                ImageView imgProfile = navigationView.getHeaderView(0).findViewById(R.id.imgProfile);

                String RETRIEVE_URL = Constants.BASE_URL + "images/teacher1.jpeg";
//                Picasso.get().load(RETRIEVE_URL).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE)
//                        .placeholder(R.drawable.ic_user).into(imgProfile);

                Glide.with(this).load(RETRIEVE_URL).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                        .placeholder(R.drawable.ic_user).into(imgProfile);
            } else
                Toast.makeText(TeacherWelcomeActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
//                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
//                .setDrawerLayout(drawer)
//                .build();

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public void setnavView() {
        TextView name = navigationView.getHeaderView(0).findViewById(R.id.tvFirstName);
        TextView username = navigationView.getHeaderView(0).findViewById(R.id.tvUsername);

        name.setText(teacher.getFirstName() + " " + teacher.getLastName());
        username.setText(teacher.getUsername());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.teacher_welcome, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.teacherHomeContainer, new NavHomeFragment());
                transaction.commit();
                break;

            case R.id.nav_view_performance:
                if (Utility.isInternetConnected(this)) {
                    if (teacher.getAssociated().equals("yes")) {
                        Performance performance = new Performance(TeacherWelcomeActivity.this);
                        Log.d("xyz", teacher.getId());
                        performance.execute("teachers", teacher.getId());
                    } else
                        Toast.makeText(TeacherWelcomeActivity.this, "No child associated", Toast.LENGTH_SHORT).show();
//                    RetrievePerformance retrievePerformance= new RetrievePerformance(ParentHomeActivity.this);
//                    retrievePerformance.execute("retrieve_performance", parent.getUsername());
                } else
                    Toast.makeText(TeacherWelcomeActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_communication:

                Communication communication = new Communication(TeacherWelcomeActivity.this);
                communication.execute("TeacherHomeActivity", teacher.getId(), "children");
                break;

            case R.id.nav_assign_tasks:
                StringRequest request = new StringRequest(Request.Method.POST, Constants.BASE_URL + "assign_tasks.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("VolleyError", "" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equalsIgnoreCase("success")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("children");
//                                for (int i=0; i<jsonArray.length(); i++){
//                                    JSONObject childObject = jsonArray.getJSONObject(i);

                                HashMap<String, ArrayList<String>> taskMap = new LinkedHashMap<>(jsonArray.length());
                                Intent intent = new Intent(TeacherWelcomeActivity.this, ListNamesActivity.class);

                                ArrayList<String> ids = new ArrayList<>();
                                ArrayList<String> firstNames = new ArrayList<>();
                                ArrayList<String> lastNames = new ArrayList<>();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jo = jsonArray.getJSONObject(i);
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
                                String json = gson.toJson(taskMap);
                                intent.putExtra("callfrom", "TeacherHomeActivity");
                                intent.putExtra("map", json);
                                startActivity(intent);

//                                }
                            }

                        } catch (JSONException e) {
                            Log.e("VolleyError", "" + e);
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", "" + error);

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("teacher_id", teacher.getId());
                        map.put("activity", "TeacherHomeActivity");
                        return map;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(TeacherWelcomeActivity.this);
                queue.add(request);
                break;
            case R.id.nav_update_profile:
                Intent intent = new Intent(TeacherWelcomeActivity.this, UpdateInfoActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(TeacherWelcomeActivity.this);
                builder.setTitle("Alert!!!")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sp = getSharedPreferences("myLoginData", 0);
                                editor = sp.edit();
                                editor.remove("type");
                                editor.apply();
                                teacher.remove();
                                Intent intent = new Intent(TeacherWelcomeActivity.this, AuthenticationActivity.class);
                                finish();
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    //    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}
