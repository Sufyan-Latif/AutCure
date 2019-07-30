package com.example.sufyanlatif.myapplication.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.activities.TeacherHomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherSignupTwoFragment extends Fragment {

    String firstname,lastname,usernam,passwor,phoneNumber,Qualification,gende;
    EditText phone, qualification;
    ProgressBar teacherProgressBar;
    public TeacherSignupTwoFragment() {
        // Required empty public constructor
    }
    private RequestQueue requestQueue;
    private static final String URL="https://autcure.000webhostapp.com/signup_teachers.php";
    private StringRequest request;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_teacher_signup_two, container, false);

        phone=(EditText) view.findViewById(R.id.edittecphone);
        qualification=(EditText) view.findViewById(R.id.edittecqual);
        teacherProgressBar = view.findViewById(R.id.teacherprogressbarsignup);

//        firstname="sufyan1";
//        lastname="latif1";
//        usernam="sufyanlatif1234";
//        passwor="sufyan7861";
//        phone_number="360012345";
//        addres="sialkot1";
//        gende="male2";

        Bundle bundle= this.getArguments();

//        if (bundle!=null)
//        {
        firstname=bundle.getString("first_name");
        lastname=bundle.getString("last_name");
        usernam=bundle.getString("username");
        passwor=bundle.getString("password");
//        }

        RadioGroup gendergroup =view.findViewById(R.id.teachergendergroup);
        gendergroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.teachergendermale)
                {
                    gende="Male";
                }
                else if (checkedId==R.id.teachergenderfemale)
                {
                    gende= "Female";
                }
            }
        });

//        RadioGroup gendergroup =view.findViewById(R.id.gendergroup);
//        int selectedId= gendergroup.getCheckedRadioButtonId();
//        RadioButton genderbutton= view.findViewById(selectedId);
//        gende=genderbutton.getText().toString();
//        gende="Male";
//        bundle.putString("phone",phone.getText().toString());
//        bundle.putString("address",address.getText().toString());


        Button signup= view.findViewById(R.id.btntecup);
        Button back= view.findViewById(R.id.btntecback2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction= getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame,new TeacherSignupOneFragment());
                fragmentTransaction.commit();
            }
        });
        requestQueue= Volley.newRequestQueue(getActivity().getBaseContext());
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teacherProgressBar.setVisibility(View.VISIBLE);

                phoneNumber=phone.getText().toString();
                Qualification=qualification.getText().toString();

//                Intent intent= new Intent(getActivity().getBaseContext(), TeacherHomeActivity.class);
//                getActivity().finish();
//                startActivity(intent);
                request = new StringRequest(Request.Method.POST, "https://autcure.000webhostapp.com/signup_teachers.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.d("ParentSignupTwoFragment","Register Responce: "+response);

                        try {
                            Intent intent= new Intent(getActivity().getBaseContext(), TeacherHomeActivity.class);
                            getActivity().finish();
                            startActivity(intent);
//                            JSONObject jsonObject= new JSONObject(response);
//                            boolean error= jsonObject.getBoolean("error");
//                            if (!error)
//                            {
//                                JSONObject first_name= jsonObject.getJSONObject("first_name");
//                                JSONObject last_name= jsonObject.getJSONObject("last_name");
//                                JSONObject username= jsonObject.getJSONObject("username");
//                                JSONObject password= jsonObject.getJSONObject("password");
//                                JSONObject phone_number= jsonObject.getJSONObject("phone_number");
//                                JSONObject address= jsonObject.getJSONObject("address");
//                                JSONObject gender= jsonObject.getJSONObject("gender");

                            JSONArray jsonArray = new JSONArray(response);

                            for(int j = 0; j<jsonArray.length(); j++) {
                                JSONObject jObj = jsonArray.getJSONObject(j);
                                String n = jObj.getString("first_name");
                                String na = jObj.getString("last_name");
                                String e = jObj.getString("username");
                                String p = jObj.getString("password");
                                String ph = jObj.getString("phone_number");
                                String ad = jObj.getString("qualification");
                                String ge = jObj.getString("gender");

                            }

//                                Intent intent= new Intent(getActivity().getBaseContext(),ParentHomeActivity.class);
//                                getActivity().finish();
//                                startActivity(intent);
//                            }
//                            else
//                            {
//                                String errorMsg= jsonObject.getString("error_msg");
//                                Toast.makeText(getActivity().getBaseContext(),"Error",Toast.LENGTH_SHORT).show();
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(getActivity().getBaseContext(),"Json Error : "+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Log.e("ParentSignupTwoFragment", "Login Error : "+error.getMessage());
                        Toast.makeText(getActivity().getBaseContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap= new HashMap<String, String>();
                        hashMap.put("first_name",firstname);
                        hashMap.put("last_name",lastname);
                        hashMap.put("username",usernam);
                        hashMap.put("password",passwor);
                        hashMap.put("phone_number",phoneNumber);
                        hashMap.put("qualification",Qualification);
                        hashMap.put("gender",gende);

                        return hashMap;
                    }
                };
                Volley.newRequestQueue(getActivity().getBaseContext()).add(request);



                teacherProgressBar.setVisibility(View.GONE);


            }
        });
        return view;
    }

}
