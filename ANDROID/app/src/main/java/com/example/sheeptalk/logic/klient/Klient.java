package com.example.sheeptalk.logic.klient;

import java.net.Socket;

public class Klient {
    //private Nadawaj nadawanie;
    //private Odbior odbieranie;
    private Polaczenie polaczenie;
    private Tools tools;

    public static final String HOST = "192.168.56.1";
    public static final int PORT=50007;
    public String wiadomosc = "\n XD";


    public Klient(){}


    public void polaczSieZSerwerem() {
        int i = 0;
        boolean flagaPetli = true;

        while(flagaPetli){
            /*try{
                //nawiazanie polaczenia z serwerem
                Socket sock;
                sock=new Socket(HOST,PORT);
                //System.out.println("Nawiazalem polaczenie: "+sock);
                wiadomosc += "\n" + "Nawiazalem polaczenie: "+sock;

                //tworzenie wątku nadającego
                nadawanie = new Nadawaj(sock);

                //tworzenie watka odbierajacego
                odbieranie = new Odbior(sock);

                //uruchomienie wątków
                nadawanie.start();
                odbieranie.start();

                //przekazanie referencji do watku
                nadawanie.podajWatekOdbierajacy(odbieranie);
                odbieranie.setWatekNadajacy(nadawanie);



                flagaPetli = false;
            }catch (Exception e){
                e.printStackTrace();
            }*/
            try {
                //nawiazanie polaczenia z serwerem
                Socket sock;
                sock=new Socket(HOST,PORT);
                polaczenie = new Polaczenie(sock);
            }catch (Exception E  ){};
        }

    }

    public boolean logIn(int id,String Pass){
        polaczenie.sendMessage("R#!*009"+id+"!"+Pass+"#END");
        while (!polaczenie.newMessage()){;}
        String mess = polaczenie.getMessage();
        if(tools.isRequest(mess)){
            return tools.handleRequest(polaczenie,mess);
        }else {
            return false;
        }
    }
    /*
    public Nadawaj getWatekNadajacy(){
        return polaczenie.getWatekNadajacy();
    }

    public Odbior getWatekOdbierajacy(){
        return polaczenie.getWatekOdbierajacy();
    }

     */


}
