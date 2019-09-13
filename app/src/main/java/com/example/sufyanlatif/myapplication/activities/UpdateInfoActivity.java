package com.example.sufyanlatif.myapplication.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.adapters.UpdateInfoAdapter;
import com.example.sufyanlatif.myapplication.interfaces.OnUpdateInfoClick;
import com.example.sufyanlatif.myapplication.models.Parent;
import com.example.sufyanlatif.myapplication.models.Teacher;
import com.example.sufyanlatif.myapplication.utils.Constants;
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
    ImageView imgEditPhoto;
    String id;
    String URL = "https://autcureapp1.000webhostapp.com/upload_image.php";
    String[] titles;

    //    ListView listViewUpdateInfo;
    RecyclerView updateInfoRecyclerView;
    //    Button cropImage;
//    ImageView resultView;
    EditText etAlertDialog;
    Teacher teacher = Teacher.getInstance();
    Parent parent = Parent.getInstance();
    String[] subtitles;
    int[] icons;
    SharedPreferences spUpdateInfo;
    SharedPreferences.Editor editorUpdateInfo;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Button btnSave;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        bindViews();

        String id = spUpdateInfo.getString("id", "");
        String RETRIEVE_URL = "";
        if (spUpdateInfo.getString("type", "null").equals("teacher"))
            RETRIEVE_URL = Constants.BASE_URL + "images/teacher" + id + ".jpeg";
        else if (spUpdateInfo.getString("type", "null").equals("parent"))
            RETRIEVE_URL = Constants.BASE_URL + "images/parent" + id + ".jpeg";
        else if (spUpdateInfo.getString("type", "null").equals("child"))
            RETRIEVE_URL = Constants.BASE_URL + "images/child" + id + ".jpeg";


        Glide.with(this).load(RETRIEVE_URL).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Toast.makeText(UpdateInfoActivity.this, "Error Loading image", Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.ic_user).into(profileImage);

//        if (parent.getUsername() != null){
        if (spUpdateInfo.getString("type", "null").equals("child")) {
            titles = new String[]{"First Name", "Last Name", "Username", "Password", "Address"};
            subtitles = new String[]{spUpdateInfo.getString("first_name", ""), spUpdateInfo.getString("last_name", ""),
                    spUpdateInfo.getString("username", ""), "******", spUpdateInfo.getString("address", "")};
            icons = new int[]{R.drawable.ic_user,
                    R.drawable.ic_user,
                    R.drawable.ic_user,
                    R.drawable.ic_lock,
                    R.drawable.ic_address};
        } else {
            titles = new String[]{"First Name", "Last Name", "Username", "Password", "Address", "Phone Number", "Email Address"};

            subtitles = new String[]{spUpdateInfo.getString("first_name", ""), spUpdateInfo.getString("last_name", ""),
                    spUpdateInfo.getString("username", ""), "******", spUpdateInfo.getString("address", ""),
                    spUpdateInfo.getString("phone", ""), spUpdateInfo.getString("email", "")};
            icons = new int[]{R.drawable.ic_user,
                    R.drawable.ic_user,
                    R.drawable.ic_user,
                    R.drawable.ic_lock,
                    R.drawable.ic_address,
                    R.drawable.telephonehome,
                    R.drawable.ic_email_black_24dp};
        }

        updateInfoRecyclerView.setAdapter(new UpdateInfoAdapter(titles, subtitles, icons, new OnUpdateInfoClick() {
            @Override
            public void onClick(final int position, String title, String subTitle) {

                final Dialog dialog = new Dialog(UpdateInfoActivity.this);
                View dialogView = LayoutInflater.from(UpdateInfoActivity.this).inflate(R.layout.update_info_alert_dialog_layout, null);

                TextView tvDialog = dialogView.findViewById(R.id.tvTitleUpdateInfoDialog);
                final EditText etDialog = dialogView.findViewById(R.id.etUpdateInfoDialog);
                Button btnDialog = dialogView.findViewById(R.id.btnOkUpdateInfoDialog);

                tvDialog.setText(title);
                etDialog.setText(subTitle);
                btnDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String data = etDialog.getText().toString().trim();
                        boolean dataValidated = true;
                        if (data.length() == 0) {
                            etDialog.setError("Please enter details");
                            dataValidated = false;
                            return;
                        } else {
                            dataValidated = true;
                            etDialog.setError(null);
                        }
                        if (position == 2 && etDialog.getText().toString().length() < 5) {
                            etDialog.setError("Username must be atleast 5 characters long");
                            dataValidated = false;
                            return;
                        } else {
                            dataValidated = true;
                            etDialog.setError(null);
                        }
                        if (position == 3 && etDialog.getText().toString().length() < 5) {
                            etDialog.setError("Password must be atleast 5 characters long");
                            dataValidated = false;
                            return;
                        } else {
                            dataValidated = true;
                            etDialog.setError(null);
                        }

                        if (dataValidated) {
                            subtitles[position] = etDialog.getText().toString();
                            dialog.dismiss();

                            switch (position) {
                                case 0:
                                    editorUpdateInfo.putString("first_name", subtitles[position]);
                                    editorUpdateInfo.apply();
                                    break;
                                case 1:
                                    editorUpdateInfo.putString("last_name", subtitles[position]);
                                    editorUpdateInfo.apply();
                                    break;
                                case 2:
                                    editorUpdateInfo.putString("username", subtitles[position]);
                                    editorUpdateInfo.apply();
                                    break;
                                case 3:
                                    editorUpdateInfo.putString("password", subtitles[position]);
                                    editorUpdateInfo.apply();
                                    break;
                                case 4:
                                    editorUpdateInfo.putString("address", subtitles[position]);
                                    editorUpdateInfo.apply();
                                    break;
                                case 5:
                                    editorUpdateInfo.putString("phone", subtitles[position]);
                                    editorUpdateInfo.apply();
                                    break;
                                case 6:
                                    editorUpdateInfo.putString("email", subtitles[position]);
                                    editorUpdateInfo.apply();
                                    break;
                            }
                            recreate();
                        }
                    }
                });

                dialog.setContentView(dialogView);
                dialog.show();
            }
        }));

/*
        updateInfoRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
//                int i = motionEvent.findPointerIndex(0);
//                Log.d("recyclerViewId", "onInterceptTouchEvent : "+i);
//                recyclerView.getChildPosition(new View)
                View childView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                int i = recyclerView.getChildLayoutPosition(childView);
                Log.d("myRecyclerViewId", "onInterceptTouchEvent : "+i);
                return true;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
//                int i = motionEvent.findPointerIndex(0);
//                Log.d("recyclerViewId", "onTouchEvent : "+i);

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });
*/
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

//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();

    /*    ImageRequest imageRequest = new ImageRequest(RETRIEVE_URL,
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
        requestQueue.add(imageRequest);*/

        imgEditPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPhoto.callOnClick();
            }
        });
        editPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent i = new Intent(Intent.ACTION_PICK);
//                i.setType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(i, "Select Image"), 1);

//                Intent intent = new Intent();
//                intent.setType("image/*");
////                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.setAction(android.content.Intent.ACTION_VIEW);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });

//        etAlertDialog = findViewById(R.id.et_update);
//        etAlertDialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                displayDialog("", )
//                final AlertDialog.Builder builder = new AlertDialog.Builder(UpdateInfoActivity.this);
//                LayoutInflater inflater = getLayoutInflater();
//
//                builder.setView(inflater.inflate(R.layout.update_info_alert_dialog_layout, null))
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Toast.makeText(UpdateInfoActivity.this, "Updated", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//        });

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                progressDialog.show();
                final boolean usernameUpdated;
                final String firstName = spUpdateInfo.getString("first_name", "");
                final String lastName = spUpdateInfo.getString("last_name", "");
                final String address = spUpdateInfo.getString("address", "");
                final String username = spUpdateInfo.getString("username", "");
                final String password = spUpdateInfo.getString("password", "");

                if (sp.getString("username", "").equals(username))
                    usernameUpdated = false;
                else
                    usernameUpdated = true;

                String URL = Constants.BASE_URL + "update_profile.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String code = jsonObject.getString("code");
                                    if (code.equalsIgnoreCase("success")) {
                                        Toast.makeText(UpdateInfoActivity.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                        editor.putString("first_name", spUpdateInfo.getString("first_name", ""));
                                        editor.putString("last_name", spUpdateInfo.getString("last_name", ""));
                                        editor.putString("address", spUpdateInfo.getString("address", ""));
                                        editor.putString("username", spUpdateInfo.getString("username", ""));
                                        editor.putString("password", spUpdateInfo.getString("password", ""));
                                        if (!spUpdateInfo.getString("type", "").equals("child")) {
                                            editor.putString("phone", spUpdateInfo.getString("phone", ""));
                                            editor.putString("email", spUpdateInfo.getString("email", ""));
                                        }
                                        editor.apply();

                                    } else {
                                        Toast.makeText(UpdateInfoActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();

                                    Toast.makeText(UpdateInfoActivity.this, "Try Again!" + e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(UpdateInfoActivity.this, "Try Again!" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();

                        map.put("model", spUpdateInfo.getString("type", ""));
                        map.put("id", spUpdateInfo.getString("id", ""));
                        map.put("username_updated", "" + usernameUpdated);
                        map.put("first_name", firstName);
                        map.put("last_name", lastName);
                        map.put("username", username);
                        map.put("password", password);
                        map.put("address", address);

                        if (!spUpdateInfo.getString("type", "").equals("child")) {
                            map.put("phone", sp.getString("phone", ""));
                            map.put("email", sp.getString("email", ""));
                        }
                        return map;
                    }
                };
                Volley.newRequestQueue(UpdateInfoActivity.this).add(stringRequest);
            }
        });
    }

    private void bindViews() {
        profileImage = findViewById(R.id.profile_image);
        imgEditPhoto = findViewById(R.id.img_edit_photo);
        editPhoto = findViewById(R.id.btn_edit_photo);
        btnSave = findViewById(R.id.btnSave);
//        listViewUpdateInfo = findViewById(R.id.listview_update_info);
        updateInfoRecyclerView = findViewById(R.id.update_info_recycler_view);
        updateInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        spUpdateInfo = getSharedPreferences("updateInfo", 0);
        editorUpdateInfo = spUpdateInfo.edit();

        sp = getSharedPreferences("myLoginData", 0);
        editor = sp.edit();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
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

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

//                Crop.pickImage(this);
                profileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            Teacher teacher = Teacher.getInstance();
//            if (spUpdateInfo.getString("type", "").equalsIgnoreCase("teacher"))
//                id = spUpdateInfo.getString("id");
//            else
//                id = parent.getId();

            id = spUpdateInfo.getString("id", "");
            uploadPicture(id, getStringImage(bitmap));
        }

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);

            if (cursor == null || cursor.getCount() < 1) {
                return; // no cursor or no record. DO YOUR ERROR HANDLING
            }

            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

            if (columnIndex < 0) // no column index
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
        if (resultCode == RESULT_OK) {
//            resultView.setImageURI(Crop.getOutput(data));
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, "Error : " + Crop.getError(data).getMessage(), Toast.LENGTH_SHORT).show();
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

//                            if (success.equals("1"))
//                                Toast.makeText(UpdateInfoActivity.this, "Successfully uploaded", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(UpdateInfoActivity.this, "Try Again!" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(UpdateInfoActivity.this, "Try Again!" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                if (spUpdateInfo.getString("type", "").equalsIgnoreCase("teacher"))
                    params.put("id", "teacher" + id);
                else if (spUpdateInfo.getString("type", "").equalsIgnoreCase("parent"))
                    params.put("id", "parent" + id);
                else
                    params.put("id", "child" + id);
                params.put("photo", photo);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
