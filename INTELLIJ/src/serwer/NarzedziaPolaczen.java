package serwer;

import baza.Baza;

import java.sql.ResultSet;

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
                    //System.out.println("Numer komendy: "+ numer);
                    state = true;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return state;
    }

    public void responseRequest(Polaczenie polaczenie, String msg, Baza baza) {
        /*-----REAGOWANIE NA PRZYSŁANE ŻĄÐANIA
         *
         */

        int numer = Integer.parseInt(msg.substring(4, 7));
        String data = msg.substring(7, msg.length()-4);

        System.out.println("Przyjęto polecenie "+numer+", DATA: ["+data+"]");

        switch (numer){
            case 9:
                boolean zalogowano = command9(polaczenie, data, baza);
                if(zalogowano){
                    command0(polaczenie);
                }else{
                    command1(polaczenie);
                }
                break;

            case 10:
                command10(polaczenie, baza);
                break;
        }
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
        //OBSŁUGA ŻĄDANIA INFORMACJI O POKOJACH UŻYTKOWNIKA

        System.out.println("ŻĄDANIE 10, KRĄG "+polaczenie.getKRAG());
    }

}
