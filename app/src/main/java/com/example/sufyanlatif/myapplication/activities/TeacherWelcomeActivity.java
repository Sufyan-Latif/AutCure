package com.example.sufyanlatif.myapplication.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.fragments.NavHomeFragment;
import com.example.sufyanlatif.myapplication.utils.Constants;

public class TeacherWelcomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    SharedPreferences spUpdateInfo;
    SharedPreferences.Editor editorUpdateInfo;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_welcome);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(R.string.app_name);
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
        id = sp.getString("id", "");

        spUpdateInfo = getSharedPreferences("updateInfo", 0);
        editorUpdateInfo = spUpdateInfo.edit();

        String username = sp.getString("username", "abc");
        String firstName = sp.getString("first_name", "");
        String lastName = sp.getString("last_name", "");

        setNavView(firstName + " " + lastName, username);
    }

    public void setNavView(String name, String username) {

//                Picasso.get().load(RETRIEVE_URL).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE)
//                        .placeholder(R.drawable.ic_user).into(imgProfile);

        String RETRIEVE_URL = Constants.BASE_URL + "images/teacher"+ id +".jpeg";
        ImageView imgProfile = navigationView.getHeaderView(0).findViewById(R.id.imgProfile);

        Glide.with(this).load(RETRIEVE_URL).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.ic_user).into(imgProfile);


        TextView tvName = navigationView.getHeaderView(0).findViewById(R.id.tvFirstName);
        TextView tvUsername = navigationView.getHeaderView(0).findViewById(R.id.tvUsername);

        tvName.setText(name);
        tvUsername.setText(username);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.teacher_welcome, menu);
//        return true;
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.teacherHomeContainer, new NavHomeFragment());
                transaction.commit();
                break;

            case R.id.nav_view_performance:
                Intent intent = new Intent(TeacherWelcomeActivity.this, ChildrenListActivity.class);
                intent.putExtra("callFrom", "TeacherWelcomeActivity.java");
                intent.putExtra("action", "ViewPerformance");
                startActivity(intent);
                /*
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
                    Toast.makeText(TeacherWelcomeActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();*/
                break;
            case R.id.nav_communication:
                intent = new Intent(TeacherWelcomeActivity.this, ChildrenListActivity.class);
                intent.putExtra("callFrom", "TeacherWelcomeActivity.java");
                intent.putExtra("action", "Communication");
                startActivity(intent);
/*
                Communication communication = new Communication(TeacherWelcomeActivity.this);
                communication.execute("TeacherHomeActivity", teacher.getId(), "children");*/
                break;

            case R.id.nav_assign_tasks:

                intent = new Intent(TeacherWelcomeActivity.this, ChildrenListActivity.class);
                intent.putExtra("callFrom", "TeacherWelcomeActivity.java");
                intent.putExtra("action", "AssignTask");
                startActivity(intent);
                /*
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
                        map.put("teacher_id", id);
                        map.put("activity", "TeacherHomeActivity");
                        return map;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(TeacherWelcomeActivity.this);
                queue.add(request);*/
                break;
            case R.id.nav_update_profile:
                editorUpdateInfo.putString("type", "teacher");
                editorUpdateInfo.putString("id", sp.getString("id", ""));
                editorUpdateInfo.putString("first_name", sp.getString("first_name", ""));
                editorUpdateInfo.putString("last_name", sp.getString("last_name", ""));
                editorUpdateInfo.putString("username", sp.getString("username", ""));
                editorUpdateInfo.putString("password", sp.getString("password", ""));
                editorUpdateInfo.putString("address", sp.getString("address", ""));
                editorUpdateInfo.putString("phone", sp.getString("phone", ""));
                editorUpdateInfo.putString("email", sp.getString("email", ""));
                editorUpdateInfo.apply();
                intent = new Intent(TeacherWelcomeActivity.this, UpdateInfoActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_associate_children:
                intent = new Intent(TeacherWelcomeActivity.this, ChildrenListActivity.class);
                intent.putExtra("callFrom", "TeacherWelcomeActivity.java");
                intent.putExtra("action", "AssociateChildren");
                startActivity(intent);
                break;

            case R.id.nav_logout:
                final Dialog dialog = new Dialog(TeacherWelcomeActivity.this);
                View dialogView = LayoutInflater.from(this).inflate(R.layout.logout_dialog, null);

                Button btnYes = dialogView.findViewById(R.id.btnYes);
                Button btnNo = dialogView.findViewById(R.id.btnNo);

                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        sp = getSharedPreferences("myLoginData", 0);
//                        editor = sp.edit();
                        editor.remove("type");
                        editor.apply();
                        Intent intent = new Intent(TeacherWelcomeActivity.this, MyAuthenticationActivity.class);
                        finish();
                        startActivity(intent);
                    }
                });
                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.setContentView(dialogView);
                dialog.show();
            /*    AlertDialog.Builder builder = new AlertDialog.Builder(TeacherWelcomeActivity.this);
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
                        .show();*/
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
}
