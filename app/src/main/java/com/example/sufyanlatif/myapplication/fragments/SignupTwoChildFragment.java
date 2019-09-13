package com.example.sufyanlatif.myapplication.fragments;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.activities.ChildHomeActivity;
import com.example.sufyanlatif.myapplication.models.Child;
import com.example.sufyanlatif.myapplication.models.Parent;
import com.example.sufyanlatif.myapplication.models.Teacher;
import com.example.sufyanlatif.myapplication.utils.Constants;
import com.example.sufyanlatif.myapplication.utils.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupTwoChildFragment extends Fragment {

    View myView;
    Child child = Child.getInstance();
    TextInputEditText etLevel, etAddress;
    RadioGroup rgGender;
    Button btnNext, btnBack;
    String gender = "";
    TextView tvDOB;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public SignupTwoChildFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView =  inflater.inflate(R.layout.fragment_signup_two_child, container, false);
        bindViews();

        tvDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showCalender();
                Utility.showDialog(myView.getContext(), tvDOB);
            }
        });

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.rbMale)
                    gender = "Male";
                else if (checkedId==R.id.childgenderfemale)
                    gender= "Female";
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Utility.isEmpty(etLevel, "Enter Level") ||
                        Utility.isEmpty(etAddress, "Enter Address")){

                } if (gender.equals("") || tvDOB.getText().toString().equalsIgnoreCase("DD/MM/YYYY")){
                    Toast.makeText(myView.getContext(), "Please Enter Data", Toast.LENGTH_SHORT).show();

                } else {

                    child.setLevel(etLevel.getText().toString());
                    child.setAddress(etAddress.getText().toString());
                    child.setDateOfBirth(tvDOB.getText().toString());
                    child.setGender(gender);

                    editor.putString("type", "child");
                    editor.putString("id", child.getId());
                    editor.putString("first_name", child.getFirstName());
                    editor.putString("last_name", child.getLastName());
                    editor.putString("address", child.getAddress());
                    editor.putString("gender", child.getGender());
                    editor.putString("username", child.getUsername());
                    editor.putString("password", child.getPassword());
                    editor.apply();

                    final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                    StringRequest request = new StringRequest(Request.Method.POST,
                            Constants.BASE_URL + "signup.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.e("signupError", "Response = "+response);
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONArray jsonArray = jsonObject.getJSONArray("response");
                                        JSONObject object = jsonArray.getJSONObject(0);
                                        if (object.getString("code").equalsIgnoreCase("success")) {

                                            progressDialog.dismiss();

                                            Intent intent= new Intent(getActivity().getBaseContext(), ChildHomeActivity.class);
                                            getActivity().finish();
                                            startActivity(intent);
                                            Toast.makeText(myView.getContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                        } else if (object.getString("code").equalsIgnoreCase("failure")) {
                                            progressDialog.dismiss();
                                            Toast.makeText(myView.getContext(), "User Not Registered", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        progressDialog.dismiss();
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Log.e("signupError", ""+error);
                            Toast.makeText(myView.getContext(), "Check Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("model", Constants.currModel);
                            map.put("first_name", child.getFirstName());
                            map.put("last_name", child.getLastName());
                            map.put("username", child.getUsername());
                            map.put("password", child.getPassword());
                            map.put("level", child.getLevel());
                            map.put("address", child.getAddress());
                            map.put("dob", child.getDateOfBirth());
                            map.put("gender", child.getGender());
                            Log.d("signupError", map.toString());
                            return map;
                        }
                    };
                    Volley.newRequestQueue(view.getContext()).add(request);
                }
            }
        });
        return myView;
    }

    private void bindViews() {
        etLevel = myView.findViewById(R.id.etLevel);
        etAddress = myView.findViewById(R.id.etAddress);
        rgGender = myView.findViewById(R.id.rgGender);
        btnNext = myView.findViewById(R.id.btnNext);
        btnBack = myView.findViewById(R.id.btnBack);
        tvDOB = myView.findViewById(R.id.tvDOB);

        sp = getActivity().getSharedPreferences("myLoginData", 0);
        editor = sp.edit();
    }

    private void showCalender(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(myView.getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                tvDOB.setText(day+"/"+(month+1)+"/"+year);
            }
        }, year, month, day);

        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }
}
