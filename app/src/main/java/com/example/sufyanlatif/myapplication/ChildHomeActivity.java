package com.example.sufyanlatif.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.example.sufyanlatif.myapplication.models.Child;
import com.example.sufyanlatif.myapplication.webservices.UserRecord;

public class ChildHomeActivity extends AppCompatActivity {

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    Child child;

    Button viewAnimations, playGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_childhome);

        sp = getSharedPreferences("myLoginData", 0);
        String username = sp.getString("username", "abc");
        String password = sp.getString("password", "xyz");

        Log.d("ChildHomeActivity", "Username : "+sp.getString("username", "No value")
                +"\nPassword"+ sp.getString("password", "No value"));

//        Toast.makeText(ChildHomeActivity.this, "Username : "+username+"\nPassword : "+password,Toast.LENGTH_SHORT).show();

        child = Child.getInstance();
        if (child.getUsername()==null)
        {
            if ( isInternetConnected() ){
                UserRecord userRecord = new UserRecord(ChildHomeActivity.this);
                userRecord.execute("getRecord","child", username, password);
            }
            else
                Toast.makeText(ChildHomeActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
        }

        playGames = findViewById(R.id.btnPlayGames);
        playGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String games[] = {"Put the balls in Basket", "Match the shapes", "Fill Colors"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ChildHomeActivity.this)
                        .setTitle("Select the game !")
                        .setItems(games, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    Intent intent = new Intent(ChildHomeActivity.this, GameDragActivity.class);
                                    startActivity(intent);
                                } else if (which == 1) {
                                    Intent intent = new Intent(ChildHomeActivity.this, GameMatchShapeActivity.class);
                                    startActivity(intent);
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        sp = getSharedPreferences("myLoginData", 0);
        editor = sp.edit();
        editor.putString("type", "students");
        editor.apply();

        viewAnimations = findViewById(R.id.viewAnimations);
        viewAnimations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChildHomeActivity.this, AnimationsActivity.class);
                startActivity(intent);

//                AlertDialog.Builder builder= new AlertDialog.Builder(ChildHomeActivity.this);
//                String users[]={"Wear Tie", "Wear Shoes","Wear Cap", "Turn Around"};
//                builder.setTitle("Display Animations !")
//                        .setItems(users, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                if (which== 0)
//                                {
//
//                                }
//                                else if (which== 1)
//                                {
//
//                                }
//                                else
//                                {
//
//                                }
//                            }
//                        })
//                        .setNegativeButton("Cancel",null);
//                AlertDialog dialog= builder.create();
//                dialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menu1 = getMenuInflater();
        menu1.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            Child child= Child.getInstance();
            String firstName= child.getFirstName();
            AlertDialog.Builder builder = new AlertDialog.Builder(ChildHomeActivity.this);
            builder.setTitle("Alert!!!")
                    .setMessage(firstName + "Are you sure you want to logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sp = getSharedPreferences("myLoginData", 0);
                            editor = sp.edit();
                            editor.remove("type");
                            editor.apply();
                            Intent intent = new Intent(ChildHomeActivity.this, authenticationact.class);
                            finish();
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        return true;
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