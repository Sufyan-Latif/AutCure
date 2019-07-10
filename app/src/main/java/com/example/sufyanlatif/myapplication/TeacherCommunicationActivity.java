package com.example.sufyanlatif.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TeacherCommunicationActivity extends AppCompatActivity {

    Button dialNumber, sendMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_communication);

        dialNumber=findViewById(R.id.btnTeacherDialNumber);
        sendMail=findViewById(R.id.btnTeacherSendMail);

        dialNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent= new Intent(Intent.ACTION_DIAL);
                String num= "03364087008";
                dialIntent.setData(Uri.parse("tel:"+num));
                startActivity(dialIntent);
            }
        });

        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("email"));
                intent.putExtra(Intent.EXTRA_EMAIL, "m.latif450@gmail.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, "From instructor of Ali");
                intent.putExtra(Intent.EXTRA_TEXT, "Email Body: ");
                intent.setType("message/rfc822");
                Intent chooser= Intent.createChooser(intent, "Launch Email");
                startActivity(chooser);
            }
        });
    }
}
