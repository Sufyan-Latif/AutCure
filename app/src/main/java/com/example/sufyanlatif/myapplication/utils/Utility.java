package com.example.sufyanlatif.myapplication.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TextInputEditText;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    public static boolean validLength(TextInputEditText et, int length){
        if (et.getText().toString().length() < length){
            et.requestFocus();
            et.setError("Must be atleast "+length+" characters long");
            return false;
        }
        else{
            et.setError(null);
            return true;
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

    public static String getCurrentDateTime(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("MMMM dd,yyyy hh:mm:ss aa");
        String datetime = dateformat.format(c.getTime());
        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        return datetime;
    }
    public static Date parseStringToDate(String dateTime){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd,yyyy hh:mm:ss aa");

        Date myDate = null;
        try {
            myDate = dateFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return myDate;
    }

    public static void showDialog(Context context, final TextView tvDOB){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                tvDOB.setText(day+"/"+(month+1)+"/"+year);
            }
        }, year, month, day);

        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }
}
