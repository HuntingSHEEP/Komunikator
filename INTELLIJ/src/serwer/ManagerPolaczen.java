package serwer;

import baza.Baza;

import java.sql.ResultSet;

public class ManagerPolaczen extends Thread{
    Baza baza;
    Polaczenie[] listaPolaczen;
    Pokoj[] listaPokoi;


    ManagerPolaczen(Polaczenie[] listaPolaczen, Baza baza){
        this.baza = baza;
        this.listaPolaczen = listaPolaczen;
        this.listaPokoi = new Pokoj[2];
    }

    public void run(){
        //na razie jeden duży pokój

        while(true){
            try{
                for (Polaczenie polaczenie : listaPolaczen){
                    if(polaczenie.newMessage()){
                        String wiadomosc = polaczenie.getMessage();

                        if(isRequest(wiadomosc)){
                            //TODO: czy nie warto dać tutaj wątku? NWM, NIE JEST TO DO KOŃCA BEZPIECZNE
                            responseRequest(polaczenie, wiadomosc);
                        }else{
                            for(Polaczenie innePolaczenie : listaPolaczen){
                                if(innePolaczenie != polaczenie){
                                    innePolaczenie.sendMessage(wiadomosc);
                                    System.out.println("WYSŁANO DALEJ >> ["+wiadomosc+"]");
                                }
                            }
                        }
                    }
                }
            }catch (Exception e){

            }


        }
    }

    private boolean isRequest(String msg){
        boolean state = false;

        if(11 <= msg.length()){
            /* Nagłówek komendy: R#!*
             * Stopka komendy: #END
             * całkowita długość komendy : MINIMUM 7+4
             */

            if(msg.substring(0, 4).equals("R#!*") && msg.substring(msg.length()-4).equals("#END")){
                try{
                    //Tutaj sprawdzam jedynie czy numer polecenia jest typu INT, domyślny zakres od 0 do 999
                    //JEŚLI NIE BĘDZIE INT TO WYRZUCI BŁĄD I NIE PRZEJDZE DO responseREQUEST

                    int numer = Integer.parseInt(msg.substring(4, 7));
                    //System.out.println("Numer komendy: "+ numer);
                    state = true;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return state;
    }

    private void responseRequest(Polaczenie polaczenie, String msg) {
        int numer = Integer.parseInt(msg.substring(4, 7));
        String data = msg.substring(7, msg.length()-4);

        System.out.println("Przyjęto polecenie "+numer+", DATA: ["+data+"]");

        switch (numer){
            case 9:
                boolean zalogowano = command9(polaczenie, data);
                if(zalogowano){
                    command0(polaczenie);
                }else{
                    command1(polaczenie);
                }
                break;
        }


    }

    private void command1(Polaczenie polaczenie) {
        polaczenie.sendMessage("R#!*001#END");
    }

    private void command0(Polaczenie polaczenie){
        polaczenie.sendMessage("R#!*000#END");
    }

    private boolean command9(Polaczenie polaczenie, String data) {
        //logowanie do serwera

        String temp = "";
        int i = 0;
        while(data.charAt(i) != '!'){
            temp += data.charAt(i);
            i++;
        }

        String pswd = data.substring(i+1);
        int usrID;

        try{
            usrID = Integer.parseInt(temp);
            if(pswd.length() < 1){
                throw new Exception("brak hasla");
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        //System.out.println("LOGOWANIE UŻYTKOWNIKA ["+usrID+"], PASS [" + pswd +"]");

        ResultSet wynikZapytania = baza.dml("select ID, PASS, IMIE from KLIENT");
        try{
            //System.out.println("------- TABELA KLIENT -------");
            int tableID;
            String tablePASS;
            String tableNAME;

            while(wynikZapytania.next()){
                tableID = wynikZapytania.getInt("ID");
                tablePASS = wynikZapytania.getString("PASS");
                tableNAME = wynikZapytania.getString("IMIE");

                if((usrID==tableID) && (pswd.equals(tablePASS))){
                    polaczenie.setID(tableID);
                    polaczenie.setNAZWA(tableNAME);
                    System.out.println("ZALOGOWANO: "+tableNAME+", ID [" + tableID + "], PASS ["+tablePASS+"]");
                    return true;
                }

            }
        }catch (Exception e){}




        return false;
    }

}
