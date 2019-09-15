package com.example.sufyanlatif.myapplication.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.activities.MyAuthenticationActivity;
import com.example.sufyanlatif.myapplication.models.Child;
import com.example.sufyanlatif.myapplication.models.Parent;
import com.example.sufyanlatif.myapplication.models.Teacher;
import com.example.sufyanlatif.myapplication.utils.Constants;
import com.example.sufyanlatif.myapplication.utils.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupOneFragment extends Fragment {

    View view;
    TextInputEditText etFirstName, etLastName, etUsername, etPassword, etConPassword;
    ImageView imgModel;
    Button btnNext, btnBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_signup_one, container, false);

        bindViews();

        switch (Constants.currModel){
            case "child":
                imgModel.setImageDrawable(getResources().getDrawable(R.drawable.boy));
                break;
            case "teacher":
                imgModel.setImageDrawable(getResources().getDrawable(R.drawable.ic_presentation));
                break;
            case "parent":
                imgModel.setImageDrawable(getResources().getDrawable(R.drawable.ic_couple));
                break;
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (Utility.isEmpty(etFirstName, "Enter First Name") ||
                        Utility.isEmpty(etLastName, "Enter Last Name") ||
                        Utility.isEmpty(etUsername, "Enter Username") ||
                        Utility.isEmpty(etPassword, "Enter Password") ||
                        Utility.isEmpty(etConPassword, "Enter Password Again")) {
//                    if (!Utility.validateConPass(etConPassword, etPassword)){
                    Toast.makeText(view.getContext(), "Enter Data", Toast.LENGTH_SHORT).show();
//                    }
                } else if (!Utility.validateConPass(etConPassword, etPassword)) {
                    Toast.makeText(view.getContext(), "Password Mismatch", Toast.LENGTH_SHORT).show();
                } else if (etUsername.getText().toString().length()<5){
                    etUsername.setError("Username must be atleast 5 characters long");
                }else if (etPassword.getText().toString().length()<5){
                    etPassword.setError("Password must be atleast 5 characters long");
                }
                else {

                    switch (Constants.currModel) {
                        case "child":
                            Child child = Child.getInstance();
                            child.setFirstName(etFirstName.getText().toString());
                            child.setLastName(etLastName.getText().toString());
                            child.setUsername(etUsername.getText().toString());
                            child.setPassword(etPassword.getText().toString());
                            break;
                        case "teacher":
                            Teacher teacher = Teacher.getInstance();
                            teacher.setFirstName(etFirstName.getText().toString());
                            teacher.setLastName(etLastName.getText().toString());
                            teacher.setUsername(etUsername.getText().toString());
                            teacher.setPassword(etPassword.getText().toString());
                            break;
                        case "parent":
                            Parent parent = Parent.getInstance();
                            parent.setFirstName(etFirstName.getText().toString());
                            parent.setLastName(etLastName.getText().toString());
                            parent.setUsername(etUsername.getText().toString());
                            parent.setPassword(etPassword.getText().toString());
                            break;
                    }

                    final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                    StringRequest request = new StringRequest(Request.Method.POST,
                            Constants.BASE_URL + "validate_username.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONArray jsonArray = jsonObject.getJSONArray("response");
                                        JSONObject object = jsonArray.getJSONObject(0);
                                        if (object.getString("code").equalsIgnoreCase("success")) {

                                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                            if (Constants.currModel.equals("child"))
                                                fragmentTransaction.replace(R.id.fragmentContainer, new SignupTwoChildFragment());
                                            if (Constants.currModel.equals("teacher"))
                                                fragmentTransaction.replace(R.id.fragmentContainer, new SignupTwoTeacherFragment());
                                            if (Constants.currModel.equals("parent"))
                                                fragmentTransaction.replace(R.id.fragmentContainer, new SignupTwoParentFragment());
                                            progressDialog.dismiss();
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();
                                            Toast.makeText(view.getContext(), "Validated", Toast.LENGTH_SHORT).show();
                                        } else if (object.getString("code").equalsIgnoreCase("failure")) {
                                            progressDialog.dismiss();
                                            Toast.makeText(view.getContext(), "Username Already Exists", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(view.getContext(), "Check Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("model", Constants.currModel);
                            map.put("username", etUsername.getText().toString().trim());
                            return map;
                        }
                    };
                    Volley.newRequestQueue(view.getContext()).add(request);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(getActivity(), MyAuthenticationActivity.class);

            @Override
            public void onClick(View view) {
                if (etFirstName.getText().toString().equals("") &&
                        etLastName.getText().toString().equals("") &&
                        etUsername.getText().toString().equals("") &&
                        etPassword.getText().toString().equals("") &&
                        etConPassword.getText().toString().equals("")) {

                    startActivity(intent);
                    getActivity().finish();

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                            .setTitle("Alert!")
                            .setMessage("Discard Changes?")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(intent);
                                    getActivity().finish();
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
            }
        });
        return view;
    }

    private void bindViews() {
        imgModel = view.findViewById(R.id.imgModel);
        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);
        etUsername = view.findViewById(R.id.etUsername);
        etPassword = view.findViewById(R.id.etPassword);
        etConPassword = view.findViewById(R.id.etConPassword);
        btnNext = view.findViewById(R.id.btnNext);
        btnBack = view.findViewById(R.id.btnBack);
    }

//    public boolean validateFirstName(){
//
//        if (etFirstName.getText().toString().equals("")){
//            etFirstName.requestFocus();
//            etFirstName.setError("Enter First Name");
//            return false;
//        }
//        else {
//            etFirstName.setError(null);
//            return true;
//        }
//    }

}
