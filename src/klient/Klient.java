package klient;

import java.net.ConnectException;
import java.net.Socket;

public class Klient {

    public static final String HOST = "127.0.0.1";
    public static final int[] PORT={50007, 50008, 50009, 50010};
    int[] flagiPortow = {0, 0, 0, 0}; //0: nie sprawdzano, 1:zajęty
    int liczbaPortow = 4;

    Klient(){
        polaczSieZSerwerem();
    }

    private void polaczSieZSerwerem() {
        int i = 0;
        boolean flagaPetli = true;
        while(flagaPetli){
            if(flagiPortow[i] == 0){
                //TODO: SPRAWDZANIE CZY JEDNAK NIE JEST ZAJĘTY PORT

                try{
                    //nawiazanie polaczenia z serwerem
                    Socket sock;
                    sock=new Socket(HOST,PORT[i]);
                    System.out.println("Nawiazalem polaczenie: "+sock);

                    //tworzenie wątku nadającego
                    NadawajK nadawanie = new NadawajK(sock);
                    nadawanie.start();

                    //tworzenie watka odbierajacego
                    OdbiorK odbieranie = new OdbiorK(sock);
                    odbieranie.start();

                    //przekazanie referencji do watku
                    nadawanie.podajWatekOdbierajacy(odbieranie);
                    odbieranie.setWatekNadajacy(nadawanie);

                    flagiPortow[i] = 1;
                    flagaPetli = false;
                }catch (ConnectException con){
                    flagiPortow[i] = 1;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                i++;
                i %= liczbaPortow;
            }

        }

    }


}
