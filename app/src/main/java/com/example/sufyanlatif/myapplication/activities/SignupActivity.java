package com.example.sufyanlatif.myapplication.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.fragments.ChildSignupOneFragment;
import com.example.sufyanlatif.myapplication.fragments.ParentSignupOneFragment;
import com.example.sufyanlatif.myapplication.fragments.SignupOneFragment;
import com.example.sufyanlatif.myapplication.fragments.TeacherSignupOneFragment;
import com.example.sufyanlatif.myapplication.utils.Constants;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, new SignupOneFragment());
        
//        if (Constants.currModel.equalsIgnoreCase("child"))
//            fragmentTransaction.add(R.id.fragmentContainer, new ChildSignupOneFragment());
//        else if (Constants.currModel.equalsIgnoreCase("parent"))
//            fragmentTransaction.add(R.id.fragmentContainer, new ParentSignupOneFragment());
//        if (Constants.currModel.equalsIgnoreCase("teacher"))
//            fragmentTransaction.add(R.id.fragmentContainer, new TeacherSignupOneFragment());

        fragmentTransaction.commit();
    }
}
