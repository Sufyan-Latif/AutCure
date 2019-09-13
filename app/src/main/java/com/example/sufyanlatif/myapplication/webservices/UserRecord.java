package com.example.sufyanlatif.myapplication.webservices;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.sufyanlatif.myapplication.activities.TeacherWelcomeActivity;
import com.example.sufyanlatif.myapplication.models.Child;
import com.example.sufyanlatif.myapplication.models.Parent;
import com.example.sufyanlatif.myapplication.models.Teacher;
import com.example.sufyanlatif.myapplication.utils.Constants;

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
    ProgressDialog progressDialog;
    public UserRecord(Context ctx)
    {
        context = ctx;
        progressDialog= new ProgressDialog(context);
        progressDialog.setMessage("Loading... Please wait");
    }

    @Override
    protected String doInBackground(String... strings) {
        String webService = strings[0];
        String userType= strings[1];
        String username = strings[2];
        String password = strings[3];
        String login_url = Constants.BASE_URL + "user_record.php";
        if (webService.equals("getRecord"))
        {
            try
            {
                URL url = new URL(login_url);
                try {
                    HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setConnectTimeout(15000);
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
        progressDialog.show();
        super.onPreExecute();
    }
    @Override
    protected void onPostExecute(String s) {

        Log.d("UserRecord.java", "Response = "+s);
        if (s == null){
            progressDialog.dismiss();
            Toast.makeText(context, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
        }

        else {
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

                switch (type) {
                    case "child":
                        Child child = Child.getInstance();
                        child.setId(id);
                        child.setFirstName(first_name);
                        child.setLastName(last_name);
                        child.setUsername(username);
                        child.setAddress(address);
                        child.setGender(gender);
                        break;
                    case "parent": {
                        String associated = jo.getString("associated");
                        Parent parent = Parent.getInstance();
                        parent.setId(id);
                        Log.d("xyzz", parent.getId());
                        parent.setFirstName(first_name);
                        parent.setLastName(last_name);
                        parent.setUsername(username);
                        parent.setAddress(address);
                        parent.setGender(gender);
                        parent.setAssociated(associated);
                        break;
                    }
                    case "teacher": {
                        String associated = jo.getString("associated");
                        Teacher teacher = Teacher.getInstance();
                        teacher.setId(id);
                        teacher.setFirstName(first_name);
                        teacher.setLastName(last_name);
                        teacher.setUsername(username);
                        teacher.setAddress(address);
                        teacher.setGender(gender);
                        teacher.setAssociated(associated);
                        break;
                    }
                    default:
                        Toast.makeText(context, "Issue in fetching record", Toast.LENGTH_LONG).show();
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "unknown error occured", Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        }
    }
}