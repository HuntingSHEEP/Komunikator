package com.example.sheeptalk.logic.klient;

import java.net.ConnectException;
import java.net.Socket;

public class Klient {

    public static final String HOST = "192.168.0.103";
    public static final int PORT=50007;
    public String wiadomosc = "\n XD";


    public Klient(){
        //polaczSieZSerwerem();
    }


    public void polaczSieZSerwerem() {
        int i = 0;
        boolean flagaPetli = true;

        while(flagaPetli){
            try{
                //nawiazanie polaczenia z serwerem
                Socket sock;
                sock=new Socket(HOST,PORT);
                //System.out.println("Nawiazalem polaczenie: "+sock);
                wiadomosc += "\n" + "Nawiazalem polaczenie: "+sock;

                //tworzenie wątku nadającego
                NadawajK nadawanie = new NadawajK(sock);

                //tworzenie watka odbierajacego
                OdbiorK odbieranie = new OdbiorK(sock);

                //uruchomienie wątków
                nadawanie.start();
                odbieranie.start();

                //przekazanie referencji do watku
                nadawanie.podajWatekOdbierajacy(odbieranie);
                odbieranie.setWatekNadajacy(nadawanie);



                flagaPetli = false;
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }


}