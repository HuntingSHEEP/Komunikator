package klient;

import java.net.ConnectException;
import java.net.Socket;

public class Klient {

    /*
    public static final String HOST = "127.0.0.1";
    public static final int[] PORT={50007, 50008, 50009, 50010};
    int[] flagiPortow = {0, 0, 0, 0}; //0: nie sprawdzano, 1:zajęty
    int liczbaPortow = 4;
     */
    public static final String HOST = "127.0.0.1";
    public static final int PORT=50007;


    Klient(){
        polaczSieZSerwerem();
    }

    private void polaczSieZSerwerem() {
        int i = 0;
        boolean flagaPetli = true;

        while(flagaPetli){
            try{
                //nawiazanie polaczenia z serwerem
                Socket sock;
                sock=new Socket(HOST,PORT);
                System.out.println("Nawiazalem polaczenie: "+sock);

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
