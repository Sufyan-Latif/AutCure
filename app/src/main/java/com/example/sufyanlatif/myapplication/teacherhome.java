package com.example.sufyanlatif.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import com.example.sufyanlatif.myapplication.models.Teacher;
import com.example.sufyanlatif.myapplication.webservices.Performance;
import com.example.sufyanlatif.myapplication.webservices.UserRecord;

public class teacherhome extends AppCompatActivity {

    int REQUEST_PHONE_CALL=1998;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    Button communication, viewPerformance;
    Teacher teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherhome);

        sp = getSharedPreferences("myLoginData", 0);
        editor = sp.edit();
        editor.putString("type", "teacher");
        editor.apply();

        String username = sp.getString("username", "abc");
        String password = sp.getString("password", "xyz");

        Log.d("teacherhome.java", "username = "+username + " Password = "+password);

        teacher = Teacher.getInstance();
        if (teacher.getUsername()==null)
        {
            if (isInternetConnected()){
                UserRecord userRecord = new UserRecord(teacherhome.this);
                userRecord.execute("getRecord","teacher", username, password);
            }
            else
                Toast.makeText(teacherhome.this, "No internet connection", Toast.LENGTH_SHORT).show();
        }

        viewPerformance= findViewById(R.id.btnTeacherViewPerformance);
        viewPerformance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetConnected()){
                    if (teacher.getAssociated().equals("yes")){
                        Performance performance= new Performance(teacherhome.this);
                        Log.d("xyz", teacher.getId());
                        performance.execute("teachers", teacher.getId());
                    }
                    else
                        Toast.makeText(teacherhome.this, "No child associated", Toast.LENGTH_SHORT).show();
//                    RetrievePerformance retrievePerformance= new RetrievePerformance(parenthome.this);
//                    retrievePerformance.execute("retrieve_performance", parent.getUsername());

                }
                else
                    Toast.makeText(teacherhome.this, "No internet connection", Toast.LENGTH_SHORT).show();
//                Intent intent= new Intent(teacherhome.this, PerformanceFinalActivity.class);
//                startActivity(intent);
            }
        });
        communication = findViewById(R.id.btnTeacherCommunication);
        communication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(teacherhome.this, TeacherCommunicationActivity.class);
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
//                if (ActivityCompat.checkSelfPermission(teacherhome.this,
//                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//                startActivity(intent);

//                if (ContextCompat.checkSelfPermission(teacherhome.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(teacherhome.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
//                }
//                else
//                {
//                    startActivity(intent);
//                }

//                try {
//                    startActivity(intent);
//                } catch (android.content.ActivityNotFoundException ex) {
//                    Toast.makeText(teacherhome.this, "Could not find an activity to place the call.", Toast.LENGTH_SHORT).show();
//                }
//                if (ActivityCompat.checkSelfPermission(teacherhome.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
            AlertDialog.Builder builder = new AlertDialog.Builder(teacherhome.this);
            builder.setTitle("Alert!!!")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sp= getSharedPreferences("myLoginData", 0);
                            editor = sp.edit();
                            editor.remove("type");
                            editor.apply();
                            Intent intent = new Intent(teacherhome.this, authenticationact.class);
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
