package serwer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class Odbior extends Thread
{
    String str;
    Socket sock;
    BufferedReader inp;



    public Odbior(Socket sock) throws IOException
    {
        this.sock=sock;
        this.inp=new BufferedReader(new InputStreamReader(sock.getInputStream()));
    }

    public void run()
    {

        try{
            while (true){
                //komunikacja - czytanie danych ze strumienia

                str=inp.readLine();
                System.out.println("<Nadeszlo:> " + str);

                if(str.equalsIgnoreCase("exit")){

                    System.out.println("OEXIT");
                    break;
                }
            }

        }catch(Exception e){System.out.println("Coś się skichało, odbiór. "+e);}

    }
}
