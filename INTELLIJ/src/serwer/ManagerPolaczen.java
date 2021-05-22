package serwer;

import baza.Baza;

import java.sql.ResultSet;

public class ManagerPolaczen extends Thread{
    //KRĄG 1 ZARZĄDZANIA

    Baza baza;
    Polaczenie[] listaPolaczen;
    ManagerUruchomienia managerUruchomienia;
    NarzedziaPolaczen narzedzia;

    ManagerPolaczen(Polaczenie[] listaPolaczen, Baza baza){
        this.baza = baza;
        this.listaPolaczen = listaPolaczen;
        this.narzedzia = new NarzedziaPolaczen();

        this.managerUruchomienia = new ManagerUruchomienia(listaPolaczen, baza);
        this.managerUruchomienia.start();
    }

    public void run(){
        //KRĄG 1 - Logowanie do serwera

        while(true){
            try{
                for (Polaczenie polaczenie : listaPolaczen){
                    if(polaczenie != null){
                        //jeśli w ogóle jest połączenie

                        if(polaczenie.getKRAG() == 1){
                            //jeśli zostało przypisane do 1 KRĘGU

                         //   if(polaczenie.getID() == (-1)){
                         //       //Niezalogowani klienci

                                if(polaczenie.newMessage()){
                                    //przyszła jakaś wiadomość
                                    String wiadomosc = polaczenie.getMessage();

                                    if(narzedzia.isRequest(wiadomosc)){
                                        //wiadomość jest komendą
                                        narzedzia.responseRequest(polaczenie, wiadomosc, baza, 1);
                                    }
                                }
                        //    }
                        }
                    }
                }



            }catch (Exception e){

            }
        }
    }




}
