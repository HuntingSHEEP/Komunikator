package klient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.Charset;

class OdbiorK extends Thread
{
    String str;
    Socket sock;
    BufferedReader inp;
    InputStream is;
    boolean isRunning = true;
    NadawajK watekNadajacy;


    public OdbiorK(Socket sock) throws IOException
    {
        this.sock=sock;
        this.inp=new BufferedReader(new InputStreamReader(sock.getInputStream()));
        this.is = sock.getInputStream();

    }

    public void setWatekNadajacy(NadawajK nadajacy){
        this.watekNadajacy = nadajacy;
    }

    public void run()
    {

        try{
            while (isRunning){
                //komunikacja - czytanie danych ze strumienia

                byte[] inputData = new byte[1024];
                int result = is.read(inputData, 0, is.available());

                //str=inp.readLine();



                if(result > 0){
                    str = new String(inputData);
                    str = str.substring(0, result - 1);
                    System.out.println("<Nadeszlo:> result [" + result + "] " + str);
                    if(str.equalsIgnoreCase("exit")){

                        killNadawaj();
                        System.out.println("OEXIT");
                        break;
                    }

                }
                sleep(100);
            }

        }catch(Exception e){System.out.println("Coś się skichało, odbiór. "+e);}

    }

    private void killNadawaj() {
        watekNadajacy.killME();
    }


    public void killME(){
        // DIE!!!
        isRunning = false;
    }
}
