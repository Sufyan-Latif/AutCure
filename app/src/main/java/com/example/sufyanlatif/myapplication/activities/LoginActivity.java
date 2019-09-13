package com.example.sufyanlatif.myapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sufyanlatif.myapplication.R;

public class LoginActivity extends AppCompatActivity {

//    JSONObject jsonObjectAttribute;
//
//    SharedPreferences sp;
//    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        sp = getSharedPreferences("myLoginData", 0);
//        String username = sp.getString("username", "abc");
//        String password = sp.getString("passsword", "xyz");
//        if (!sp.getString("type", "null").equals(null)) {
//
//            boolean connected = false;
//            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
//                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
//                we are connected to a network
//                connected = true;
//            }
//            else
//                connected = false;
//            if (connected)
//            {
//                SPBackGroundWorkerInner spBackGroundWorkerInner= new SPBackGroundWorkerInner(LoginActivity.this);
//                spBackGroundWorkerInner.execute("login", username, password);

//            }
//            else
//                Toast.makeText(LoginActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
//            if (sp.getString("type", "null").equals("parents")) {
//                Intent intent = new Intent(LoginActivity.this, ParentHomeActivity.class);
//                finish();
//                startActivity(intent);
//
//            } else if (sp.getString("type", "null").equals("students")) {
//                Intent intent = new Intent(LoginActivity.this, ChildHomeActivity.class);
//                finish();
//                startActivity(intent);
//            }
//            if (sp.getString("type", "null").equals("teacher")) {
//                Intent intent = new Intent(LoginActivity.this, TeacherWelcomeActivity.class);
//                finish();
//                startActivity(intent);
//            }
//        }
        Button login = findViewById(R.id.login_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MyAuthenticationActivity.class);
                finish();
                startActivity(intent);
            }
        });

        Button btnPlayGames = findViewById(R.id.btnPlayGames);
        btnPlayGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, GamesListActivity.class);
                intent.putExtra("status", "unRegistered");
                startActivity(intent);
            }
        });
    }


//**********************Inner Class**********************

//    public class SPBackGroundWorkerInner extends AsyncTask<String, String, String> {
//        Context context;
//
//        SPBackGroundWorkerInner(Context ctx) {
//            context = ctx;
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//
//            String type = strings[0];
//            String username = strings[1];
//            String password = strings[2];
//            String login_url = "https://autcure.000webhostapp.com/ourlogin.php";
//            if (type.equals("login")) {
//                try {
//                    URL url = new URL(login_url);
//                    try {
//                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                        httpURLConnection.setRequestMethod("POST");
//                        httpURLConnection.setDoOutput(true);
//                        httpURLConnection.setDoInput(true);
//
//                        OutputStream outputStream = httpURLConnection.getOutputStream();
//                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                        String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
//                                + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
//                        bufferedWriter.write(post_data);
//                        bufferedWriter.flush();
//                        bufferedWriter.close();
//
//                        InputStream inputStream = httpURLConnection.getInputStream();
//                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
//                        String result = "";
//                        String line = "";
//                        while ((line = bufferedReader.readLine()) != null) {
//                            result += line;
//                        }
//                        bufferedReader.close();
//                        inputStream.close();
//                        httpURLConnection.disconnect();
//                        return result;
//                    } catch (IOException err) {
//                        err.printStackTrace();
//                    }
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//
//            try {
//                JSONObject jsonObject = new JSONObject(s);
//                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
//                JSONObject jo = jsonArray.getJSONObject(0);
//                jsonObjectAttribute = jsonArray.getJSONObject(0);
//
//                String type = jsonObjectAttribute.getString("type");
//
//                String first_name = jsonObjectAttribute.getString("first_name");
//                String last_name = jsonObjectAttribute.getString("last_name");
//                String gender = jsonObjectAttribute.getString("gender");
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}