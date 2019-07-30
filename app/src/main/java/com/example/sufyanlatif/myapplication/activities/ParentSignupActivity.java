package com.example.sufyanlatif.myapplication.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.fragments.ParentSignupOneFragment;

public class ParentSignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_signup);

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame,new ParentSignupOneFragment());
        fragmentTransaction.commit();

//        Button signup= findViewById(R.id.button6);
//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(ParentSignupActivity.this,ParentHomeActivity.class);
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
