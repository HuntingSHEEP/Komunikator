package serwer;

import baza.Baza;

public class ManagerPolaczen extends Thread{
    Polaczenie[] listaPolaczen;
    Baza bazaDanych;

    ManagerPolaczen(Polaczenie[] listaPolaczen, Baza baza){
        this.bazaDanych = baza;
        this.listaPolaczen = listaPolaczen;
    }

    public void run(){
        //na razie jeden duży pokój

        while(true){
            try{
                for (Polaczenie polaczenie : listaPolaczen){
                    if(polaczenie.newMessage()){
                        String wiadomosc = polaczenie.getMessage();

                        for(Polaczenie innePolaczenie : listaPolaczen){
                            if(innePolaczenie != polaczenie){
                                innePolaczenie.sendMessage(wiadomosc);
                                System.out.println("WYSŁANO DALEJ >> ["+wiadomosc+"]");
                            }
                        }

                    }
                }
            }catch (Exception e){

            }


        }
    }
}
