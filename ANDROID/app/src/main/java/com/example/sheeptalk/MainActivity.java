package com.example.sheeptalk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.sheeptalk.logic.klient.AsyncClient;
import com.example.sheeptalk.logic.klient.Klient;
import com.example.sheeptalk.logic.klient.NadawajK;
import com.example.sheeptalk.logic.klient.OdbiorK;

public class MainActivity extends AppCompatActivity {
    Klient klient;
    TextView textHello;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Klient klient = new Klient();
        new AsyncClient().execute(klient);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textHello = (TextView) findViewById(R.id.hello);
        textHello.setText("XDD"+klient.wiadomosc);

        NadawajK nadawanie = klient.getWatekNadajacy();
        OdbiorK odbieranie = klient.getWatekOdbierajacy();


    }
}