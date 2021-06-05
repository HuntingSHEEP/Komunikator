package serwer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

class Odbior extends Thread
{
    String str;
    Socket sock;
    BufferedReader inp;
    InputStream is;
    boolean isRunning = true;
    Nadawaj watekNadajacy;
    private String bufor;



    public Odbior(Socket sock) throws IOException
    {
        this.sock=sock;
        this.inp=new BufferedReader(new InputStreamReader(sock.getInputStream()));
        this.is = sock.getInputStream();
        this.bufor = "";
    }

    public void setWatekNadajacy(Nadawaj nadajacy){
        this.watekNadajacy = nadajacy;
    }

    public void killME(){
        // DIE!!!
        isRunning = false;
    }

    public void run()
    {

        try{
            while (isRunning){
                //komunikacja - czytanie danych ze strumienia
                byte[] inputData = new byte[1024];
                int result = is.read(inputData, 0, is.available());

                if(result > 0){
                    str = new String(inputData).substring(0, result - 1);

                    this.bufor += str;
                    //System.out.println("ZAWARTOŚĆ BUFORA: "+bufor);

                    if(str.equalsIgnoreCase("exit")){
                        watekNadajacy.killME();
                        System.out.println("OEXIT");
                        break;
                    }
                }
                sleep(100);
            }

        }catch(Exception e){System.out.println("Coś się skichało, odbiór. "+e);}

    }

    public boolean newMessage() {
        return bufor.length() > 0;
    }


    public String getMessage() {
        //TODO: WYSYŁANIE JEDNEJ WIADOMOŚCI CZYLI OD [R#!*] DO [#END]
        //System.out.println("Przed wysłaniem zawartości bufora: " + bufor);
        String dane = bufor;
        bufor = "";
        //System.out.println("Bufor po skopiowaniu i wyzerowanieu: " + bufor);
        //System.out.println("Wysłano: " + dane);
        return  dane;
    }
}
