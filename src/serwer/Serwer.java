package serwer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serwer  {
    public static final int[] PORT={50007, 50008, 50009, 50010};
    int[] flagiPortow = {0, 0, 0, 0}; //0: nie sprawdzano, 1:zajęty
    int liczbaPortow = 4;


    Nadawaj[] watkiNadajace = new Nadawaj[4];
    Odbior[] watkiOdbierajace = new Odbior[4];



    Serwer()  {
        obslugaPolaczen();
    }

    private void obslugaPolaczen() {
        int i = 0;

        while(true){
            if(flagiPortow[i] == 0){
                try{
                    //tworzenie gniazda serwerowego
                    ServerSocket serv;
                    serv=new ServerSocket(PORT[i]);

                    //oczekiwanie na polaczenie i tworzenie gniazda sieciowego
                    System.out.println("Nasluchuje: "+serv);
                    Socket sock;

                    sock=serv.accept();
                    System.out.println("Jest polaczenie: "+sock);

                    //tworzenie wątku nadającego
                    //nadaj(sock);

                    Nadawaj watekNadajacy = new Nadawaj(sock);
                    watekNadajacy.start();

                    //tworzenie watka odbierajacego
                    //odbior(sock);

                    Odbior watekOdbierajacy = new Odbior(sock);
                    watekOdbierajacy.setWatekNadajacy(watekNadajacy);
                    watekNadajacy.podajWatekOdbierajacy(watekOdbierajacy);
                    watekOdbierajacy.start();

                    watkiNadajace[i] = watekNadajacy;
                    watkiOdbierajace[i] = watekOdbierajacy;

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
