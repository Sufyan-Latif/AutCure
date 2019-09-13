package com.example.sufyanlatif.myapplication.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.activities.ChildHomeActivity;
import com.example.sufyanlatif.myapplication.activities.ParentHomeActivity;
import com.example.sufyanlatif.myapplication.models.Parent;
import com.example.sufyanlatif.myapplication.utils.Constants;
import com.example.sufyanlatif.myapplication.utils.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupTwoParentFragment extends Fragment {

    View myView;
    Parent parent = Parent.getInstance();
    TextInputEditText etPhoneNumber, etEmailAddress, etAddress;
    RadioGroup rgGender;
    Button btnNext, btnBack;
    String gender = "";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public SignupTwoParentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_signup_two_parent, container, false);

        bindViews();

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

                if (Utility.isEmpty(etPhoneNumber, "Enter Phone Number") ||
                        Utility.isEmpty(etEmailAddress, "Enter Email Address") ||
                        Utility.isEmpty(etAddress, "Enter Address")){
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(etEmailAddress.getText().toString().trim()).matches())
                {
                    etEmailAddress.requestFocus();
                    etEmailAddress.setError("Please Enter a valid email address");
                    return;
                }
                if (gender.equals("")){
                    Toast.makeText(myView.getContext(), "Choose Gender", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    parent.setPhoneNumber(etPhoneNumber.getText().toString());
                    parent.setAddress(etAddress.getText().toString());
                    parent.setEmailAddress(etEmailAddress.getText().toString());
                    parent.setGender(gender);

                    editor.putString("type", "parent");
                    editor.putString("id", parent.getId());
                    editor.putString("first_name", parent.getFirstName());
                    editor.putString("last_name", parent.getLastName());
                    editor.putString("address", parent.getAddress());
                    editor.putString("gender", parent.getGender());
                    editor.putString("username", parent.getUsername());
                    editor.putString("password", parent.getPassword());
                    editor.putString("phone", parent.getphoneNumber());
                    editor.putString("email", parent.getEmailAddress());
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

                                            Intent intent= new Intent(getActivity().getBaseContext(), ParentHomeActivity.class);
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
                            map.put("first_name", parent.getFirstName());
                            map.put("last_name", parent.getLastName());
                            map.put("username", parent.getUsername());
                            map.put("password", parent.getPassword());
                            map.put("address", parent.getAddress());
                            map.put("phone", parent.getphoneNumber());
                            map.put("email", parent.getEmailAddress());
                            map.put("phone_scope", "public");
                            map.put("email_scope", "public");
                            map.put("gender", parent.getGender());
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
        etAddress = myView.findViewById(R.id.etAddress);
        etPhoneNumber = myView.findViewById(R.id.etPhoneNumber);
        etEmailAddress = myView.findViewById(R.id.etEmailAddress);
        rgGender = myView.findViewById(R.id.rgGender);
        btnNext = myView.findViewById(R.id.btnNext);
        btnBack = myView.findViewById(R.id.btnBack);

        sp = getActivity().getSharedPreferences("myLoginData", 0);
        editor = sp.edit();
    }
}
