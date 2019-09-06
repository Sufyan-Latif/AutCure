package com.example.sufyanlatif.myapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sufyanlatif.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavHomeFragment extends Fragment {


    View myView;
    public NavHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_nav_home, container, false);

        return myView;
    }

}
