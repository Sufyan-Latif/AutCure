package com.example.sufyanlatif.myapplication.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.models.Child;
import com.example.sufyanlatif.myapplication.models.Teacher;
import com.example.sufyanlatif.myapplication.utils.Constants;
import com.example.sufyanlatif.myapplication.utils.Utility;
import com.example.sufyanlatif.myapplication.webservices.UserRecord;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChildHomeActivity extends AppCompatActivity {

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    SharedPreferences spUpdateInfo;
    SharedPreferences.Editor editorUpdateInfo;
    ImageView imgLogout;
    CircleImageView imgProfile;
    TextView tvName;
    Child child;
    Button viewAnimations, playGames, viewTasks, btnUpdateProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_home);

        bindViews();

        tvName.setText(sp.getString("first_name", "") + " " + sp.getString("last_name", ""));

        String id = sp.getString("id", "");
        String RETRIEVE_URL = Constants.BASE_URL + "images/child" + id + ".jpeg";
        Glide.with(this).load(RETRIEVE_URL).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Toast.makeText(ChildHomeActivity.this, "Check your internet connection", Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.ic_user).into(imgProfile);

        viewTasks = findViewById(R.id.btnViewTasks);
        viewTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewTasks();
            }
        });
        playGames = findViewById(R.id.btnPlayGames);
        playGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChildHomeActivity.this, GamesListActivity.class);
                intent.putExtra("status", "registered");
                startActivity(intent);
            }
        });

//        sp = getSharedPreferences("myLoginData", 0);
//        editor = sp.edit();
//        editor.putString("type", "students");
//        editor.apply();

        viewAnimations = findViewById(R.id.viewAnimations);
        viewAnimations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(ChildHomeActivity.this);
                String users[]={"Wear Tie", "Wear Shoes","Wear Shirt","Wear Socks", "Turn Around"};
                builder.setTitle("Display Animations !")
                        .setItems(users, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ChildHomeActivity.this, MyAnimationsActivity.class);
                                switch (which){
                                    case 0:
                                        intent.putExtra("gif", "tie");
                                        break;
                                    case 1:
                                        intent.putExtra("gif", "shoes");
                                        break;
                                    case 2:
                                        intent.putExtra("gif", "shirt");
                                        break;
                                    case 3:
                                        intent.putExtra("gif", "socks");
                                        break;
                                    case 4:
                                        intent.putExtra("gif", "turn");
                                        break;
                                }
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel",null);
                AlertDialog dialog= builder.create();
                dialog.show();
            }
        });

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editorUpdateInfo.putString("type", "child");
                editorUpdateInfo.putString("id", sp.getString("id", ""));
                editorUpdateInfo.putString("first_name", sp.getString("first_name", ""));
                editorUpdateInfo.putString("last_name", sp.getString("last_name", ""));
                editorUpdateInfo.putString("username", sp.getString("username", ""));
                editorUpdateInfo.putString("password", sp.getString("password", ""));
                editorUpdateInfo.putString("address", sp.getString("address", ""));
                editorUpdateInfo.apply();
                Intent intent = new Intent(ChildHomeActivity.this, UpdateInfoActivity.class);
                startActivity(intent);
            }
        });

        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(ChildHomeActivity.this);
                View dialogView = LayoutInflater.from(ChildHomeActivity.this).inflate(R.layout.logout_dialog, null);

                Button btnYes = dialogView.findViewById(R.id.btnYes);
                Button btnNo = dialogView.findViewById(R.id.btnNo);

                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                    sp = getSharedPreferences("myLoginData", 0);
//                    editor = sp.edit();
                        dialog.dismiss();
                        editor.remove("type");
                        editor.apply();
                        Intent intent = new Intent(ChildHomeActivity.this, MyAuthenticationActivity.class);
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
            }
        });
    }

    private void viewTasks() {
        StringRequest request = new StringRequest(Request.Method.POST, Constants.BASE_URL + "my_assign_tasks.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("code");
                    if (code.equalsIgnoreCase("success")){
                        JSONArray teachersArray = jsonObject.getJSONArray("teachers");
                        JSONArray tasksArray = jsonObject.getJSONArray("tasks");

                        if (tasksArray.length() > 1){
                            
                        }
                        else if (tasksArray.length() == 1){

                            JSONObject task = tasksArray.getJSONObject(0);
                            String description = task.getString("description");

                            if (description.equalsIgnoreCase(getString(R.string.put_the_balls_in_the_basket))){
                                Intent intent = new Intent(ChildHomeActivity.this, GameDragActivity.class);
                                intent.putExtra("status", "task");
                                startActivity(intent);
                            }
                            else if (description.equalsIgnoreCase(getString(R.string.put_the_shapes_at_correct_position))){
                                Intent intent = new Intent(ChildHomeActivity.this, GameMatchShapeActivity.class);
                                intent.putExtra("status", "task");
                                startActivity(intent);
                            }
                            else if (description.equalsIgnoreCase(getString(R.string.choose_the_animal))){
                                Intent intent = new Intent(ChildHomeActivity.this, AnimalGameActivity.class);
                                intent.putExtra("game", "task");
                                startActivity(intent);
                            }
                            else if (description.equalsIgnoreCase(getString(R.string.choose_the_fruit))){
                                Intent intent = new Intent(ChildHomeActivity.this, AnimalGameActivity.class);
                                intent.putExtra("game", "task");
                                startActivity(intent);
                            }
                            else if (description.equalsIgnoreCase(getString(R.string.choose_the_vegetable))){
                                Intent intent = new Intent(ChildHomeActivity.this, AnimalGameActivity.class);
                                intent.putExtra("game", "task");
                                startActivity(intent);
                            }

                        }





                        if (teachersArray.length()>1){

                            Intent intent = new Intent(ChildHomeActivity.this, TeachersListActivity.class);
                            intent.putExtra("response", response);
                            startActivity(intent);
/*
                            ArrayList<Teacher> teacherArrayList = new ArrayList<>();
                            String[] descriptionsArray = new String[teachersArray.length()-1];
                            for (int i=0; i<teachersArray.length(); i++){
                                JSONObject teacherObj = teachersArray.getJSONObject(i);
                                Teacher teacher = new Teacher();
                                teacher.setId(teacherObj.getString("id"));
                                teacher.setFirstName(teacherObj.getString("first_name"));
                                teacher.setLastName(teacherObj.getString("last_name"));
                                teacherArrayList.add(teacher);

                                JSONObject task = tasksArray.getJSONObject(i);
//                                String description = task.getString("description");
//                                descriptionsArray[i] = description;
                            }

*/
                        }else if (teachersArray.length() == 1){
                            JSONObject task = tasksArray.getJSONObject(0);
                            String description = task.getString("description");
                            String assignedDate = task.getString("assigned_date");

                            if (description.equalsIgnoreCase(getString(R.string.put_the_balls_in_the_basket))){
                                Intent intent = new Intent(ChildHomeActivity.this, GameDragActivity.class);
                                intent.putExtra("status", "task");
                                startActivity(intent);
                            }
                            else if (description.equalsIgnoreCase(getString(R.string.put_the_shapes_at_correct_position))){
                                Intent intent = new Intent(ChildHomeActivity.this, GameMatchShapeActivity.class);
                                intent.putExtra("status", "task");
                                startActivity(intent);
                            }
                            else if (description.equalsIgnoreCase(getString(R.string.choose_the_animal))){
                                Intent intent = new Intent(ChildHomeActivity.this, AnimalGameActivity.class);
                                intent.putExtra("game", "task");
                                startActivity(intent);
                            }
                            else if (description.equalsIgnoreCase(getString(R.string.choose_the_fruit))){
                                Intent intent = new Intent(ChildHomeActivity.this, AnimalGameActivity.class);
                                intent.putExtra("game", "task");
                                startActivity(intent);
                            }
                            else if (description.equalsIgnoreCase(getString(R.string.choose_the_vegetable))){
                                Intent intent = new Intent(ChildHomeActivity.this, AnimalGameActivity.class);
                                intent.putExtra("game", "task");
                                startActivity(intent);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChildHomeActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("child_id", sp.getString("id", ""));
                return map;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void bindViews() {
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
        imgProfile = findViewById(R.id.profile_image);
        imgLogout = findViewById(R.id.imgLogout);
        tvName = findViewById(R.id.tvName);

        sp = getSharedPreferences("myLoginData", 0);
        editor = sp.edit();
        spUpdateInfo = getSharedPreferences("updateInfo", 0);
        editorUpdateInfo = spUpdateInfo.edit();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menu1 = getMenuInflater();
//        menu1.inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.logout) {
//
//            final Dialog dialog = new Dialog(ChildHomeActivity.this);
//            View dialogView = LayoutInflater.from(this).inflate(R.layout.logout_dialog, null);
//
//            Button btnYes = dialogView.findViewById(R.id.btnYes);
//            Button btnNo = dialogView.findViewById(R.id.btnNo);
//
//            btnYes.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    sp = getSharedPreferences("myLoginData", 0);
////                    editor = sp.edit();
//                    dialog.dismiss();
//                    editor.remove("type");
//                    editor.apply();
//                    Intent intent = new Intent(ChildHomeActivity.this, MyAuthenticationActivity.class);
//                    finish();
//                    startActivity(intent);
//                }
//            });
//            btnNo.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    dialog.dismiss();
//                }
//            });
//
//            dialog.setContentView(dialogView);
//            dialog.show();
//            /*
//            builder.setTitle("Alert!!!")
//                    .setMessage("Are you sure you want to logout?")
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            sp = getSharedPreferences("myLoginData", 0);
//                            editor = sp.edit();
//                            editor.remove("type");
//                            editor.apply();
//                            Intent intent = new Intent(ChildHomeActivity.this, AuthenticationActivity.class);
//                            finish();
//                            startActivity(intent);
//                        }
//                    })
//                    .setNegativeButton("No", null)
//                    .show();*/
//        }
//        return true;
//    }

//    public boolean isInternetConnected(){
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
//                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
//            return true;
//        } else
//            return false;
//    }
}