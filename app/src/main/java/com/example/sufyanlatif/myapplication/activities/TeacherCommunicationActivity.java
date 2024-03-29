package com.example.sufyanlatif.myapplication.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.utils.Constants;

public class TeacherCommunicationActivity extends AppCompatActivity {

    Button dialNumber, sendMail;
    TextView tvname, tvaddress;
    ImageView imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_communication);

        dialNumber=findViewById(R.id.btnTeacherDialNumber);
        sendMail=findViewById(R.id.btnTeacherSendMail);
        tvname= findViewById(R.id.tvname);
        tvaddress= findViewById(R.id.tvaddress);
        imgProfile= findViewById(R.id.imgProfile);

        String RETRIEVE_URL = Constants.BASE_URL + "images/parent1.jpeg";
        Glide.with(this).load(RETRIEVE_URL).placeholder(R.drawable.ic_couple).into(imgProfile);

        Intent intent= getIntent();
        String name= intent.getStringExtra("first_name")+ " "+ intent.getStringExtra("last_name");
        String address= intent.getStringExtra("address");
        tvname.setText(tvname.getText().toString()+name);
        tvaddress.setText(tvaddress.getText().toString()+address);

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
