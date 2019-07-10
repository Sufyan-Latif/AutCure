package com.example.sufyanlatif.myapplication;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class childsignup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_childsignup);



//        EditText username= findViewById(R.id.editText2);
//        EditText password= findViewById(R.id.editText3);
//        EditText studentId= findViewById(R.id.editText5);
//        EditText dob= findViewById(R.id.editText6);
//        EditText level= findViewById(R.id.editText7);

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame,new childup());
        fragmentTransaction.commit();


//        Button signup= findViewById(R.id.button6);
//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(childsignup.this,childhome.class);
//                finish();
//                startActivity(intent);
//            }
//        });

////        Button cancel = findViewById(R.id.button7);
////        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

    }
}
