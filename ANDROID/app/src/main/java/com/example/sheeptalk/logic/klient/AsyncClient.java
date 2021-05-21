package com.example.sheeptalk.logic.klient;


import android.os.AsyncTask;

public class AsyncClient extends AsyncTask<Klient, Integer, Integer> {

    private Exception exception;

    @Override
    protected Integer doInBackground(Klient... klients) {
        klients[0].polaczSieZSerwerem();
        return 0;
    }

    protected void onPostExecute(int result) {

    }

    protected void onProgressUpdate(Integer... progress) {

    }
}
