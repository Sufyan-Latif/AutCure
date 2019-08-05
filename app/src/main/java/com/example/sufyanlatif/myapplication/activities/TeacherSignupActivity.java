package com.example.sufyanlatif.myapplication.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.fragments.TeacherSignupOneFragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TeacherSignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_signup);

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame,new TeacherSignupOneFragment());
        fragmentTransaction.commit();

//        Button signup= findViewById(R.id.button6);
//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(TeacherSignupActivity.this,TeacherHomeActivity.class);
//                finish();
//                startActivity(intent);
//            }
//        });
//
//        Button cancel = findViewById(R.id.button7);
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }
}
