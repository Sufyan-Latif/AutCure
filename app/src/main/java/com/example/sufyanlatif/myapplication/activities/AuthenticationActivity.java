package com.example.sufyanlatif.myapplication.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.models.Child;
import com.example.sufyanlatif.myapplication.utils.Constants;
import com.example.sufyanlatif.myapplication.utils.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class AuthenticationActivity extends AppCompatActivity {

    JSONObject jsonObjectAttribute;
    CoordinatorLayout coordinatorLayout;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    ProgressBar progress;

    EditText user_name;
    EditText pass_word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        Constants.currModel = "";

        user_name = findViewById(R.id.editText);
        pass_word = findViewById(R.id.editText1);
        coordinatorLayout = findViewById(R.id.snackBarLayout);

        progress = findViewById(R.id.progress);

        sp = getSharedPreferences("myLoginData", 0);
        if (!sp.getString("type", "null").equals(null)) {

//            boolean connected = false;
//            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
//                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
//                //we are connected to a network
//                connected = true;
//            } else
//                connected = false;

//            if (Utility.isInternetConnected(getApplicationContext())) {
                if (sp.getString("type", "null").equals("parents")) {
                    Intent intent = new Intent(AuthenticationActivity.this, ParentHomeActivity.class);
                    finish();
                    startActivity(intent);
                } else if (sp.getString("type", "null").equals("students")) {
                    Intent intent = new Intent(AuthenticationActivity.this, ChildHomeActivity.class);
                    finish();
                    startActivity(intent);
                }
                if (sp.getString("type", "null").equals("teacher")) {
                    Intent intent = new Intent(AuthenticationActivity.this, TeacherWelcomeActivity.class);
                    finish();
                    startActivity(intent);
                }
//            }
//            else
//                Toast.makeText(AuthenticationActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
        final Button login = findViewById(R.id.button5);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = user_name.getText().toString();
                String password = pass_word.getText().toString();
//                boolean connected = false;
//                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
//                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
//                    we are connected to a network
//                    connected = true;
//                } else
//                    connected = false;
                if (Utility.isInternetConnected(getApplicationContext())) {
                    BackGroundWorkerInner backGroundWorkerInner = new BackGroundWorkerInner(AuthenticationActivity.this);
                    backGroundWorkerInner.execute("login", username, password);
                } else {
                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "No Internet Connection", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    login.callOnClick();

                                }
                            });
                    snackbar.show();
                }
//                    Toast.makeText(AuthenticationActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
            }
        });

        Button signup = findViewById(R.id.button6);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AuthenticationActivity.this);
                String users[] = {"Teacher", "Parent", "Child"};
                builder.setTitle("Signup As !")
                        .setItems(users, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent newIntent = new Intent(AuthenticationActivity.this, SignupActivity.class);
                                if (which == 0) {
                                    Constants.currModel = "teacher";
//                                    Intent intent = new Intent(AuthenticationActivity.this, TeacherSignupActivity.class);
//                                    finish();
//                                    startActivity(intent);
                                } else if (which == 1) {
                                    Constants.currModel = "parent";
//                                    Intent intent = new Intent(AuthenticationActivity.this, ParentSignupActivity.class);
//                                    finish();
//                                    startActivity(intent);
                                } else {
                                    Constants.currModel = "child";
//                                    Intent intent = new Intent(AuthenticationActivity.this, ChildSignupActivity.class);
//                                    finish();
//                                    startActivity(intent);
                                }
                                finish();
                                startActivity(newIntent);
                            }
                        })
                        .setNegativeButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    //**********************Inner Class**********************

    public class BackGroundWorkerInner extends AsyncTask<String, String, String> {
        Context context;

        BackGroundWorkerInner(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... strings) {

            String type = strings[0];
            String username = strings[1];
            String password = strings[2];
//            String login_url = "https://autcure.000webhostapp.com/ourlogin.php";
            String login_url = "https://autcureapp1.000webhostapp.com/login.php";
//            String login_url = "http://localhost/login.php";
            if (type.equals("login")) {
                try {
                    URL url = new URL(login_url);
                    try {
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);

                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                                + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                        bufferedWriter.write(post_data);
                        bufferedWriter.flush();
                        bufferedWriter.close();

                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                        String result = "";
                        String line = "";
                        while ((line = bufferedReader.readLine()) != null) {
                            result += line;
                        }
                        bufferedReader.close();
                        inputStream.close();
                        httpURLConnection.disconnect();
                        return result;
                    } catch (IOException err) {
                        err.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                JSONObject jo = jsonArray.getJSONObject(0);
                jsonObjectAttribute = jsonArray.getJSONObject(0);

//                sp= getSharedPreferences("myLoginData", 0);
//                editor = sp.edit();

                String type = jsonObjectAttribute.getString("type");

                String first_name = jsonObjectAttribute.getString("first_name");
                String last_name = jsonObjectAttribute.getString("last_name");
                String gender = jsonObjectAttribute.getString("gender");
                String username = jsonObjectAttribute.getString("username");
                String password = jsonObjectAttribute.getString("password");
                String id= jsonObjectAttribute.getString("id");

                sp = getSharedPreferences("myLoginData", 0);
                editor = sp.edit();
                editor.putString("username", username);
                editor.putString("password", password);
                editor.apply();

//                Log.d("authentication", "Username : "+sp.getString("username", "No value")
//                +"\nPassword"+ sp.getString("password", "No value"));


                if (type.equals("parent")) {
//                    editor.putString("type", "parents");
//                    editor.apply();

                    Intent intent = new Intent(context, ParentHomeActivity.class);
                    finish();
                    context.startActivity(intent);
                } else if (type.equals("child")) {

                    Child child= Child.getInstance();
                    child.setId(id);
                    child.setFirstName(first_name);
                    child.setLastName(last_name);
                    child.setGender(gender);
                    child.setUsername(username);
                    child.setPassword(password);

//                    editor.putString("type", "students");
//                    editor.apply();

                    Intent intent = new Intent(context, ChildHomeActivity.class);
                    finish();
                    context.startActivity(intent);
                } else if (type.equals("teacher")) {
//                    editor.putString("type", "teacher");
//                    editor.apply();

                    Intent intent = new Intent(context, TeacherWelcomeActivity.class);
                    finish();
                    context.startActivity(intent);
                } else {
//                    Toast.makeText(context, "No record", Toast.LENGTH_LONG).show();
                }
                progress.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
                progress.setVisibility(View.GONE);
                Toast.makeText(AuthenticationActivity.this, "Invalid Username or Password" +e, Toast.LENGTH_SHORT).show();
            }
//            Toast.makeText(context, "Result = "+s, Toast.LENGTH_LONG).show();

//        try {
//            JSONObject jsonObject = new JSONObject(s);
//            Boolean success = true;
//            if (success)
//            {
//                String gender = jsonObject.getString("gender");
//                Toast.makeText(context, "Gender = "+gender, Toast.LENGTH_LONG).show();
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

//        JSONArray jsonArray = null;
//        try {
//            jsonArray = new JSONArray(s);
//            for(int j = 0; j<jsonArray.length(); j++) {
//                JSONObject jObj = jsonArray.getJSONObject(j);
////                String n = jObj.getString("first_name");
////                String na = jObj.getString("last_name");
////                String e = jObj.getString("username");
////                String p = jObj.getString("password");
////                String le = jObj.getString("level");
////                String dob=jObj.getString("DOB");
////                String ad = jObj.getString("Address");
//                String ge = jObj.getString("gender");
//                Toast.makeText(context, "Gender = "+ge, Toast.LENGTH_LONG).show();
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


//        Toast.makeText(context, "Result = "+s, Toast.LENGTH_LONG).show();
        }
    }


}
