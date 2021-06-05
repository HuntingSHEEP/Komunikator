package com.example.sheeptalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sheeptalk.logic.klient.Klient;
import com.example.sheeptalk.logic.klient.Singleton;

public class MainMENU extends AppCompatActivity {
    Singleton singleton;
    private Button convers, logout;
    MainMENU act;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        act = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        convers = (Button) findViewById(R.id.convButt);
        logout = (Button) findViewById(R.id.logoutButt);

        convers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToConv();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void goToConv(){
        Intent intent = new Intent(this, Rozmowy.class);
        startActivity(intent);
    }

    private  void logout(){
        singleton = Singleton.getInstance();
        singleton.reset();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}