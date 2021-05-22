package serwer;

import baza.Baza;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serwer  {
    //KRĄG 0 ZARZĄDZANIA

    public static final int PORT=50007;
    Polaczenie[] listaPolaczen;
    ManagerPolaczen managerPolaczen;
    Baza bazaDanych;

    Serwer(Baza baza)  {
        bazaDanych = baza;
        listaPolaczen = new Polaczenie[5];
        managerPolaczen = new ManagerPolaczen(listaPolaczen, bazaDanych);
        managerPolaczen.start();

        akceptowaniePolaczen();
    }

    private void akceptowaniePolaczen() {
        ServerSocket serv = null;
        Socket sock = null;

        try{
            //tworzenie gniazda serwerowego
            serv=new ServerSocket(PORT);
        }catch (IOException e){
            e.printStackTrace();
        }

        while(true){
            //KRĄG 0 - Akceptowanie połączeń

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
                polaczenie.setKRAG(1);

                //TODO: ZROBIĆ DYNAMICZNE ZWIĘKSZANIE LISTY, inaczej się połączy a nie zapisze do listy jeśli jest już zapełniona

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
