package com.example.sufyanlatif.myapplication.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.activities.AuthenticationActivity;

import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChildSignupOneFragment extends Fragment {

    EditText first_name,last_name,username,password, conPassword;
    Button next, cancel;
    String f_name,l_name,user,pass,conpass;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +
                    "(?=.*[a-zA-z])" +
                    "$"
                    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_child_signup_one, container, false);
        first_name= view.findViewById(R.id.editchildfirst);
        last_name= view.findViewById(R.id.editchildlast);
        username= view.findViewById(R.id.editchildusername);
        password= view.findViewById(R.id.editchildpassword);
        conPassword = view.findViewById(R.id.editchconpassword);

        next= view.findViewById(R.id.childbtnnext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateName() | !validateEmail() | !validatePassword()) {
                    return;
                }
                f_name=first_name.getText().toString();
                l_name=last_name.getText().toString();
                user=username.getText().toString();
                pass=password.getText().toString();
                conpass = conPassword.getText().toString();

                Bundle bundle= new Bundle();
                bundle.putString("first_name",f_name);
                bundle.putString("last_name",l_name);
                bundle.putString("username",user);
                bundle.putString("password",pass);

                FragmentManager fragmentManager= getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                ChildSignupTwoFragment ChildSignupTwoFragment = new ChildSignupTwoFragment();
                ChildSignupTwoFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.frame, ChildSignupTwoFragment);
                fragmentTransaction.commit();
            }
        });

        cancel= view.findViewById(R.id.childbtnback1);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(), AuthenticationActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });
        return view ;
    }

    private boolean validateName()
    {
        String fName= first_name.getText().toString();
        String lName= last_name.getText().toString();

        if (fName.isEmpty() || lName.isEmpty())
        {
            Toast.makeText(getActivity().getBaseContext(),"Field can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }

    private boolean validateEmail()
    {
        String emailInput = username.getText().toString();

        if (emailInput.isEmpty())
        {
            Toast.makeText(getActivity().getBaseContext(),"Field can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (emailInput.length()<5){
            Toast.makeText(getActivity().getBaseContext(),"username must be atleast 5 characters long", Toast.LENGTH_SHORT).show();
            return false;
  }
//        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches())
//        {
//            Toast.makeText(getActivity().getBaseContext(),"Please Enter a valid email address", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        else
            return true;
    }
    private boolean validatePassword()
    {
        String passwordInput = password.getText().toString();
        String conpasswordInput=conPassword.getText().toString();
        if (passwordInput.isEmpty() ||conpasswordInput.isEmpty())
        {
            Toast.makeText(getActivity().getBaseContext(),"Field can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (passwordInput.length()<5 )
        {
            Toast.makeText(getActivity().getBaseContext(),"Password must be atleast 5 characters long", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!passwordInput.equals(conpasswordInput))
        {
            Toast.makeText(getActivity().getBaseContext(),"Password mismatch", Toast.LENGTH_SHORT).show();
            return false;
        }
//        if (!PASSWORD_PATTERN.matcher(passwordInput).matches())
//        {
//            Toast.makeText(getActivity().getBaseContext(),"Password must have alphabets and numbers", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        else
            return true;
    }
}
