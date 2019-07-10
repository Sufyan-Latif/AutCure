package com.example.sufyanlatif.myapplication;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogStatic {
    Context context;
    public static ProgressDialog instance=null;

    public ProgressDialogStatic(Context ctx){
        this.context= ctx;
    }

//    public static ProgressDialog getInstance() {
//
//        if (instance==null)
//            instance = new ProgressDialog();
//
//        return instance;
//    }

    public void message(String msg){
        instance.setMessage(msg);
    }

    public void show(){
        instance.show();
    }
    public void dismiss(){
        instance.dismiss();
    }



}
