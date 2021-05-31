package serwer;

import baza.Baza;

public class ManagerUruchomienia extends Thread {
    //KRĄG 2 ZARZĄDZANIA
    Baza baza;
    Polaczenie[] listaPolaczen;
    NarzedziaPolaczen narzedzia;

    ManagerUruchomienia(Polaczenie[] listaPolaczen, Baza baza){
        this.baza = baza;
        this.listaPolaczen = listaPolaczen;
        this.narzedzia = new NarzedziaPolaczen();
    }

    public void run(){
        while(true){
            try{
                for (Polaczenie polaczenie : listaPolaczen){
                    if(polaczenie != null){
                        //jeśli w ogóle jest połączenie
                        if(polaczenie.getKRAG() == 2){
                            if(polaczenie.newMessage()){
                                //przyszła jakaś wiadomość
                                String wiadomosc = polaczenie.getMessage();

                                if(narzedzia.isRequest(wiadomosc)){
                                    //wiadomość jest komendą
                                    narzedzia.responseRequest(polaczenie, wiadomosc, baza);
                                }
                            }
                        }
                    }
                }

                sleep(1);
            }catch (Exception e){

            }
        }

    }

}
