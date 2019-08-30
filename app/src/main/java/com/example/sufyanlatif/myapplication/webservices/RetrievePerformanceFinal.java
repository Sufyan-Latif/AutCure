package com.example.sufyanlatif.myapplication.webservices;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.sufyanlatif.myapplication.activities.PerformanceActivity;
import com.example.sufyanlatif.myapplication.models.Score;
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
import java.util.LinkedHashMap;
import java.util.Map;

public class RetrievePerformanceFinal extends AsyncTask<String, String, String> {

    Context context;
    ProgressDialog progressDialog;

    public RetrievePerformanceFinal(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... strings) {

        String type = strings[0];
        String child_id = strings[1];
        String login_url = "https://autcureapp1.000webhostapp.com/retrieve_performance_final.php";
        if (type.equals("retrieve_performance_final")) {
            try {
                URL url = new URL(login_url);
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("child_id", "UTF-8") + "=" + URLEncoder.encode(child_id, "UTF-8");
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
        Log.d("RetrievePerformanceFina", "Response : "+s);
        JSONObject jsonObject = null;
        Intent intent= new Intent(context, PerformanceActivity.class);
        try {
            Log.d("RetrievePerformanceFina", "Response : "+s);
            jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");

//            PerformanceFinalActivity obj= new PerformanceFinalActivity();
//            TableLayout scoreTable= obj.getScoreTable();
//            TableRow[] tableRow=new TableRow[jsonArray.length()];
//            TextView[] correct= new TextView[jsonArray.length()];

            Map< Integer, Score> score= new LinkedHashMap<>(jsonArray.length());

//            ArrayList<String> ids= new ArrayList<>();
//            ArrayList<String> firstNames= new ArrayList<>();
//            ArrayList<String> lastNames= new ArrayList<>();
            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jo= jsonArray.getJSONObject(i);

                String id= jo.getString("id");
                String correct= jo.getString("correct");
                String incorrect= jo.getString("incorrect");
                String game_id= jo.getString("game_id");

                Score scoreObject= new Score(id, correct, incorrect, game_id);
                score.put(i, scoreObject);

//                JSONObject json = new JSONObject();
//                json.put("score", score );

                Gson gson = new Gson();
                String json= gson.toJson(score);

                intent.putExtra("score", json);

//                intent.putExtra("score", json);


//                tableRow[i]=new TableRow(context);
//                tableRow[i].setLayoutParams(new TableLayout.LayoutParams(
//                        TableLayout.LayoutParams.MATCH_PARENT,
//                        TableLayout.LayoutParams.WRAP_CONTENT));

//                if(jo.getString("message").equals("MultipleScore")){
//
////                    ids.add(jo.getString("id"));
////                    firstNames.add(jo.getString("first_name"));
////                    lastNames.add(jo.getString("last_name"));
//
////                Toast.makeText(context, "System Response : " + record,Toast.LENGTH_SHORT).show();
//
//
////                    if(i==jsonArray.length()-1){
////                        record.put("id", ids);
////                        record.put("first_name", firstNames);
////                        record.put("last_name", lastNames);
////                        Log.d("unique", String.valueOf(ids));
////                    }
//
////                    intent.putExtra("message", "MultipleChildren");
////                    intent.putExtra("map", record);
//
//                    Log.d("map", "\n"+ record);
//                }
            }
            context.startActivity(intent);

//            context.startActivity(intent);

//            JSONObject jo = jsonArray.getJSONObject(0);
//            String correct= jo.getString("correct");
//            String incorrect= jo.getString("incorrect");
//            Toast.makeText(context, "correct" + correct, Toast.LENGTH_LONG).show();
//            Toast.makeText(context, "incorrect" + incorrect, Toast.LENGTH_LONG).show();

//            for (JSONObject rows : (Iterable<JSONObject>) jsonArray){
//                String row= "\nCorrect : "+ rows.getString("correct");
//                Log.d("correct", row);
//            }


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error!!! : " + e,Toast.LENGTH_SHORT).show();
        }

//        context.finish();
//            if (progressDialog.isShowing())
//                progressDialog.dismiss();
    }
}
