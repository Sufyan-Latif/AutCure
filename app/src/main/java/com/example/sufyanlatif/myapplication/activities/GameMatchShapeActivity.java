package com.example.sufyanlatif.myapplication.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sufyanlatif.myapplication.R;

import library.shmehdi.dragger.Dragger;

public class GameMatchShapeActivity extends AppCompatActivity {

    ImageView squareShape, circleShape, rectangleShape;
    TextView tvSquare, tvCircle, tvRectangle, tvCorrectShape, tvIncorrectShape;
    int correctShapeScore = 0, incorrectShapeScore = 0;
    int noOfShapes = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_match_shape);

        squareShape= findViewById(R.id.squareShape);
        circleShape= findViewById(R.id.circleShape);
        rectangleShape= findViewById(R.id.rectangleShape);
        tvSquare= findViewById(R.id.tvSquare);
        tvCircle= findViewById(R.id.tvCircle);
        tvRectangle= findViewById(R.id.tvRectangle);
        tvCorrectShape= findViewById(R.id.tvCorrectShape);
        tvIncorrectShape= findViewById(R.id.tvIncorrectShape);

        MediaPlayer putTheShapesAtCorrectPosition = MediaPlayer.create(GameMatchShapeActivity.this, R.raw.put_the_shapes_at_correct_position);
        putTheShapesAtCorrectPosition.start();

        final MediaPlayer correctVoice = MediaPlayer.create(GameMatchShapeActivity.this, R.raw.correct);
        final MediaPlayer incorrectVoice = MediaPlayer.create(GameMatchShapeActivity.this, R.raw.incorrect);

        final Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

//        redBall.setOnClickListener(onClickListener);

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
                        finish();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GameMatchShapeActivity.this)
                .setMessage("Are you sure you want to exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
