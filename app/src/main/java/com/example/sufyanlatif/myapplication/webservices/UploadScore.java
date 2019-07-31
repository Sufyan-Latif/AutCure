package com.example.sufyanlatif.myapplication.webservices;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.sufyanlatif.myapplication.models.Child;

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

public class UploadScore extends AsyncTask <String, String, String > {

    Context context;
    ProgressDialog myProgressDialog;

    public UploadScore(Context ctx) {
        context = ctx;
        myProgressDialog= new ProgressDialog(context);
        myProgressDialog.setMessage("Loading... Please wait");
    }

    @Override
    protected String doInBackground(String... strings) {



        String type = strings[0];
        String game_name = strings[1];
        String username = strings[2];
        String correct = strings[3];
        String incorrect = strings[4];
        String login_url = "https://autcureapp1.000webhostapp.com/upload_score.php";
        if (type.equals("upload_score")) {
            try {
                URL url = new URL(login_url);
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("game_name", "UTF-8") + "=" + URLEncoder.encode(game_name, "UTF-8") + "&"
                            + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                            + URLEncoder.encode("correct", "UTF-8") + "=" + URLEncoder.encode(correct, "UTF-8") + "&"
                            + URLEncoder.encode("incorrect", "UTF-8") + "=" + URLEncoder.encode(incorrect, "UTF-8");
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
//        progressDialog= new ProgressDialog(context);
//        progressDialog.setMessage("Uploading Score...");
        myProgressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(context, "System Response : " + s,Toast.LENGTH_SHORT).show();
//        myProgressDialog.dismiss();

//        context.finish();
//            if (progressDialog.isShowing())
//                progressDialog.dismiss();
    }
}
