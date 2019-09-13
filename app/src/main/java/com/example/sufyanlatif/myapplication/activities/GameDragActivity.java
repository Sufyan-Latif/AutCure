package com.example.sufyanlatif.myapplication.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.models.Child;
import com.example.sufyanlatif.myapplication.utils.Constants;
import com.example.sufyanlatif.myapplication.webservices.UploadScore;

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
import java.util.HashMap;
import java.util.Map;

import library.shmehdi.dragger.Dragger;

public class GameDragActivity extends AppCompatActivity {

//    Child child = Child.getInstance();
    ImageView redBall, blueBall, greenBall, blueBasket, redBasket, greenBasket;
    TextView tvCorrectBalls, tvIncorrectBalls;
    int correctBall = 0, incorrectBall = 0;
    int noOfBalls = 3;
    SharedPreferences sp;
    Dialog dialog;
    View dialogView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_drag);

        Log.d("GameDragActivity", "onCreate");

        redBall = findViewById(R.id.redBall);
        blueBall = findViewById(R.id.blueBall);
        greenBall = findViewById(R.id.greenBall);
        blueBasket = findViewById(R.id.blueBasket);
        redBasket = findViewById(R.id.redBasket);
        greenBasket = findViewById(R.id.greenBasket);
        tvCorrectBalls = findViewById(R.id.correctBalls);
        tvIncorrectBalls = findViewById(R.id.incorrectBalls);
        sp = getSharedPreferences("myLoginData", 0);

        dialog = new Dialog(this);
        dialogView = LayoutInflater.from(this).inflate(R.layout.logout_dialog, null);
        progressDialog = new ProgressDialog(GameDragActivity.this);
        progressDialog.setMessage("Saving Score...");

        MediaPlayer putTheBallsInBasket = MediaPlayer.create(GameDragActivity.this, R.raw.put_the_balls_in_basket);
        putTheBallsInBasket.start();

        final MediaPlayer correctVoice = MediaPlayer.create(GameDragActivity.this, R.raw.correct);
        final MediaPlayer incorrectVoice = MediaPlayer.create(GameDragActivity.this, R.raw.incorrect);

        final Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

//        redBall.setOnClickListener(onClickListener);

        Dragger.create().setDragView(redBall).setTargetViews(new View[]{blueBasket, redBasket, greenBasket}).setDragEventListener(
                new Dragger.DragEventListener() {
                    @Override
                    public void onDragComplete(View view) {
                        if (view.equals(blueBasket)) {
                            v.vibrate(500);
                            incorrectVoice.start();
                            incorrectBall++;
                            tvIncorrectBalls.setText("Incorrect : " + incorrectBall);
                            Toast.makeText(GameDragActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                        }
                        if (view.equals(redBasket)) {
                            redBall.setVisibility(View.GONE);
                            correctVoice.start();
                            correctBall++;
                            tvCorrectBalls.setText("Correct : " + correctBall);
                            Toast.makeText(GameDragActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                            noOfBalls--;
                            if (noOfBalls == 0) {
                                showDialog();
                            }
                            Log.d("balls", "No. of Balls: " + noOfBalls);
                        }
                        if (view.equals(greenBasket)) {
                            v.vibrate(500);
                            incorrectVoice.start();
                            incorrectBall++;
                            tvIncorrectBalls.setText("Incorrect : " + incorrectBall);
                            Toast.makeText(GameDragActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        ).startDragging();
        Dragger.create().setDragView(blueBall).setTargetViews(new View[]{blueBasket, redBasket, greenBasket}).setDragEventListener(
                new Dragger.DragEventListener() {
                    @Override
                    public void onDragComplete(View view) {
                        if (view.equals(blueBasket)) {
                            blueBall.setVisibility(View.GONE);
                            correctVoice.start();
                            correctBall++;
                            tvCorrectBalls.setText("Correct : " + correctBall);
                            Toast.makeText(GameDragActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                            noOfBalls--;
                            if (noOfBalls == 0) {
                                showDialog();
                            }
                            Log.d("balls", "No. of Balls: " + noOfBalls);
                        }
                        if (view.equals(redBasket)) {
                            v.vibrate(500);
                            incorrectVoice.start();
                            incorrectBall++;
                            tvIncorrectBalls.setText("Incorrect : " + incorrectBall);
                            Toast.makeText(GameDragActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                        }
                        if (view.equals(greenBasket)) {
                            v.vibrate(500);
                            incorrectVoice.start();
                            incorrectBall++;
                            tvIncorrectBalls.setText("Incorrect : " + incorrectBall);
                            Toast.makeText(GameDragActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        ).startDragging();
        Dragger.create().setDragView(greenBall).setTargetViews(new View[]{blueBasket, redBasket, greenBasket}).setDragEventListener(
                new Dragger.DragEventListener() {
                    @Override
                    public void onDragComplete(View view) {
                        if (view.equals(blueBasket)) {
                            v.vibrate(500);
                            incorrectVoice.start();
                            incorrectBall++;
                            tvIncorrectBalls.setText("Incorrect : " + incorrectBall);
                            Toast.makeText(GameDragActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                        }
                        if (view.equals(redBasket)) {
                            v.vibrate(500);
                            incorrectVoice.start();
                            incorrectBall++;
                            tvIncorrectBalls.setText("Incorrect : " + incorrectBall);
                            Toast.makeText(GameDragActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                        }
                        if (view.equals(greenBasket)) {
                            greenBall.setVisibility(View.GONE);
                            correctVoice.start();
                            correctBall++;
                            tvCorrectBalls.setText("Correct : " + correctBall);
                            Toast.makeText(GameDragActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            noOfBalls--;
                            if (noOfBalls == 0) {
                                showDialog();
                            }
                            Log.d("balls", "No. of Balls: " + noOfBalls);
                        }
                    }
                }
        ).startDragging();
    }


//    View.OnClickListener onClickListener= new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            ClipData data = ClipData.newPlainText("", "");
//            View.DragShadowBuilder myShadowBuilder = new View.DragShadowBuilder(v);
//            v.startDrag(data, myShadowBuilder, v, 0);
//        }
//    };

    public void showDialog() {
//        final String username = child.getUsername();
        AlertDialog.Builder builder = new AlertDialog.Builder(GameDragActivity.this)
                .setMessage("Game completed!!!\nDo you want to play again?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        recreate();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (getIntent().getStringExtra("status").equalsIgnoreCase("registered"))
                            uploadScore(correctBall, incorrectBall);

//                        UploadScore uploadScore = new UploadScore(GameDragActivity.this);
//                        uploadScore.execute(
//                                "upload_score",
//                                "Put the Balls in Basket",
////                                child.getUsername(),
//                                sp.getString("username", ""),
//                                Integer.toString(correctBall),
//                                Integer.toString(incorrectBall)
//                        );
                        finish();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {

        TextView tvAlertSubtitle = dialogView.findViewById(R.id.tvAlertSubtitle);
        tvAlertSubtitle.setText("Are you sure you want to exit?");
        Button btnYes = dialogView.findViewById(R.id.btnYes);
        Button btnNo = dialogView.findViewById(R.id.btnNo);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (correctBall == 0 && incorrectBall == 0){
                    dialog.dismiss();
                    finish();
                }
                else{
                    if (getIntent().getStringExtra("status").equalsIgnoreCase("registered"))
                        uploadScore(correctBall, incorrectBall);
//                    progressDialog.show();
//                    StringRequest request = new StringRequest(Request.Method.POST, Constants.BASE_URL + "upload_score.php",
//                            new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String response) {
//                                    dialog.dismiss();
//                                    progressDialog.dismiss();
//                                    finish();
//                                }
//                            }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            dialog.dismiss();
//                            progressDialog.dismiss();
//                            Log.e("responseError", ""+error);
//                            Toast.makeText(GameDragActivity.this, "Error"+error, Toast.LENGTH_SHORT).show();
//                        }
//                    }){
//                        @Override
//                        protected Map<String, String> getParams() throws AuthFailureError {
//                            Map<String, String> map = new HashMap<>();
//                            map.put("game_name", getResources().getString(R.string.put_the_balls_in_the_basket));
//                            map.put("username", sp.getString("username", ""));
//                            map.put("correct", ""+correctBall);
//                            map.put("incorrect", ""+incorrectBall);
//                            return map;
//                        }
//                    };
//                    RequestQueue queue = Volley.newRequestQueue(GameDragActivity.this);
//                    queue.add(request);
                }
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(dialogView);
        dialog.show();
    }

    @Override
    public void recreate() {
        if (Build.VERSION.SDK_INT >= 11)
            super.recreate();
        else {
            startActivity(getIntent());
            finish();
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.d("GameDragActivity", "onResume");
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.d("GameDragActivity", "onStart");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d("GameDragActivity", "onPause");
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Log.d("GameDragActivity", "onRestart");
//    }


    //*************************************INNER CLASS*****************************************************


//    public class UploadScoreInner extends AsyncTask<String, String, String> {
//
//        Context context;
//        ProgressDialog progressDialog;
//
//        public UploadScoreInner(Context ctx) {
//            context = ctx;
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//
//
//            String type = strings[0];
//            String game_name = strings[1];
//            String username = strings[2];
//            String correct = strings[3];
//            String incorrect = strings[4];
//            String login_url = Constants.BASE_URL + "upload_score.php";
//            if (type.equals("upload_score")) {
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
//                        String post_data = URLEncoder.encode("game_name", "UTF-8") + "=" + URLEncoder.encode(game_name, "UTF-8") + "&"
//                                + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
//                                + URLEncoder.encode("correct", "UTF-8") + "=" + URLEncoder.encode(correct, "UTF-8") + "&"
//                                + URLEncoder.encode("incorrect", "UTF-8") + "=" + URLEncoder.encode(incorrect, "UTF-8");
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
//            progressDialog = new ProgressDialog(context);
//            progressDialog.setMessage("Uploading Score...");
//            progressDialog.show();
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            Toast.makeText(context, "System Response : " + s, Toast.LENGTH_SHORT).show();
//
//            finish();
////        if (s.equals("New record created successfully"))
////            Toast.makeText(context, "New record created successfully",Toast.LENGTH_SHORT).show();
////        else
////            Toast.makeText(context, "Unknown Error" + s,Toast.LENGTH_SHORT).show();
////            if (progressDialog.isShowing())
////                progressDialog.dismiss();
//        }
//    }

    private void uploadScore(final int correctBall, final int incorrectBall){
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Constants.BASE_URL + "upload_score.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        progressDialog.dismiss();
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                progressDialog.dismiss();
                Log.e("responseError", ""+error);
                Toast.makeText(GameDragActivity.this, "Error"+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("game_name", getResources().getString(R.string.put_the_balls_in_the_basket));
                map.put("username", sp.getString("username", ""));
                map.put("correct", ""+correctBall);
                map.put("incorrect", ""+incorrectBall);
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(GameDragActivity.this);
        queue.add(request);
    }
}