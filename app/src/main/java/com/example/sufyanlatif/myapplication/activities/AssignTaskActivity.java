package com.example.sufyanlatif.myapplication.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
import com.example.sufyanlatif.myapplication.utils.Constants;
import com.example.sufyanlatif.myapplication.utils.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AssignTaskActivity extends AppCompatActivity {

    TextView tvFirstName, tvLastName, tvAddress, tvGender,tvDOB, tvTaskAssigned, tvAssignedDate;
    Button btnAssignTask, btnRemoveTask;
    CircleImageView imgProfile;
    SharedPreferences sp;
    String assignedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_task);

        bindViews();

        final String childId = getIntent().getStringExtra("child_id");
        String RETRIEVE_URL = Constants.BASE_URL + "images/child"+ childId +".jpeg";

        Glide.with(this).load(RETRIEVE_URL).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Toast.makeText(AssignTaskActivity.this, "Error Loading image", Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.ic_user).into(imgProfile);

        tvFirstName.setText(getIntent().getStringExtra("first_name"));
        tvLastName.setText(getIntent().getStringExtra("last_name"));
        tvAddress.setText(getIntent().getStringExtra("address"));
        tvGender.setText(getIntent().getStringExtra("gender"));
        tvDOB.setText(getIntent().getStringExtra("dob"));
        tvTaskAssigned.setText(getIntent().getStringExtra("task_assigned"));
        tvAssignedDate.setText(getIntent().getStringExtra("assigned_date"));

        btnAssignTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String games[] = {getResources().getString(R.string.put_the_balls_in_the_basket),
                        getResources().getString(R.string.put_the_shapes_at_correct_position),
                        "Choose the Animal", "Choose the fruit", "Choose the vegetable"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AssignTaskActivity.this)
                        .setTitle("Select the game")
                        .setItems(games, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                assignTask(childId, games[which]);
                                assignedDate = Utility.getCurrentDateTime();
//                                Log.d("MYDATE", ""+mydate);
//                                Date newDate = Utility.parseStringToDate(mydate);
//                                Log.d("MYDATE", ""+newDate);
                            }
                        })
                        .setNegativeButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btnRemoveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(AssignTaskActivity.this);
                View dialogView = LayoutInflater.from(AssignTaskActivity.this).inflate(R.layout.logout_dialog, null);

                TextView tvAlertSubtitle = dialogView.findViewById(R.id.tvAlertSubtitle);
                tvAlertSubtitle.setText("Are you sure you want to delete task?");
                Button btnYes = dialogView.findViewById(R.id.btnYes);
                Button btnNo = dialogView.findViewById(R.id.btnNo);

                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        removeTask(childId);
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

    private void bindViews() {
        imgProfile = findViewById(R.id.imgProfile);
        tvFirstName = findViewById(R.id.tvFirstName);
        tvLastName = findViewById(R.id.tvLastName);
        tvAddress = findViewById(R.id.tvAssignAddress);
        tvGender = findViewById(R.id.tvGender);
        tvDOB = findViewById(R.id.tvDOB);
        tvTaskAssigned = findViewById(R.id.tvTaskAssigned);
        tvAssignedDate = findViewById(R.id.tvAssignedDate);

        btnAssignTask = findViewById(R.id.btnAssignTask);
        btnRemoveTask = findViewById(R.id.btnRemoveTask);

        sp = getSharedPreferences("myLoginData", 0);
    }

    private void assignTask(final String child_id, final String task){
        StringRequest request = new StringRequest(Request.Method.POST, Constants.BASE_URL + "myassign_tasks.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("code");
                    if (code.equalsIgnoreCase("success")){
                        Toast.makeText(AssignTaskActivity.this, "Task Assigned Successfully", Toast.LENGTH_SHORT).show();
                        tvTaskAssigned.setText(task);
                        tvAssignedDate.setText(assignedDate);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AssignTaskActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("child_id", child_id);
                map.put("teacher_id", sp.getString("id", ""));
                map.put("description", task);
                map.put("assigned_date", assignedDate);
                return map;
            }
        };
        Volley.newRequestQueue(AssignTaskActivity.this).add(request);
    }


    private void removeTask(final String child_id) {
        StringRequest request = new StringRequest(Request.Method.POST, Constants.BASE_URL + "remove_task.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("code");
                    if (code.equalsIgnoreCase("success")){
                        Toast.makeText(AssignTaskActivity.this, "Task Removed Successfully", Toast.LENGTH_SHORT).show();
                        tvTaskAssigned.setText("");
                        tvAssignedDate.setText("");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AssignTaskActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("child_id", child_id);
                map.put("teacher_id", sp.getString("id", ""));
                return map;
            }
        };
        Volley.newRequestQueue(AssignTaskActivity.this).add(request);
    }
}
