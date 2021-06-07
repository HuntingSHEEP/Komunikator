package com.example.sheeptalk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.sheeptalk.logic.klient.CustomAdapterWiadomosci;
import com.example.sheeptalk.CustomAdapterWiadomosci;
import com.example.sheeptalk.logic.klient.Klient;
import com.example.sheeptalk.logic.klient.Singleton;
import com.example.sheeptalk.logic.klient.WiadomościTreści;

import static java.lang.Thread.sleep;
import java.sql.Date;

public class Wiadomosci extends AppCompatActivity {
    private int ConvID;
    private Button send;
    private RecyclerView recycle;
    private EditText nowaWiadomosc;
    private RecyclerView.LayoutManager layoutManager;
    private CustomAdapterWiadomosci adapter;
    private Singleton singleton;
    private Klient klient;
    private WiadomościTreści tresci;


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.messeges);

        Intent intent = getIntent();
        ConvID = (int) intent.getIntExtra(CustomAdapterRozmowy.idRozmowy, 0);

        singleton = Singleton.getInstance();
        klient = singleton.klient;

        tresci=WiadomościTreści.getInstance();
        tresci.reset();
        tresci=WiadomościTreści.getInstance();

        send = (Button) findViewById(R.id.sendButt);
        nowaWiadomosc = (EditText) findViewById(R.id.textMessage);

        recycle = (RecyclerView) findViewById(R.id.recycleConv);
        recycle.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new CustomAdapterWiadomosci(this);
        recycle.setLayoutManager(layoutManager);
        recycle.setAdapter(adapter);



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = nowaWiadomosc.getText().toString();
                Date date = new Date(System.currentTimeMillis());
                klient.sendMessage("R#!*022"+ConvID+"!"+date+"!"+message+"#END");
            }

        });

        loadMess();
        adapter.notifyDataSetChanged();

        


    }

    private void loadMess(){
        String[] temp = new String[3];
        klient.sendMessage("R#!*016"+ConvID+"#END");

        int i = 0;
        boolean end =false;
        do{
            try{
                sleep(100);
            }catch (Exception E){}
            temp = klient.getMessage();
            if(temp!=null) {
                if (temp.length == 1 && temp[0].equals("end")) {
                    end = true;
                }

                if (!end) {
                    tresci.uczestnicy[i] = temp[1];
                    tresci.daty[i] = temp[2];
                    tresci.tresci[i++] = temp[3];
                }
            }
            System.out.println(end);

        }while (!end);
    }
}
