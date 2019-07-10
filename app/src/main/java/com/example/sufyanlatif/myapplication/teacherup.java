package com.example.sufyanlatif.myapplication;


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


/**
 * A simple {@link Fragment} subclass.
 */
public class teacherup extends Fragment {
    EditText first_name,last_name,username,password, conPassword;
    Button next, cancel;
    String f_name,l_name,user,pass, conpass;

    public teacherup() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_teacherup, container, false);
        first_name= view.findViewById(R.id.edittecfirst);
        last_name= view.findViewById(R.id.editteclast);
        username= view.findViewById(R.id.edittechusern);
        password= view.findViewById(R.id.edittecpass);
        conPassword = view.findViewById(R.id.edittecconpass);

        next= view.findViewById(R.id.btntecnext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateName() | !validateEmail() | !validatePassword())
                    return;

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
                teacherup2 tecup2= new teacherup2();
                tecup2.setArguments(bundle);
                fragmentTransaction.replace(R.id.frame,tecup2);
                fragmentTransaction.commit();
//                Toast.makeText(getActivity(),"name= "+f_name+" "+l_name,Toast.LENGTH_SHORT).show();
            }
        });

        cancel= view.findViewById(R.id.btntecback1);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(), authenticationact.class);
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
        else if (emailInput.length()<8){
            Toast.makeText(getActivity().getBaseContext(),"username must be atleast 8 characters long", Toast.LENGTH_SHORT).show();
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
        String conpasswordInputw=conPassword.getText().toString();

        if (passwordInput.isEmpty() || conpasswordInputw.isEmpty())
        {
            Toast.makeText(getActivity().getBaseContext(),"Field can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (passwordInput.length()<8 )
        {
            Toast.makeText(getActivity().getBaseContext(),"Password must be atleast 8 digits long", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!passwordInput.equals(conpasswordInputw))
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
