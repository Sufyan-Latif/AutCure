package com.example.sufyanlatif.myapplication.fragments;


import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.activities.ParentHomeActivity;
import com.example.sufyanlatif.myapplication.utils.Constants;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavHomeFragment extends Fragment {

    TextView tvName;
    CircleImageView imgProfile;
    SharedPreferences sp;

    View myView;
    public NavHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_nav_home, container, false);

        tvName = myView.findViewById(R.id.tvName);
        imgProfile = myView.findViewById(R.id.profile_image_teacher);
        sp = getActivity().getSharedPreferences("myLoginData", 0);

        tvName.setText(sp.getString("first_name","")+" "+sp.getString("last_name",""));
        String id = sp.getString("id", "");
        String RETRIEVE_URL = Constants.BASE_URL + "images/teacher" + id + ".jpeg";
        Glide.with(this).load(RETRIEVE_URL).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Toast.makeText(myView.getContext(), "Check your internet connection", Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.ic_user).into(imgProfile);

        return myView;
    }

}
