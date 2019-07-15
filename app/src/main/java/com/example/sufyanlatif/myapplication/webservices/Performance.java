package com.example.sufyanlatif.myapplication.webservices;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.sufyanlatif.myapplication.ListNamesActivity;
import com.example.sufyanlatif.myapplication.Score;
import com.example.sufyanlatif.myapplication.TempActivity;
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
import java.util.Map;

public class Performance extends AsyncTask<String, String, String> {


    Context context;
    ProgressDialog progressDialog;
    String id_type, id;

    public Performance(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... strings) {

        String type="retrieve_performance";
        id_type = strings[0];
        id = strings[1];
        String login_url = "https://autcureapp1.000webhostapp.com/performance.php";
        if (type.equals("retrieve_performance")) {
            try {
                URL url = new URL(login_url);
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("id_type", "UTF-8") + "=" + URLEncoder.encode(id_type, "UTF-8") + "&"
                            + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
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
//        progressDialog= new ProgressDialog(context);
//        progressDialog.setMessage("Fetching Score...");
//        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");

            if (id_type.equals("children")){
                Map<Integer, Score> scoreMap= new LinkedHashMap<>(jsonArray.length());
                Intent performanceIntent = new Intent(context, TempActivity.class);

                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject jo= jsonArray.getJSONObject(i);

                    String correct= jo.getString("correct");
                    String incorrect= jo.getString("incorrect");
                    String game_id= jo.getString("game_id");

                    Score scoreObject= new Score(id, correct, incorrect, game_id);
                    scoreMap.put(i, scoreObject);

                    Gson gson = new Gson();
                    String json= gson.toJson(scoreMap);

                    performanceIntent.putExtra("score", json);
                }
                context.startActivity(performanceIntent);
            }
            else if (id_type.equals("teachers")||id_type.equals("parents")){
                if (jsonArray.length()==1){
                    JSONObject jo = jsonArray.getJSONObject(0);
                    String id= jo.getString("id");

                    Performance performance = new Performance(context);
                    performance.execute("children", id);
                }
                else if (jsonArray.length()>1){
                    HashMap<String, ArrayList<String>> recordMap= new LinkedHashMap<>(jsonArray.length());
                    Intent intentRecord = new Intent(context, ListNamesActivity.class);

                    ArrayList<String> ids= new ArrayList<>();
                    ArrayList<String> firstNames= new ArrayList<>();
                    ArrayList<String> lastNames= new ArrayList<>();

                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jo= jsonArray.getJSONObject(i);
                        ids.add(jo.getString("id"));
                        Log.d("Performance.java", "ids = "+ids);
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
                    intentRecord.putExtra("callfrom", "Performance.java");
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
            Toast.makeText(context, "Error!!! : " + e,Toast.LENGTH_SHORT).show();
        }

//        context.finish();
//            if (progressDialog.isShowing())
//                progressDialog.dismiss();
    }
}
