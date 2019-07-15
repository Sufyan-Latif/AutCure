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

import com.example.sufyanlatif.myapplication.models.Parent;
import com.example.sufyanlatif.myapplication.webservices.Communication;
import com.example.sufyanlatif.myapplication.webservices.Performance;
import com.example.sufyanlatif.myapplication.webservices.RetrievePerformance;
import com.example.sufyanlatif.myapplication.webservices.UserRecord;

public class parenthome extends AppCompatActivity {

    Button viewPerformance, parentCommunication;
    Parent parent;

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parenthome);

        viewPerformance= findViewById(R.id.btnParentViewPerformance);
        parentCommunication= findViewById(R.id.btnParentCommunication);

        sp = getSharedPreferences("myLoginData", 0);
        editor = sp.edit();
        editor.putString("type", "parents");
        editor.apply();
        String username = sp.getString("username", "abc");
        String password = sp.getString("password", "xyz");


        parent = Parent.getInstance();
        if (parent.getUsername()==null)
        {
            if (isInternetConnected()){
                UserRecord userRecord = new UserRecord(parenthome.this);
                userRecord.execute("getRecord","parent", username, password);
            }
            else
                Toast.makeText(parenthome.this, "No internet connection", Toast.LENGTH_SHORT).show();
        }

        viewPerformance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetConnected()){
                    if (parent.getAssociated().equals("yes")){
                        Performance performance= new Performance(parenthome.this);
                        Log.d("xyz", parent.getId());
                        performance.execute("parents", parent.getId());
                    }
                    else
                        Toast.makeText(parenthome.this, "No child associated", Toast.LENGTH_SHORT).show();
//                    RetrievePerformance retrievePerformance= new RetrievePerformance(parenthome.this);
//                    retrievePerformance.execute("retrieve_performance", parent.getUsername());

                }
                else
                    Toast.makeText(parenthome.this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });

        parentCommunication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Communication communication= new Communication(parenthome.this);
                communication.execute("parenthome", parent.getId(), "children");
//                Intent intent= new Intent(parenthome.this, PerformanceFinalActivity.class);
//                startActivity(intent);
            }
        });
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
            AlertDialog.Builder builder = new AlertDialog.Builder(parenthome.this);
            builder.setTitle("Alert!!!")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sp= getSharedPreferences("myLoginData", 0);
                            editor = sp.edit();
                            editor.remove("type");
                            editor.apply();
                            Intent intent = new Intent(parenthome.this, authenticationact.class);
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
