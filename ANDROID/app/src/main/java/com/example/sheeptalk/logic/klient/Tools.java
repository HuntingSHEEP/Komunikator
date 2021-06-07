package com.example.sheeptalk.logic.klient;

import android.widget.Switch;

public class Tools {
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
        System.out.println(state);
        return state;
    }

    public String[] cutTheData(String data){
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
                output[index] = temporary;
                temporary = "";
                System.out.println("index "+index+" string "+output[index]);
                index++;
            }else{
                temporary += data.charAt(i);
            }
            if(i == data.length()-1){
                output[index] = temporary;
                temporary = "";
                System.out.println("index "+index+" string "+output[index]);
                index++;
            }
        }


        return output;
    }

    public Boolean handleRequest(Polaczenie polaczenie,String msg){
        int numer = Integer.parseInt(msg.substring(4, 7));
        String data = msg.substring(7, msg.length()-4);

        switch(numer){
            case 0: {
                return true;
            }
            case 1: {
                return false;
            }
            default: {
                return false;
            }

        }

    }

    public  String numberCutout(Polaczenie polaczenie, String msg){
        return msg.substring(7, msg.length()-4);
    }

}
