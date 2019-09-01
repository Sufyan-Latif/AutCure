package com.example.sufyanlatif.myapplication.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TextInputEditText;
import android.widget.Toast;

public class Utility {

    public static boolean isInternetConnected(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }


    public static boolean isEmpty(TextInputEditText et, String errorMsg){

        if (et.getText().toString().equals("")){
            et.requestFocus();
            et.setError(errorMsg);
            return true;
        }
        else {
            et.setError(null);
            return false;
        }
    }

    public static boolean validateConPass(TextInputEditText etConPassword, TextInputEditText etPassword){


        if (!etConPassword.getText().toString().equals(etPassword.getText().toString())){
            etConPassword.requestFocus();
            etConPassword.setError("Passwords Mismatch");
            return false;
        }
        else {
            etConPassword.setError(null);
            return true;
        }
    }
}
