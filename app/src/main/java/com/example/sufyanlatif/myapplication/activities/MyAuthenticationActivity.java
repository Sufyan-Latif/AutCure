package com.example.sufyanlatif.myapplication.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sufyanlatif.myapplication.R;
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

public class MyAuthenticationActivity extends AppCompatActivity {

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    EditText etUsername, etPassword;
    Button btnLogin, btnsignup;
    CoordinatorLayout myAuthenticationLayout;
    ProgressDialog progressDialog;
//    CheckBox cbRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_authentication);

        bindViews();

//        if (!sp.getString("type", "null").equals(null)) {
//
//            if (sp.getString("type", "null").equals("parents")) {
//                Intent intent = new Intent(MyAuthenticationActivity.this, ParentHomeActivity.class);
//                finish();
//                startActivity(intent);
//            } else if (sp.getString("type", "null").equals("students")) {
//                Intent intent = new Intent(MyAuthenticationActivity.this, ChildHomeActivity.class);
//                finish();
//                startActivity(intent);
//            }
//            if (sp.getString("type", "null").equals("teacher")) {
//                Intent intent = new Intent(MyAuthenticationActivity.this, TeacherWelcomeActivity.class);
//                finish();
//                startActivity(intent);
//            }
//        }

        Constants.currModel = "";

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();

                if (Utility.isInternetConnected(MyAuthenticationActivity.this)) {

                    progressDialog.show();
                    StringRequest request = new StringRequest(Request.Method.POST, Constants.BASE_URL + "my_login.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String code = jsonObject.getString("code");
                                if (code.equalsIgnoreCase("success")){

                                    editor.putString("username", username);
                                    editor.putString("password", password);
                                    editor.apply();

                                    String type = jsonObject.getString("type");
                                    JSONArray user = jsonObject.getJSONArray("user");
                                    JSONObject userObj = user.getJSONObject(0);

                                    Intent intent;
                                    switch (type){
                                        case "child":
//                                            Child child = Child.getInstance();
                                            editor.putString("type", "child");
//                                            child.setId(userObj.getString("id"));
                                            editor.putString("id", userObj.getString("id"));
//                                            child.setUsername(userObj.getString("username"));
//                                            child.setFirstName(userObj.getString("first_name"));
//                                            child.setLastName(userObj.getString("last_name"));
                                            editor.putString("first_name", userObj.getString("first_name"));
                                            editor.putString("last_name", userObj.getString("last_name"));
//                                            child.setAddress(userObj.getString("address"));
                                            editor.putString("address", userObj.getString("address"));
//                                            child.setGender(userObj.getString("gender"));
                                            editor.putString("gender", userObj.getString("gender"));
                                            editor.apply();
                                            intent = new Intent(MyAuthenticationActivity.this, ChildHomeActivity.class);
                                            progressDialog.dismiss();
                                            finish();
                                            startActivity(intent);
                                            break;
                                        case "teacher":
//                                            Teacher teacher = Teacher.getInstance();
                                            editor.putString("type", "teacher");
//                                            teacher.setId(userObj.getString("id"));
                                            editor.putString("id", userObj.getString("id"));
//                                            teacher.setUsername(userObj.getString("username"));
//                                            teacher.setFirstName(userObj.getString("first_name"));
//                                            teacher.setLastName(userObj.getString("last_name"));
                                            editor.putString("first_name", userObj.getString("first_name"));
                                            editor.putString("last_name", userObj.getString("last_name"));
//                                            teacher.setAddress(userObj.getString("address"));
                                            editor.putString("address", userObj.getString("address"));
//                                            teacher.setGender(userObj.getString("gender"));
                                            editor.putString("gender", userObj.getString("gender"));
//                                            teacher.setPhoneNumber(userObj.getString("phone"));
                                            editor.putString("phone", userObj.getString("phone"));
//                                            teacher.setEmailAddress(userObj.getString("email"));
                                            editor.putString("email", userObj.getString("email"));
                                            editor.apply();                                             // TO DISPLAY ON NAVIGATION DRAWER
                                            intent = new Intent(MyAuthenticationActivity.this, TeacherWelcomeActivity.class);
                                            progressDialog.dismiss();
                                            startActivity(intent);
                                            finish();
                                            break;
                                        case "parent":
//                                            Parent parent = Parent.getInstance();
                                            editor.putString("type", "parent");
//                                            parent.setId(userObj.getString("id"));
                                            editor.putString("id", userObj.getString("id"));
//                                            parent.setUsername(userObj.getString("username"));
                                            editor.putString("username", userObj.getString("username"));
//                                            parent.setFirstName(userObj.getString("first_name"));
                                            editor.putString("first_name", userObj.getString("first_name"));
//                                            parent.setLastName(userObj.getString("last_name"));
                                            editor.putString("last_name", userObj.getString("last_name"));
//                                            parent.setAddress(userObj.getString("address"));
                                            editor.putString("address", userObj.getString("address"));
//                                            parent.setGender(userObj.getString("gender"));
                                            editor.putString("gender", userObj.getString("gender"));
//                                            parent.setPhoneNumber(userObj.getString("phone"));
                                            editor.putString("phone", userObj.getString("phone"));
//                                            parent.setEmailAddress(userObj.getString("email"));
                                            editor.putString("email", userObj.getString("email"));
                                            editor.apply();
                                            intent = new Intent(MyAuthenticationActivity.this, ParentHomeActivity.class);
                                            progressDialog.dismiss();
                                            finish();
                                            startActivity(intent);
                                            break;
                                    }
                                }
                                else {
                                    progressDialog.dismiss();
                                    Toast.makeText(MyAuthenticationActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(MyAuthenticationActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("username", username);
                            map.put("password", password);
                            return map;
                        }
                    };
                    Volley.newRequestQueue(MyAuthenticationActivity.this).add(request);

                } else {
                    Snackbar snackbar = Snackbar.make(myAuthenticationLayout, "No Internet Connection", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    btnLogin.callOnClick();
                                }
                            });
                    snackbar.show();
                }
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignupDialog();
            }
        });
    }

    private void showSignupDialog(){
        final Dialog dialog = new Dialog(this, R.style.custom_Dialog);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.signup_as_dialog, null);

        Button btnChild = dialogView.findViewById(R.id.btnChild);
        Button btnTeacher = dialogView.findViewById(R.id.btnTeacher);
        Button btnParent = dialogView.findViewById(R.id.btnParent);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        final Intent newIntent = new Intent(MyAuthenticationActivity.this, SignupActivity.class);
        btnChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.currModel = "child";
                finish();
                startActivity(newIntent);
            }
        });

        btnTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.currModel = "teacher";
                finish();
                startActivity(newIntent);
            }
        });

        btnParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.currModel = "parent";
                finish();
                startActivity(newIntent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.setContentView(dialogView);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void bindViews() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnsignup = findViewById(R.id.btnSignup);
//        cbRememberMe = findViewById(R.id.cbRememberMe);
        myAuthenticationLayout = findViewById(R.id.myAuthenticationLayout);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading... Please Wait");
        progressDialog.setCancelable(false);

        sp = getSharedPreferences("myLoginData", 0);
        editor = sp.edit();
    }
}
