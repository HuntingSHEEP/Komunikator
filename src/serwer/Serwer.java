package serwer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serwer  {
    public static final int PORT=50007;


    Polaczenie[] listaPolaczen = new Polaczenie[5];


    Serwer()  {
        obslugaPolaczen();
    }

    private void obslugaPolaczen() {
        ServerSocket serv = null;
        Socket sock = null;

        try{
            //tworzenie gniazda serwerowego
            serv=new ServerSocket(PORT);
        }catch (IOException e){
            e.printStackTrace();
        }


        while(true){

            try{

                try{
                    //oczekiwanie na polaczenie i tworzenie gniazda sieciowego
                    System.out.println("Nasluchuje: "+serv);
                    sock=serv.accept();
                    System.out.println("Jest polaczenie: "+sock);
                }catch (Exception e){
                    System.out.println("Try accept " + e);
                }



                //tworzenie wątku nadającego
                Nadawaj watekNadajacy = new Nadawaj(sock);
                watekNadajacy.start();

                //tworzenie watka odbierajacego
                Odbior watekOdbierajacy = new Odbior(sock);
                watekOdbierajacy.start();

                //wzajemne przekazanie referancji
                watekOdbierajacy.setWatekNadajacy(watekNadajacy);
                watekNadajacy.podajWatekOdbierajacy(watekOdbierajacy);

                Polaczenie polaczenie = new Polaczenie(watekNadajacy, watekOdbierajacy);

                for(int i=0; i<listaPolaczen.length; i++){
                    if(listaPolaczen[i] == null){
                        listaPolaczen[i] = polaczenie;
                        //break the loop
                        i=listaPolaczen.length;
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }



        }

    }
}
