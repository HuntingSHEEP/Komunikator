package serwer;

import baza.Baza;

import java.sql.ResultSet;
import static java.lang.Thread.sleep;

public class NarzedziaPolaczen {


    boolean isRequest(String msg){
        /*----Sprawdzamy czy wiadomość jest komendą:
         * 1) długość przynajmniej 10 znaków (zmieści się nagłówek i stopka, DATA zerowe)
         * 2) Poprawny nagłowek R#!*XXX oraz stopka #END
         * 3) Numer komendy XXX czy na pewno jest INTEGER
         */
        boolean state = false;

        if(11 <= msg.length()){
            /* Nagłówek komendy: R#!*
             * Stopka komendy: #END
             * całkowita długość komendy : MINIMUM 7+4
             */

            if(msg.substring(0, 4).equals("R#!*") && msg.substring(msg.length()-4).equals("#END")){

                try{
                    //Tutaj sprawdzam jedynie czy numer polecenia jest typu INT, domyślny zakres od 0 do 999
                    //JEŚLI NIE BĘDZIE INT TO WYRZUCI BŁĄD I NIE PRZEJDZIE DO responseREQUEST

                    int numer = Integer.parseInt(msg.substring(4, 7));
                    state = true;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        return state;
    }

    public void responseRequest(Polaczenie polaczenie, String msg, Baza baza, Polaczenie[] listaPolaczen) {
        /*-----REAGOWANIE NA PRZYSŁANE ŻĄÐANIA
         * KRĄG -1 TO KLIENT
         * METODA SPRAWDZA W KTÓRYM KRĘGU ZNAJDUJE SIĘ KLIENT I W ZALEŻNOŚCI
         * OD TEGO UDOSTĘPNIA KOMENDY
         */
        int numer = Integer.parseInt(msg.substring(4, 7));
        String data = msg.substring(7, msg.length()-4);
        boolean polecenie = false;

        if(polaczenie.getKRAG() == 1){
            switch (numer){
                case 9:
                    boolean zalogowano = command9(polaczenie, data, baza);
                    if(zalogowano){
                        command0(polaczenie);
                    }else{
                        command1(polaczenie);
                    }
                    polecenie = true;
                    break;
            }

        }else if(polaczenie.getKRAG() == 2){
            switch (numer){
                case 10:
                    command10(polaczenie, baza);
                    polecenie = true;
                    break;

                case 14:
                    command14(polaczenie, data, baza);
                    break;

                case 16:
                    command16(polaczenie, data, baza);
                    break;

                case 17:
                    command17(polaczenie, data, baza);
                    break;

                case 21:
                    command21(polaczenie);
                    break;
                case 22:
                    command22(polaczenie, data, baza, listaPolaczen);
                    break;
            }

        }else if(polaczenie.getKRAG() == 3){
            switch(numer){
                case 22:
                    command22(polaczenie, data, baza, listaPolaczen);
                    break;
            }
        }

        if(polecenie){
            //raczej w błąd wprowadza bo pokazuje status Kręgu po wykomaniu akcji a nie przed
            //System.out.println("Przyjęto polecenie "+numer+", DATA: ["+data+", ID: "+polaczenie.getID());
        }

    }

    private String[] cutTheData(String data){
        /* Zwraca dane zapisane do pierwszego wykrzyknika!
         * przydałoby sie sprawdzać poprawność data i wyrzucać błąd
         */

        //TODO: uwzględnianie znaków specjalnych w wiadomościach >> \ jako wyłączenie znaku specjalnego

        //ZLICZANIE ILOŚCI WYKRZYKNIKÓW
        int exclamationCount = 0;
        for(int i=0; i<data.length(); i++){
            if(data.charAt(i) == '!'){
                exclamationCount++;
            }
        }

        //PRZYGOTOWANIE LISTY
        String[] output = new String[exclamationCount+1];

        //WYCIĘCIE DANYCH
        String temporary = "";
        int index = 0;
        for(int i=0; i<data.length(); i++){
            if(data.charAt(i) == '!'){
                //System.out.println("WYCIĘTO "+index+": ["+temporary+"]");
                output[index] = temporary;
                temporary = "";
                index++;
            }else{
                temporary += data.charAt(i);
            }
            if(i == data.length()-1){
                //System.out.println("WYCIĘTO "+index+": ["+temporary+"]");
                output[index] = temporary;
                temporary = "";
                index++;
            }
        }


        return output;
    }


    private void command0(Polaczenie polaczenie){
        //TRUE, wszystko OK
        polaczenie.sendMessage("R#!*000#END");
    }

    private void command1(Polaczenie polaczenie) {
        //FALSE, wystąpił BŁĄD
        polaczenie.sendMessage("R#!*001#END");
    }

    private boolean command9(Polaczenie polaczenie, String data, Baza baza) {
        /*----LOGOWANIE DO SERWERA
         * 1) Pobieranie id Klienta
         * 2) Pobieranie hasła Klienta
         * 3) -Sprawdzanie czy ID jest INTEGER
         *    -Sprawdzenie czy hasło jest niezerowe
         * 4) Przeszukanie tabeli KLIENT
         * 5) Logowanie
         * 6) Przekazanie połączenia do KRĘGU 2
         */

        // 1) Pobieranie id Klienta
        String temp = "";
        int i = 0;
        while(data.charAt(i) != '!'){
            temp += data.charAt(i);
            i++;
        }
        //2) Pobieranie hasła Klienta
        String pswd = data.substring(i+1);

        // 3)
        int usrID;
        try{
            //3) Sprawdzanie czy ID jest INTEGER
            usrID = Integer.parseInt(temp);

            // 3) Sprawdzenie czy hasło jest niezerowe
            if(pswd.length() < 1){
                throw new Exception("brak hasla");
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        //System.out.println("LOGOWANIE UŻYTKOWNIKA ["+usrID+"], PASS [" + pswd +"]");

        //4) Przeszukanie tabeli KLIENT
        ResultSet wynikZapytania = baza.dml("select ID, PASS, IMIE from KLIENT");
        try{
            int tableID;
            String tablePASS;
            String tableNAME;

            while(wynikZapytania.next()){
                tableID = wynikZapytania.getInt("ID");
                tablePASS = wynikZapytania.getString("PASS");
                tableNAME = wynikZapytania.getString("IMIE");

                //5) Logowanie
                if((usrID==tableID) && (pswd.equals(tablePASS))){
                    polaczenie.setID(tableID);
                    polaczenie.setNAZWA(tableNAME);
                    System.out.println("ZALOGOWANO: "+tableNAME+", ID [" + tableID + "], PASS ["+tablePASS+"]");

                    //6)  Przekazanie połączenia do kolejnego kręgu
                    polaczenie.setKRAG(2);

                    return true;
                }
            }
        }catch (Exception e){}
        return false;
    }

    private void command10(Polaczenie polaczenie, Baza baza) {
        /* OBSŁUGA ŻĄDANIA INFORMACJI O POKOJACH UŻYTKOWNIKA + ID INNYCH UCZESTNIKOW
         *
         */
        int usrID = polaczenie.getID();

        // *) Przeszukanie tabeli KLIENT
        String pokoje = "(select ID_POKOJ from KLIENT_POKOJ where ID_KLIENT = "+usrID+")";

        //Wpierw sprawdzimy ilość rekordów
        String zapytanieILOSC = "select count(*) ILOSC from KLIENT_POKOJ kp, "+pokoje+" p where kp.ID_POKOJ = p.ID_POKOJ AND kp.ID_KLIENT != "+usrID;
        ResultSet wynikZapytaniaILOSC = baza.dml(zapytanieILOSC);

        int iloscRekordow = -1;
        try{
            while(wynikZapytaniaILOSC.next()){
                iloscRekordow = wynikZapytaniaILOSC.getInt("ILOSC");

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if(0 <= iloscRekordow){
            //Wysyłamy komendę 11 - Informacja o ilości nadchadzących paczek
            System.out.println("ILOSĆ REKORDOW: "+ iloscRekordow);
            command11(polaczenie, iloscRekordow);
            try{
                sleep(150);
            }catch (Exception e){;}
            //Teraz odpytamy bazę
            //TODO: Teoretyczna dziura: a co w przypadku kiedy to jest jeden pokój z wyłącznie z jednym użytkownikiem? Przecież tego pokoju zapytanie nie wyłapie!
            String zapytanie = "select kp.ID_POKOJ, kp.ID_KLIENT from KLIENT_POKOJ kp, "+pokoje+" p where kp.ID_POKOJ = p.ID_POKOJ AND kp.ID_KLIENT != "+usrID;
            ResultSet wynikZapytania = baza.dml(zapytanie);
            try{
                int tableID_POKOJ;
                int tableID_KLIENT;
                String tablePOKOJ_NAZWA;
                String tableKLIENT_NAZWA;

                while(wynikZapytania.next()){
                    tableID_POKOJ = wynikZapytania.getInt("ID_POKOJ");
                    tableID_KLIENT = wynikZapytania.getInt("ID_KLIENT");

                    //wysyłamy komendę 12 - para ID_POKOJ oraz ID_KLIENT
                    System.out.println("ID_POKOJ: "+tableID_POKOJ+", ID_KLIENT: "+tableID_KLIENT);
                    command12(polaczenie, tableID_POKOJ, tableID_KLIENT);
                    try{
                        sleep(150);
                    }catch (Exception e){;}
                }
                //Wysłanie komendy 13 - Informacja o końcu wysyłania pakietów pokoju
                command13(polaczenie);


            }catch (Exception e){}
        }
    }

    private void command11(Polaczenie polaczenie, int iloscRekordow){
        polaczenie.sendMessage("R#!*011"+iloscRekordow+"#END");
    }

    private void command12(Polaczenie polaczenie, int tableID_POKOJ, int tableID_KLIENT) {
        polaczenie.sendMessage("R#!*012"+tableID_POKOJ+"!"+tableID_KLIENT+"#END");
    }

    private void command13(Polaczenie polaczenie) {
        polaczenie.sendMessage("R#!*013#END");
    }

    private void command14(Polaczenie polaczenie, String data, Baza baza) {
        /* ŻĄDANIE INFORMACJI O KONKRETNYM UŻYTKOWNIKU
         * 1) SPRAWDZANIE POPRAWNOŚCI DANYCH
         * 2) JEŻELI WYSTĄPI BŁĄD, WYSYŁAMY KOMUNIKAT 1 - ERROR
         */

        int ID_KLIENT, DATA_TYPE;

        //TODO: zrobić takie samo sprawdzanie poprawności danych w komendzie 9
        //1) SPRAWDZANIE POPRAWNOŚCI DANYCH
        try{
            String[] dane = cutTheData(data);
            ID_KLIENT = Integer.parseInt(dane[0]);
            DATA_TYPE = Integer.parseInt(dane[1]);
        }catch (Exception e){
            e.printStackTrace();
            // 2) JEŻELI WYSTĄPI BŁĄD, WYSYŁAMY KOMUNIKAT 1 - ERROR
            // WYSYPIE SIĘ W NASTĘPUJĄCYCH PRZYPADKACH: [], [!], [0!], [!0]
            // WIĘCEJ NIE TESTOWANO
            command1(polaczenie);
            return;
        }

        //3) SPRAWDZENIE TYPU POLECENIA - O KTÓRE DANE Z TABELI CHODZI
        String kolumna = "";
        switch (DATA_TYPE){
            case 0:
                kolumna = "IMIE";
                break;
        }

        //4) Przeszukanie tabeli KLIENT
        ResultSet wynikZapytania = baza.dml("select "+kolumna+" from KLIENT where ID = "+ID_KLIENT);
        try{
            String tableDANE;
            while(wynikZapytania.next()){
                tableDANE = wynikZapytania.getString(kolumna);
                //WYSŁANIE DANYCH - KOMENDA 15
                command15(polaczenie, ID_KLIENT, DATA_TYPE, tableDANE);
            }
        }catch (Exception e){
            e.printStackTrace();
            // JEŻELI WYSTĄPI BŁĄD
            command1(polaczenie);
        }
    }

    private void command15(Polaczenie polaczenie, int idKlient, int dataType, String desiredData) {
        polaczenie.sendMessage("R#!*015"+idKlient+"!"+dataType+"!"+desiredData+"#END");
    }

    private void command16(Polaczenie polaczenie, String data, Baza baza) {
        //System.out.println("KOMENDA 16!");

        int usrID = polaczenie.getID();
        int idPokoju;

        try{
            idPokoju = Integer.parseInt(data);
            //System.out.println(idPokoju);
        }catch (Exception e){
            command1(polaczenie);
            return;
        }

        String zapytanie = "select ID_UCZESTNIKA,DATA,TRESC from ROZMOWA where ID_POKOJU = "+idPokoju;
        ResultSet wynikZapytania = baza.dml(zapytanie);

        try{
            while(wynikZapytania.next()){
                int idUczestnika = wynikZapytania.getInt("ID_UCZESTNIKA");
                String dataWyslania = wynikZapytania.getString("DATA");
                String trescRozmowy = wynikZapytania.getString("TRESC");

                System.out.println("ID_POKOJU: " + idPokoju +
                        "; ID_UCZESTNIKA: " + idUczestnika +
                        "; Data: " + dataWyslania +
                        "; TREŚĆ: " + trescRozmowy
                );
                command19(polaczenie, idPokoju, idUczestnika, dataWyslania, trescRozmowy);
                sleep(150);
            }
            command20(polaczenie);
            sleep(150);
        }catch (Exception e) {
            e.printStackTrace();
            command1(polaczenie);
            try {
                sleep(150);
            }
            catch (Exception E){}
        }
    }

    private void command17(Polaczenie polaczenie, String data, Baza baza) {
        int usrID = polaczenie.getID();

        String zapytanie = "select count(*) ILOSC from KLIENT_POKOJ where ID_KLIENT = " + usrID;
        ResultSet wynikZapytaniaILOSC = baza.dml(zapytanie);

        int iloscRekordow = -1;
        try{
            while(wynikZapytaniaILOSC.next()){
                iloscRekordow = wynikZapytaniaILOSC.getInt("ILOSC");
                System.out.println("Ilość pokoi użytkownika "+usrID+" :"+iloscRekordow);
                command18(polaczenie, iloscRekordow);
            }
        }catch (Exception e){
            e.printStackTrace();
            command1(polaczenie);
        }

    }

    private void command18(Polaczenie polaczenie, int iloscRekordow) {
        polaczenie.sendMessage("R#!*018"+iloscRekordow+"#END");
    }

    private void command19(Polaczenie polaczenie, int idPokoju, int idUczestnika, String dataWyslania, String trescRozmowy) {
        polaczenie.sendMessage("R#!*019"+idPokoju+"!"+idUczestnika+"!"+dataWyslania+"!"+trescRozmowy+"#END");
    }

    private void command20(Polaczenie polaczenie) {
        polaczenie.sendMessage("R#!*020#END");
    }

    private void command21(Polaczenie polaczenie){
        polaczenie.setKRAG(3);
        command0(polaczenie);
    }

    private void command22(Polaczenie polaczenie, String data, Baza baza, Polaczenie[] listaPolaczen) {
        /*
         * Komenda 22 - żądanie przekierowania wiadomości do uczestników pokoju.
         * 1) Pobranie informacji z DATA i sprawdzenie proprawności danych (czy w ogóle są i typ).
         * 2) Odpytanie bazy na temat uczestników danego pokoju.
         * 3) Odczytanie pojedynczego rekordu.
         * 4) Przesłanie wiadomości do uczestników pokoju.
         */
        System.out.println("KOMENDA 22, KRĄG "+polaczenie.getKRAG());
        int ID_POKOJU;
        String dataWYSLANIA, TRESC;

        // 1) Pobranie informacji z DATA i sprawdzenie proprawności danych (czy w ogóle są i typ)
        try{
            String[] dane = cutTheData(data);
            ID_POKOJU = Integer.parseInt(dane[0]);
            dataWYSLANIA = dane[1];
            TRESC = dane[2];
        }catch (Exception e){
            command1(polaczenie);
            return;
        }

        // 2) Odpytanie bazy na temat uczestników danego pokoju.
        String zapytanie = "select ID_KLIENT from KLIENT_POKOJ where ID_POKOJ = "+ID_POKOJU;
        ResultSet wynikZapytania = baza.dml(zapytanie);

        try{
            while(wynikZapytania.next()){
                // 3) Odczytanie pojedynczego rekordu.
                int ID_KLIENT = wynikZapytania.getInt("ID_KLIENT");
                System.out.println("ID_POKOJU: " + ID_POKOJU +
                        "; ID_KLIENT: " + ID_KLIENT
                );

                // 4) Przesłanie wiadomości do uczestników pokoju.
                // TODO: Czy sprawdzać przynależność do kręgu?
                for(Polaczenie pol:listaPolaczen){
                    if(pol != null){
                        // Sprawdzamy czy znaleźliśmy odpowiedniego klienta
                        if(pol.getID() == ID_KLIENT){
                            // Oraz czy nie wyślemy do tego, od którego przyszło żądanie (ten sam klient
                            // może się przecież logować z wielu urządzeń)
                            if(pol != polaczenie){
                                command23(pol, ID_KLIENT, ID_POKOJU, dataWYSLANIA, TRESC);
                            }
                        }
                    }

                }
            }

            command0(polaczenie);
        }catch (Exception e){
            e.printStackTrace();
            command1(polaczenie);
        }

    }

    private void command23(Polaczenie pol, int id_klient, int id_pokoju, String dataWYSLANIA, String tresc) {
        pol.sendMessage("R#!*023"+id_klient+"!"+id_pokoju+"!"+dataWYSLANIA+"!"+tresc+"#END");
    }

}
