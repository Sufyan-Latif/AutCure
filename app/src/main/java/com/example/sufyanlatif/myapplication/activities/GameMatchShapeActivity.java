package com.example.sufyanlatif.myapplication.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
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
import com.example.sufyanlatif.myapplication.utils.Constants;
import java.util.HashMap;
import java.util.Map;
import library.shmehdi.dragger.Dragger;

public class GameMatchShapeActivity extends AppCompatActivity {

    ImageView squareShape, circleShape, rectangleShape;
    TextView tvSquare, tvCircle, tvRectangle, tvCorrectShape, tvIncorrectShape;
    int correctShapeScore = 0, incorrectShapeScore = 0;
    int noOfShapes = 3;
    SharedPreferences sp;
    Dialog dialog;
    View dialogView;
    MediaPlayer correctVoice, incorrectVoice;
    Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_match_shape);

        bindViews();

        Dragger.create().setDragView(squareShape).setTargetViews(new View[]{tvSquare, tvRectangle, tvCircle}).setDragEventListener(
                new Dragger.DragEventListener() {
                    @Override
                    public void onDragComplete(View view) {
                        if (view.equals(tvRectangle)) {
                            v.vibrate(500);
                            incorrectVoice.start();
                            incorrectShapeScore++;
                            tvIncorrectShape.setText("Incorrect : " + incorrectShapeScore);
                            Toast.makeText(GameMatchShapeActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                        }
                        if (view.equals(tvSquare)) {
                            squareShape.setVisibility(View.GONE);
                            correctVoice.start();
                            correctShapeScore++;
                            tvCorrectShape.setText("Correct : " + correctShapeScore);
                            Toast.makeText(GameMatchShapeActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                            noOfShapes--;
                            if (noOfShapes == 0) {
                                showDialog();
                            }
                            Log.d("balls", "No. of Balls: "+noOfShapes);
                        }
                        if (view.equals(tvCircle)) {
                            v.vibrate(500);
                            incorrectVoice.start();
                            incorrectShapeScore++;
                            tvIncorrectShape.setText("Incorrect : " + incorrectShapeScore);
                            Toast.makeText(GameMatchShapeActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        ).startDragging();
        Dragger.create().setDragView(circleShape).setTargetViews(new View[]{tvSquare, tvRectangle, tvCircle}).setDragEventListener(
                new Dragger.DragEventListener() {
                    @Override
                    public void onDragComplete(View view) {
                        if (view.equals(tvRectangle)) {
                            v.vibrate(500);
                            incorrectVoice.start();
                            incorrectShapeScore++;
                            tvIncorrectShape.setText("Incorrect : " + incorrectShapeScore);
                            Toast.makeText(GameMatchShapeActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                        }
                        if (view.equals(tvSquare)) {

                            v.vibrate(500);
                            incorrectVoice.start();
                            incorrectShapeScore++;
                            tvIncorrectShape.setText("Incorrect : " + incorrectShapeScore);
                            Toast.makeText(GameMatchShapeActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                        }
                        if (view.equals(tvCircle)) {
                            circleShape.setVisibility(View.GONE);
                            correctVoice.start();
                            correctShapeScore++;
                            tvCorrectShape.setText("Correct : " + correctShapeScore);
                            Toast.makeText(GameMatchShapeActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                            noOfShapes--;
                            if (noOfShapes == 0) {
                                showDialog();
                            }
                            Log.d("balls", "No. of Balls: "+noOfShapes);
                        }
                    }
                }
        ).startDragging();
        Dragger.create().setDragView(rectangleShape).setTargetViews(new View[]{tvSquare, tvRectangle, tvCircle}).setDragEventListener(
                new Dragger.DragEventListener() {
                    @Override
                    public void onDragComplete(View view) {
                        if (view.equals(tvRectangle)) {

                            rectangleShape.setVisibility(View.GONE);
                            correctVoice.start();
                            correctShapeScore++;
                            tvCorrectShape.setText("Correct : " + correctShapeScore);
                            Toast.makeText(GameMatchShapeActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                            noOfShapes--;
                            if (noOfShapes == 0) {
                                showDialog();
                            }
                            Log.d("balls", "No. of Balls: "+noOfShapes);
                        }
                        if (view.equals(tvSquare)) {

                            v.vibrate(500);
                            incorrectVoice.start();
                            incorrectShapeScore++;
                            tvIncorrectShape.setText("Incorrect : " + incorrectShapeScore);
                            Toast.makeText(GameMatchShapeActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();

                        }
                        if (view.equals(tvCircle)) {

                            v.vibrate(500);
                            incorrectVoice.start();
                            incorrectShapeScore++;
                            tvIncorrectShape.setText("Incorrect : " + incorrectShapeScore);
                            Toast.makeText(GameMatchShapeActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
        ).startDragging();
    }

    private void bindViews() {
        squareShape= findViewById(R.id.squareShape);
        circleShape= findViewById(R.id.circleShape);
        rectangleShape= findViewById(R.id.rectangleShape);
        tvSquare= findViewById(R.id.tvSquare);
        tvCircle= findViewById(R.id.tvCircle);
        tvRectangle= findViewById(R.id.tvRectangle);
        tvCorrectShape= findViewById(R.id.tvCorrectShape);
        tvIncorrectShape= findViewById(R.id.tvIncorrectShape);
        sp = getSharedPreferences("myLoginData", 0);
        dialog = new Dialog(this);
        dialogView = LayoutInflater.from(this).inflate(R.layout.logout_dialog, null);

        MediaPlayer putTheShapesAtCorrectPosition = MediaPlayer.create(GameMatchShapeActivity.this, R.raw.put_the_shapes_at_correct_position);
        putTheShapesAtCorrectPosition.start();

        correctVoice = MediaPlayer.create(GameMatchShapeActivity.this, R.raw.correct);
        incorrectVoice = MediaPlayer.create(GameMatchShapeActivity.this, R.raw.incorrect);

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GameMatchShapeActivity.this)
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
                            uploadScore(correctShapeScore, incorrectShapeScore);

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

                if (correctShapeScore == 0 && incorrectShapeScore == 0){
                    dialog.dismiss();
                    finish();
                }
                else{
                    if (getIntent().getStringExtra("status").equalsIgnoreCase("registered"))
                        uploadScore(correctShapeScore, incorrectShapeScore);
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

    private void uploadScore(final int correctBall, final int incorrectBall){
        StringRequest request = new StringRequest(Request.Method.POST, Constants.BASE_URL + "upload_score.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Log.e("responseError", ""+error);
                Toast.makeText(GameMatchShapeActivity.this, "Error"+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("game_name", getResources().getString(R.string.put_the_shapes_at_correct_position));
                map.put("username", sp.getString("username", ""));
                map.put("correct", ""+correctBall);
                map.put("incorrect", ""+incorrectBall);
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(GameMatchShapeActivity.this);
        queue.add(request);
    }
}
