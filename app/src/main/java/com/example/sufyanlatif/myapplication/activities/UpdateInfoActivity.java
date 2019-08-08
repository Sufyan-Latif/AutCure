package com.example.sufyanlatif.myapplication.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.adapters.UpdateInfoAdapter;
import com.example.sufyanlatif.myapplication.models.Teacher;
import com.soundcloud.android.crop.Crop;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateInfoActivity extends AppCompatActivity {

    Button editPhoto;
    Bitmap bitmap;
    CircleImageView profileImage;
    String id;
    String URL = "https://autcureapp1.000webhostapp.com/upload_image.php";
    ListView listViewUpdateInfo;
    RecyclerView updateInfoRecyclerView;
//    Button cropImage;
//    ImageView resultView;
    EditText etAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        bindViews();

        String[] titles = {"First Name", "Last Name","Temp", "Temp2", "Password", "Username"};
        String[] subtitles = {"Sufyan","Latif","Temp","Temp2", "12345", "hassan101"};
        int[] icons = {R.drawable.ic_user,
                R.drawable.ic_user,
                R.drawable.ic_user,
                R.drawable.ic_user,
                R.drawable.ic_user,
                R.drawable.ic_user};
        updateInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateInfoRecyclerView.setAdapter(new UpdateInfoAdapter(titles, subtitles, icons));



//        cropImage= findViewById(R.id.btn_crop_photo);
//
//        cropImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                resultView = findViewById(R.id.resultView);
//                Crop.pickImage(UpdateInfoActivity.this);
//                resultView.setImageDrawable(null);
//            }
//        });

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String RETRIEVE_URL = "https://autcureapp1.000webhostapp.com/images/1.jpeg";
        ImageRequest imageRequest = new ImageRequest(RETRIEVE_URL,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        progressDialog.dismiss();
                        profileImage.setImageBitmap(response);

                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(UpdateInfoActivity.this, "Error = "+error, Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(imageRequest);

        editPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent i = new Intent(Intent.ACTION_PICK);
//                i.setType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(i, "Select Image"), 1);

//                Intent intent = new Intent();
//                intent.setType("image/*");
////                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.setAction(android.content.Intent.ACTION_VIEW);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });

        etAlertDialog = findViewById(R.id.et_update);
        etAlertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                displayDialog("", )
                final AlertDialog.Builder builder = new AlertDialog.Builder(UpdateInfoActivity.this);
                LayoutInflater inflater = getLayoutInflater();

                builder.setView(inflater.inflate(R.layout.update_info_alert_dialog_layout, null))
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(UpdateInfoActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void bindViews() {
        profileImage = findViewById(R.id.profile_image);
        editPhoto = findViewById(R.id.btn_edit_photo);
        listViewUpdateInfo = findViewById(R.id.listview_update_info);
        updateInfoRecyclerView = findViewById(R.id.update_info_recycler_view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK){
//            beginCrop(data.getData());
//        }
//        else if (requestCode == Crop.REQUEST_CROP){
//            handleCrop(resultCode, data);
//
//        }

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

//                Crop.pickImage(this);
                profileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Teacher teacher = Teacher.getInstance();
            id = teacher.getId();

            uploadPicture(id, getStringImage(bitmap));
        }

        if (requestCode == 2 && resultCode == RESULT_OK  && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);

            if (cursor == null || cursor.getCount() < 1) {
                return; // no cursor or no record. DO YOUR ERROR HANDLING
            }

            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

            if(columnIndex < 0) // no column index
                return; // DO YOUR ERROR HANDLING

            String picturePath = cursor.getString(columnIndex);

            cursor.close(); // close cursor

//            Bitmap photo = decodeFilePath(picturePath.toString());
//
//            List<Bitmap> bitmap = new ArrayList<Bitmap>();
//            bitmap.add(photo);
//            ImageAdapter imageAdapter = new ImageAdapter(
//                    AddIncidentScreen.this, bitmap);
//            imageAdapter.notifyDataSetChanged();
//            newTagImage.setAdapter(imageAdapter);
        }

    }

    private void handleCrop(int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
//            resultView.setImageURI(Crop.getOutput(data));
        }
        else if (resultCode == Crop.RESULT_ERROR){
            Toast.makeText(this, "Error : "+Crop.getError(data).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void beginCrop(Uri data) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(data, destination).asSquare().start(UpdateInfoActivity.this);
    }

    private String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);

        return encodedImage;
    }

    private void uploadPicture(final String id, final String photo) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1"))
                                Toast.makeText(UpdateInfoActivity.this, "Success!", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(UpdateInfoActivity.this, "Try Again!"+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(UpdateInfoActivity.this, "Try Again!"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("photo", photo);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
