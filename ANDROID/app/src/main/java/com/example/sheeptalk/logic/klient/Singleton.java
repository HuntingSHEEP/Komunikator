package com.example.sheeptalk.logic.klient;

import java.util.ArrayList;
import java.util.List;

public class Singleton {
    private static Singleton single_instance = null;
    public Klient klient;
    public String id;


    private Singleton(){
        this.klient = new Klient();
        new AsyncClient().execute(klient);
        this.id ="";



    }

    public static  Singleton getInstance(){
        if (single_instance == null)
            single_instance = new Singleton();

        return single_instance;
    }

    public void reset(){
        klientReset();
        instanceReset();
    }

    private void klientReset(){
        klient = null;
    }

    private  static void instanceReset(){
        single_instance = null;
    }
}