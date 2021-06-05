package com.example.sheeptalk.logic.klient;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.sheeptalk.MainMENU;

public class AsyncLogin extends AsyncTask<String, Integer, Integer> {
    Klient klient;
    int LOGIN;
    String PASSWORD;
    Activity activity;
    boolean loggedIN;


    public AsyncLogin(Klient klient, int LOGIN, String PASSWORD, Activity activity){
        this.klient = klient;
        this.LOGIN = LOGIN;
        this.PASSWORD = PASSWORD;
        this.activity = activity;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        loggedIN = klient.logIn(LOGIN, PASSWORD);
        return 0;
    }

    @Override
    protected void onPostExecute(Integer result) {
        //musi przyjmować TAKI SAM TYP PARAMETRU (Integer) jak zadeklarowano w AsyncTask<X, X, Integer>

        System.out.println("onPostEXECUTE");
        if(loggedIN){
            Toast.makeText(activity, "POMYŚLNIE ZALOGOWANO!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(activity, MainMENU.class);
            activity.startActivity(intent);
        }else{
            Toast.makeText(activity, "BŁĄÐ LOGOWANIA!", Toast.LENGTH_SHORT).show();
        }

    }



}