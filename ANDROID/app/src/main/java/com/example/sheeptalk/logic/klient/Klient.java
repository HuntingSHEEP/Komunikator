package com.example.sheeptalk.logic.klient;

import java.net.Socket;

import static java.lang.Thread.sleep;

public class Klient {
    //private Nadawaj nadawanie;
    //private Odbior odbieranie;
    private Polaczenie polaczenie;
    private Tools tools;

    public static final String HOST = "192.168.56.1";
    //public static final String HOST = "192.168.0.102";
    public static final int PORT=50007;
    public String wiadomosc = "\n XD";


    public Klient(){
        this.tools = new Tools();
    }


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
                flagaPetli = false;
            }catch (Exception E  ){};

            try{
                sleep(1);
            }catch (Exception e){

            }
        }

    }

    public boolean logIn(int id,String Pass){
        System.out.println(id+" "+Pass);
        String message = "R#!*009"+id+"!"+Pass+"#END";
        polaczenie.sendMessage(message);

        while (!polaczenie.newMessage()){;}
        String message1 = polaczenie.getMessage();
        System.out.println("tools.isRequest(message1) "+tools.isRequest(message1));
        if(tools.isRequest(message1)){
            System.out.println("tools.handleRequest(polaczenie,message1) "+tools.handleRequest(polaczenie,message1));
            return tools.handleRequest(polaczenie,message1);
        }else {
            return false;
        }
    }


    public int convNumber(){
        String message = "R#!*017#END";
        polaczenie.sendMessage(message);

        while (!polaczenie.newMessage()){;}
        String message1 = polaczenie.getMessage();
        if(tools.isRequest(message1)){
            return tools.numberCutout(polaczenie,message1);
        }
        return 0;
    }

    public void sendMessage(String message){
        polaczenie.sendMessage(message);
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
