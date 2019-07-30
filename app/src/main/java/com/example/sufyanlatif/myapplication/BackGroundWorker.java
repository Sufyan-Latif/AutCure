package com.example.sufyanlatif.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.sufyanlatif.myapplication.activities.ChildHomeActivity;
import com.example.sufyanlatif.myapplication.activities.ParentHomeActivity;
import com.example.sufyanlatif.myapplication.activities.TeacherHomeActivity;

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

public class BackGroundWorker extends AsyncTask<String, String, String> {
    Context context;
    BackGroundWorker(Context ctx)
    {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... strings) {

        String type = strings[0];
        String username = strings[1];
        String password = strings[2];
        String login_url = "https://autcure.000webhostapp.com/ourlogin.php";
        if (type.equals("login"))
        {
            try
            {
                URL url = new URL(login_url);
                try {
                    HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream= httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username,"UTF-8") + "&"
                            + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password,"UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine())!=null)
                    {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                }
                catch (IOException err)
                {
                    err.printStackTrace();
                }
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected void onPostExecute(String s) {

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
            JSONObject jo = jsonArray.getJSONObject(0);

            String type = jo.getString("type");
            String first_name = jo.getString("first_name");
            String last_name = jo.getString("last_name");
            String gender = jo.getString("gender");

            if (type.equals("parents"))
            {
                Intent intent = new Intent(context, ParentHomeActivity.class);
                context.startActivity(intent);
            }
            else if (type.equals("students"))
            {
                Intent intent = new Intent(context, ChildHomeActivity.class);
                context.startActivity(intent);
            }
            else if (type.equals("teacher"))
            {
                Intent intent = new Intent(context, TeacherHomeActivity.class);
                context.startActivity(intent);
            }
            else {
                Toast.makeText(context, "No record", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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



//        try {
//            JSONObject reader= new JSONObject(s);
//            JSONObject jsonObject = reader.getJSONObject("server_response");
//            String gender = jsonObject.getString("gender");
//            Toast.makeText(context, "Gender = "+gender, Toast.LENGTH_LONG).show();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


//        Toast.makeText(context, "Result = "+s, Toast.LENGTH_LONG).show();
    }
}
