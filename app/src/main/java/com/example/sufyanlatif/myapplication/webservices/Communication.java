package com.example.sufyanlatif.myapplication.webservices;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.sufyanlatif.myapplication.activities.ListNamesActivity;
import com.example.sufyanlatif.myapplication.activities.TeacherCommunicationActivity;
import com.example.sufyanlatif.myapplication.utils.Constants;
import com.google.gson.Gson;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Communication extends AsyncTask<String, String, String>{

    Context context;
    ProgressDialog progressDialog;
    String activityName, table, id;

    public Communication(Context ctx) {
        context = ctx;
        progressDialog= new ProgressDialog(context);
        progressDialog.setMessage("Fetching Data...");
    }

    @Override
    protected String doInBackground(String... strings) {

        String type="communication";
        activityName = strings[0];
        id = strings[1];
        table = strings[2];

        String login_url = Constants.BASE_URL + "communication.php";
        if (type.equals("communication")) {
            try {
                URL url = new URL(login_url);
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("activity", "UTF-8") + "=" + URLEncoder.encode(activityName, "UTF-8") + "&"
                            + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&"
                            + URLEncoder.encode("table", "UTF-8") + "=" + URLEncoder.encode(table, "UTF-8");
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

        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("communicationresponse", s);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");

            if (activityName.equals("listnames")){

                JSONObject jo= jsonArray.getJSONObject(0);
                String firstName= jo.getString("first_name");
                String lastName= jo.getString("last_name");
                String address= jo.getString("address");
                String phone= jo.getString("phone");
                String email= jo.getString("email");

                Intent intent= new Intent(context, TeacherCommunicationActivity.class);

                intent.putExtra("first_name", firstName);
                intent.putExtra("last_name", lastName);
                intent.putExtra("address", address);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);

                context.startActivity(intent);

//                Map<Integer, Score> scoreMap= new LinkedHashMap<>(jsonArray.length());
//                Intent performanceIntent = new Intent(context, PerformanceActivity.class);

//
//                for (int i=0; i<jsonArray.length(); i++){
//                    JSONObject jo= jsonArray.getJSONObject(i);
//
//                    String correct= jo.getString("correct");
//                    String incorrect= jo.getString("incorrect");
//                    String game_id= jo.getString("game_id");
//
//                    Score scoreObject= new Score(id, correct, incorrect, game_id);
//                    scoreMap.put(i, scoreObject);
//
//                    Gson gson = new Gson();
//                    String json= gson.toJson(scoreMap);
//
//                    performanceIntent.putExtra("score", json);
//                }
//                context.startActivity(performanceIntent);
            }
            if (activityName.equals("ParentHomeActivity")){
                if (jsonArray.length()==1){
                    JSONObject jo = jsonArray.getJSONObject(0);
                    String teacher_id= jo.getString("teacher_id");
                    Log.d("communicationresponse", "teacher id = "+teacher_id);

                    Communication communication = new Communication(context);
                    communication.execute("listnames", teacher_id, "teachers");
                }
                else if (jsonArray.length()>1){
                    HashMap<String, ArrayList<String>> recordMap= new LinkedHashMap<>(jsonArray.length());
                    Intent intentRecord = new Intent(context, ListNamesActivity.class);

                    ArrayList<String> ids= new ArrayList<>();
                    ArrayList<String> firstNames= new ArrayList<>();
                    ArrayList<String> lastNames= new ArrayList<>();

                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jo= jsonArray.getJSONObject(i);
                        ids.add(jo.getString("teacher_id"));
                        Log.d("communicationresponse", "ids = "+ids);
                        firstNames.add(jo.getString("first_name"));
                        lastNames.add(jo.getString("last_name"));
                        if(i==jsonArray.length()-1){
                            recordMap.put("id", ids);
                            recordMap.put("first_name", firstNames);
                            recordMap.put("last_name", lastNames);
                            Log.d("unique", String.valueOf(ids));
                        }
                    }

                    Gson gson = new Gson();
                    String json= gson.toJson(recordMap);
                    intentRecord.putExtra("callfrom", "Communication.java parent");
                    intentRecord.putExtra("map", json);
                    context.startActivity(intentRecord);
                }
            }
            else if (activityName.equals("TeacherHomeActivity")){
                if (jsonArray.length()==1){
                    JSONObject jo = jsonArray.getJSONObject(0);
                    String parent_id= jo.getString("parent_id");
                    Log.d("communicationresponse", "teacher id = "+parent_id);

                    Communication communication = new Communication(context);
                    communication.execute("listnames", parent_id, "parents");
                }
                else if (jsonArray.length()>1){
                    HashMap<String, ArrayList<String>> recordMap= new LinkedHashMap<>(jsonArray.length());
                    Intent intentRecord = new Intent(context, ListNamesActivity.class);

                    ArrayList<String> ids= new ArrayList<>();
                    ArrayList<String> firstNames= new ArrayList<>();
                    ArrayList<String> lastNames= new ArrayList<>();

                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jo= jsonArray.getJSONObject(i);
                        ids.add(jo.getString("parent_id"));
                        Log.d("communicationresponse", "ids = "+ids);
                        firstNames.add(jo.getString("first_name"));
                        lastNames.add(jo.getString("last_name"));
                        if(i==jsonArray.length()-1){
                            recordMap.put("id", ids);
                            recordMap.put("first_name", firstNames);
                            recordMap.put("last_name", lastNames);
                            Log.d("unique", String.valueOf(ids));
                        }
                    }

                    Gson gson = new Gson();
                    String json= gson.toJson(recordMap);
                    intentRecord.putExtra("callfrom", "Communication.java teacher");
                    intentRecord.putExtra("map", json);
                    context.startActivity(intentRecord);
                }
            }

            /*
            ArrayList<String> ids= new ArrayList<>();
            ArrayList<String> firstNames= new ArrayList<>();
            ArrayList<String> lastNames= new ArrayList<>();
            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jo= jsonArray.getJSONObject(i);
                if(jo.getString("message").equals("MultipleChildren")){


                    ids.add(jo.getString("id"));
                    firstNames.add(jo.getString("first_name"));
                    lastNames.add(jo.getString("last_name"));

//                Toast.makeText(context, "System Response : " + record,Toast.LENGTH_SHORT).show();


                    if(i==jsonArray.length()-1){
                        record.put("id", ids);
                        record.put("first_name", firstNames);
                        record.put("last_name", lastNames);
                        Log.d("unique", String.valueOf(ids));
                    }

                    intent.putExtra("message", "MultipleChildren");
                    intent.putExtra("map", record);

                    Log.d("map", "\n"+ record);
                }
                else if(jo.getString("message").equals("SingleData")){
                    HashMap<String, String> sigleRecord= new HashMap<String, String>();
                    sigleRecord.put("id", jo.getString("id"));
                    sigleRecord.put("correct", jo.getString("correct"));
                    sigleRecord.put("incorrect", jo.getString("incorrect"));

                    intent.putExtra("message", "SingleData");
                    intent.putExtra("map", sigleRecord);

                    Log.d("map", "\n"+ sigleRecord);
                }
            }
            context.startActivity(intent);

//            JSONObject jo = jsonArray.getJSONObject(0);
//            String correct= jo.getString("correct");
//            String incorrect= jo.getString("incorrect");
//            Toast.makeText(context, "correct" + correct, Toast.LENGTH_LONG).show();
//            Toast.makeText(context, "incorrect" + incorrect, Toast.LENGTH_LONG).show();

//            for (JSONObject rows : (Iterable<JSONObject>) jsonArray){
//                String row= "\nCorrect : "+ rows.getString("correct");
//                Log.d("correct", row);
//            }

*/
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "User not registered OR\nContact details might be hidden by user",Toast.LENGTH_LONG).show();
            Log.e("CommunicationError", "Error!!! : " + e);
        }

//        context.finish();
//            if (progressDialog.isShowing())
                progressDialog.dismiss();
    }

}
