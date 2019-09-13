package com.example.sufyanlatif.myapplication.fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameAnimalFragment extends Fragment {

    View myView;
    ImageView imgOne, imgTwo, imgNext;
    TextView tvAnimal;
    Button btnExit;
    Toast toastCorrect, toastIncorrect;
    int correct = 0;
    int inCorrect = 0;
    String[] categoryNames;
    ImageView[] images;
    int[] categoryImages;
    MediaPlayer correctVoice, incorrectVoice;
    Vibrator vibrator;
    int firstIndex, secondIndex, correctIndex, categoryCount;
    Random random;
    private int SPLASH_DELAY= 1500;
    private Handler mDelayHandler= null;
    SharedPreferences sp;
//    Child child = Child.getInstance();

    public GameAnimalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_game_animal, container, false);

        bindViews();
        initializeToasts();

//        Bundle bundle = getArguments();
//        String game = bundle.getString("game");
        final String game = getActivity().getIntent().getStringExtra("game");
        switch (game) {
            case "Choose the Animal":
                categoryNames = new String[]{"CAT", "COW", "DOG", "ELEPHANT", "LION", "MONKEY"};
                categoryImages = new int[]{R.drawable.animal_cat,
                        R.drawable.animal_cow,
                        R.drawable.animal_dog,
                        R.drawable.animal_elephant,
                        R.drawable.animal_lion,
                        R.drawable.animal_monkey
                };
                categoryCount = 6;
                break;
            case "Choose the Fruit":
                categoryNames = new String[]{"APPLE", "BANANA", "GRAPES", "LEMON", "WATERMELON", "MANGO", "CHERRIES"};
                categoryImages = new int[]{R.drawable.fruit_apple,
                        R.drawable.fruit_banana,
                        R.drawable.fruit_grapes,
                        R.drawable.fruit_lemon,
                        R.drawable.fruit_watermelon,
                        R.drawable.fruit_mango,
                        R.drawable.fruit_cherries
                };
                categoryCount = 7;
                break;
            case "Choose the Vegetable":
                categoryNames = new String[]{"CARROT", "GARLIC", "ONION", "POTATO", "RADISH", "SALAD"};
                categoryImages = new int[]{R.drawable.vegetable_carrot,
                        R.drawable.vegetable_garlic,
                        R.drawable.vegetable_onion,
                        R.drawable.vegetable_potato,
                        R.drawable.vegetable_radish,
                        R.drawable.vegetable_salad,
                };
                categoryCount = 6;
                break;
        }

        newGame(categoryCount);

        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGame(categoryCount);
            }
        });

        imgOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (correctIndex) {
                    case 0:
                        toastCorrect.show();
                        correctVoice.start();
                        correct++;
                        mDelayHandler = new Handler();
                        Runnable mRunnable = new Runnable() {
                            @Override
                            public void run() {
                                newGame(categoryCount);
                            }
                        };
                        mDelayHandler.postDelayed(mRunnable, SPLASH_DELAY);
                        break;
                    case 1:
                        toastIncorrect.show();
                        incorrectVoice.start();
                        vibrator.vibrate(500);
                        inCorrect++;
                        break;
                }
            }
        });

        imgTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (correctIndex) {
                    case 0:
                        toastIncorrect.show();
                        incorrectVoice.start();
                        vibrator.vibrate(500);
                        inCorrect++;
                        break;
                    case 1:
                        toastCorrect.show();
                        correctVoice.start();
                        correct++;
                        mDelayHandler = new Handler();
                        Runnable mRunnable = new Runnable() {
                            @Override
                            public void run() {
                                newGame(categoryCount);
                            }
                        };
                        mDelayHandler.postDelayed(mRunnable, SPLASH_DELAY);
                        break;
                }
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(myView.getContext())
                        .setMessage("Alert!")
                        .setMessage("Are you sure you want to exit?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final ProgressDialog progressDialog = new ProgressDialog(myView.getContext());
                                progressDialog.setMessage("Saving Score...");
                                progressDialog.show();
                                StringRequest request = new StringRequest(Request.Method.POST, Constants.BASE_URL + "upload_score.php",
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                progressDialog.dismiss();
                                                getActivity().finish();
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        progressDialog.dismiss();
                                        Log.e("responseError", ""+error);
                                        Toast.makeText(myView.getContext(), "Error"+error, Toast.LENGTH_SHORT).show();
                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> map = new HashMap<>();
                                        map.put("game_name", game);
//                                        map.put("username", child.getUsername());
                                        map.put("username", sp.getString("username", ""));
                                        map.put("correct", ""+correct);
                                        map.put("incorrect", ""+inCorrect);
                                        return map;
                                    }
                                };
                                RequestQueue queue = Volley.newRequestQueue(myView.getContext());
                                queue.add(request);
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return myView;
    }

    private void newGame(int categoryCount) {
        firstIndex = random.nextInt(categoryCount);

        do {
            secondIndex = random.nextInt(categoryCount);
        } while (secondIndex == firstIndex);

        correctIndex = random.nextInt(2);

        tvAnimal.setText(categoryNames[firstIndex]);
        images[correctIndex].setImageResource(categoryImages[firstIndex]);

        switch (correctIndex) {
            case 0:
                images[1].setImageResource(categoryImages[secondIndex]);
                break;
            case 1:
                images[0].setImageResource(categoryImages[secondIndex]);
                break;
        }
    }

    private void initializeToasts() {
        toastCorrect = new Toast(myView.getContext());
        ImageView viewCorrect = new ImageView(myView.getContext());
        viewCorrect.setImageResource(R.drawable.tick);
        toastCorrect.setView(viewCorrect);
        toastCorrect.setDuration(Toast.LENGTH_SHORT);

        toastIncorrect = new Toast(myView.getContext());
        ImageView viewIncorrect = new ImageView(myView.getContext());
        viewIncorrect.setImageResource(R.drawable.cross);
        toastIncorrect.setView(viewIncorrect);
        toastIncorrect.setDuration(Toast.LENGTH_SHORT);
    }

    private void bindViews() {
        imgOne = myView.findViewById(R.id.imgAnimalOne);
        imgTwo = myView.findViewById(R.id.imgAnimalTwo);
        imgNext = myView.findViewById(R.id.imgNext);
        tvAnimal = myView.findViewById(R.id.tvAnimal);
        btnExit = myView.findViewById(R.id.btnExit);

        correctVoice = MediaPlayer.create(myView.getContext(), R.raw.correct);
        incorrectVoice = MediaPlayer.create(myView.getContext(), R.raw.incorrect);
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        random = new Random();
        images = new ImageView[]{imgOne, imgTwo};

        sp = getActivity().getSharedPreferences("myLoginData", 0);
    }
}
