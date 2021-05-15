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

                Polaczenie polaczenie = new Polaczenie(sock);

                //TODO: ZROBIĆ DYNAMICZNE ZWIĘKSZANIE LISTY
                for(int i=0; i<listaPolaczen.length; i++){
                    if(listaPolaczen[i] == null){
                        listaPolaczen[i] = polaczenie;
                        //break the loop
                        i=listaPolaczen.length;
                    }else if(!listaPolaczen[i].isActive()){
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
