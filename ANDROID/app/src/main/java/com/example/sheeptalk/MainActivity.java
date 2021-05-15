package com.example.sheeptalk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.sheeptalk.logic.klient.Klient;

public class MainActivity extends AppCompatActivity {
    Klient klient;
    TextView textHello;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        klient = new Klient();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textHello = (TextView) findViewById(R.id.hello);
        textHello.setText("XD");
    }
}