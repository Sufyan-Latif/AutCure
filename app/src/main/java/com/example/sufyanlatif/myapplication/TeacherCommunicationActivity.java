package com.example.sufyanlatif.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TeacherCommunicationActivity extends AppCompatActivity {

    Button dialNumber, sendMail;
    TextView tvname, tvaddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_communication);

        dialNumber=findViewById(R.id.btnTeacherDialNumber);
        sendMail=findViewById(R.id.btnTeacherSendMail);
        tvname= findViewById(R.id.tvname);
        tvaddress= findViewById(R.id.tvaddress);

        Intent intent= getIntent();
        String name= intent.getStringExtra("first_name")+ " "+ intent.getStringExtra("last_name");
        String address= intent.getStringExtra("address");
        tvname.setText("Name: "+name);
        tvaddress.setText("Address: "+address);

        final String phone= intent.getStringExtra("phone");
        final String email= intent.getStringExtra("email");

        dialNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!phone.equals("")){
                    Intent dialIntent= new Intent(Intent.ACTION_DIAL);
                    dialIntent.setData(Uri.parse("tel:"+phone));
                    startActivity(dialIntent);
                }
                else {
                    Toast.makeText(TeacherCommunicationActivity.this,"Not Available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!email.equals("")){
                    Intent intent= new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.fromParts("mailto", email, null));
//                    intent.putExtra(Intent.EXTRA_EMAIL, email);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Subject: ");
                    intent.putExtra(Intent.EXTRA_TEXT, "Body: ");
//                    intent.setType("message/rfc822");
                    Intent chooser= Intent.createChooser(intent, "Launch Email");
                    startActivity(chooser);
                }
                else {
                    Toast.makeText(TeacherCommunicationActivity.this,"Not Available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
