package com.example.sheeptalk.logic.klient;

public class WiadomościTreści {
    private  static WiadomościTreści single_instance = null;
    public String[] uczestnicy;
    public String[] daty;
    public String[] tresci;
    public  int last;

    private WiadomościTreści(){
        this.uczestnicy = new String[100];
        this.daty = new String[100];
        this.tresci = new String[100];
        this.last=0;
    }

    public static WiadomościTreści getInstance(){
        if(single_instance==null){
            single_instance = new WiadomościTreści();
        }
        return single_instance;
    }
    public void reset(){
        single_instance=null;
    }
}
