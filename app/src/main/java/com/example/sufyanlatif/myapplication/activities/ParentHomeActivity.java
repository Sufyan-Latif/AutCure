package com.example.sufyanlatif.myapplication.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.models.Parent;
import com.example.sufyanlatif.myapplication.utils.Constants;

import de.hdodenhof.circleimageview.CircleImageView;

public class ParentHomeActivity extends AppCompatActivity {

    Button viewPerformance, parentCommunication, btnUpdateInfo, btnAssociateChildren;
    Parent parent;
    TextView tvName;
    ImageView imgLogout;
    CircleImageView imgProfile;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    SharedPreferences spUpdateInfo;
    SharedPreferences.Editor editorUpdateInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home);

        bindViews();

        tvName.setText(sp.getString("first_name","")+" "+sp.getString("last_name",""));
        String id = sp.getString("id", "");
        String RETRIEVE_URL = Constants.BASE_URL + "images/parent" + id + ".jpeg";
        Glide.with(this).load(RETRIEVE_URL).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Toast.makeText(ParentHomeActivity.this, "Check your internet connection", Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.ic_user).into(imgProfile);

//        editor.putString("type", "parents");
//        editor.apply();
//        String username = sp.getString("username", "abc");
//        String password = sp.getString("password", "xyz");


//        parent = Parent.getInstance();
//        if (parent.getUsername()==null)
//        {
//            if (isInternetConnected()){
//                UserRecord userRecord = new UserRecord(ParentHomeActivity.this);
//                userRecord.execute("getRecord","parent", username, password);
//            }
//            else
//                Toast.makeText(ParentHomeActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
//        }

        viewPerformance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParentHomeActivity.this, ChildrenListActivity.class);
                intent.putExtra("callFrom", "ParentHomeActivity.java");
                intent.putExtra("action", "ViewPerformance");
                startActivity(intent);
                /*
                if (isInternetConnected()){
                    if (parent.getAssociated().equals("yes")){
                        Performance performance= new Performance(ParentHomeActivity.this);
                        Log.d("xyz", parent.getId());
                        performance.execute("parents", parent.getId());
                    }
                    else
                        Toast.makeText(ParentHomeActivity.this, "No child associated", Toast.LENGTH_SHORT).show();
//                    RetrievePerformance retrievePerformance= new RetrievePerformance(ParentHomeActivity.this);
//                    retrievePerformance.execute("retrieve_performance", parent.getUsername());

                }
                else
                    Toast.makeText(ParentHomeActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();*/
            }
        });

        parentCommunication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParentHomeActivity.this, ChildrenListActivity.class);
                intent.putExtra("callFrom", "ParentHomeActivity.java");
                intent.putExtra("action", "Communication");
                startActivity(intent);
/*                Communication communication= new Communication(ParentHomeActivity.this);
                communication.execute("ParentHomeActivity", parent.getId(), "children");*/
//                Intent intent= new Intent(ParentHomeActivity.this, PerformanceFinalActivity.class);
//                startActivity(intent);
            }
        });

        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editorUpdateInfo.putString("type", "parent");
                editorUpdateInfo.putString("id", sp.getString("id", ""));
                editorUpdateInfo.putString("first_name", sp.getString("first_name", ""));
                editorUpdateInfo.putString("last_name", sp.getString("last_name", ""));
                editorUpdateInfo.putString("username", sp.getString("username", ""));
                editorUpdateInfo.putString("password", sp.getString("password", ""));
                editorUpdateInfo.putString("address", sp.getString("address", ""));
                editorUpdateInfo.putString("phone", sp.getString("phone", ""));
                editorUpdateInfo.putString("email", sp.getString("email", ""));
                editorUpdateInfo.apply();
                Intent intent = new Intent(ParentHomeActivity.this, UpdateInfoActivity.class);
                startActivity(intent);
            }
        });

        btnAssociateChildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ParentHomeActivity.this, ChildrenListActivity.class);
                intent.putExtra("callFrom", "ParentHomeActivity.java");
                intent.putExtra("action", "AssociateChildren");
                startActivity(intent);
            }
        });

        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(ParentHomeActivity.this);
                View dialogView = LayoutInflater.from(ParentHomeActivity.this).inflate(R.layout.logout_dialog, null);

                Button btnYes = dialogView.findViewById(R.id.btnYes);
                Button btnNo = dialogView.findViewById(R.id.btnNo);

                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                    sp = getSharedPreferences("myLoginData", 0);
//                    editor = sp.edit();
                        dialog.dismiss();
                        editor.remove("type");
                        editor.apply();
                        Intent intent = new Intent(ParentHomeActivity.this, MyAuthenticationActivity.class);
                        finish();
                        startActivity(intent);
                    }
                });
                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.setContentView(dialogView);
                dialog.show();
            }
        });
    }

    private void bindViews() {
        viewPerformance = findViewById(R.id.btnParentViewPerformance);
        parentCommunication = findViewById(R.id.btnParentCommunication);
        btnUpdateInfo = findViewById(R.id.btnParentUpdateInfo);
        btnAssociateChildren = findViewById(R.id.btnAssociateChildren);
        tvName = findViewById(R.id.tvName);
        imgProfile = findViewById(R.id.profile_image);
        imgLogout = findViewById(R.id.imgLogout);

        sp = getSharedPreferences("myLoginData", 0);
        editor = sp.edit();

        spUpdateInfo = getSharedPreferences("updateInfo", 0);
        editorUpdateInfo = spUpdateInfo.edit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menu1 = getMenuInflater();
        menu1.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            final Dialog dialog = new Dialog(ParentHomeActivity.this);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.logout_dialog, null);

            Button btnYes = dialogView.findViewById(R.id.btnYes);
            Button btnNo = dialogView.findViewById(R.id.btnNo);

            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    sp = getSharedPreferences("myLoginData", 0);
//                    editor = sp.edit();
                    editor.remove("type");
                    editor.apply();
                    Intent intent = new Intent(ParentHomeActivity.this, MyAuthenticationActivity.class);
                    finish();
                    startActivity(intent);
                }
            });
            btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.setContentView(dialogView);
            dialog.show();
            /*
            AlertDialog.Builder builder = new AlertDialog.Builder(ParentHomeActivity.this);
            builder.setTitle("Alert!!!")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sp= getSharedPreferences("myLoginData", 0);
                            editor = sp.edit();
                            editor.remove("type");
                            editor.apply();
                            parent.remove();
                            Intent intent = new Intent(ParentHomeActivity.this, AuthenticationActivity.class);
                            finish();
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
                    */
        }
        return true;
    }

//    public boolean isInternetConnected(){
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
//                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
//            return true;
//        } else
//            return false;
//    }
}
