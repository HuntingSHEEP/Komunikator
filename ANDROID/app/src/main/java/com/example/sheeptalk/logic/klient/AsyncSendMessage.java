package com.example.sheeptalk.logic.klient;

import android.os.AsyncTask;

import java.io.PrintWriter;

public class AsyncSendMessage extends AsyncTask<String, Integer, Integer> {
    PrintWriter output;

    AsyncSendMessage(PrintWriter outp){
        this.output = outp;
    }

    @Override
    protected Integer doInBackground(String... strings){
        output.println(strings[0]);
        output.flush();
        return 0;
    }

    @Override
    protected void onPostExecute(Integer integer){

    }
}
