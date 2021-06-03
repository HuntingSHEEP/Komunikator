package serwer;

import baza.Baza;

public class ManagerRozmow extends Thread{
    //KRĄG 3 - ROZMOWY
    Polaczenie[] listaPolaczen;
    Baza baza;
    NarzedziaPolaczen narzedzia;

    public ManagerRozmow(Polaczenie[] listaPolaczen, Baza baza) {
        this.listaPolaczen = listaPolaczen;
        this.baza = baza;
        this.narzedzia = new NarzedziaPolaczen();
    }

    public void run(){
        while(true){
            try{
                for (Polaczenie polaczenie : listaPolaczen){
                    if(polaczenie != null){
                        //jeśli w ogóle jest połączenie
                        if(polaczenie.getKRAG() == 3){
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

                sleep(100);
            }catch (Exception e){

            }
        }
    }
}
