package com.example.sheeptalk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sheeptalk.logic.klient.Klient;
import com.example.sheeptalk.logic.klient.Singleton;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Rozmowy extends AppCompatActivity{
    private Button newConv;
    private RecyclerView recycle;
    private RecyclerView.LayoutManager layoutManager;
    private CustomAdapterRozmowy adapter;
    public Singleton singleton;
    public Klient klient;
    private List<Object> roomList = new ArrayList<Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversations);

        newConv = (Button) findViewById(R.id.newConvButt);
        singleton = Singleton.getInstance();

        klient = singleton.klient;
        loadConv();

        newConv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newConv();
            }
        });
        recycle = (RecyclerView) findViewById(R.id.recycleConv);
        recycle.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new CustomAdapterRozmowy(roomList,this);
        recycle.setLayoutManager(layoutManager);
        recycle.setAdapter(adapter);


    }

    private void newConv(){
        Intent intent=new Intent(this,NewConv.class);
        startActivity(intent);
    }

    private void loadConv(){
        System.out.println("loadConv");
        String temp;
        klient.sendMessage("R#!*010#END");
        System.out.println("loadConv: Wysłano zapytanie");
        do{
            try {
                sleep(100);
            }catch (Exception e){;}

            temp = klient.getData();
            System.out.println("Data "+ temp);
            if(temp !=""){
                if (!temp.substring(0,4).equals("R#!*")) {
                    roomList.add(temp);
                }
            }
        }while (!temp.equals("R#!*013#END"));
    }
}
