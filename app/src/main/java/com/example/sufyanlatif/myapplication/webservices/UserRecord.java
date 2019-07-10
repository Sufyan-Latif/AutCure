package com.example.sufyanlatif.myapplication.webservices;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.sufyanlatif.myapplication.models.Child;
import com.example.sufyanlatif.myapplication.models.Parent;
import com.example.sufyanlatif.myapplication.models.Teacher;

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

public class UserRecord extends AsyncTask<String, String, String> {

    Context context;
    public UserRecord(Context ctx)
    {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... strings) {
        String webService = strings[0];
        String userType= strings[1];
        String username = strings[2];
        String password = strings[3];
        String login_url = "https://autcureapp1.000webhostapp.com/user_record.php";
        if (webService.equals("getRecord"))
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
                    String post_data = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(userType,"UTF-8") + "&"
                            + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username,"UTF-8") + "&"
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

        Log.d("UserRecord.java", "Response = "+s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
            JSONObject jo = jsonArray.getJSONObject(0);
            String type = jo.getString("type");
            String id = jo.getString("id");
            String first_name = jo.getString("first_name");
            String last_name = jo.getString("last_name");
            String username = jo.getString("username");
            String address = jo.getString("address");
            String gender = jo.getString("gender");

            Log.d("UserRecord.java", "type = "+type);

            if (type.equals("child"))
            {
                Child child = Child.getInstance();
                child.setId(id);
                child.setFirstName(first_name);
                child.setLastName(last_name);
                child.setUsername(username);
                child.setAddress(address);
                child.setGender(gender);
            }
            else if (type.equals("parent"))
            {
                String associated = jo.getString("associated");
                Parent parent= Parent.getInstance();
                parent.setId(id);
                Log.d("xyzz", parent.getId());
                parent.setFirstName(first_name);
                parent.setLastName(last_name);
                parent.setUsername(username);
                parent.setAddress(address);
                parent.setGender(gender);
                parent.setAssociated(associated);
            }
            else if (type.equals("teacher"))
            {
                String associated = jo.getString("associated");
                Teacher teacher= Teacher.getInstance();
                teacher.setId(id);
                teacher.setFirstName(first_name);
                teacher.setLastName(last_name);
                teacher.setUsername(username);
                teacher.setAddress(address);
                teacher.setGender(gender);
                teacher.setAssociated(associated);
            }
            else {
                Toast.makeText(context, "Issue in record fetching", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "unknown error occured", Toast.LENGTH_SHORT).show();
        }
    }
}