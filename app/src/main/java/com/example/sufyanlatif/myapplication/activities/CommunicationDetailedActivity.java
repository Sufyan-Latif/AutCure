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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.models.Parent;
import com.example.sufyanlatif.myapplication.models.Teacher;
import com.example.sufyanlatif.myapplication.utils.Constants;

public class CommunicationDetailedActivity extends AppCompatActivity {

    Button btnDialNumber, btnSendMail;
    TextView tvName, tvAddress;
    ImageView imgProfile;
    Parent parent;
    Teacher teacher;
    String firstName, lastName, address, phoneNumber, emailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication_detailed);

        bindViews();

        String RETRIEVE_URL = Constants.BASE_URL + "images/parent1.jpeg";
        Glide.with(this).load(RETRIEVE_URL).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.ic_couple).into(imgProfile);

        String model = getIntent().getStringExtra("model");
        if (model.equalsIgnoreCase("Teacher")){
            parent = (Parent) getIntent().getSerializableExtra("parent");
            firstName = parent.getFirstName();
            lastName = parent.getLastName();
            address = parent.getAddress();
            phoneNumber = parent.getphoneNumber();
            emailAddress = parent.getEmailAddress();
        }
        else if (model.equalsIgnoreCase("Parent")){
            teacher = (Teacher) getIntent().getSerializableExtra("teacher");
            firstName = teacher.getFirstName();
            lastName = teacher.getLastName();
            address = teacher.getAddress();
            phoneNumber = teacher.getphoneNumber();
            emailAddress = teacher.getEmailAddress();
        }

        tvName.setText(tvName.getText() +" "+ firstName + " " + lastName);
        tvAddress.setText(tvAddress.getText() +" "+ address);

        btnDialNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!phoneNumber.equals("")){
                    Intent dialIntent= new Intent(Intent.ACTION_DIAL);
                    dialIntent.setData(Uri.parse("tel:"+phoneNumber));
                    startActivity(dialIntent);
                }
                else {
                    Toast.makeText(CommunicationDetailedActivity.this,"Not Available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!emailAddress.equals("")){
                    Intent intent= new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.fromParts("mailto", emailAddress, null));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Subject: ");
                    intent.putExtra(Intent.EXTRA_TEXT, "Body: ");
                    Intent chooser= Intent.createChooser(intent, "Launch Email");
                    startActivity(chooser);
                }
                else {
                    Toast.makeText(CommunicationDetailedActivity.this,"Not Available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void bindViews() {
        btnDialNumber=findViewById(R.id.btnDialNumber);
        btnSendMail=findViewById(R.id.btnSendMail);
        tvName= findViewById(R.id.tvName);
        tvAddress= findViewById(R.id.tvAddress);
        imgProfile= findViewById(R.id.imgProfile);
    }
}
