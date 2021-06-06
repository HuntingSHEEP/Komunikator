package com.example.sheeptalk;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sheeptalk.logic.klient.CustomAdapterWiadomosci;
import com.example.sheeptalk.logic.klient.Klient;
import com.example.sheeptalk.logic.klient.Singleton;

import java.sql.Date;

public class Wiadomosci extends AppCompatActivity {
    private Button send;
    private RecyclerView recycle;
    private EditText nowaWiadomosc;
    private RecyclerView.LayoutManager layoutManager;
    private CustomAdapterWiadomosci adapter;
    private Singleton singleton;
    private Klient klient;
    private int id = 0;

    @Override
    protected void OnCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messeges);

        singleton = Singleton.getInstance();
        klient = singleton.klient;

        send = (Button) findViewById(R.id.sendButt);
        nowaWiadomosc = (EditText) findViewById(R.id.textMessage);

        recycle.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new CustomAdapterWiadomosci();
        recycle.setLayoutManager(layoutManager);
        recycle.setAdapter(adapter);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = nowaWiadomosc.getText().toString();
                Date date = new Date(System.currentTimeMillis());
                klient.sendMessage("R#!*022"+String.valueOf(id)+"!"+date+"!"+message+"#END");
            }

        });



    }
}
